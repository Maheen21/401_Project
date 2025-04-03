package com.dishcraft.security;

import com.dishcraft.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

/*
 * JwtUtil
 *
 * Description:
 *   Utility class for generating and validating JWT tokens.
 *   - Generates tokens using a secret key and expiration time.
 *   - Extracts claims such as username from tokens.
 */
@Component
public class JwtUtil {
    @Value("${jwt.secret}")//need to set up the proper secret key
    private String SECRET_KEY;

    // Token expiration time (e.g., 10 hours)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    // Made public to allow access from JwtAuthenticationFilter
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    // Updated to accept Role enum
    public String generateToken(UserDetails userDetails, Role role) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), role);
    }

    // Updated to accept Role enum
    private String createToken(Map<String, Object> claims, String subject, Role role) {
        // Store role name in token
        claims.put("role", role.name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
    
    // Add method to extract role from token
    public Role extractRole(String token) {
        String roleName = extractClaim(token, claims -> claims.get("role", String.class));
        if (roleName != null) {
            try {
                return Role.valueOf(roleName);
            } catch (IllegalArgumentException e) {
                return Role.USER; // Default role if parsing fails
            }
        }
        return Role.USER; // Default role if no role in token
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
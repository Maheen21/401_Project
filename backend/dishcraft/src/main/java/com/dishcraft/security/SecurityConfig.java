package com.dishcraft.security;

import com.dishcraft.config.CorsConfig; // if needed
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;

/*
 * SecurityConfig
 *
 * Description:
 *   Configures Spring Security for JWT-based authentication.
 *   - Disables session creation (stateless authentication).
 *   - Configures public endpoints and secures others.
 *   - Registers the JwtAuthenticationFilter to validate bearer tokens.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService; // Now using MyUserDetailsService

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Enable CORS so Spring Security uses the CorsConfig
            .cors(Customizer.withDefaults())
            // Disable CSRF as we're using JWT
            .csrf(csrf -> csrf.disable())
            // Use stateless session management
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Configure endpoint security: permit auth endpoints, secure others
            .authorizeHttpRequests(auth -> auth
                // public endpoints
                // Swagger and API documentation endpoints
                .requestMatchers(
                    "/api/auth/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**"
                ).permitAll()
                
                // public API endpoints for get requests
                .requestMatchers(HttpMethod.GET, "/api/recipes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/ingredients/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/dietary-restrictions/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                
                // api endpoints for user registration and login
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/root/**").hasRole("ROOT")
                
                // any loged-in user can access these endpoints
                .requestMatchers(HttpMethod.DELETE, "/api/favorites/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/user-favorite-recipes/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/dietary-restrictions/current-user/**").authenticated()
                
                // authenticated endpoints for user and recipe management which require admin or root role
                .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN", "ROOT")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ADMIN", "ROOT")
                
                // any other request requires authentication
                .anyRequest().authenticated()

            )
            // Add our JWT filter before the UsernamePasswordAuthenticationFilter
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /*
     * Expose AuthenticationManager Bean
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
 * JwtAuthenticationFilter
 *
 * Description:
 *   A filter to extract and validate JWT tokens from the Authorization header.
 */
class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authentication -> authentication);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7).trim();
            if (!token.isEmpty()) {
                String username = jwtUtil.extractUsername(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    
                    if (jwtUtil.validateToken(token, userDetails)) {
                        // Use the authorities directly from UserDetails
                        // This will now use the MyUserDetails.getAuthorities() implementation
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
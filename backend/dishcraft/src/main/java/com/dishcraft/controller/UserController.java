package com.dishcraft.controller;

import com.dishcraft.model.Role;
import com.dishcraft.model.User;
import com.dishcraft.service.UserService;
import com.dishcraft.dto.LoginRequest;
import com.dishcraft.dto.JwtResponse;
import com.dishcraft.dto.RefreshTokenRequest;
import com.dishcraft.dto.UserResponseDto;
import com.dishcraft.dto.UserRequestDto;

import com.dishcraft.security.MyUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// Import Spring Security classes.
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import com.dishcraft.security.MyUserDetailsService;
import com.dishcraft.security.JwtUtil;

/*
 * AuthController
 *
 * Description:
 *   This controller handles JWT-based user authentication.
 *
 * Endpoints:
 *   - POST /api/auth/register
 *       • Input: User details (username, email, password, etc.)
 *       • Output: Created user information or success message.
 *
 *   - POST /api/auth/login
 *       • Input: Credentials (username/email and password)
 *       • Output: A JWT access token (and optionally a refresh token).
 *
 *   - POST /api/auth/refresh
 *       • Input: A valid refresh token.
 *       • Output: A new JWT access token.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    
    // Constructor-based dependency injection of UserService.
    public UserController(
            UserService userService,
            AuthenticationManager authenticationManager,
            MyUserDetailsService myUserDetailsService,
            JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
    }
    
    /**
     * User Registration Endpoint
     * URL: POST /api/auth/register
     * Input: JSON representing a new User.
     * Output: The created User or a success message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDto dto) {
        // Typically, you would validate data and encode the password here.
        User createdUser = userService.createUser(dto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    /**
     * User Login Endpoint
     * URL: POST /api/auth/login
     * Input: JSON with username/email and password.
     * Output: A JWT token and user role in the response body.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(), loginRequest.getPassword());

            authenticationManager.authenticate(authToken);
            
            MyUserDetails myUser = (MyUserDetails) myUserDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());
            Role role = myUser.getRole();
            String token = jwtUtil.generateToken(myUser, role);
            
            // return the JWT token and user role in the response body
            return ResponseEntity.ok(new JwtResponse(token, role));
        } catch (Exception ex) {
            // Log exception details for troubleshooting.
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body("Authentication failed: " + ex.getMessage());
        }
    }

    /**
     * Token Refresh Endpoint
     * URL: POST /api/auth/refresh
     * Input: JSON with a refresh token.
     * Output: A new JWT token and user role.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            // Extract username from the refresh token
            String refreshToken = refreshTokenRequest.getRefreshToken();
            String username = jwtUtil.extractUsername(refreshToken);
            
            if (username != null) {
                // Load user details to get role
                MyUserDetails myUser = (MyUserDetails) myUserDetailsService.loadUserByUsername(username);
                
                if (jwtUtil.validateToken(refreshToken, myUser)) {
                    // Generate new token
                    Role role = myUser.getRole();
                    String newToken = jwtUtil.generateToken(myUser, role);
                    
                    // Return new token with role
                    return ResponseEntity.ok(new JwtResponse(newToken, role));
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error processing refresh token: " + ex.getMessage());
        }
    }

    @GetMapping("/users")//For Testing
    public ResponseEntity<?> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
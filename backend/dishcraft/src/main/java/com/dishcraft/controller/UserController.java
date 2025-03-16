package com.dishcraft.controller;

import com.dishcraft.model.User;
import com.dishcraft.service.UserService;
import com.dishcraft.dto.LoginRequest;
import com.dishcraft.dto.JwtResponse;
import com.dishcraft.dto.RefreshTokenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Import Spring Security classes.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import com.dishcraft.security.MyUserDetailsService;
import com.dishcraft.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

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
public class UserController {
    
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    
    // Constructor-based dependency injection of UserService.
    public UserController(
            UserService userService,
            AuthenticationManager authenticationManager,
            MyUserDetailsService myUserDetailsService,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder) {  
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.myUserDetailsService = myUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;  
    }
    
    /**
     * Registers a new user in the system.
     *
     * @param user The user object containing username, email, password, etc.
     * @return A ResponseEntity containing the created User, or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Encode the raw password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param loginRequest The DTO object containing username/email and password.
     * @return A ResponseEntity containing a JWT token if authentication succeeds, or a 403 error otherwise.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
        
            authenticationManager.authenticate(authToken);
            
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequest.getUsernameOrEmail());
            String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception ex) {
            // Log the exception details for troubleshooting.
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Authentication failed: " + ex.getMessage());
        }
    }
    
    /**
    * Generates a new JWT token based on a valid refresh token.
    *
    * @param refreshTokenRequest The DTO object containing a valid refresh token.
    * @return A ResponseEntity containing a new JWT token.
    */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = jwtUtil.extractUsername(refreshToken);

        // Validate the refresh token:
        if (username != null && jwtUtil.validateToken(refreshToken, myUserDetailsService.loadUserByUsername(username))) {
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            String newToken = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(newToken));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired refresh token.");
        }
    }

    /**
     * Retrieves all registered users in the system.
     *
     * @return A ResponseEntity containing the list of all users.
     */
    @GetMapping("/users")//For Testing
    public ResponseEntity<?> getAllUsers() {
        var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Add this method inside the UserController class:

    /**
     * Retrieves a new JWT token for a given username.
     * <p><b>Warning:</b> This endpoint bypasses authentication and is meant for testing only.</p>
     *
     * @param username the username to generate the token for.
     * @return a ResponseEntity containing a JWT token.
     */
    @GetMapping("/token/{username}")
    public ResponseEntity<?> getTokenForUser(@PathVariable String username) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
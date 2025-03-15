package com.dishcraft.controller;

import com.dishcraft.model.User;
import com.dishcraft.service.UserService;
import com.dishcraft.dto.LoginRequest;
import com.dishcraft.dto.JwtResponse;
import com.dishcraft.dto.RefreshTokenRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
    // Constructor-based dependency injection of UserService.
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * User Registration Endpoint
     * URL: POST /api/auth/register
     * Input: JSON representing a new User.
     * Output: The created User or a success message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Typically, you would validate data and encode the password here.
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    
    /**
     * User Login Endpoint
     * URL: POST /api/auth/login
     * Input: JSON with username/email and password.
     * Output: A JWT token in the response body.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        // Here, you would normally authenticate the user using UserService.
        // On successful authentication, generate a JWT token.
        // The following token generation is a placeholder.
        String token = "dummy-jwt-token";
        return ResponseEntity.ok(new JwtResponse(token));
    }
    
    /**
     * Token Refresh Endpoint
     * URL: POST /api/auth/refresh
     * Input: JSON with a refresh token.
     * Output: A new JWT token.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        // Normally, validate the refresh token and generate a new JWT.
        // The code below is a simplified placeholder.
        String newToken = "dummy-new-jwt-token";
        return ResponseEntity.ok(new JwtResponse(newToken));
    }
}
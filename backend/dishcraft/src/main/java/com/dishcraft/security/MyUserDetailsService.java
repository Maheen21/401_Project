package com.dishcraft.security;

import com.dishcraft.model.User;
import com.dishcraft.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

/*
 * MyUserDetailsService
 *
 * Description:
 *   Implements Spring Security's UserDetailsService to load user-specific data.
 *   It retrieves a User from the database using UserRepository and converts
 *   it into a UserDetails object that Spring Security uses for authentication.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor-based dependency injection of UserRepository.
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username.
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated UserDetails object.
     * @throws UsernameNotFoundException if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user from the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        
        // Create and return a UserDetails object
        // Here we use Spring Security's built-in User class; roles or authorities can be added as needed.
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()  // In a complete implementation, populate user authorities if available.
        );
    }
}
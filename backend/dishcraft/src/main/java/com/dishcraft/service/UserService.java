package com.dishcraft.service;

import com.dishcraft.model.User;
import com.dishcraft.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*
 * UserService
 * Description:
 *   This service handles business logic related to user operations.
 *   It acts as an intermediary between the controllers (handling HTTP requests)
 *   and the UserRepository (which manages database interactions for User entities).
 * Role:
 *   - Retrieve user accounts.
 *   - Create, update, and delete users.
 *   - Provide additional business validations and operations if needed.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * Retrieve all users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*
     * Retrieve a user by its unique ID.
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /*
     * Create a new user, encrypting the password.
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /*
     * Update an existing user by ID.
     */
    public Optional<User> updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
            .map(user -> {
                user.setUsername(updatedUser.getUsername());
                user.setEmail(updatedUser.getEmail());
                // If updating password, encrypt it
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                return userRepository.save(user);
            });
    }

    /*
     * Delete a user by ID.
     */
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
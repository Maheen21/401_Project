package com.dishcraft.service;

import com.dishcraft.dto.UserResponseDto;
import com.dishcraft.model.User;
import com.dishcraft.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import com.dishcraft.dto.UserRequestDto;

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
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(user -> {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setId(user.getId());
                userResponseDto.setUsername(user.getUsername());
                userResponseDto.setEmail(user.getEmail());
                userResponseDto.setRole(user.getRole());
                // Include role if needed
                return userResponseDto;
            })
            .collect(Collectors.toList());
    }

    /*
     * Retrieve a user by its unique ID.
     */
    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id)
            .map(user -> {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setId(user.getId());
                userResponseDto.setUsername(user.getUsername());
                userResponseDto.setEmail(user.getEmail());
                userResponseDto.setRole(user.getRole());
                // Include role if needed
                return userResponseDto;
            });
    }

    /*
     * Create a new user, encrypting the password.
     */
    public User createUser(UserRequestDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        // Include role if needed
        return userRepository.save(user);
    }

    /*
     * Update an existing user by ID.
     */
    public Optional<UserResponseDto> updateUser(Long id, UserRequestDto dto) {
        return userRepository.findById(id)
            .map(user -> {
                user.setUsername(dto.getUsername());
                user.setEmail(dto.getEmail());
                
                if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                }
    
                User updatedUser = userRepository.save(user);
    
                UserResponseDto responseDto = new UserResponseDto();
                responseDto.setId(updatedUser.getId());
                responseDto.setUsername(updatedUser.getUsername());
                responseDto.setEmail(updatedUser.getEmail());
    
                return responseDto;
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
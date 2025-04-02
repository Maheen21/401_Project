package com.dishcraft.service;

import com.dishcraft.dto.UserResponseDto;
import com.dishcraft.dto.UserRequestDto;
import com.dishcraft.mapper.UserMapperUtil;
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
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapperUtil.toResponseDtoList(users);
    }

    /*
     * Retrieve a user by its unique ID.
     */
    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapperUtil::toResponseDto);
    }

    /*
     * Create a new user, encrypting the password.
     */
    public User createUser(UserRequestDto dto) {
        User user = UserMapperUtil.toEntity(dto, passwordEncoder);
        return userRepository.save(user);
    }

    /*
     * Update an existing user by ID.
     */
    public Optional<UserResponseDto> updateUser(Long id, UserRequestDto dto) {
        return userRepository.findById(id)
            .map(user -> {
                UserMapperUtil.updateEntityFromDto(dto, user, passwordEncoder);
                User updatedUser = userRepository.save(user);
                return UserMapperUtil.toResponseDto(updatedUser);
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
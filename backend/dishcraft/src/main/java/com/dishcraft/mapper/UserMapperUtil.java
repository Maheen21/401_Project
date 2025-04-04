package com.dishcraft.mapper;

import com.dishcraft.dto.UserRequestDto;
import com.dishcraft.dto.UserResponseDto;
import com.dishcraft.model.User;
import com.dishcraft.model.Role;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapperUtil {

    public static UserResponseDto toResponseDto(User entity) {
        if (entity == null) return null;

        UserResponseDto dto = new UserResponseDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole());
        
        return dto;
    }

    public static List<UserResponseDto> toResponseDtoList(List<User> entities) {
        if (entities == null) return new ArrayList<>();
        
        return entities.stream()
                .map(UserMapperUtil::toResponseDto)
                .collect(Collectors.toList());
    }

    public static User toEntity(UserRequestDto dto, PasswordEncoder passwordEncoder) {
        if (dto == null) return null;

        User entity = new User();
        // Do not set ID - always let the database generate it with @GeneratedValue
        // Ignoring any user-provided ID for security reasons
        
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        
        // save password with encryption
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        // Always set role to USER for new registrations regardless of what was provided
        // This ensures users cannot give themselves admin privileges
        entity.setRole(Role.USER);
        
        return entity;
    }

    public static void updateEntityFromDto(UserRequestDto dto, User entity, PasswordEncoder passwordEncoder) {
        if (dto == null || entity == null) return;

        if (dto.getUsername() != null) {
            entity.setUsername(dto.getUsername());
        }
        
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        
        // update password only if it's provided
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        // Note: Role updates should be handled by admin-specific endpoints with proper authorization
        // Do not update role from standard user DTO to prevent privilege escalation
    }
}
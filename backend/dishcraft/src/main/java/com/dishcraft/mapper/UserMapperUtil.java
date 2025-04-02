package com.dishcraft.mapper;

import com.dishcraft.dto.UserRequestDto;
import com.dishcraft.dto.UserResponseDto;
import com.dishcraft.model.User;

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
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        
        // 패스워드는 암호화하여 저장
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        entity.setRole(dto.getRole());
        
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
        
        if (dto.getRole() != null) {
            entity.setRole(dto.getRole());
        }
    }
}
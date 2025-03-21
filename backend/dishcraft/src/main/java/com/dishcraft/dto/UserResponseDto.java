package com.dishcraft.dto;

import lombok.Data;
// UserResponseDto.java
@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String role;  // Include role if needed
}

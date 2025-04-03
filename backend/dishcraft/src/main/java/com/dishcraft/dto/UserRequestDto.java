package com.dishcraft.dto;

import com.dishcraft.model.Role;
import lombok.Data;

/*
 * UserDto
 *
 * Description:
 *   A data transfer object for transferring essential user information.
 *   Use this instead of exposing the full User entity.
 */
@Data
public class UserRequestDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Role role;  // Changed from String to Role enum
}
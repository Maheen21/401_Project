package com.dishcraft.dto;

import lombok.Data;

/*
 * UserDto
 *
 * Description:
 *   A data transfer object for transferring essential user information.
 *   Use this instead of exposing the full User entity.
 */
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    // Include any additional fields you want to expose (exclude sensitive info)
}
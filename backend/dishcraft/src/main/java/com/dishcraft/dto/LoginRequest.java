package com.dishcraft.dto;

import lombok.Data;

/*
 * LoginRequest DTO
 *
 * Description:
 *   Encapsulates the login credentials submitted by the client.
 */
@Data
public class LoginRequest {
    private String usernameOrEmail;
    private String password;
}
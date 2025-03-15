package com.dishcraft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * JwtResponse DTO
 *
 * Description:
 *   Contains the JWT token returned upon successful authentication.
 */
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
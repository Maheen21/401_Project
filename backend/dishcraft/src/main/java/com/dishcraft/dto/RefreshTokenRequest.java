package com.dishcraft.dto;

import lombok.Data;

/*
 * RefreshTokenRequest DTO
 *
 * Description:
 *   Contains the refresh token used to obtain a new JWT access token.
 */
@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
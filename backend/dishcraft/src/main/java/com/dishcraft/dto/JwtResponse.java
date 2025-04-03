package com.dishcraft.dto;

import com.dishcraft.model.Role;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * JwtResponse DTO
 *
 * Description:
 *   Contains the JWT token returned upon successful authentication.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JwtResponse {
    @JsonProperty
    private String token;
    @JsonProperty
    private String type = "Bearer";
    @JsonProperty
    private Role role;

    public JwtResponse(String accessToken, Role role) {
        this.token = accessToken;
        this.role = role;
    }
}
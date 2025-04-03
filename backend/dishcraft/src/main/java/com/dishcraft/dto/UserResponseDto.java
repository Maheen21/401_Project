package com.dishcraft.dto;

import com.dishcraft.model.Role;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// UserResponseDto.java
@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserResponseDto {
    @JsonProperty
    private Long id;
    
    @JsonProperty
    private String username;
    
    @JsonProperty
    private String email;
    
    @JsonProperty
    private Role role;  // Changed from String to Role enum
}

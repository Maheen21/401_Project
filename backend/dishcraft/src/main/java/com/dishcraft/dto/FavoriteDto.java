package com.dishcraft.dto;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Favorite entity.
 */
@Data
public class FavoriteDto {
    private Long id;
    private Long userId;
    private Long recipeId;
    private String recipeName;
    private String recipeImageUrl;
    private LocalDateTime createdAt;
}
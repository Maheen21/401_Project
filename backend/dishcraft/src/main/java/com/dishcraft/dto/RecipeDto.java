package com.dishcraft.dto;

import lombok.Data;
import java.util.List;

/*
 * Recipe DTO
 *
 * Description:
 *   Contains the recipe information.
 */
@Data
public class RecipeDto {
    private Long id;
    private String name;
    private String description;
    private String instruction;
    private Integer cookingTime;
    private List<RecipeIngredientDto> recipeIngredients;
    private String imageUrl;
}
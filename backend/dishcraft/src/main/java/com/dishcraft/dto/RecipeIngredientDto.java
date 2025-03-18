package com.dishcraft.dto;

import lombok.Data;

/*
 * RecipeIngredient DTO
 *
 * Description:
 *   Contains the recipe ingredient information.
 */
@Data
public class RecipeIngredientDto {
    private Long id;
    private String name;
    private String quantity;
    private String unit;
    private Boolean isRequired;    
}

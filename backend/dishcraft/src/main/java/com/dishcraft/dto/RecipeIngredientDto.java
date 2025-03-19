package com.dishcraft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * RecipeIngredient DTO
 *
 * Description:
 *   Contains the recipe ingredient information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RecipeIngredientDto {
    private Long id;
    private String name;
    private Double quantity;
    private String unit;
    private Boolean isRequired;    
}

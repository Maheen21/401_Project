package com.dishcraft.dto;

import lombok.Data;
import java.util.List;
/*
 * Ingredient DTO
 */
@Data
public class IngredientDto {
    private Long id;
    private String name;
    private String category;
    private String description;
    private String rank;
    private List<DietaryRestrictionDto> dietaryRestrictions;    
}

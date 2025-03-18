package com.dishcraft.service;

import com.dishcraft.dto.IngredientDto;
import java.util.List;

/**
 * IngredientService defines the business logic for managing ingredients.
 */
public interface IngredientService {

    /**
     * Retrieve the ingredient details by its ID.
     *
     * @param id the unique identifier of the ingredient
     * @return the ingredient details as IngredientDto
     */
    IngredientDto getIngredientById(Long id);

    /**
     * Search for ingredients by their name. This method performs a case-insensitive search
     * and returns a list of matching ingredients.
     *
     * @param name the search keyword for the ingredient name
     * @return a list of IngredientDto that match the search criteria
     */
    List<IngredientDto> searchIngredientsByName(String name);

    // Additional methods can be added for filtering by category, dietary restrictions, etc.
}

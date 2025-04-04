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

    /*
     * Retrieve all ingredients from the database.
     * @return a list of all ingredients as IngredientDto
     */
    List<IngredientDto> getAllIngredients();

    /**
     * Create a new ingredient in the database.
     *
     * @param ingredientDto the ingredient details to be created
     * @return the created ingredient details as IngredientDto
     */
    IngredientDto createIngredient(IngredientDto ingredientDto);

    /**
     * Update the ingredient details by its ID.
     *
     * @param id the unique identifier of the ingredient
     * @param ingredientDto the updated ingredient details
     * @return the updated ingredient details as IngredientDto
     */
    IngredientDto updateIngredient(Long id, IngredientDto ingredientDto);

    /**
     * Delete an ingredient by its ID.
     *
     * @param id the unique identifier of the ingredient
     */
    void deleteIngredient(Long id);
    
    /**
     * Filter ingredients by excluding those with the specified dietary restrictions.
     * Returns ingredients that do NOT have any of the specified dietary restrictions.
     *
     * @param dietaryRestrictionIds the list of dietary restriction IDs to exclude
     * @return a list of ingredients that are NOT associated with the specified dietary restrictions
     */
    List<IngredientDto> filterByDietaryRestrictions(List<Long> dietaryRestrictionIds);
}

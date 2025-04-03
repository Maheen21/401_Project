package com.dishcraft.service;

import com.dishcraft.dto.FavoriteDto;
import com.dishcraft.dto.RecipeDto;

import java.util.List;

/**
 * Service interface for managing favorite recipes.
 */
public interface FavoriteService {

    /**
     * Add a recipe to the current user's favorites.
     *
     * @param recipeId the ID of the recipe to add to favorites
     * @return the created favorite DTO
     */
    FavoriteDto addFavorite(Long recipeId);
    
    /**
     * Remove a recipe from the current user's favorites.
     *
     * @param recipeId the ID of the recipe to remove from favorites
     */
    void removeFavorite(Long recipeId);
    
    /**
     * Get all favorite recipes for the current user.
     *
     * @return a list of the user's favorite recipes
     */
    List<FavoriteDto> getCurrentUserFavorites();
    
    /**
     * Check if a recipe is in the current user's favorites.
     *
     * @param recipeId the ID of the recipe to check
     * @return true if the recipe is in the user's favorites, false otherwise
     */
    boolean isFavorite(Long recipeId);
    
    /**
     * Get all favorite recipes for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of the user's favorite recipes
     */
    List<FavoriteDto> getUserFavorites(Long userId);
    
    /**
     * Get all recipes favorited by the current user.
     *
     * @return a list of recipe DTOs
     */
    List<RecipeDto> getFavoriteRecipes();
}
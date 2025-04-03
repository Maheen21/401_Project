package com.dishcraft.controller;

import com.dishcraft.dto.FavoriteDto;
import com.dishcraft.dto.RecipeDto;
import com.dishcraft.service.FavoriteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for managing user's favorite recipes.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Add a recipe to the current user's favorites.
     *
     * @param recipeId the ID of the recipe to add
     * @return the created favorite
     */
    @Operation(summary = "Add recipe to favorites", 
               description = "Add a recipe to the current user's favorites",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Recipe successfully added to favorites"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Recipe not found"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping
    public ResponseEntity<FavoriteDto> addFavorite(@RequestBody Map<String, Long> payload) {
        Long recipeId = payload.get("recipeId");
        FavoriteDto favoriteDto = favoriteService.addFavorite(recipeId);
        return new ResponseEntity<>(favoriteDto, HttpStatus.CREATED);
    }

    /**
     * Remove a recipe from the current user's favorites.
     *
     * @param recipeId the ID of the recipe to remove
     * @return no content response
     */
    @Operation(summary = "Remove recipe from favorites", 
               description = "Remove a recipe from the current user's favorites",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Recipe successfully removed from favorites"),
        @ApiResponse(responseCode = "404", description = "Recipe not found in favorites"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long recipeId) {
        favoriteService.removeFavorite(recipeId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all favorite recipes for the current user.
     *
     * @return list of favorite recipes
     */
    @Operation(summary = "Get current user's favorite recipes", 
               description = "Returns a list of the current user's favorite recipes",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite recipes"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping
    public ResponseEntity<List<FavoriteDto>> getCurrentUserFavorites() {
        List<FavoriteDto> favorites = favoriteService.getCurrentUserFavorites();
        return ResponseEntity.ok(favorites);
    }
    
    /**
     * Get all recipes that are favorited by the current user.
     *
     * @return list of recipe DTOs
     */
    @Operation(summary = "Get current user's favorite recipes as recipe DTOs", 
               description = "Returns a list of complete recipe DTOs that are favorited by the current user",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully retrieved favorite recipes"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDto>> getFavoriteRecipes() {
        List<RecipeDto> recipes = favoriteService.getFavoriteRecipes();
        return ResponseEntity.ok(recipes);
    }

    /**
     * Check if a recipe is in the current user's favorites.
     *
     * @param recipeId the ID of the recipe to check
     * @return true if favorited, false otherwise
     */
    @Operation(summary = "Check if recipe is favorite", 
               description = "Check if a recipe is in the current user's favorites",
               security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status check successful"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/check/{recipeId}")
    public ResponseEntity<Map<String, Boolean>> isFavorite(@PathVariable Long recipeId) {
        boolean isFavorite = favoriteService.isFavorite(recipeId);
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }
}
package com.dishcraft.controller;

import com.dishcraft.dto.RecipeDto;
import com.dishcraft.service.RecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RecipeController exposes REST API endpoints for managing recipes.
 */
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Create a new recipe.
     *
     * @param recipeDto the recipe data to create
     * @return the created recipe details
     */
    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody RecipeDto recipeDto) {
        RecipeDto createdRecipe = recipeService.createRecipe(recipeDto);
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    /**
     * Retrieve a recipe by its ID.
     *
     * @param id the recipe ID
     * @return the recipe details
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        RecipeDto recipeDto = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipeDto);
    }

    /**
     * Retrieve all recipes with pagination.
     *
     * @param pageable pagination parameters
     * @return paginated list of recipes
     */
    @GetMapping
    public ResponseEntity<Page<RecipeDto>> getAllRecipes( @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<RecipeDto> recipes = recipeService.getAllRecipes(pageable);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Update an existing recipe.
     *
     * @param id        the recipe ID to update
     * @param recipeDto the updated recipe data
     * @return the updated recipe details
     */
    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeDto recipeDto) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, recipeDto);
        return ResponseEntity.ok(updatedRecipe);
    }
    /**
     * Delete a recipe.
     *
     * @param id the recipe ID to delete
     * @return no content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Search for recipes based on selected ingredient IDs and search mode.
     * This endpoint automatically filters the search results using the current user's dietary restrictions.
     *
     * @param ingredientIds List of ingredient IDs to search by.
     * @param mode          Search mode: "all" (must contain all ingredients) or "any" (contain any ingredient; MAIN ingredients prioritized).
     * @return a list of RecipeDto that match the search criteria.
     */
    @Operation(summary = "Search Recipes with Dietary Restrictions",
               description = "Search recipes by ingredient IDs and search mode. The result is automatically filtered based on the current user's dietary restrictions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved search results"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> searchRecipes(
            @Parameter(description = "List of ingredient IDs", required = true)
            @RequestParam List<Long> ingredientIds,
            @Parameter(description = "Search mode: 'all' or 'any'", required = true)
            @RequestParam String mode) {

        List<RecipeDto> recipes = recipeService.searchRecipes(ingredientIds, mode);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Filter recipes by dietary restrictions.
     *
     * @param dietaryRestrictionIds List of dietary restriction IDs to filter by
     * @return a list of RecipeDto that match the dietary restrictions
     */
    @Operation(summary = "Filter Recipes by Dietary Restrictions",
               description = "Returns recipes that are compatible with all specified dietary restrictions.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered recipes"),
            @ApiResponse(responseCode = "400", description = "Invalid dietary restriction parameters")
    })
    @GetMapping("/filter/dietary")
    public ResponseEntity<List<RecipeDto>> filterByDietaryRestrictions(
            @Parameter(description = "List of dietary restriction IDs", required = true)
            @RequestParam List<Long> dietaryRestrictionIds) {

        List<RecipeDto> recipes = recipeService.filterByDietaryRestrictions(dietaryRestrictionIds);
        return ResponseEntity.ok(recipes);
    }

    /**
     * Retrieve all recipes.
     *
     * @return a list of all recipes
     */
    @GetMapping("/all")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }
}

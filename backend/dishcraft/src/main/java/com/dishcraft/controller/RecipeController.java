package com.dishcraft.controller;

import com.dishcraft.dto.RecipeDto;
import com.dishcraft.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Page<RecipeDto>> getAllRecipes(Pageable pageable) {
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
     *
     * @param ingredientIds the list of ingredient IDs to search by
     * @param mode          search mode ("all" or "any")
     * @return a list of matching recipes
     */
    @GetMapping("/search")
    public ResponseEntity<List<RecipeDto>> searchRecipes(@RequestParam List<Long> ingredientIds,
                                                         @RequestParam String mode) {
        List<RecipeDto> recipes = recipeService.searchRecipes(ingredientIds, mode);
        return ResponseEntity.ok(recipes);
    }
}

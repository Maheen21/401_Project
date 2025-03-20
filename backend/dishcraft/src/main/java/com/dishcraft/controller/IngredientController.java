package com.dishcraft.controller;

import com.dishcraft.dto.IngredientDto;
import com.dishcraft.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IngredientController exposes REST API endpoints for managing ingredients.
 */
@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Retrieve ingredient details by its ID.
     *
     * @param id the ingredient ID
     * @return the ingredient details as IngredientDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<IngredientDto> getIngredientById(@PathVariable Long id) {
        IngredientDto ingredientDto = ingredientService.getIngredientById(id);
        return ResponseEntity.ok(ingredientDto);
    }

    /**
     * Search for ingredients by name (case-insensitive).
     *
     * @param name the search keyword for ingredient names
     * @return a list of IngredientDto that match the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<IngredientDto>> searchIngredients(@RequestParam String name) {
        List<IngredientDto> ingredients = ingredientService.searchIngredientsByName(name);
        return ResponseEntity.ok(ingredients);
    }

    /**
     * Retrieve all ingredients from the database.
     *
     * @return a list of all ingredients as IngredientDto
     */
    @GetMapping("/all")
    public ResponseEntity<List<IngredientDto>> getAllIngredients() {
        List<IngredientDto> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    /**
     * Create a new ingredient in the database.
     *
     * @param ingredientDto the ingredient details to be created
     * @return the created ingredient details as IngredientDto
     */
    @PostMapping
    public ResponseEntity<IngredientDto> createIngredient(@RequestBody IngredientDto ingredientDto) {
        IngredientDto createdIngredient = ingredientService.createIngredient(ingredientDto);
        return ResponseEntity.ok(createdIngredient);
    }

    /**
     * Update the ingredient details by its ID.
     *
     * @param id the unique identifier of the ingredient
     * @param ingredientDto the updated ingredient details
     * @return the updated ingredient details as IngredientDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<IngredientDto> updateIngredient(@PathVariable Long id, @RequestBody IngredientDto ingredientDto) {
        IngredientDto updatedIngredient = ingredientService.updateIngredient(id, ingredientDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    /**
     * Delete an ingredient by its ID.
     *
     * @param id the unique identifier of the ingredient
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
    
}

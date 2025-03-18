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

    // Additional endpoints can be added here, such as listing all ingredients,
    // filtering by category or dietary restrictions, etc.
}

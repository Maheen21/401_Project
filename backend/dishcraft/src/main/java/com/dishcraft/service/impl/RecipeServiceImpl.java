package com.dishcraft.service.impl;

import com.dishcraft.dto.RecipeDto;
import com.dishcraft.model.Recipe;
import com.dishcraft.repository.RecipeIngredientRepository;
import com.dishcraft.repository.RecipeRepository;
import com.dishcraft.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the RecipeService interface.
 * It provides CRUD operations for recipes and implements search functionality
 * based on ingredient IDs and search mode ("all" or "any").
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeIngredientRepository recipeIngredientRepository,
                             ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        // Convert DTO to entity
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        Recipe savedRecipe = recipeRepository.save(recipe);
        // Convert the saved entity back to DTO
        return modelMapper.map(savedRecipe, RecipeDto.class);
    }

    @Override
    public RecipeDto getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        return modelMapper.map(recipe, RecipeDto.class);
    }

    @Override
    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable)
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }

    @Override
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // Update fields from DTO
        existingRecipe.setName(recipeDto.getName());
        existingRecipe.setDescription(recipeDto.getDescription());
        existingRecipe.setInstruction(recipeDto.getInstruction());
        existingRecipe.setCookingTime(recipeDto.getCookingTime());
        existingRecipe.setImageUrl(recipeDto.getImageUrl());
        // If necessary, update related RecipeIngredient data here

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return modelMapper.map(updatedRecipe, RecipeDto.class);
    }

    @Override
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        recipeRepository.delete(recipe);
    }

    @Override
    public List<RecipeDto> searchRecipes(List<Long> ingredientIds, String mode) {
        // Retrieve candidate recipes that contain at least one of the selected ingredients
        List<Recipe> candidateRecipes = recipeRepository.findDistinctByRecipeIngredientsIngredientIdIn(ingredientIds);

        if ("all".equalsIgnoreCase(mode)) {
            // Filter candidate recipes to include only those that contain all selected ingredients
            candidateRecipes = candidateRecipes.stream()
                    .filter(recipe -> {
                        Set<Long> recipeIngredientIds = recipe.getRecipeIngredients().stream()
                                .map(ri -> ri.getIngredient().getId())
                                .collect(Collectors.toSet());
                        return recipeIngredientIds.containsAll(ingredientIds);
                    })
                    .collect(Collectors.toList());
        } else if ("any".equalsIgnoreCase(mode)) {
            // Sort candidate recipes based on the count of MAIN ingredients among the selected ones, in descending order
            candidateRecipes = candidateRecipes.stream()
                    .sorted((r1, r2) -> {
                        int r1MainCount = (int) r1.getRecipeIngredients().stream()
                                .filter(ri -> ingredientIds.contains(ri.getIngredient().getId()) &&
                                              "MAIN".equalsIgnoreCase(ri.getIngredient().getRank()))
                                .count();
                        int r2MainCount = (int) r2.getRecipeIngredients().stream()
                                .filter(ri -> ingredientIds.contains(ri.getIngredient().getId()) &&
                                              "MAIN".equalsIgnoreCase(ri.getIngredient().getRank()))
                                .count();
                        return Integer.compare(r2MainCount, r1MainCount);
                    })
                    .collect(Collectors.toList());
        }

        // Convert the list of Recipe entities to RecipeDto objects
        return candidateRecipes.stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }
}

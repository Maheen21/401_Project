package com.dishcraft.service.impl;

import com.dishcraft.dto.RecipeDto;
import com.dishcraft.dto.RecipeIngredientDto;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import com.dishcraft.repository.RecipeIngredientRepository;
import com.dishcraft.repository.RecipeRepository;
import com.dishcraft.service.CurrentUserService;
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
 * based on ingredient IDs, search mode ("all" or "any"), and the current user's dietary restrictions.
 */
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeIngredientRepository recipeIngredientRepository,
                             ModelMapper modelMapper,
                             CurrentUserService currentUserService) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.modelMapper = modelMapper;
        this.currentUserService = currentUserService;
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

    RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);

    // ✅ add name of ingredient to RecipeIngredientDto
    List<RecipeIngredientDto> ingredientDtos = recipe.getRecipeIngredients().stream()
            .map(ri -> {
                RecipeIngredientDto dto = new RecipeIngredientDto();
                dto.setId(ri.getId());
                dto.setName(ri.getIngredient().getName()); // ✅ map ingredient name
                dto.setQuantity(ri.getQuantity());
                dto.setUnit(ri.getUnit());
                dto.setIsRequired(ri.getIsRequired());
                return dto;
            })
            .collect(Collectors.toList());

    recipeDto.setRecipeIngredients(ingredientDtos);
    return recipeDto;
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
        // Additional update logic for RecipeIngredient can be added here

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

        // Retrieve the current user's dietary restrictions (as lower-case names)

        User currentUser;

        try {
            currentUser = currentUserService.getCurrentUser();
        } catch (IllegalStateException e) {
            // if not logged in, use empty sets
            currentUser = null;
        }

        Set<String> userDietaryRestrictions = (currentUser != null) ?
        currentUser.getDietaryRestrictions().stream()
            .map(dr -> dr.getName().toLowerCase())
            .collect(Collectors.toSet()) : 
        Set.of();
        
        // Filter recipes based on the user's dietary restrictions
        candidateRecipes = filterRecipesByDietaryRestrictions(candidateRecipes, userDietaryRestrictions);

        // Apply mode-specific filtering/sorting
        if ("all".equalsIgnoreCase(mode)) {
            candidateRecipes = candidateRecipes.stream()
                    .filter(recipe -> {
                        Set<Long> recipeIngredientIds = recipe.getRecipeIngredients().stream()
                                .map(ri -> ri.getIngredient().getId())
                                .collect(Collectors.toSet());
                        return recipeIngredientIds.containsAll(ingredientIds);
                    })
                    .collect(Collectors.toList());
        } else if ("any".equalsIgnoreCase(mode)) {
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

        // Convert the filtered list of Recipe entities to RecipeDto objects
        return candidateRecipes.stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Filters the provided list of recipes based on the user's dietary restrictions.
     *
     * @param recipes the list of candidate recipes
     * @param userDietaryRestrictions the set of dietary restrictions (lower-case) from the current user
     * @return a filtered list of recipes that are compatible with the user's dietary restrictions
     */
    private List<Recipe> filterRecipesByDietaryRestrictions(List<Recipe> recipes, Set<String> userDietaryRestrictions) {
        return recipes.stream()
                .filter(recipe -> isRecipeCompatibleWithDiet(recipe, userDietaryRestrictions))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether a recipe is compatible with the user's dietary restrictions.
     *
     * @param recipe the recipe to check
     * @param userDietaryRestrictions the set of dietary restrictions from the user
     * @return true if the recipe is compatible, false otherwise
     */
    private boolean isRecipeCompatibleWithDiet(Recipe recipe, Set<String> userDietaryRestrictions) {
        return recipe.getRecipeIngredients().stream()
                .allMatch(ri -> {
                    // Extract ingredient dietary restrictions as lower-case names
                    Set<String> ingredientRestrictions = ri.getIngredient().getDietaryRestrictions().stream()
                            .map(dr -> dr.getName().toLowerCase())
                            .collect(Collectors.toSet());
                    // The ingredient is compatible if all its restrictions are satisfied by the user
                    return userDietaryRestrictions.containsAll(ingredientRestrictions);
                });
    }
}

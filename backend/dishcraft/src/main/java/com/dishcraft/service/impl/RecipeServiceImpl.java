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

    /**
     * given a recipe DTO, create a new recipe and save it to the database, then return the recipe DTO
     */
    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        // map the DTO to the entity
        Recipe recipe = modelMapper.map(recipeDto, Recipe.class);
        // if additional logic is needed when creating a recipe, add it here


        // save the recipe to the database
        Recipe savedRecipe = recipeRepository.save(recipe);
        // map the entity back to the DTO and return it
        return modelMapper.map(savedRecipe, RecipeDto.class);
    }

    /**
     * given a recipe ID, get the recipe information and return the recipe DTO
     */
    @Override
    public RecipeDto getRecipeById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        return modelMapper.map(recipe, RecipeDto.class);
    }

    /**
     * get all recipes in the database and return them in a paginated form
     */
    @Override
    public Page<RecipeDto> getAllRecipes(Pageable pageable) {
        return recipeRepository.findAll(pageable)
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class));
    }

    /**
     * given a recipe ID and a recipe DTO, update the recipe information and return the updated recipe DTO
     */
    @Override
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // update the existing recipe with the new information
        existingRecipe.setName(recipeDto.getName());
        existingRecipe.setDescription(recipeDto.getDescription());
        existingRecipe.setInstruction(recipeDto.getInstruction());
        existingRecipe.setCookingTime(recipeDto.getCookingTime());
        existingRecipe.setImageUrl(recipeDto.getImageUrl());
        // if additional logic is needed when updating a recipe, add it here


        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return modelMapper.map(updatedRecipe, RecipeDto.class);
    }

    /**
     * given a recipe ID, delete the recipe from the database
     */
    @Override
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
        recipeRepository.delete(recipe);
    }

    /**
     * search recipes by the list of ingredient IDs and the mode
     * - "all" mode : all ingredients must be included in the recipe
     * - "any" mode : at least one ingredient must be included in the recipe. If there are ingredients with higher ranks, they will be prioritized.
     * 
     */
    @Override
    public List<RecipeDto> searchRecipes(List<Long> ingredientIds, String mode) {
        // find recipes that contain at least one of the ingredients from the list
        List<Recipe> candidateRecipes = recipeRepository.findDistinctByRecipeIngredientsIngredientIdIn(ingredientIds);

        if ("all".equalsIgnoreCase(mode)) {
            // all mode: filter out recipes that do not contain all the ingredients
            candidateRecipes = candidateRecipes.stream()
                    .filter(recipe -> {
                        // get the set of ingredient IDs in the recipe
                        Set<Long> recipeIngredientIds = recipe.getRecipeIngredients().stream()
                                .map(ri -> ri.getIngredient().getId())
                                .collect(Collectors.toSet());
                        return recipeIngredientIds.containsAll(ingredientIds);
                    })
                    .collect(Collectors.toList());
        } else if ("any".equalsIgnoreCase(mode)) {
            // any mode: sort the recipes by the number of main ingredients
            // main ingredients are ingredients with the rank "MAIN"
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
        
        // map the list of recipes to a list of recipe DTOs and return it
        return candidateRecipes.stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }
}
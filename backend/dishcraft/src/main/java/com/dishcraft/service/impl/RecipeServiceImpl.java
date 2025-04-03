package com.dishcraft.service.impl;

import com.dishcraft.dto.RecipeDto;
import com.dishcraft.dto.RecipeIngredientDto;
import com.dishcraft.mapper.RecipeMapperUtil;
import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.model.Ingredient;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.RecipeIngredient;
import com.dishcraft.model.User;
import com.dishcraft.repository.DietaryRestrictionRepository;
import com.dishcraft.repository.IngredientRepository;
import com.dishcraft.repository.RecipeIngredientRepository;
import com.dishcraft.repository.RecipeRepository;
import com.dishcraft.service.CurrentUserService;
import com.dishcraft.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    private final CurrentUserService currentUserService;
    private final IngredientRepository ingredientRepository;
    private final DietaryRestrictionRepository dietaryRestrictionRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeIngredientRepository recipeIngredientRepository,
                             IngredientRepository ingredientRepository,
                             CurrentUserService currentUserService,
                             DietaryRestrictionRepository dietaryRestrictionRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.currentUserService = currentUserService;
        this.dietaryRestrictionRepository = dietaryRestrictionRepository;
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        // Convert DTO to entity
        Recipe recipe = RecipeMapperUtil.toEntity(recipeDto);

        // Initialize the recipe ingredients collection
        recipe.setRecipeIngredients(new ArrayList<>());

        // if client sends recipeIngredients, process it
        if (recipeDto.getRecipeIngredients() != null) {
            for (RecipeIngredientDto riDto : recipeDto.getRecipeIngredients()) {
                Ingredient ingredient = ingredientRepository.findById(riDto.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + riDto.getIngredientId()));
                        // make new RecipeIngredient entity and give a parameters
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setQuantity(riDto.getQuantity());
                recipeIngredient.setUnit(riDto.getUnit());
                recipeIngredient.setIsRequired(riDto.getIsRequired());
                // add the RecipeIngredient entity to the Recipe entity
                recipe.addIngredient(recipeIngredient);
            }
        }

                
        Recipe savedRecipe = recipeRepository.save(recipe);
        // Convert the saved entity back to DTO
        return RecipeMapperUtil.toDto(savedRecipe);
    }

@Override
public RecipeDto getRecipeById(Long id) {
    Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

    RecipeDto recipeDto = RecipeMapperUtil.toDto(recipe);

    // ✅ add name of ingredient to RecipeIngredientDto
    List<RecipeIngredientDto> ingredientDtos = recipe.getRecipeIngredients().stream()
            .map(ri -> {
                RecipeIngredientDto dto = new RecipeIngredientDto();
                dto.setIngredientId(ri.getIngredient().getId());
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
        // Define allowed sorting fields
        List<String> allowedSortFields = List.of("name", "cookingTime", "description");
        

        // Validate and filter sorting fields
        Sort filteredSort = Sort.by(pageable.getSort().stream()
                .filter(order -> allowedSortFields.contains(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(), order.getProperty()))
                .toList());

        Pageable validatedPageable = PageRequest.of(
                pageable.getPageNumber(), pageable.getPageSize(), filteredSort);
        Page<Recipe> recipes = recipeRepository.findAll(validatedPageable);
        if (recipes.isEmpty()) {
            System.out.println("⚠️ No recipes found in pagination request. Total records in DB: " + recipeRepository.count());
        } else {
            System.out.println("✅ Recipes retrieved: " + recipes.getTotalElements());
        }
            
        return recipes.map(RecipeMapperUtil::toDto);
    }

    @Override
    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));
    
        RecipeMapperUtil.updateEntityFromDto(recipeDto, existingRecipe);
    
        if (recipeDto.getRecipeIngredients() != null) {
            // Delete existing recipe ingredients and add new ones
            recipeIngredientRepository.deleteAll(existingRecipe.getRecipeIngredients());
            recipeIngredientRepository.flush();
            existingRecipe.getRecipeIngredients().clear();
    
            for (RecipeIngredientDto riDto : recipeDto.getRecipeIngredients()) {
                Ingredient ingredient = ingredientRepository.findById(riDto.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + riDto.getIngredientId()));
    
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(existingRecipe);
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setQuantity(riDto.getQuantity());
                recipeIngredient.setUnit(riDto.getUnit());
                recipeIngredient.setIsRequired(riDto.getIsRequired());
    
                existingRecipe.addIngredient(recipeIngredient);
            }
        }
    
        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return RecipeMapperUtil.toDto(updatedRecipe);
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
                .map(RecipeMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDto> filterByDietaryRestrictions(List<Long> dietaryRestrictionIds) {
        // Get all dietary restrictions by their IDs
        List<DietaryRestriction> dietaryRestrictions = dietaryRestrictionRepository.findAllById(dietaryRestrictionIds);
        
        if (dietaryRestrictions.isEmpty()) {
            throw new RuntimeException("No dietary restrictions found with the provided IDs");
        }
        
        // Convert to lowercase names for case-insensitive comparison
        Set<String> restrictionNames = dietaryRestrictions.stream()
                .map(dr -> dr.getName().toLowerCase())
                .collect(Collectors.toSet());
        
        // Get all recipes
        List<Recipe> allRecipes = recipeRepository.findAll();
        
        // Filter recipes based on the provided dietary restrictions
        List<Recipe> filteredRecipes = allRecipes.stream()
                .filter(recipe -> {
                    // For each recipe, check if all its ingredients are compatible with the dietary restrictions
                    return recipe.getRecipeIngredients().stream().allMatch(ri -> {
                        // Check each ingredient's dietary restrictions
                        Set<String> ingredientRestrictions = ri.getIngredient().getDietaryRestrictions().stream()
                                .map(dr -> dr.getName().toLowerCase())
                                .collect(Collectors.toSet());
                        
                        // Check if the ingredient's restrictions conflict with the requested restrictions
                        return ingredientRestrictions.stream().noneMatch(restrictionNames::contains);
                    });
                })
                .collect(Collectors.toList());
        
        return filteredRecipes.stream()
                .map(RecipeMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Filters the provided list of recipes based on the user's dietary restrictions.
     *
     * @param recipes the list of candidate recipes
     * @param userDietaryRestrictions the set of dietary restrictions (lower-case) from the current user
     * @return a filtered list of recipes that are NOT restricted by the user's dietary restrictions
     */
    private List<Recipe> filterRecipesByDietaryRestrictions(List<Recipe> recipes, Set<String> userDietaryRestrictions) {
        return recipes.stream()
                .filter(recipe -> isRecipeAllowedForUser(recipe, userDietaryRestrictions)) // ✅ 겹치지 않는 레시피만 반환
                .collect(Collectors.toList());
    }

    /**
     * Checks whether a recipe does NOT conflict with the user's dietary restrictions.
     *
     * @param recipe the recipe to check
     * @param userDietaryRestrictions the set of dietary restrictions from the user
     * @return true if the recipe does NOT conflict with the user's dietary restrictions, false otherwise
     */
    private boolean isRecipeAllowedForUser(Recipe recipe, Set<String> userDietaryRestrictions) {
        return recipe.getRecipeIngredients().stream()
                .allMatch(ri -> {
                    // Get the dietary restrictions for each ingredient (in lowercase)
                    Set<String> ingredientRestrictions = ri.getIngredient().getDietaryRestrictions().stream()
                            .map(dr -> dr.getName().toLowerCase())
                            .collect(Collectors.toSet());

                    // ✅ only retain recipes that do not contain any restricted ingredients
                    return ingredientRestrictions.stream().noneMatch(userDietaryRestrictions::contains);
                });
    }

    /*
     * get all recipes in the database and return them in a list
     * 
     * @return the list of recipe DTOs
     */
    @Override
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return RecipeMapperUtil.toDtoList(recipes);
    }
}

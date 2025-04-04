package com.dishcraft.service.impl;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.dto.IngredientDto;
import com.dishcraft.mapper.IngredientMapperUtil;
import com.dishcraft.model.Ingredient;
import com.dishcraft.repository.DietaryRestrictionRepository;
import com.dishcraft.repository.IngredientRepository;
import com.dishcraft.service.IngredientService;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * IngredientServiceImpl implements the business logic for managing ingredients.
 * It retrieves ingredient data from the IngredientRepository and converts entities to DTOs.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final DietaryRestrictionRepository dietaryRestrictionRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, 
                               DietaryRestrictionRepository dietaryRestrictionRepository) {
        this.ingredientRepository = ingredientRepository;
        this.dietaryRestrictionRepository = dietaryRestrictionRepository;
    }

    /**
     * Retrieves an ingredient by its ID and maps it to IngredientDto.
     *
     * @param id the unique identifier of the ingredient
     * @return the mapped IngredientDto
     * @throws RuntimeException if the ingredient is not found
     */
    @Transactional(readOnly = true)
    @Override
    public IngredientDto getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findByIdWithDietaryRestrictions(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    
        IngredientDto ingredientDto = IngredientMapperUtil.toDto(ingredient);
    
        // Initialize the dietary restrictions collection to avoid LazyInitializationException
        ingredientDto.setDietaryRestrictions(
            ingredient.getDietaryRestrictions().stream()
                    .map(dietaryRestriction -> {
                        DietaryRestrictionDto dto = new DietaryRestrictionDto();
                        dto.setId(dietaryRestriction.getId());
                        dto.setName(dietaryRestriction.getName());
                        return dto;
                    })
                    .collect(Collectors.toList())
        );
    
        return ingredientDto;
    }

    /**
     * Searches for ingredients whose names contain the specified keyword, case-insensitive.
     *
     * @param name the search keyword for ingredient name
     * @return a list of IngredientDto matching the search criteria
     */
    @Override
    public List<IngredientDto> searchIngredientsByName(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByNameContainingIgnoreCase(name);
        return IngredientMapperUtil.toDtoList(ingredients);
    }

    /**
     * Retrieves all ingredients from the database and maps them to IngredientDto.
     *
     * @return a list of all ingredients as IngredientDto
     */
    @Override
    public List<IngredientDto> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return IngredientMapperUtil.toDtoList(ingredients);
    }

    /**
     * Creates a new ingredient in the database.
     *
     * @param ingredientDto the ingredient details to be created
     * @return the created ingredient details as IngredientDto
     */
    @Transactional
    @Override
    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        Ingredient ingredient = IngredientMapperUtil.toEntity(ingredientDto);
        ingredient.setDietaryRestrictions(new HashSet<>()); // Initialize the dietary restrictions collection
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapperUtil.toDto(savedIngredient);
    }

    /**
     * Updates the ingredient details by its ID.
     *
     * @param id the unique identifier of the ingredient
     * @param ingredientDto the updated ingredient details
     * @return the updated ingredient details as IngredientDto
     */
    @Transactional
    @Override
    public IngredientDto updateIngredient(Long id, IngredientDto ingredientDto) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    
        IngredientMapperUtil.updateEntityFromDto(ingredientDto, ingredient);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapperUtil.toDto(savedIngredient);
    }

    /**
     * Deletes an ingredient by its ID.
     *
     * @param id the unique identifier of the ingredient
     */
    @Transactional
    @Override
    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
    
    /**
     * Filter ingredients by excluding those with the specified dietary restrictions.
     * Returns ingredients that do NOT have any of the specified dietary restrictions.
     *
     * @param dietaryRestrictionIds the list of dietary restriction IDs to exclude
     * @return a list of ingredients that are NOT associated with the specified dietary restrictions
     */
    @Transactional(readOnly = true)
    @Override
    public List<IngredientDto> filterByDietaryRestrictions(List<Long> dietaryRestrictionIds) {
        // Get all ingredients from the database
        List<Ingredient> allIngredients = ingredientRepository.findAll();
        
        // If no dietary restriction IDs are provided, return all ingredients
        if (dietaryRestrictionIds == null || dietaryRestrictionIds.isEmpty()) {
            return IngredientMapperUtil.toDtoList(allIngredients);
        }
        
        // Get the names of the dietary restrictions that we're filtering out (converted to lowercase)
        Set<String> restrictionNames = dietaryRestrictionRepository.findAllById(dietaryRestrictionIds)
                .stream()
                .map(dr -> dr.getName().toLowerCase())
                .collect(Collectors.toSet());
        
        // Filter ingredients to exclude those that have any of the specified dietary restrictions
        List<Ingredient> filteredIngredients = allIngredients.stream()
                .filter(ingredient -> {
                    // If this ingredient has no dietary restrictions, include it
                    if (ingredient.getDietaryRestrictions() == null || ingredient.getDietaryRestrictions().isEmpty()) {
                        return true;
                    }
                    
                    // Convert ingredient's dietary restrictions to lowercase names
                    Set<String> ingredientRestrictions = ingredient.getDietaryRestrictions().stream()
                            .map(dr -> dr.getName().toLowerCase())
                            .collect(Collectors.toSet());
                    
                    // Check if this ingredient has NONE of the dietary restrictions we're excluding
                    // Using !anyMatch to check that there's no intersection between the sets
                    return ingredientRestrictions.stream().noneMatch(restrictionNames::contains);
                })
                .collect(Collectors.toList());
        
        return IngredientMapperUtil.toDtoList(filteredIngredients);
    }
}

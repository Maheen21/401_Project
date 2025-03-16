package com.dishcraft.service.impl;

import com.dishcraft.dto.IngredientDto;
import com.dishcraft.model.Ingredient;
import com.dishcraft.repository.IngredientRepository;
import com.dishcraft.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * IngredientServiceImpl implements the business logic for managing ingredients.
 * It retrieves ingredient data from the IngredientRepository and converts entities to DTOs.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Retrieves an ingredient by its ID and maps it to IngredientDto.
     *
     * @param id the unique identifier of the ingredient
     * @return the mapped IngredientDto
     * @throws RuntimeException if the ingredient is not found
     */
    @Override
    public IngredientDto getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
        return modelMapper.map(ingredient, IngredientDto.class);
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
        return ingredients.stream()
                .map(ingredient -> modelMapper.map(ingredient, IngredientDto.class))
                .collect(Collectors.toList());
    }
}

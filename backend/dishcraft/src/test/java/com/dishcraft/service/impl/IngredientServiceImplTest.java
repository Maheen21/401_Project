package com.dishcraft.service.impl;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.dto.IngredientDto;
import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.model.Ingredient;
import com.dishcraft.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient mockIngredient;
    private IngredientDto mockIngredientDto;
    private DietaryRestriction mockDietaryRestriction;

    @BeforeEach
    void setUp() {
        // Setup mock dietary restriction
        mockDietaryRestriction = new DietaryRestriction();
        mockDietaryRestriction.setId(1L);
        mockDietaryRestriction.setName("Vegetarian");

        // Setup mock ingredient
        mockIngredient = new Ingredient();
        mockIngredient.setId(1L);
        mockIngredient.setName("Test Ingredient");
        mockIngredient.setCategory("Test Category");
        mockIngredient.setDescription("Test Description");
        mockIngredient.setRank("MAIN");
        
        Set<DietaryRestriction> restrictions = new HashSet<>();
        restrictions.add(mockDietaryRestriction);
        mockIngredient.setDietaryRestrictions(restrictions);

        // Setup mock ingredient DTO
        mockIngredientDto = new IngredientDto();
        mockIngredientDto.setId(1L);
        mockIngredientDto.setName("Test Ingredient");
        mockIngredientDto.setCategory("Test Category");
        mockIngredientDto.setDescription("Test Description");
        mockIngredientDto.setRank("MAIN");
        
        // Create DietaryRestrictionDto using setters instead of constructor
        List<DietaryRestrictionDto> restrictionDtos = new ArrayList<>();
        DietaryRestrictionDto restrictionDto = new DietaryRestrictionDto();
        restrictionDto.setId(1L);
        restrictionDto.setName("Vegetarian");
        restrictionDtos.add(restrictionDto);
        
        mockIngredientDto.setDietaryRestrictions(restrictionDtos);
    }

    @Test
    void testGetIngredientById() {
        // Given
        when(ingredientRepository.findByIdWithDietaryRestrictions(1L)).thenReturn(Optional.of(mockIngredient));

        // When
        IngredientDto result = ingredientService.getIngredientById(1L);

        // Then
        assertNotNull(result);
        assertEquals(mockIngredient.getId(), result.getId());
        assertEquals(mockIngredient.getName(), result.getName());
        assertEquals(mockIngredient.getCategory(), result.getCategory());
        assertEquals(mockIngredient.getDescription(), result.getDescription());
        assertEquals(mockIngredient.getRank(), result.getRank());
        assertEquals(1, result.getDietaryRestrictions().size());
        assertEquals("Vegetarian", result.getDietaryRestrictions().get(0).getName());
    }

    @Test
    void testGetAllIngredients() {
        // Given
        List<Ingredient> ingredients = List.of(mockIngredient);
        when(ingredientRepository.findAll()).thenReturn(ingredients);

        // When
        List<IngredientDto> results = ingredientService.getAllIngredients();

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(mockIngredient.getName(), results.get(0).getName());
        assertEquals(mockIngredient.getCategory(), results.get(0).getCategory());
    }

    @Test
    void testSearchIngredientsByName() {
        // Given
        List<Ingredient> ingredients = List.of(mockIngredient);
        when(ingredientRepository.findByNameContainingIgnoreCase("Test")).thenReturn(ingredients);

        // When
        List<IngredientDto> results = ingredientService.searchIngredientsByName("Test");

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(mockIngredient.getName(), results.get(0).getName());
    }

    @Test
    void testCreateIngredient() {
        // Given
        when(ingredientRepository.save(any(Ingredient.class))).thenAnswer(invocation -> {
            Ingredient savedIngredient = invocation.getArgument(0);
            savedIngredient.setId(1L); // Simulate ID generation
            return savedIngredient;
        });

        // When
        IngredientDto result = ingredientService.createIngredient(mockIngredientDto);

        // Then
        assertNotNull(result);
        assertEquals(mockIngredientDto.getName(), result.getName());
        assertEquals(mockIngredientDto.getCategory(), result.getCategory());
        assertEquals(mockIngredientDto.getDescription(), result.getDescription());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    void testUpdateIngredient() {
        // Given
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(mockIngredient));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(mockIngredient);

        // Update the ingredient name
        mockIngredientDto.setName("Updated Ingredient Name");

        // When
        IngredientDto result = ingredientService.updateIngredient(1L, mockIngredientDto);

        // Then
        assertNotNull(result);
        assertEquals("Updated Ingredient Name", result.getName());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }
}
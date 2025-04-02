package com.dishcraft.service.impl;

import com.dishcraft.dto.RecipeDto;
import com.dishcraft.dto.RecipeIngredientDto;
import com.dishcraft.mapper.RecipeMapperUtil;
import com.dishcraft.model.Ingredient;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.RecipeIngredient;
import com.dishcraft.repository.IngredientRepository;
import com.dishcraft.repository.RecipeIngredientRepository;
import com.dishcraft.repository.RecipeRepository;
import com.dishcraft.service.CurrentUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeIngredientRepository recipeIngredientRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private CurrentUserService currentUserService;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Recipe mockRecipe;
    private RecipeDto mockRecipeDto;
    private Ingredient mockIngredient;

    @BeforeEach
    void setUp() {
        // Setup mock recipe
        mockRecipe = new Recipe();
        mockRecipe.setId(1L);
        mockRecipe.setName("Test Recipe");
        mockRecipe.setDescription("Test Description");
        mockRecipe.setInstruction("Test Instructions");
        mockRecipe.setCookingTime(30);
        mockRecipe.setImageUrl("test-image.jpg");
        mockRecipe.setRecipeIngredients(new ArrayList<>());

        // Setup mock ingredient
        mockIngredient = new Ingredient();
        mockIngredient.setId(1L);
        mockIngredient.setName("Test Ingredient");
        mockIngredient.setCategory("Test Category");

        // Setup mock recipe ingredient
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(mockRecipe);
        recipeIngredient.setIngredient(mockIngredient);
        recipeIngredient.setQuantity(100.0);
        recipeIngredient.setUnit("g");
        recipeIngredient.setIsRequired(true);
        mockRecipe.getRecipeIngredients().add(recipeIngredient);

        // Setup mock recipeDto
        mockRecipeDto = new RecipeDto();
        mockRecipeDto.setId(1L);
        mockRecipeDto.setName("Test Recipe");
        mockRecipeDto.setDescription("Test Description");
        mockRecipeDto.setInstruction("Test Instructions");
        mockRecipeDto.setCookingTime(30);
        mockRecipeDto.setImageUrl("test-image.jpg");

        // Setup mock recipe ingredient dto
        List<RecipeIngredientDto> recipeIngredientDtos = new ArrayList<>();
        RecipeIngredientDto recipeIngredientDto = new RecipeIngredientDto();
        recipeIngredientDto.setIngredientId(1L);
        recipeIngredientDto.setName("Test Ingredient");
        recipeIngredientDto.setQuantity(100.0);
        recipeIngredientDto.setUnit("g");
        recipeIngredientDto.setIsRequired(true);
        recipeIngredientDtos.add(recipeIngredientDto);
        mockRecipeDto.setRecipeIngredients(recipeIngredientDtos);
    }

    @Test
    void testGetRecipeById() {
        // Given
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(mockRecipe));

        // When
        RecipeDto result = recipeService.getRecipeById(1L);

        // Then
        assertNotNull(result);
        assertEquals(mockRecipe.getId(), result.getId());
        assertEquals(mockRecipe.getName(), result.getName());
        assertEquals(mockRecipe.getDescription(), result.getDescription());
        assertEquals(mockRecipe.getInstruction(), result.getInstruction());
        assertEquals(mockRecipe.getCookingTime(), result.getCookingTime());
        assertEquals(mockRecipe.getImageUrl(), result.getImageUrl());
        assertEquals(1, result.getRecipeIngredients().size());
        assertEquals(mockIngredient.getName(), result.getRecipeIngredients().get(0).getName());
    }

    @Test
    void testCreateRecipe() {
        // Given
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(mockIngredient));
        when(recipeRepository.save(any(Recipe.class))).thenAnswer(invocation -> {
            Recipe savedRecipe = invocation.getArgument(0);
            savedRecipe.setId(1L); // Simulate ID generation
            return savedRecipe;
        });

        // When
        RecipeDto result = recipeService.createRecipe(mockRecipeDto);

        // Then
        assertNotNull(result);
        assertEquals(mockRecipeDto.getName(), result.getName());
        assertEquals(mockRecipeDto.getDescription(), result.getDescription());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testGetAllRecipes() {
        // Given
        List<Recipe> recipes = List.of(mockRecipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        // When
        List<RecipeDto> results = recipeService.getAllRecipes();

        // Then
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(mockRecipe.getName(), results.get(0).getName());
    }

    @Test
    void testGetAllRecipesPageable() {
        // Given
        List<Recipe> recipes = List.of(mockRecipe);
        Page<Recipe> recipePage = new PageImpl<>(recipes);
        Pageable pageable = PageRequest.of(0, 10);
        
        when(recipeRepository.findAll(any(Pageable.class))).thenReturn(recipePage);

        // When
        Page<RecipeDto> results = recipeService.getAllRecipes(pageable);

        // Then
        assertNotNull(results);
        assertEquals(1, results.getContent().size());
        assertEquals(mockRecipe.getName(), results.getContent().get(0).getName());
    }

    @Test
    void testUpdateRecipe() {
        // Given
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(mockRecipe));
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(mockIngredient));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(mockRecipe);

        // Update the recipe name
        mockRecipeDto.setName("Updated Recipe Name");

        // When
        RecipeDto result = recipeService.updateRecipe(1L, mockRecipeDto);

        // Then
        assertNotNull(result);
        assertEquals("Updated Recipe Name", result.getName());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}
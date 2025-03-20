package com.dishcraft.service;

import com.dishcraft.dto.RecipeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Recipe Service interface
 * 
 * Description: Recipe Service interface defines the contract of methods that process 
 * the business logic of Recipe entities.
 * 
 * service layer is responsible for processing the business logic(CRUD , ingredients-based search, etc.)
 */

public interface RecipeService {
    /*
     * create a new recipe and save it to the database, then return the recipe DTO
     * 
     * @param recipeDto the recipe DTO to be created
     * @return the recipe DTO that has been created
     */
    RecipeDto createRecipe(RecipeDto recipeDto);

    /**
     * get a information of a recipe by its id and return the recipe DTO
     * 
     * @param id the id of the recipe
     * @return the recipe DTO that has the id
     */
    RecipeDto getRecipeById(Long id);

    /**
     * get all recipes in the database and return them in a paginated form
     * 
     * @param pageable the page information
     * @return the page of recipe DTOs
     * 
     */
    Page<RecipeDto> getAllRecipes(Pageable pageable);

    /**
     * update the recipe information by its id and return the updated recipe DTO
     *
     * @param id       the id of the recipe
     * @param recipeDto the recipe DTO to be updated
     * @return the updated recipe DTO
     */
    RecipeDto updateRecipe(Long id, RecipeDto recipeDto);

    /**
     * delete a recipe by its id
     *
     * @param id the id of the recipe
     */
    void deleteRecipe(Long id);

    /**
     * search recipes by the list of ingredient ids and the mode
     * the mode can be "all" or "any"
     * all - the recipe must contain all the ingredients
     * any - the recipe must contain at least one of the ingredients
     * 
     * the result will be manipulated in the service layer
     *
     * @param ingredientIds the list of ingredient ids
     * @param mode          the mode of the search
     * @return the list of recipe DTOs that match the search criteria
     */
    List<RecipeDto> searchRecipes(List<Long> ingredientIds, String mode);    

    /*
     * get all recipes in the database and return them in a list
     * 
     * @return the list of recipe DTOs
     */
    List<RecipeDto> getAllRecipes();
}

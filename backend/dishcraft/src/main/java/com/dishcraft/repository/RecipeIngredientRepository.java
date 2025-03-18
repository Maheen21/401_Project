package com.dishcraft.repository;

import com.dishcraft.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing RecipeIngredient entities.
 * This interface provides CRUD operations as well as any custom queries related to
 * the association between Recipe and Ingredient (including quantity, unit, and required flag).
 */
@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {

    /*
     * find recipe ingredients by recipe id
     * 
     * @param recipeId the id of the recipe
     * @return the list of recipe ingredients that belong to the recipe
     */
    List<RecipeIngredient> findByRecipeId(Long recipeId);

    /*
     * find recipe ingredients by ingredient id
     * 
     * @param ingredientId the id of the ingredient
     * @return the list of recipe ingredients that contain the ingredient
     */
    List<RecipeIngredient> findByIngredientId(Long ingredientId);

    /*
     * find recipe ingredient by recipe id and ingredient id
     * 
     * @param recipeId the id of the recipe
     * @param ingredientId the id of the ingredient
     * @return the recipe ingredient that belongs to the recipe and contains the ingredient
     */
    Optional<RecipeIngredient> findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    /*
     * count the number of ingredients in the recipe
     * 
     * @param recipeId the id of the recipe
     * @return the number of ingredients in the recipe
     */
    Long countByRecipeId(Long recipeId);
    



}

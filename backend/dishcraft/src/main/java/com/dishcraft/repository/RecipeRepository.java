package com.dishcraft.repository;

import com.dishcraft.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    /**
     * find recipes by name in the database
     *
     * @param name the name of the recipe
     * @return the list of recipes that contain the name
     */
    List<Recipe> findByNameContainingIgnoreCase(String name);

    /*
     * find recipes by the list of ingredient ids
     * this query will return the list of recipes that contain some of the ingredients
     * the recipes will be ordered by the rank of the ingredient
     */
    @Query("SELECT DISTINCT r FROM Recipe r JOIN r.recipeIngredients ri " +
       "WHERE ri.ingredient.id IN :ingredientIds " +
       "ORDER BY CASE WHEN ri.ingredient.rank = 'MAIN' THEN 0 ELSE 1 END")
    List<Recipe> findRecipesByAnyIngredientsOrdered(@Param("ingredientIds") List<Long> ingredientIds);





}

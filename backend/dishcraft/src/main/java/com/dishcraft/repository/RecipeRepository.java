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
     * find recipes that contain at least one of the ingredients from the list
     * the result will be manipulated in the service layer
     * using mode 
     * 
     * @param ingredientIds the list of ingredient ids
     * @return the list of recipes that contain at least one of the ingredients
     */
    List<Recipe> findDistinctByRecipeIngredientsIngredientIdIn(List<Long> ingredientIds);

}

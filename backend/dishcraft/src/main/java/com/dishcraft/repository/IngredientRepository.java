package com.dishcraft.repository;

import com.dishcraft.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    /**
     * find ingredient by name in the database
     *
     * @param name ingredient name
     * @return the ingredient that has the name
     */
    Ingredient findByName(String name);

    /**
     * find ingredients by name in the database without case sensitivity
     *
     * @param name search keyword
     * @return ingredients list that contains the keyword
     */
    List<Ingredient> findByNameContainingIgnoreCase(String name);

    /**
     * find ingredients by category in the database
     *
     * @param category ingredient category
     * @return the list of ingredients that have the category
     */
    List<Ingredient> findByCategory(String category);

    /**
     * check if the ingredient exists in the database
     *
     * @param name ingredient name
     * @return true if the ingredient exists, false otherwise
     */
    boolean existsByName(String name);

    /**
     * find ingredients by dietary restriction ID
     *
     * @param dietaryRestrictionId the ID of the dietary restriction
     * @return the list of ingredients that comply with the dietary restriction
     */
    List<Ingredient> findByDietaryRestrictions_Id(Long dietaryRestrictionId);
}
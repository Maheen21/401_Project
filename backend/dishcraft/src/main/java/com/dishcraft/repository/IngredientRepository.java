package com.dishcraft.repository;

import com.dishcraft.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Find ingredient by name in the database
    Ingredient findByName(String name);
}

package com.dishcraft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RecipeIngredient is a join entity that manages the many-to-many relationship between Recipe and Ingredient.
 * It includes additional metadata such as quantity, unit of measurement, and whether the ingredient is required.
 */
@Entity
@Table(name = "recipe_ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredient {

    /**
     * Unique identifier for the RecipeIngredient entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to the Recipe that uses this ingredient.
     */
    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    /**
     * Reference to the Ingredient used in the recipe.
     */
    @ManyToOne
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    /**
     * The quantity of the ingredient required for the recipe.
     */
    @Column(nullable = false)
    private Double quantity;

    /**
     * The unit of measurement for the quantity (e.g., g, ml, cups).
     */
    private String unit;

    /**
     * Flag indicating if the ingredient is required (true) or optional (false).
     */
    @Column(nullable = false)
    private Boolean isRequired;
}

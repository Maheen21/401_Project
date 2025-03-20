package com.dishcraft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

/**
 * The Ingredient model represents individual ingredients used in recipes.
 * It includes fields such as name, category, description, and rank.
 * The rank field indicates the importance of the ingredient (e.g., "MAIN" for primary ingredients, "SUB" for secondary ingredients).
 * Additionally, this entity supports:
 * - A self-referencing many-to-many relationship for substitutes.
 * - A many-to-many relationship with DietaryRestriction.
 */
@Entity
@Table(name = "ingredients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    /**
     * Unique identifier for the ingredient.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the ingredient.
     * This field is required.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The category of the ingredient (e.g., vegetable, meat, spice).
     */
    private String category;

    /**
     * A brief description of the ingredient.
     */
    @Column(length = 500)
    private String description;

    /**
     * Rank or type of the ingredient indicating its importance within a recipe.
     * Example values: "MAIN" for core ingredients, "SUB" for secondary ingredients.
     */
    @Column(name = "ingredient_rank")
    private String rank;

    /**
     * Self-referencing many-to-many relationship to manage ingredient substitutes.
     * This allows the system to suggest alternate ingredients when necessary.
     */
    @ManyToMany
    @JoinTable(
        name = "ingredient_substitutes",
        joinColumns = @JoinColumn(name = "ingredient_id"),
        inverseJoinColumns = @JoinColumn(name = "substitute_id")
    )
    private Set<Ingredient> substitutes;

    /**
     * Many-to-many relationship with DietaryRestriction.
     * This indicates which dietary restrictions the ingredient complies with.
     */
    @ManyToMany
    @JoinTable(
        name = "ingredient_dietary_restrictions",
        joinColumns = @JoinColumn(name = "ingredient_id"),
        inverseJoinColumns = @JoinColumn(name = "dietary_restriction_id")
    )
    private Set<DietaryRestriction> dietaryRestrictions;

    @Override
    public int hashCode() {
        return Objects.hash(id); // use id only
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ingredient)) return false;
        Ingredient other = (Ingredient) obj;
        return Objects.equals(id, other.id); // use id only
    }
}

package com.dishcraft.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Set;

/**
 * The DietaryRestriction model defines various dietary restrictions such as Vegan, Gluten-Free, etc.
 * It is associated with both the User and Ingredient models via many-to-many relationships.
 */
@Entity
@Table(name = "dietary_restrictions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DietaryRestriction {

    /**
     * Unique identifier for the dietary restriction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the dietary restriction (e.g., Vegan, Gluten-Free).
     * This field is required and must be unique.
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The set of Users that have this dietary restriction.
     * Mapped by the "dietaryRestrictions" field in the User entity.
     */
    @ManyToMany(mappedBy = "dietaryRestrictions")
    private Set<User> users;

    /**
     * The set of Ingredients that comply with this dietary restriction.
     * Mapped by the "dietaryRestrictions" field in the Ingredient entity.
     */
    @ManyToMany(mappedBy = "dietaryRestrictions")
    private Set<Ingredient> ingredients;
}

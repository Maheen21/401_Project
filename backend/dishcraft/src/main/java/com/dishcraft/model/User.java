package com.dishcraft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/*
 * ## 1. User Model
 *
 * Description:
 *   The User model represents the core entity for user accounts within the application.
 *   - Core Fields:
 *         • id: A unique identifier for each user.
 *         • username, email, password: Basic user data and security credentials.
 *   - Relationships:
 *         • Many-to-many relationship with DietaryRestriction (to manage user dietary restrictions).
 *         • Many-to-many relationship with Recipe (to manage favorite recipes).
 *         • One-to-many relationship with Feedback (to store user reviews and ratings).
 *   - Role:
 *         • Handles user authentication, profile management, and the provision of personalized services.
 */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /*
     * User ID
     * - Primary key to uniquely identify the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Username
     * - The user's display name.
     * - Mandatory and must be unique.
     */
    @Column(nullable = false, unique = true)
    private String username;

    /*
     * Email
     * - The user's email address.
     * - Mandatory and must be unique.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Password
     * - The user's encrypted password.
     * - Mandatory for user authentication.
     */
    @Column(nullable = false)
    private String password;

    /*
     * role
     * - The user's role.
     * - Stored as a string representation of the Role enum
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    /*
     * Dietary Restrictions
     * - Manages the many-to-many relationship between User and DietaryRestriction.
     * - Stores the dietary restrictions associated with the user.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_dietary_restrictions",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "dietary_restriction_id")
    )
    private Set<DietaryRestriction> dietaryRestrictions = new HashSet<>();

    /*
     * Favorite Recipes
     * - Manages the many-to-many relationship between User and Recipe.
     * - Stores the recipes favorited by the user.
     */
//  @ManyToMany
//  @JoinTable(
//      name = "user_favorite_recipes",
//      joinColumns = @JoinColumn(name = "user_id"),
//      inverseJoinColumns = @JoinColumn(name = "recipe_id")
//  )
//  private Set<Recipe> favoriteRecipes;

    /*
     * Feedbacks
     * - Represents the one-to-many relationship between User and Feedback.
     * - Contains the reviews and ratings submitted by the user.
     */
//  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<Feedback> feedbacks;
    
    // Safely get dietary restrictions to avoid concurrent modification
    public Set<DietaryRestriction> getDietaryRestrictionsReadOnly() {
        return Collections.unmodifiableSet(dietaryRestrictions);
    }
}
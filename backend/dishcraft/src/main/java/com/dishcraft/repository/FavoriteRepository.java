package com.dishcraft.repository;

import com.dishcraft.model.Favorite;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing Favorite entities.
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    /**
     * Find all favorites for a specific user.
     *
     * @param user the user whose favorites to find
     * @return a list of user's favorite recipes
     */
    List<Favorite> findByUser(User user);
    
    /**
     * Find all users who have favorited a specific recipe.
     *
     * @param recipe the recipe to check for favorites
     * @return a list of favorites for the recipe
     */
    List<Favorite> findByRecipe(Recipe recipe);
    
    /**
     * Find a specific favorite by user and recipe.
     *
     * @param user the user
     * @param recipe the recipe
     * @return an Optional containing the favorite if found, or empty if not found
     */
    Optional<Favorite> findByUserAndRecipe(User user, Recipe recipe);
    
    /**
     * Delete a favorite by user and recipe.
     *
     * @param user the user
     * @param recipe the recipe
     */
    void deleteByUserAndRecipe(User user, Recipe recipe);
    
    /**
     * Check if a favorite exists for a user and recipe.
     *
     * @param user the user
     * @param recipe the recipe
     * @return true if the favorite exists, false otherwise
     */
    boolean existsByUserAndRecipe(User user, Recipe recipe);
}
package com.dishcraft.repository;

import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing DietaryRestriction entities.
 * This interface provides CRUD operations for the DietaryRestriction model.
 */
@Repository
public interface DietaryRestrictionRepository extends JpaRepository<DietaryRestriction, Long> {
    
    /**
     * Find all dietary restrictions for a specific user.
     *
     * @param user the user whose dietary restrictions to find
     * @return a list of user's dietary restrictions
     */
    @Query("SELECT dr FROM DietaryRestriction dr JOIN dr.users u WHERE u = :user")
    List<DietaryRestriction> findByUser(@Param("user") User user);
    
    /**
     * Check if a user has a specific dietary restriction.
     *
     * @param user the user
     * @param dietaryRestrictionId the dietary restriction ID
     * @return true if the user has this dietary restriction, false otherwise
     */
    @Query("SELECT COUNT(dr) > 0 FROM DietaryRestriction dr JOIN dr.users u WHERE u = :user AND dr.id = :dietaryRestrictionId")
    boolean existsByUserAndDietaryRestrictionId(@Param("user") User user, @Param("dietaryRestrictionId") Long dietaryRestrictionId);
    
    /**
     * Delete the relationship between a user and a dietary restriction.
     *
     * @param userId the user ID
     * @param dietaryRestrictionId the dietary restriction ID
     * @return the number of deleted relationships
     */
    @Modifying
    @Query(value = "DELETE FROM user_dietary_restrictions WHERE user_id = :userId AND dietary_restriction_id = :dietaryRestrictionId", nativeQuery = true)
    int deleteUserDietaryRestriction(@Param("userId") Long userId, @Param("dietaryRestrictionId") Long dietaryRestrictionId);
}
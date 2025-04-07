package com.dishcraft.service;

import com.dishcraft.dto.DietaryRestrictionDto;
import java.util.List;

/**
 * DietaryRestrictionService interface defines operations for managing dietary restrictions.
 */
public interface DietaryRestrictionService {
    
    /**
     * Retrieve all dietary restrictions.
     * 
     * @return A list of all dietary restrictions as DTOs.
     */
    List<DietaryRestrictionDto> getAllDietaryRestrictions();
    
    /**
     * Retrieve dietary restrictions for the current authenticated user.
     * 
     * @return A list of dietary restrictions for the current user.
     */
    List<DietaryRestrictionDto> getCurrentUserDietaryRestrictions();
    
    /**
     * Add a dietary restriction to the current user's profile.
     * 
     * @param dietaryRestrictionId The ID of the dietary restriction to add
     * @return The added dietary restriction
     */
    DietaryRestrictionDto addDietaryRestrictionToCurrentUser(Long dietaryRestrictionId);
    
    /**
     * Remove a dietary restriction from the current user's profile.
     * 
     * @param dietaryRestrictionId The ID of the dietary restriction to remove
     * @return true if successfully removed, false otherwise
     */
    boolean removeDietaryRestrictionFromCurrentUser(Long dietaryRestrictionId);
    
    /**
     * Check if the current user has a specific dietary restriction.
     *
     * @param dietaryRestrictionId the ID of the dietary restriction to check
     * @return true if the user has this dietary restriction, false otherwise
     */
    boolean hasDietaryRestriction(Long dietaryRestrictionId);
}
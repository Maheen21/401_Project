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
}
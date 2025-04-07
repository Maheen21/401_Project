package com.dishcraft.service.impl;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.mapper.DietaryRestrictionMapperUtil;
import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.model.User;
import com.dishcraft.repository.DietaryRestrictionRepository;
import com.dishcraft.repository.UserRepository;
import com.dishcraft.service.CurrentUserService;
import com.dishcraft.service.DietaryRestrictionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the DietaryRestrictionService interface.
 * This service provides operations for managing dietary restrictions.
 */
@Service
public class DietaryRestrictionServiceImpl implements DietaryRestrictionService {

    private final DietaryRestrictionRepository dietaryRestrictionRepository;
    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DietaryRestrictionServiceImpl(DietaryRestrictionRepository dietaryRestrictionRepository,
                                         CurrentUserService currentUserService,
                                         UserRepository userRepository,
                                         JdbcTemplate jdbcTemplate) {
        this.dietaryRestrictionRepository = dietaryRestrictionRepository;
        this.currentUserService = currentUserService;
        this.userRepository = userRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Retrieve all dietary restrictions sorted by ID.
     * 
     * @return A list of all dietary restriction DTOs sorted by ID.
     */
    @Override
    public List<DietaryRestrictionDto> getAllDietaryRestrictions() {
        // Retrieve dietary restrictions sorted by ID in ascending order
        List<DietaryRestriction> dietaryRestrictions = dietaryRestrictionRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        
        return dietaryRestrictions.stream()
                .map(DietaryRestrictionMapperUtil::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieve dietary restrictions for the current authenticated user.
     * 
     * @return A list of dietary restrictions for the current user.
     */
    @Override
    @Transactional
    public List<DietaryRestrictionDto> getCurrentUserDietaryRestrictions() {
        try {
            // Get the current authenticated user
            User currentUser = currentUserService.getCurrentUser();
            
            // Query the repository directly to avoid collection issues
            List<DietaryRestriction> restrictions = dietaryRestrictionRepository.findByUser(currentUser);
            
            // Convert to DTOs
            return restrictions.stream()
                    .map(DietaryRestrictionMapperUtil::toDto)
                    .collect(Collectors.toList());
        } catch (IllegalStateException e) {
            // If no authenticated user is found, return an empty list
            return List.of();
        }
    }

    /**
     * Add a dietary restriction to the current user's profile.
     * 
     * @param dietaryRestrictionId The ID of the dietary restriction to add
     * @return The added dietary restriction
     * @throws IllegalStateException if no authenticated user is found
     * @throws RuntimeException if the dietary restriction does not exist
     */
    @Override
    @Transactional
    public DietaryRestrictionDto addDietaryRestrictionToCurrentUser(Long dietaryRestrictionId) {
        User currentUser = currentUserService.getCurrentUser();
        
        DietaryRestriction dietaryRestriction = dietaryRestrictionRepository.findById(dietaryRestrictionId)
                .orElseThrow(() -> new RuntimeException("Dietary restriction not found with id: " + dietaryRestrictionId));
        
        // Check if the user already has this restriction
        if (!dietaryRestrictionRepository.existsByUserAndDietaryRestrictionId(currentUser, dietaryRestrictionId)) {
            // Use direct JDBC instead of EntityManager for better GraalVM compatibility
            jdbcTemplate.update(
                "INSERT INTO user_dietary_restrictions (user_id, dietary_restriction_id) VALUES (?, ?)",
                currentUser.getId(), dietaryRestrictionId);
        }
        
        return DietaryRestrictionMapperUtil.toDto(dietaryRestriction);
    }
    
    /**
     * Remove a dietary restriction from the current user's profile.
     * 
     * @param dietaryRestrictionId The ID of the dietary restriction to remove
     * @return true if successfully removed, false if not found in user's restrictions
     * @throws IllegalStateException if no authenticated user is found
     */
    @Override
    @Transactional
    public boolean removeDietaryRestrictionFromCurrentUser(Long dietaryRestrictionId) {
        User currentUser = currentUserService.getCurrentUser();
        
        // Check if the user has this restriction
        if (!dietaryRestrictionRepository.existsByUserAndDietaryRestrictionId(currentUser, dietaryRestrictionId)) {
            return false;
        }
        
        // Use the repository method to delete the relationship
        int deletedCount = dietaryRestrictionRepository.deleteUserDietaryRestriction(currentUser.getId(), dietaryRestrictionId);
        
        // Consider success if at least one row was deleted
        return deletedCount > 0;
    }

    /**
     * Check if the current user has a specific dietary restriction.
     *
     * @param dietaryRestrictionId the ID of the dietary restriction to check
     * @return true if the user has this dietary restriction, false otherwise
     */
    @Override
    public boolean hasDietaryRestriction(Long dietaryRestrictionId) {
        try {
            // Get the current authenticated user
            User currentUser = currentUserService.getCurrentUser();
            
            // Query the repository directly to check if the relationship exists
            return dietaryRestrictionRepository.existsByUserAndDietaryRestrictionId(currentUser, dietaryRestrictionId);
        } catch (Exception e) {
            // If there's any error (like no logged-in user), return false
            return false;
        }
    }
}
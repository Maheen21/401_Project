package com.dishcraft.service.impl;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.mapper.DietaryRestrictionMapperUtil;
import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.repository.DietaryRestrictionRepository;
import com.dishcraft.service.DietaryRestrictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    @Autowired
    public DietaryRestrictionServiceImpl(DietaryRestrictionRepository dietaryRestrictionRepository) {
        this.dietaryRestrictionRepository = dietaryRestrictionRepository;
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
}
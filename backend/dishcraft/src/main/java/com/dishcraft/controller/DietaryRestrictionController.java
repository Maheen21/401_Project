package com.dishcraft.controller;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.service.DietaryRestrictionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * DietaryRestrictionController exposes REST API endpoints for managing dietary restrictions.
 */
@RestController
@RequestMapping("/api/dietary-restrictions")
public class DietaryRestrictionController {

    private final DietaryRestrictionService dietaryRestrictionService;

    @Autowired
    public DietaryRestrictionController(DietaryRestrictionService dietaryRestrictionService) {
        this.dietaryRestrictionService = dietaryRestrictionService;
    }

    /**
     * Retrieve all dietary restrictions.
     *
     * @return a list of all dietary restrictions
     */
    @Operation(summary = "Get All Dietary Restrictions",
               description = "Returns a list of all dietary restrictions available in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all dietary restrictions")
    })
    @GetMapping
    public ResponseEntity<List<DietaryRestrictionDto>> getAllDietaryRestrictions() {
        List<DietaryRestrictionDto> dietaryRestrictions = dietaryRestrictionService.getAllDietaryRestrictions();
        return ResponseEntity.ok(dietaryRestrictions);
    }
}
package com.dishcraft.controller;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.service.DietaryRestrictionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    
    /**
     * Retrieve dietary restrictions for the current authenticated user.
     *
     * @return a list of the current user's dietary restrictions
     */
    @Operation(summary = "Get Current User's Dietary Restrictions",
               description = "Returns the dietary restrictions of the currently authenticated user.",
               security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user's dietary restrictions"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/current-user")
    public ResponseEntity<List<DietaryRestrictionDto>> getCurrentUserDietaryRestrictions() {
        List<DietaryRestrictionDto> dietaryRestrictions = dietaryRestrictionService.getCurrentUserDietaryRestrictions();
        return ResponseEntity.ok(dietaryRestrictions);
    }
    
    /**
     * Add a dietary restriction to current user's profile.
     *
     * @param dietaryRestrictionId ID of the dietary restriction to add
     * @return the added dietary restriction
     */
    @Operation(summary = "Add Dietary Restriction to Current User",
               description = "Adds a dietary restriction to the currently authenticated user's profile.",
               security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added dietary restriction to user"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Dietary restriction not found")
    })
    @PostMapping("/current-user/{dietaryRestrictionId}")
    public ResponseEntity<DietaryRestrictionDto> addDietaryRestrictionToCurrentUser(
            @PathVariable Long dietaryRestrictionId) {
        DietaryRestrictionDto added = dietaryRestrictionService.addDietaryRestrictionToCurrentUser(dietaryRestrictionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }
    
    /**
     * Remove a dietary restriction from current user's profile.
     *
     * @param dietaryRestrictionId ID of the dietary restriction to remove
     * @return success response
     */
    @Operation(summary = "Remove Dietary Restriction from Current User",
               description = "Removes a dietary restriction from the currently authenticated user's profile.",
               security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed dietary restriction"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Dietary restriction not found in user's profile")
    })
    @DeleteMapping("/current-user/{dietaryRestrictionId}")
    public ResponseEntity<Map<String, Boolean>> removeDietaryRestrictionFromCurrentUser(
            @PathVariable Long dietaryRestrictionId) {
        boolean removed = dietaryRestrictionService.removeDietaryRestrictionFromCurrentUser(dietaryRestrictionId);
        
        if (removed) {
            return ResponseEntity.ok(Map.of("removed", true));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("removed", false));
        }
    }
    
    /**
     * Check if a dietary restriction is in the current user's profile.
     *
     * @param dietaryRestrictionId ID of the dietary restriction to check
     * @return true if user has this dietary restriction, false otherwise
     */
    @Operation(summary = "Check if user has dietary restriction", 
               description = "Check if a dietary restriction is in the current user's profile",
               security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status check successful"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/has/{dietaryRestrictionId}")
    public ResponseEntity<Map<String, Boolean>> hasDietaryRestriction(@PathVariable Long dietaryRestrictionId) {
        boolean hasDietaryRestriction = dietaryRestrictionService.hasDietaryRestriction(dietaryRestrictionId);
        return ResponseEntity.ok(Map.of("has", hasDietaryRestriction));
    }
}
package com.dishcraft.repository;

import com.dishcraft.model.DietaryRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing DietaryRestriction entities.
 * This interface provides CRUD operations for the DietaryRestriction model.
 */
@Repository
public interface DietaryRestrictionRepository extends JpaRepository<DietaryRestriction, Long> {
    // Spring Data JPA will provide basic CRUD operations by default
}
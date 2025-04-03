package com.dishcraft.mapper;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.dto.RecipeDto;
import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.model.Recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeMapperUtil {

    public static RecipeDto toDto(Recipe entity) {
        if (entity == null) return null;

        RecipeDto dto = new RecipeDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setInstruction(entity.getInstruction());
        dto.setCookingTime(entity.getCookingTime());
        dto.setImageUrl(entity.getImageUrl());
        
        // Map recipeIngredients
        if (entity.getRecipeIngredients() != null) {
            dto.setRecipeIngredients(
                RecipeIngredientMapperUtil.toDtoList(entity.getRecipeIngredients())
            );
            
            // Calculate and set dietary restrictions
            Set<DietaryRestriction> allDietaryRestrictions = new HashSet<>();
            entity.getRecipeIngredients().forEach(ri -> {
                if (ri.getIngredient() != null && ri.getIngredient().getDietaryRestrictions() != null) {
                    allDietaryRestrictions.addAll(ri.getIngredient().getDietaryRestrictions());
                }
            });
            dto.setDietaryRestrictions(DietaryRestrictionMapperUtil.toDtoList(allDietaryRestrictions));
        }
        
        return dto;
    }

    public static List<RecipeDto> toDtoList(List<Recipe> entities) {
        if (entities == null) return new ArrayList<>();
        
        return entities.stream()
                .map(RecipeMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    public static Recipe toEntity(RecipeDto dto) {
        if (dto == null) return null;

        Recipe entity = new Recipe();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInstruction(dto.getInstruction());
        entity.setCookingTime(dto.getCookingTime());
        entity.setImageUrl(dto.getImageUrl());
        
        // RecipeIngredient relationship need special handling
        // noremally handled in service layer
        
        return entity;
    }

    public static void updateEntityFromDto(RecipeDto dto, Recipe entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setInstruction(dto.getInstruction());
        entity.setCookingTime(dto.getCookingTime());
        entity.setImageUrl(dto.getImageUrl());
        
        // RecipeIngredient relationship need special handling
        // noremally handled in service layer
    }
}
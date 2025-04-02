package com.dishcraft.mapper;

import com.dishcraft.dto.RecipeIngredientDto;
import com.dishcraft.model.Ingredient;
import com.dishcraft.model.Recipe;
import com.dishcraft.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeIngredientMapperUtil {

    public static RecipeIngredientDto toDto(RecipeIngredient entity) {
        if (entity == null) return null;

        RecipeIngredientDto dto = new RecipeIngredientDto();
        if (entity.getIngredient() != null) {
            dto.setIngredientId(entity.getIngredient().getId());
            dto.setName(entity.getIngredient().getName());
        }
        dto.setQuantity(entity.getQuantity());
        dto.setUnit(entity.getUnit());
        dto.setIsRequired(entity.getIsRequired());
        
        return dto;
    }

    public static List<RecipeIngredientDto> toDtoList(List<RecipeIngredient> entities) {
        if (entities == null) return new ArrayList<>();
        
        return entities.stream()
                .map(RecipeIngredientMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    public static RecipeIngredient toEntity(RecipeIngredientDto dto, Recipe recipe, Ingredient ingredient) {
        if (dto == null) return null;

        RecipeIngredient entity = new RecipeIngredient();
        entity.setRecipe(recipe);
        entity.setIngredient(ingredient);
        entity.setQuantity(dto.getQuantity());
        entity.setUnit(dto.getUnit());
        entity.setIsRequired(dto.getIsRequired());
        
        return entity;
    }

    public static void updateEntityFromDto(RecipeIngredientDto dto, RecipeIngredient entity, Ingredient ingredient) {
        if (dto == null || entity == null) return;

        if (ingredient != null) {
            entity.setIngredient(ingredient);
        }
        entity.setQuantity(dto.getQuantity());
        entity.setUnit(dto.getUnit());
        entity.setIsRequired(dto.getIsRequired());
    }
}
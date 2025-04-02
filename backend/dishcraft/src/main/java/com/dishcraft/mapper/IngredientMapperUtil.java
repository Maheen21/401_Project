package com.dishcraft.mapper;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.dto.IngredientDto;
import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientMapperUtil {

    public static IngredientDto toDto(Ingredient ingredient) {
        if (ingredient == null) return null;

        IngredientDto dto = new IngredientDto();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setCategory(ingredient.getCategory());
        dto.setDescription(ingredient.getDescription());
        dto.setRank(ingredient.getRank());

        // Map dietaryRestrictions (Set<DietaryRestriction> → List<DietaryRestrictionDto>)
        dto.setDietaryRestrictions(
                DietaryRestrictionMapperUtil.toDtoList(ingredient.getDietaryRestrictions())
        );

        return dto;
    }

    public static List<IngredientDto> toDtoList(List<Ingredient> ingredients) {
        if (ingredients == null) return new ArrayList<>();
        return ingredients.stream()
                .map(IngredientMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    public static Ingredient toEntity(IngredientDto dto) {
        if (dto == null) return null;

        Ingredient entity = new Ingredient();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        entity.setRank(dto.getRank());

        // Map dietaryRestrictions (List<DietaryRestrictionDto> → Set<DietaryRestriction>)
        entity.setDietaryRestrictions(
                DietaryRestrictionMapperUtil.toEntitySet(dto.getDietaryRestrictions())
        );

        return entity;
    }

    public static void updateEntityFromDto(IngredientDto dto, Ingredient entity) {
        if (dto == null || entity == null) return;

        entity.setName(dto.getName());
        entity.setCategory(dto.getCategory());
        entity.setDescription(dto.getDescription());
        entity.setRank(dto.getRank());
        entity.setDietaryRestrictions(
                DietaryRestrictionMapperUtil.toEntitySet(dto.getDietaryRestrictions())
        );
    }
}

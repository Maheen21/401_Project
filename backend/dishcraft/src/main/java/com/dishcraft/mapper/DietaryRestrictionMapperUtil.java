package com.dishcraft.mapper;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.model.DietaryRestriction;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DietaryRestrictionMapperUtil {

    public static DietaryRestrictionDto toDto(DietaryRestriction entity) {
        if (entity == null) return null;

        DietaryRestrictionDto dto = new DietaryRestrictionDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    public static DietaryRestriction toEntity(DietaryRestrictionDto dto) {
        if (dto == null) return null;

        DietaryRestriction entity = new DietaryRestriction();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        return entity;
    }

    public static List<DietaryRestrictionDto> toDtoList(Set<DietaryRestriction> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(DietaryRestrictionMapperUtil::toDto)
                .collect(Collectors.toList());
    }

    public static Set<DietaryRestriction> toEntitySet(List<DietaryRestrictionDto> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(DietaryRestrictionMapperUtil::toEntity)
                .collect(Collectors.toSet());
    }
}

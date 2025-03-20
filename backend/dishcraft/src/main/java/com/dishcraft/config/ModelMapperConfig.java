package com.dishcraft.config;

import com.dishcraft.dto.DietaryRestrictionDto;
import com.dishcraft.dto.IngredientDto;
import com.dishcraft.model.DietaryRestriction;
import com.dishcraft.model.Ingredient;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setCollectionsMergeEnabled(false);

        // set to list converter
        Converter<Set<Object>, List<Object>> setToListConverter = context -> {
            Set<Object> source = context.getSource();
            return (source == null) ? null : new ArrayList<>(source);
        };
        modelMapper.addConverter(setToListConverter);

        // Ingredient to IngredientDto mapping configuration to avoid infinite recursion
        modelMapper.typeMap(Ingredient.class, IngredientDto.class)
            .addMappings(mapping -> mapping.skip(IngredientDto::setDietaryRestrictions))
            .setPostConverter(context -> {
                Ingredient source = context.getSource();
                IngredientDto destination = context.getDestination();

                List<DietaryRestrictionDto> dietaryRestrictionDtos = source.getDietaryRestrictions().stream()
                        .map(dietaryRestriction -> {
                            DietaryRestrictionDto dto = new DietaryRestrictionDto();
                            dto.setId(dietaryRestriction.getId());
                            dto.setName(dietaryRestriction.getName());
                            return dto;
                        })
                        .collect(Collectors.toList());

                destination.setDietaryRestrictions(dietaryRestrictionDtos);

                return destination;
            });

        return modelMapper;
    }
}

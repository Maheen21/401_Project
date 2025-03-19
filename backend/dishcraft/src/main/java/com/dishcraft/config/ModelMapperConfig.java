package com.dishcraft.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Configuration class for setting up common beans.
 * This class defines a ModelMapper bean which can be used across the application
 * for mapping between entities and DTOs.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setCollectionsMergeEnabled(false);

        // Converter to convert a Set to a List
        Converter<Set<Object>, List<Object>> setToListConverter = new Converter<Set<Object>, List<Object>>() {
            @Override
            public List<Object> convert(MappingContext<Set<Object>, List<Object>> context) {
                Set<Object> source = context.getSource();
                return (source == null) ? null : new ArrayList<>(source);
            }
        };

        // Register converter for converting from Set to List
        modelMapper.addConverter(setToListConverter);
        return modelMapper;
    }
}
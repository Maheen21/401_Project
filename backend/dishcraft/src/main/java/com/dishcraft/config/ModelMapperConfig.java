package com.dishcraft.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up common beans.
 * This class defines a ModelMapper bean which can be used across the application
 * for mapping between entities and DTOs.
 */
@Configuration
public class ModelMapperConfig {

    /**
     * Creates a ModelMapper bean to be used for object mapping.
     *
     * @return a new ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

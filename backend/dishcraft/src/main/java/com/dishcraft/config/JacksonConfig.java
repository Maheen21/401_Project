package com.dishcraft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Jackson configuration for proper serialization in GraalVM native image
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Configure visibility to use field-level access rather than getters/setters
        // This helps with GraalVM native image serialization
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        
        // Prevent failures on empty beans
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        
        // Register JavaTimeModule to handle Java 8 date/time types like LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        // Configure dates to be serialized as strings rather than timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        return objectMapper;
    }
}
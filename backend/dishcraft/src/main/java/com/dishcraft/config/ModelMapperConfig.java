package com.dishcraft.config;

import org.springframework.context.annotation.Configuration;

/**
 * This class previously contained ModelMapper configuration.
 * It has been refactored to use custom mappers instead of ModelMapper
 * to support GraalVM native image compilation.
 */
@Configuration
public class ModelMapperConfig {
    // ModelMapper configuration removed - using custom mappers instead
}

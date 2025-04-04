package com.dishcraft.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Logs the application startup time when the ApplicationReadyEvent is triggered.
 * This is useful for monitoring cold start performance improvements.
 */
@Component
public class StartupTimeLogger implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(StartupTimeLogger.class);
    private final long startTime;

    public StartupTimeLogger() {
        // Capture the time when the bean is instantiated, which is very early in the startup process
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        long startupTime = System.currentTimeMillis() - startTime;
        
        // Create a highly visible log message
        String separatorLine = "=".repeat(60);
        
        logger.info(separatorLine);
        logger.info("🚀 APPLICATION STARTUP COMPLETED in {} ms", startupTime);
        logger.info("   Runtime type: {}", isRunningAsNative() ? "NATIVE IMAGE" : "JVM");
        logger.info(separatorLine);
    }

    /**
     * Determines if the application is running as a GraalVM native image.
     * 
     * @return true if running as native image, false if running on JVM
     */
    private boolean isRunningAsNative() {
        // The presence of this system property indicates a native image
        return System.getProperty("org.graalvm.nativeimage.imagecode") != null;
    }
}
package com.plazoleta.restaurants.infrastructure.configuration;

import feign.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeignConfigurationTest {

    private FeignConfiguration feignConfiguration;

    @BeforeEach
    void setUp() {
        feignConfiguration = new FeignConfiguration();
    }

    @Test
    void shouldCreateFeignConfiguration() {
        // When
        FeignConfiguration config = new FeignConfiguration();

        // Then
        assertNotNull(config);
    }

    @Test
    void shouldReturnBasicLoggerLevel() {
        // When
        Logger.Level loggerLevel = feignConfiguration.feignLoggerLevel();

        // Then
        assertNotNull(loggerLevel);
        assertEquals(Logger.Level.BASIC, loggerLevel);
    }

    @Test
    void shouldReturnSameLoggerLevelOnMultipleCalls() {
        // When
        Logger.Level loggerLevel1 = feignConfiguration.feignLoggerLevel();
        Logger.Level loggerLevel2 = feignConfiguration.feignLoggerLevel();

        // Then
        assertNotNull(loggerLevel1);
        assertNotNull(loggerLevel2);
        assertEquals(loggerLevel1, loggerLevel2);
        assertEquals(Logger.Level.BASIC, loggerLevel1);
        assertEquals(Logger.Level.BASIC, loggerLevel2);
    }
}
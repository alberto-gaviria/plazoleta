package com.plazoleta.restaurants.adapters.driven.messaging.client;

import feign.Logger;
import feign.Request;
import feign.Retryer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class MessagingServiceClientConfigTest {

    private MessagingServiceClientConfig config;

    @BeforeEach
    void setUp() throws Exception {
        config = new MessagingServiceClientConfig();

        // Inyección manual usando reflexión
        Field timeoutField = MessagingServiceClientConfig.class.getDeclaredField("timeout");
        timeoutField.setAccessible(true);
        timeoutField.set(config, 30000);

        Field retryField = MessagingServiceClientConfig.class.getDeclaredField("retryAttempts");
        retryField.setAccessible(true);
        retryField.set(config, 3);
    }

    @Test
    void testOptionsBean() {
        Request.Options options = config.options();
        assertNotNull(options);
    }

    @Test
    void testRetryerBean() {
        Retryer retryer = config.retryer();
        assertNotNull(retryer);
    }

    @Test
    void testLoggerLevelBean() {
        Logger.Level level = config.feignLoggerLevel();
        assertEquals(Logger.Level.BASIC, level);
    }
}

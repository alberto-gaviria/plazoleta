package com.plazoleta.messaging.infrastructure.configuration.exceptionhandler;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void defaultConstructor_ShouldInitializeTimestamp() {
        // When
        ErrorResponse errorResponse = new ErrorResponse();

        // Then
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    void parameterizedConstructor_ShouldInitializeAllFields() {
        // When
        ErrorResponse errorResponse = new ErrorResponse("Test message", "TEST_ERROR", 400, "/test/path");

        // Then
        assertEquals("Test message", errorResponse.getMessage());
        assertEquals("TEST_ERROR", errorResponse.getError());
        assertEquals(400, errorResponse.getStatus());
        assertEquals("/test/path", errorResponse.getPath());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse();
        LocalDateTime now = LocalDateTime.now();

        // When
        errorResponse.setMessage("Test message");
        errorResponse.setError("TEST_ERROR");
        errorResponse.setStatus(500);
        errorResponse.setPath("/test/path");
        errorResponse.setTimestamp(now);

        // Then
        assertEquals("Test message", errorResponse.getMessage());
        assertEquals("TEST_ERROR", errorResponse.getError());
        assertEquals(500, errorResponse.getStatus());
        assertEquals("/test/path", errorResponse.getPath());
        assertEquals(now, errorResponse.getTimestamp());
    }
}
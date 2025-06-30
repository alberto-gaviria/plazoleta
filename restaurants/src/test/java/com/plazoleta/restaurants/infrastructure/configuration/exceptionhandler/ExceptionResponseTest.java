package com.plazoleta.restaurants.infrastructure.configuration.exceptionhandler;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionResponseTest {

    @Test
    void shouldCreateExceptionResponseWithAllFields() {
        // Given
        String message = "Error message";
        String status = "BAD_REQUEST";
        LocalDateTime timestamp = LocalDateTime.now();

        // When
        ExceptionResponse response = new ExceptionResponse(message, status, timestamp);

        // Then
        assertEquals(message, response.getMessage());
        assertEquals(status, response.getStatus());
        assertEquals(timestamp, response.getTimestamp());
    }

    @Test
    void shouldGetMessage() {
        // Given
        String message = "Test message";
        ExceptionResponse response = new ExceptionResponse(message, "STATUS", LocalDateTime.now());

        // When
        String result = response.getMessage();

        // Then
        assertEquals(message, result);
    }

    @Test
    void shouldGetStatus() {
        // Given
        String status = "NOT_FOUND";
        ExceptionResponse response = new ExceptionResponse("Message", status, LocalDateTime.now());

        // When
        String result = response.getStatus();

        // Then
        assertEquals(status, result);
    }

    @Test
    void shouldGetTimestamp() {
        // Given
        LocalDateTime timestamp = LocalDateTime.now();
        ExceptionResponse response = new ExceptionResponse("Message", "STATUS", timestamp);

        // When
        LocalDateTime result = response.getTimestamp();

        // Then
        assertEquals(timestamp, result);
    }

    @Test
    void shouldHandleNullValues() {
        // When
        ExceptionResponse response = new ExceptionResponse(null, null, null);

        // Then
        assertNull(response.getMessage());
        assertNull(response.getStatus());
        assertNull(response.getTimestamp());
    }

    @Test
    void shouldCreateResponseWithCurrentTimestamp() {
        // Given
        LocalDateTime before = LocalDateTime.now();

        // When
        ExceptionResponse response = new ExceptionResponse("Message", "STATUS", LocalDateTime.now());

        // Then
        assertNotNull(response.getTimestamp());
        assertTrue(response.getTimestamp().isAfter(before.minusSeconds(1)));
    }
}
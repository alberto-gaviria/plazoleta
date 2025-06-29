package com.plazoleta.users.infrastructure.configuration.exceptionhandler;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionResponseTest {

    @Test
    void testExceptionResponseCreation() {
        // Given
        String message = "Error de prueba";
        String status = "400 BAD_REQUEST";
        LocalDateTime timestamp = LocalDateTime.now();

        // When
        ExceptionResponse response = new ExceptionResponse(message, status, timestamp);

        // Then
        assertEquals(message, response.getMessage());
        assertEquals(status, response.getStatus());
        assertEquals(timestamp, response.getTimestamp());
    }

    @Test
    void testExceptionResponseGetters() {
        // Given
        String message = "Error interno del servidor";
        String status = "500 INTERNAL_SERVER_ERROR";
        LocalDateTime timestamp = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        // When
        ExceptionResponse response = new ExceptionResponse(message, status, timestamp);

        // Then
        assertEquals("Error interno del servidor", response.getMessage());
        assertEquals("500 INTERNAL_SERVER_ERROR", response.getStatus());
        assertEquals(LocalDateTime.of(2024, 1, 1, 12, 0, 0), response.getTimestamp());
    }
}
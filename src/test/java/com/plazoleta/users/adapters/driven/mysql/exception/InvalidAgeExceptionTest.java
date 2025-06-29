package com.plazoleta.users.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidAgeExceptionTest {

    @Test
    void testInvalidAgeExceptionWithMessage() {
        // Given
        String message = "El usuario debe ser mayor de 18 años";

        // When
        InvalidAgeException exception = new InvalidAgeException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testInvalidAgeExceptionWithDefaultMessage() {
        // When
        InvalidAgeException exception = new InvalidAgeException();

        // Then
        assertEquals("Edad inválida", exception.getMessage());
    }

    @Test
    void testInvalidAgeExceptionIsRuntimeException() {
        // Given
        InvalidAgeException exception = new InvalidAgeException();

        // Then
        assertTrue(exception instanceof RuntimeException);
    }
}

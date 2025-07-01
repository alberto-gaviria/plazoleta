package com.plazoleta.users.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidUserExceptionTest {

    @Test
    void testInvalidUsuarioExceptionWithMessage() {
        // Given
        String message = "El usuario no es válido";

        // When
        InvalidUsuarioException exception = new InvalidUsuarioException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testInvalidUsuarioExceptionIsDomainException() {
        // Given
        InvalidUsuarioException exception = new InvalidUsuarioException("Test message");

        // Then
        assertTrue(exception instanceof DomainException);
        assertTrue(exception instanceof RuntimeException);
    }
}

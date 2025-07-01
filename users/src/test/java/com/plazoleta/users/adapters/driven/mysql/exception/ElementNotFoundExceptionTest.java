package com.plazoleta.users.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementNotFoundExceptionTest {

    @Test
    void testElementNotFoundExceptionWithMessage() {
        // Given
        String message = "Elemento no encontrado con ID: 123";

        // When
        ElementNotFoundException exception = new ElementNotFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testElementNotFoundExceptionWithDefaultMessage() {
        // When
        ElementNotFoundException exception = new ElementNotFoundException();

        // Then
        assertEquals("Elemento no encontrado", exception.getMessage());
    }

    @Test
    void testElementNotFoundExceptionIsRuntimeException() {
        // Given
        ElementNotFoundException exception = new ElementNotFoundException();

        // Then
        assertTrue(exception instanceof RuntimeException);
    }
}
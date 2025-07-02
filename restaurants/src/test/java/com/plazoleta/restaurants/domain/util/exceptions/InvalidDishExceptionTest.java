package com.plazoleta.restaurants.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidDishExceptionTest {

    @Test
    void testExceptionWithMessage() {
        // Given
        String expectedMessage = "Error en el plato";

        // When
        InvalidDishException exception = new InvalidDishException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        assertTrue(exception instanceof DomainException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionInheritance() {
        // Given
        String message = "Test message";

        // When
        InvalidDishException exception = new InvalidDishException(message);

        // Then
        assertInstanceOf(DomainException.class, exception);
        assertInstanceOf(RuntimeException.class, exception);
    }
}
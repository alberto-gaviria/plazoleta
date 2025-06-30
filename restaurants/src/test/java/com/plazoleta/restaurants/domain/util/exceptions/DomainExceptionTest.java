package com.plazoleta.restaurants.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainExceptionTest {

    @Test
    void shouldCreateDomainExceptionWithMessage() {
        // Given
        String message = "Domain exception message";

        // Create concrete implementation for testing
        DomainException exception = new DomainException(message) {};

        // When & Then
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldBeAbstractClass() {
        // Then
        assertTrue(java.lang.reflect.Modifier.isAbstract(DomainException.class.getModifiers()));
    }
}
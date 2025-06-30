// InvalidRestaurantExceptionTest.java
package com.plazoleta.restaurants.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidRestaurantExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Given
        String message = "Invalid restaurant message";

        // When
        InvalidRestaurantException exception = new InvalidRestaurantException(message);

        // Then
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof DomainException);
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldExtendDomainException() {
        // Given
        InvalidRestaurantException exception = new InvalidRestaurantException("Test");

        // Then
        assertTrue(exception instanceof DomainException);
    }
}
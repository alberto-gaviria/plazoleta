package com.plazoleta.restaurants.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidOrderExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessage() {
        // Arrange
        String message = "Test exception message";

        // Act
        InvalidOrderException exception = new InvalidOrderException(message);

        // Assert
        assertEquals(message, exception.getMessage());
    }

    @Test
    void inheritance_ShouldExtendDomainException() {
        // Arrange
        InvalidOrderException exception = new InvalidOrderException("Test");

        // Act & Assert
        assertTrue(exception instanceof DomainException);
    }
}
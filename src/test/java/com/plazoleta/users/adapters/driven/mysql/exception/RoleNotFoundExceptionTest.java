package com.plazoleta.users.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleNotFoundExceptionTest {

    @Test
    void constructor_WithMessage_ShouldSetMessage() {
        // Given
        String message = "Custom role not found message";

        // When
        RoleNotFoundException exception = new RoleNotFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructor_WithoutMessage_ShouldUseDefaultMessage() {
        // When
        RoleNotFoundException exception = new RoleNotFoundException();

        // Then
        assertEquals("Rol no encontrado", exception.getMessage());
    }

    @Test
    void constructor_WithNullMessage_ShouldAcceptNull() {
        // When
        RoleNotFoundException exception = new RoleNotFoundException(null);

        // Then
        assertNull(exception.getMessage());
    }

    @Test
    void exceptionInheritance_ShouldBeRuntimeException() {
        // Given
        RoleNotFoundException exception = new RoleNotFoundException();

        // Then
        assertTrue(exception instanceof RuntimeException);
    }
}

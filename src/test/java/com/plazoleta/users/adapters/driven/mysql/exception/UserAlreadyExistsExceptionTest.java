package com.plazoleta.users.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAlreadyExistsExceptionTest {

    @Test
    void testUsuarioAlreadyExistsExceptionWithMessage() {
        // Given
        String message = "Ya existe un usuario con ese correo";

        // When
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testUsuarioAlreadyExistsExceptionWithDefaultMessage() {
        // When
        UserAlreadyExistsException exception = new UserAlreadyExistsException();

        // Then
        assertEquals("El usuario ya existe", exception.getMessage());
    }

    @Test
    void testUsuarioAlreadyExistsExceptionIsRuntimeException() {
        // Given
        UserAlreadyExistsException exception = new UserAlreadyExistsException();

        // Then
        assertTrue(exception instanceof RuntimeException);
    }
}
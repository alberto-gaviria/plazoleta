package com.plazoleta.users.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidCredentialsExceptionTest {

    @Test
    void constructor_WithMessage_ShouldCreateExceptionWithMessage() {
        // Given
        String expectedMessage = "Credenciales inválidas";

        // When
        InvalidCredentialsException exception = new InvalidCredentialsException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithNullMessage_ShouldCreateExceptionWithNullMessage() {
        // Given
        String nullMessage = null;

        // When
        InvalidCredentialsException exception = new InvalidCredentialsException(nullMessage);

        // Then
        assertNull(exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithEmptyMessage_ShouldCreateExceptionWithEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        InvalidCredentialsException exception = new InvalidCredentialsException(emptyMessage);

        // Then
        assertEquals(emptyMessage, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void exception_ShouldBeThrowable() {
        // Given
        String message = "Test credentials exception";

        // When & Then
        assertThrows(InvalidCredentialsException.class, () -> {
            throw new InvalidCredentialsException(message);
        });
    }

    @Test
    void exception_WhenThrown_ShouldMaintainMessage() {
        // Given
        String expectedMessage = "Usuario o contraseña incorrectos";

        // When & Then
        InvalidCredentialsException thrownException = assertThrows(
                InvalidCredentialsException.class,
                () -> { throw new InvalidCredentialsException(expectedMessage); }
        );

        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void exception_ShouldExtendRuntimeException() {
        // Given
        InvalidCredentialsException exception = new InvalidCredentialsException("test");

        // Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }
}
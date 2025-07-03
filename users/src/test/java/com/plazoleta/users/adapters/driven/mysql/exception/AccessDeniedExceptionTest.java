package com.plazoleta.users.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessDeniedExceptionTest {

    @Test
    void constructor_WithMessage_ShouldCreateExceptionWithMessage() {
        // Given
        String expectedMessage = "Acceso denegado";

        // When
        AccessDeniedException exception = new AccessDeniedException(expectedMessage);

        // Then
        assertEquals(expectedMessage, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithNullMessage_ShouldCreateExceptionWithNullMessage() {
        // Given
        String nullMessage = null;

        // When
        AccessDeniedException exception = new AccessDeniedException(nullMessage);

        // Then
        assertNull(exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithEmptyMessage_ShouldCreateExceptionWithEmptyMessage() {
        // Given
        String emptyMessage = "";

        // When
        AccessDeniedException exception = new AccessDeniedException(emptyMessage);

        // Then
        assertEquals(emptyMessage, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void exception_ShouldBeThrowable() {
        // Given
        String message = "Test access denied exception";

        // When & Then
        assertThrows(AccessDeniedException.class, () -> {
            throw new AccessDeniedException(message);
        });
    }

    @Test
    void exception_WhenThrown_ShouldMaintainMessage() {
        // Given
        String expectedMessage = "No tiene permisos para realizar esta acción";

        // When & Then
        AccessDeniedException thrownException = assertThrows(
                AccessDeniedException.class,
                () -> { throw new AccessDeniedException(expectedMessage); }
        );

        assertEquals(expectedMessage, thrownException.getMessage());
    }

    @Test
    void exception_ShouldExtendRuntimeException() {
        // Given
        AccessDeniedException exception = new AccessDeniedException("test");

        // Then
        assertTrue(exception instanceof RuntimeException);
        assertTrue(exception instanceof Exception);
        assertTrue(exception instanceof Throwable);
    }

    @Test
    void constructor_WithLongMessage_ShouldCreateExceptionWithFullMessage() {
        // Given
        String longMessage = "Este es un mensaje muy largo que describe en detalle por qué el acceso fue denegado. " +
                "Incluye información sobre los permisos requeridos, el usuario que intentó acceder, " +
                "y las políticas de seguridad que impidieron el acceso al recurso solicitado.";

        // When
        AccessDeniedException exception = new AccessDeniedException(longMessage);

        // Then
        assertEquals(longMessage, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void constructor_WithSpecialCharacters_ShouldCreateExceptionWithMessage() {
        // Given
        String messageWithSpecialChars = "Acceso denegado para: Usuario='José María', Recurso=/admin/configuración, Símbolos: ñáéíóú!@#$%";

        // When
        AccessDeniedException exception = new AccessDeniedException(messageWithSpecialChars);

        // Then
        assertEquals(messageWithSpecialChars, exception.getMessage());
        assertInstanceOf(RuntimeException.class, exception);
    }
}
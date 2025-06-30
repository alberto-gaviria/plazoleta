package com.plazoleta.restaurants.adapters.driven.feign.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeignUserExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultMessage() {
        // When
        FeignUserException exception = new FeignUserException();

        // Then
        assertNotNull(exception);
        assertEquals("Error en la comunicación con el servicio de usuarios", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        // Given
        String customMessage = "Error específico de prueba";

        // When
        FeignUserException exception = new FeignUserException(customMessage);

        // Then
        assertNotNull(exception);
        assertEquals(customMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        // Given
        String customMessage = "Error específico con causa";
        RuntimeException cause = new RuntimeException("Causa del error");

        // When
        FeignUserException exception = new FeignUserException(customMessage, cause);

        // Then
        assertNotNull(exception);
        assertEquals(customMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals("Causa del error", exception.getCause().getMessage());
    }

    @Test
    void shouldBeRuntimeException() {
        // When
        FeignUserException exception = new FeignUserException();

        // Then
        assertInstanceOf(RuntimeException.class, exception);
    }

    @Test
    void shouldPreserveStackTrace() {
        // Given
        Exception originalException = new IllegalArgumentException("Error original");

        // When
        FeignUserException exception = new FeignUserException("Error wrapper", originalException);

        // Then
        assertNotNull(exception.getStackTrace());
        assertEquals(originalException, exception.getCause());
    }

    @Test
    void shouldCreateExceptionWithNullMessage() {
        // When
        FeignUserException exception = new FeignUserException(null);

        // Then
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithEmptyMessage() {
        // When
        FeignUserException exception = new FeignUserException("");

        // Then
        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithNullCause() {
        // When
        FeignUserException exception = new FeignUserException("Test message", null);

        // Then
        assertNotNull(exception);
        assertEquals("Test message", exception.getMessage());
        assertNull(exception.getCause());
    }
}
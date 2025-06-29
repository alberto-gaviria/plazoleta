package com.plazoleta.users.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoDataFoundExceptionTest {

    @Test
    void testNoDataFoundExceptionWithMessage() {
        // Given
        String message = "No se encontraron usuarios";

        // When
        NoDataFoundException exception = new NoDataFoundException(message);

        // Then
        assertEquals(message, exception.getMessage());
    }

    @Test
    void testNoDataFoundExceptionWithDefaultMessage() {
        // When
        NoDataFoundException exception = new NoDataFoundException();

        // Then
        assertEquals("No se encontraron datos", exception.getMessage());
    }

    @Test
    void testNoDataFoundExceptionIsRuntimeException() {
        // Given
        NoDataFoundException exception = new NoDataFoundException();

        // Then
        assertTrue(exception instanceof RuntimeException);
    }
}
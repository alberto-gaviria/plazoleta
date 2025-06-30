package com.plazoleta.restaurants.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoDataFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultMessage() {
        // When
        NoDataFoundException exception = new NoDataFoundException();

        // Then
        assertEquals("No se encontraron datos", exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        // Given
        String customMessage = "No se encontraron datos específicos";

        // When
        NoDataFoundException exception = new NoDataFoundException(customMessage);

        // Then
        assertEquals(customMessage, exception.getMessage());
    }
}
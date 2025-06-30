package com.plazoleta.restaurants.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultMessage() {
        // When
        ElementNotFoundException exception = new ElementNotFoundException();

        // Then
        assertEquals("Elemento no encontrado", exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        // Given
        String customMessage = "Elemento específico no encontrado";

        // When
        ElementNotFoundException exception = new ElementNotFoundException(customMessage);

        // Then
        assertEquals(customMessage, exception.getMessage());
    }
}
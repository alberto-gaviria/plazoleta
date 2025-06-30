package com.plazoleta.restaurants.adapters.driven.mysql.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantAlreadyExistsExceptionTest {

    @Test
    void shouldCreateExceptionWithDefaultMessage() {
        // When
        RestaurantAlreadyExistsException exception = new RestaurantAlreadyExistsException();

        // Then
        assertEquals("El restaurante ya existe", exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithCustomMessage() {
        // Given
        String customMessage = "El restaurante ya existe con ese NIT";

        // When
        RestaurantAlreadyExistsException exception = new RestaurantAlreadyExistsException(customMessage);

        // Then
        assertEquals(customMessage, exception.getMessage());
    }
}
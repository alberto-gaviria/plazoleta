package com.plazoleta.restaurants.adapters.driven.mysql.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class AdapterConstantsTest {

    @Test
    void shouldNotInstantiateAdapterConstants() throws NoSuchMethodException {
        // Given
        Constructor<AdapterConstants> constructor = AdapterConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateErrorMessages() throws NoSuchMethodException {
        // Given
        Constructor<AdapterConstants.ErrorMessages> constructor =
                AdapterConstants.ErrorMessages.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
        // Then
        assertEquals("Ya existe un restaurante con ese NIT",
                AdapterConstants.ErrorMessages.RESTAURANT_NIT_DUPLICADO);
        assertEquals("Ya existe un restaurante con ese nombre",
                AdapterConstants.ErrorMessages.RESTAURANT_NOMBRE_DUPLICADO);
        assertEquals("No se encontró el restaurante solicitado",
                AdapterConstants.ErrorMessages.RESTAURANT_NO_ENCONTRADO);
        assertEquals("No se encontró el propietario solicitado",
                AdapterConstants.ErrorMessages.PROPIETARIO_NO_ENCONTRADO);
        assertEquals("No se encontró el plato solicitado",
                AdapterConstants.ErrorMessages.DISH_NO_ENCONTRADO);
    }
}
package com.plazoleta.restaurants.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void shouldNotInstantiateDomainConstants() throws NoSuchMethodException {
        // Given
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
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
    void shouldNotInstantiateRestaurantConstants() throws NoSuchMethodException {
        // Given
        Constructor<DomainConstants.Restaurant> constructor =
                DomainConstants.Restaurant.class.getDeclaredConstructor();
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
    void shouldHaveCorrectPatterns() {
        // Then
        assertEquals("^[0-9]+$", DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN);
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
        // Then
        assertEquals("El restaurante no puede ser nulo",
                DomainConstants.Restaurant.ERROR_RESTAURANT_NULO);
        assertEquals("El nombre es obligatorio",
                DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO);
        assertEquals("El NIT es obligatorio",
                DomainConstants.Restaurant.ERROR_NIT_REQUERIDO);
        assertEquals("La dirección es obligatoria",
                DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA);
        assertEquals("El teléfono es obligatorio",
                DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO);
        assertEquals("La URL del logo es obligatoria",
                DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA);
        assertEquals("El ID del propietario es obligatorio",
                DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO);
        assertEquals("El ID del propietario no corresponde a un usuario con rol propietario",
                DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO);
        assertEquals("El nombre del restaurante no puede contener sólo números",
                DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS);
    }
}
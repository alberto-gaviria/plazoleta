package com.plazoleta.restaurants.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void shouldNotInstantiateHttpConstants() throws NoSuchMethodException {
        // Given
        Constructor<HttpConstants> constructor = HttpConstants.class.getDeclaredConstructor();
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
    void shouldNotInstantiateRestaurantValidation() throws NoSuchMethodException {
        // Given
        Constructor<HttpConstants.RestaurantValidation> constructor =
                HttpConstants.RestaurantValidation.class.getDeclaredConstructor();
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
    void shouldHaveCorrectValidationPatterns() {
        // Then
        assertEquals("^[0-9]+$", HttpConstants.RestaurantValidation.NIT_PATTERN);
        assertEquals("^\\+?[0-9]{1,13}$", HttpConstants.RestaurantValidation.TELEFONO_PATTERN);
    }

    @Test
    void shouldHaveCorrectValidationMessages() {
        // Then
        assertEquals("El nombre es obligatorio",
                HttpConstants.RestaurantValidation.NOMBRE_REQUERIDO);
        assertEquals("El NIT es obligatorio",
                HttpConstants.RestaurantValidation.NIT_REQUERIDO);
        assertEquals("El NIT debe ser únicamente numérico",
                HttpConstants.RestaurantValidation.NIT_NUMERICO);
        assertEquals("La dirección es obligatoria",
                HttpConstants.RestaurantValidation.DIRECCION_REQUERIDA);
        assertEquals("El teléfono es obligatorio",
                HttpConstants.RestaurantValidation.TELEFONO_REQUERIDO);
        assertEquals("El teléfono debe contener un máximo de 13 caracteres y puede contener el símbolo +",
                HttpConstants.RestaurantValidation.TELEFONO_FORMATO);
        assertEquals("La URL del logo es obligatoria",
                HttpConstants.RestaurantValidation.URL_LOGO_REQUERIDA);
        assertEquals("El ID del propietario es obligatorio",
                HttpConstants.RestaurantValidation.ID_PROPIETARIO_REQUERIDO);
    }
}
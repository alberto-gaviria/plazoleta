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
        assertEquals("^\\+?[0-9]{1,13}$", DomainConstants.Restaurant.TELEFONO_PATTERN);
    }

    @Test
    void shouldHaveCorrectRoles() {
        // Then
        assertEquals("PROPIETARIO", DomainConstants.Restaurant.ROL_PROPIETARIO);
        assertEquals("ADMINISTRADOR", DomainConstants.Restaurant.ROL_ADMINISTRADOR);
        assertEquals(Long.valueOf(1L), DomainConstants.Restaurant.ROL_ADMINISTRADOR_ID);
        assertEquals(Long.valueOf(2L), DomainConstants.Restaurant.ROL_PROPIETARIO_ID);
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
        // Restaurant validation errors
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

        // Propietario validation errors
        assertEquals("El ID del propietario no corresponde a un usuario con rol propietario",
                DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO);
        assertEquals("No se encontró el usuario propietario especificado",
                DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO);

        // Business rules validation errors
        assertEquals("El nombre del restaurante no puede contener sólo números",
                DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS);
        assertEquals("El NIT debe contener únicamente números",
                DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO);
        assertEquals("El teléfono debe contener máximo 13 caracteres numéricos y puede incluir el símbolo +",
                DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO);

        // Admin validation errors
        assertEquals("El ID del administrador es obligatorio",
                DomainConstants.Restaurant.ERROR_ADMIN_ID_REQUERIDO);
        assertEquals("No se encontró el usuario administrador especificado",
                DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_ENCONTRADO);
        assertEquals("El ID del administrador no corresponde a un usuario con rol administrador",
                DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_VALIDO);
    }
}
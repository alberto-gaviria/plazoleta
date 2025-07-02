package com.plazoleta.restaurants.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void domainConstants_ShouldNotBeInstantiable() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void restaurantConstants_ShouldNotBeInstantiable() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<DomainConstants.Restaurant> constructor = DomainConstants.Restaurant.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void dishConstants_ShouldNotBeInstantiable() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<DomainConstants.Dish> constructor = DomainConstants.Dish.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void restaurantConstants_ShouldHaveCorrectPatterns() {
        // Then
        assertEquals("^[0-9]+$", DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN);
        assertEquals("^\\+?[0-9]{1,13}$", DomainConstants.Restaurant.TELEFONO_PATTERN);
        assertEquals("PROPIETARIO", DomainConstants.Restaurant.ROL_PROPIETARIO);
    }

    @Test
    void restaurantConstants_ShouldHaveCorrectErrorMessages() {
        // Then
        assertEquals("El restaurante no puede ser nulo", DomainConstants.Restaurant.ERROR_RESTAURANT_NULO);
        assertEquals("El nombre es obligatorio", DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO);
        assertEquals("El NIT es obligatorio", DomainConstants.Restaurant.ERROR_NIT_REQUERIDO);
        assertEquals("La dirección es obligatoria", DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA);
        assertEquals("El teléfono es obligatorio", DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO);
        assertEquals("La URL del logo es obligatoria", DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA);
        assertEquals("El ID del propietario es obligatorio", DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO);
        assertEquals("El ID del propietario no corresponde a un usuario con rol propietario", DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO);
        assertEquals("No se encontró el usuario propietario especificado", DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO);
        assertEquals("El nombre del restaurante no puede contener sólo números", DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS);
        assertEquals("El NIT debe contener únicamente números", DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO);
        assertEquals("El teléfono debe contener máximo 13 caracteres numéricos y puede incluir el símbolo +", DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO);
    }

    @Test
    void dishConstants_ShouldHaveCorrectRoles() {
        // Then
        assertEquals("PROPIETARIO", DomainConstants.Dish.ROL_PROPIETARIO);
    }

    @Test
    void dishConstants_ShouldHaveCorrectErrorMessages() {
        // Then
        assertEquals("El plato no puede ser nulo", DomainConstants.Dish.ERROR_DISH_NULO);
        assertEquals("El nombre del plato es obligatorio", DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO);
        assertEquals("El precio del plato es obligatorio", DomainConstants.Dish.ERROR_PRECIO_REQUERIDO);
        assertEquals("El precio del plato debe ser un número entero positivo y mayor a 0", DomainConstants.Dish.ERROR_PRECIO_POSITIVO);
        assertEquals("La descripción del plato es obligatoria", DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA);
        assertEquals("La URL de la imagen es obligatoria", DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA);
        assertEquals("La categoría es obligatoria", DomainConstants.Dish.ERROR_CATEGORIA_REQUERIDA);
        assertEquals("El restaurante es obligatorio", DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO);
        assertEquals("No se encontró el restaurante especificado", DomainConstants.Dish.ERROR_RESTAURANTE_NO_ENCONTRADO);
        assertEquals("Solo el propietario del restaurante puede crear platos", DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO);
    }

    @Test
    void allConstants_ShouldNotBeNull() {
        // Restaurant constants
        assertNotNull(DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN);
        assertNotNull(DomainConstants.Restaurant.TELEFONO_PATTERN);
        assertNotNull(DomainConstants.Restaurant.ROL_PROPIETARIO);
        assertNotNull(DomainConstants.Restaurant.ERROR_RESTAURANT_NULO);
        assertNotNull(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO);
        assertNotNull(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO);
        assertNotNull(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA);
        assertNotNull(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO);
        assertNotNull(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA);
        assertNotNull(DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO);
        assertNotNull(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO);
        assertNotNull(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO);
        assertNotNull(DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS);
        assertNotNull(DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO);
        assertNotNull(DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO);

        // Dish constants
        assertNotNull(DomainConstants.Dish.ROL_PROPIETARIO);
        assertNotNull(DomainConstants.Dish.ERROR_DISH_NULO);
        assertNotNull(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO);
        assertNotNull(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO);
        assertNotNull(DomainConstants.Dish.ERROR_PRECIO_POSITIVO);
        assertNotNull(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA);
        assertNotNull(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA);
        assertNotNull(DomainConstants.Dish.ERROR_CATEGORIA_REQUERIDA);
        assertNotNull(DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO);
        assertNotNull(DomainConstants.Dish.ERROR_RESTAURANTE_NO_ENCONTRADO);
        assertNotNull(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO);
    }

    @Test
    void allConstants_ShouldNotBeEmpty() {
        // Restaurant constants
        assertFalse(DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN.isEmpty());
        assertFalse(DomainConstants.Restaurant.TELEFONO_PATTERN.isEmpty());
        assertFalse(DomainConstants.Restaurant.ROL_PROPIETARIO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_RESTAURANT_NULO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO.isEmpty());
        assertFalse(DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO.isEmpty());

        // Dish constants
        assertFalse(DomainConstants.Dish.ROL_PROPIETARIO.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_DISH_NULO.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_PRECIO_POSITIVO.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_CATEGORIA_REQUERIDA.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_RESTAURANTE_NO_ENCONTRADO.isEmpty());
        assertFalse(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO.isEmpty());
    }
}
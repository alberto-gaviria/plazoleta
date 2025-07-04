package com.plazoleta.restaurants.adapters.driven.mysql.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class AdapterConstantsTest {

    @Test
    void shouldNotInstantiateAdapterConstants() throws NoSuchMethodException {
        Constructor<AdapterConstants> ctor = AdapterConstants.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateErrorMessages() throws NoSuchMethodException {
        Constructor<AdapterConstants.ErrorMessages> ctor =
                AdapterConstants.ErrorMessages.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateDatabaseColumns() throws NoSuchMethodException {
        Constructor<AdapterConstants.DatabaseColumns> ctor =
                AdapterConstants.DatabaseColumns.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
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

    @Test
    void shouldHaveCorrectDatabaseColumns() {
        assertEquals("nombre", AdapterConstants.DatabaseColumns.NOMBRE_COLUMN);
        assertEquals("nit",    AdapterConstants.DatabaseColumns.NIT_COLUMN);
        assertEquals("id",     AdapterConstants.DatabaseColumns.ID_COLUMN);
    }
}

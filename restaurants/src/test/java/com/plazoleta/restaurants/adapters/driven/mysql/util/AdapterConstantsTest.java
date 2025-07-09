package com.plazoleta.restaurants.adapters.driven.mysql.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class AdapterConstantsTest {

    @Test
    void testPrivateConstructors() throws Exception {
        // Probar constructor privado de AdapterConstants
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(AdapterConstants.class));

        // Probar constructor privado de cada clase interna
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(AdapterConstants.DatabaseColumns.class));
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(AdapterConstants.ErrorMessages.class));
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(AdapterConstants.LogMessages.class));
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(AdapterConstants.TemporaryData.class));
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(AdapterConstants.ValidationMessages.class));
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(AdapterConstants.OrderConstants.class));
    }

    private void invokePrivateConstructor(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (Exception e) {
            // Re-lanza la causa si es una IllegalStateException como en este caso
            throw (Exception) e.getCause();
        }
    }
}

package com.plazoleta.restaurants.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void testPrivateConstructorHttpConstants() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.class));
    }

    @Test
    void testPrivateConstructorRoles() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.Roles.class));
    }

    @Test
    void testPrivateConstructorPaths() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.Paths.class));
    }

    @Test
    void testPrivateConstructorPagination() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.Pagination.class));
    }

    @Test
    void testPrivateConstructorMessages() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.Messages.class));
    }

    @Test
    void testPrivateConstructorValidationPatterns() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.ValidationPatterns.class));
    }

    @Test
    void testPrivateConstructorHeaders() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.Headers.class));
    }

    @Test
    void testPrivateConstructorHttpStatusMessages() {
        assertThrows(IllegalStateException.class, () -> invokePrivateConstructor(HttpConstants.HttpStatusMessages.class));
    }

    private void invokePrivateConstructor(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            // Verificamos que la causa sea la excepción esperada
            if (!(e.getCause() instanceof IllegalStateException)) {
                throw e;
            }
            throw (IllegalStateException) e.getCause();
        }
    }
}

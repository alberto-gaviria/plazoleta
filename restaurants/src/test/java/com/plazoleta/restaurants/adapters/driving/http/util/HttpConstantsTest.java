package com.plazoleta.restaurants.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void testPrivateConstructorOfHttpConstants() throws Exception {
        assertThrowsIllegalState(HttpConstants.class);
    }

    @Test
    void testPrivateConstructorOfMessages() throws Exception {
        assertThrowsIllegalState(HttpConstants.Messages.class);
    }

    @Test
    void testPrivateConstructorOfHttpStatusMessages() throws Exception {
        assertThrowsIllegalState(HttpConstants.HttpStatusMessages.class);
    }

    @Test
    void testPrivateConstructorOfPaths() throws Exception {
        assertThrowsIllegalState(HttpConstants.Paths.class);
    }

    @Test
    void testPrivateConstructorOfRoles() throws Exception {
        assertThrowsIllegalState(HttpConstants.Roles.class);
    }

    @Test
    void testPrivateConstructorOfPagination() throws Exception {
        assertThrowsIllegalState(HttpConstants.Pagination.class);
    }

    @Test
    void testPrivateConstructorOfValidationPatterns() throws Exception {
        assertThrowsIllegalState(HttpConstants.ValidationPatterns.class);
    }

    @Test
    void testPrivateConstructorOfHeaders() throws Exception {
        assertThrowsIllegalState(HttpConstants.Headers.class);
    }

    private void assertThrowsIllegalState(Class<?> clazz) throws Exception {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
            fail("Expected IllegalStateException to be thrown");
        } catch (Exception e) {
            Throwable cause = e.getCause();
            assertNotNull(cause, "Expected cause to be not null");
            assertTrue(cause instanceof IllegalStateException, "Expected IllegalStateException but got: " + cause.getClass());
            assertEquals("Clase de constantes", cause.getMessage());
        }
    }
}

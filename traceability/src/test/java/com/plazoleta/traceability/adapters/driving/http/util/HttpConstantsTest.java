package com.plazoleta.traceability.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void testMainClassPrivateConstructorThrowsException() {
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants> constructor = HttpConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testRolesPrivateConstructorThrowsException() {
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Roles> constructor = HttpConstants.Roles.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testPathsPrivateConstructorThrowsException() {
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Paths> constructor = HttpConstants.Paths.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testPaginationPrivateConstructorThrowsException() {
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Pagination> constructor = HttpConstants.Pagination.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testMessagesPrivateConstructorThrowsException() {
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Messages> constructor = HttpConstants.Messages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void testConstantsNotNull() {
        assertEquals("CLIENTE", HttpConstants.Roles.CLIENTE);

        assertEquals("/traceability", HttpConstants.Paths.TRACEABILITY);
        assertEquals("/order/{orderId}", HttpConstants.Paths.ORDER_TRACEABILITY);

        assertEquals("0", HttpConstants.Pagination.DEFAULT_PAGE_VALUE);
        assertEquals("10", HttpConstants.Pagination.DEFAULT_SIZE_VALUE);
        assertEquals(0, HttpConstants.Pagination.MIN_PAGE);
        assertEquals(1, HttpConstants.Pagination.MIN_SIZE);
        assertEquals(100, HttpConstants.Pagination.MAX_SIZE);

        assertEquals("Trazabilidad del pedido obtenida exitosamente", HttpConstants.Messages.GET_TRACEABILITY_SUCCESS);
        assertEquals("Parámetros de paginación inválidos", HttpConstants.Messages.INVALID_PAGINATION);
        assertEquals("No autorizado - Token requerido", HttpConstants.Messages.UNAUTHORIZED);
        assertEquals("Prohibido - No tiene permisos para esta acción", HttpConstants.Messages.FORBIDDEN);
        assertEquals("Pedido no encontrado", HttpConstants.Messages.ORDER_NOT_FOUND);
        assertEquals("Error interno del servidor", HttpConstants.Messages.INTERNAL_ERROR);
    }
}

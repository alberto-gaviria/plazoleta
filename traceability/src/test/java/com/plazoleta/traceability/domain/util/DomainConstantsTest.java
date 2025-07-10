package com.plazoleta.traceability.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void testDomainConstantsConstructorThrowsException() throws Exception {
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void testTraceabilityConstructorThrowsException() throws Exception {
        Constructor<DomainConstants.Traceability> constructor = DomainConstants.Traceability.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        InvocationTargetException exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }


    @Test
    void testTraceabilityConstantsValues() {
        assertEquals(100, DomainConstants.Traceability.MAX_PAGE_SIZE);
        assertEquals(0, DomainConstants.Traceability.MIN_PAGE_NUMBER);
        assertEquals(1, DomainConstants.Traceability.MIN_PAGE_SIZE);
        assertEquals(10, DomainConstants.Traceability.DEFAULT_PAGE_SIZE);

        assertEquals("El ID del pedido es obligatorio", DomainConstants.Traceability.ERROR_ORDER_ID_REQUIRED);
        assertEquals("El ID del cliente es obligatorio", DomainConstants.Traceability.ERROR_CLIENT_ID_REQUIRED);
        assertEquals("El email del cliente es obligatorio", DomainConstants.Traceability.ERROR_CLIENT_EMAIL_REQUIRED);
        assertEquals("El nuevo estado es obligatorio", DomainConstants.Traceability.ERROR_NEW_STATUS_REQUIRED);
        assertEquals("El pedido no pertenece al cliente especificado", DomainConstants.Traceability.ERROR_ORDER_NOT_BELONGS_TO_CLIENT);
        assertEquals("No se encontró el pedido especificado", DomainConstants.Traceability.ERROR_ORDER_NOT_FOUND);
        assertEquals("No se encontró información de trazabilidad para este pedido", DomainConstants.Traceability.ERROR_NO_TRACEABILITY_FOUND);

        assertEquals("El número de página debe ser mayor o igual a 0", DomainConstants.Traceability.ERROR_PAGE_NUMBER_INVALID);
        assertEquals("El tamaño de página debe ser mayor o igual a 1", DomainConstants.Traceability.ERROR_PAGE_SIZE_INVALID);
        assertEquals("El tamaño de página no puede ser mayor a 100", DomainConstants.Traceability.ERROR_PAGE_SIZE_TOO_LARGE);

        assertEquals("PENDIENTE", DomainConstants.Traceability.STATUS_PENDIENTE);
        assertEquals("EN_PREPARACION", DomainConstants.Traceability.STATUS_EN_PREPARACION);
        assertEquals("LISTO", DomainConstants.Traceability.STATUS_LISTO);
        assertEquals("ENTREGADO", DomainConstants.Traceability.STATUS_ENTREGADO);
        assertEquals("CANCELADO", DomainConstants.Traceability.STATUS_CANCELADO);
    }



}

package com.plazoleta.traceability.adapters.driven.mongodb.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraceabilityRetrieveExceptionTest {

    @Test
    void testDefaultConstructor() {
        TraceabilityRetrieveException ex = new TraceabilityRetrieveException();
        assertEquals("Error al recuperar la trazabilidad", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testConstructorWithMessage() {
        String msg = "Mensaje personalizado";
        TraceabilityRetrieveException ex = new TraceabilityRetrieveException(msg);
        assertEquals(msg, ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new IllegalArgumentException("Causa interna");
        TraceabilityRetrieveException ex = new TraceabilityRetrieveException("Otro mensaje", cause);
        assertEquals("Otro mensaje", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testConstructorWithCauseOnly() {
        Throwable cause = new RuntimeException("Solo causa");
        TraceabilityRetrieveException ex = new TraceabilityRetrieveException(cause);
        assertEquals("Error al recuperar la trazabilidad", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testThrowingException() {
        TraceabilityRetrieveException thrown = assertThrows(
                TraceabilityRetrieveException.class,
                () -> { throw new TraceabilityRetrieveException("Excepción lanzada"); }
        );
        assertEquals("Excepción lanzada", thrown.getMessage());
    }
}

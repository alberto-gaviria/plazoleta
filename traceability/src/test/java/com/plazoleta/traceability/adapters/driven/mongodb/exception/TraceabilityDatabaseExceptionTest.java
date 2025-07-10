package com.plazoleta.traceability.adapters.driven.mongodb.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraceabilityDatabaseExceptionTest {

    @Test
    void testDefaultConstructor() {
        TraceabilityDatabaseException ex = new TraceabilityDatabaseException();
        assertEquals("Error inesperado en la base de datos de trazabilidad", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testConstructorWithMessage() {
        TraceabilityDatabaseException ex = new TraceabilityDatabaseException("Mi mensaje");
        assertEquals("Mi mensaje", ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new IllegalStateException("Causa interna");
        TraceabilityDatabaseException ex = new TraceabilityDatabaseException("Mensaje ext", cause);
        assertEquals("Mensaje ext", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testConstructorWithCauseOnly() {
        Throwable cause = new RuntimeException("Solo causa");
        TraceabilityDatabaseException ex = new TraceabilityDatabaseException(cause);
        assertEquals("Error inesperado en la base de datos de trazabilidad", ex.getMessage());
        assertSame(cause, ex.getCause());
    }

    @Test
    void testThrowingException() {
        // Ejemplo de uso en assertThrows
        TraceabilityDatabaseException ex = assertThrows(
                TraceabilityDatabaseException.class,
                () -> { throw new TraceabilityDatabaseException("Oops"); }
        );
        assertEquals("Oops", ex.getMessage());
    }
}

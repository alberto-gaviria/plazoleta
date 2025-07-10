package com.plazoleta.traceability.adapters.driven.mongodb.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraceabilitySaveExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Custom error message";
        TraceabilitySaveException exception = new TraceabilitySaveException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Custom error message with cause";
        Throwable cause = new RuntimeException("Cause");
        TraceabilitySaveException exception = new TraceabilitySaveException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testDefaultConstructor() {
        TraceabilitySaveException exception = new TraceabilitySaveException();

        assertEquals("Error al guardar la trazabilidad", exception.getMessage());
    }
}

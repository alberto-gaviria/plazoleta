package com.plazoleta.traceability.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidTraceabilityExceptionTest {

    @Test
    void testConstructorSetsMessageCorrectly() {
        String message = "Invalid traceability data";
        InvalidTraceabilityException exception = new InvalidTraceabilityException(message);

        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof DomainException);
    }
}

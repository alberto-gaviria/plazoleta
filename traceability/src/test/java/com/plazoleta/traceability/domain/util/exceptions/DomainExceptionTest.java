package com.plazoleta.traceability.domain.util.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DomainExceptionTest {

    // Subclase concreta solo para probar DomainException (porque es abstracta)
    static class TestDomainException extends DomainException {
        public TestDomainException(String message) {
            super(message);
        }
    }

    @Test
    void testDomainExceptionMessage() {
        String message = "Test exception message";
        DomainException exception = new TestDomainException(message);

        assertEquals(message, exception.getMessage());
    }
}

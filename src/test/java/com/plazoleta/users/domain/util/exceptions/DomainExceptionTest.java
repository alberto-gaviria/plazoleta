package com.plazoleta.users.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainExceptionTest {

    // Clase concreta para testing de la clase abstracta
    private static class TestDomainException extends DomainException {
        public TestDomainException(String message) {
            super(message);
        }
    }

    @Test
    void testDomainExceptionWithMessage() {
        // Given
        String message = "Error de dominio de prueba";

        // When
        TestDomainException exception = new TestDomainException(message);

        // Then
        assertEquals(message, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }
}
package com.plazoleta.messaging.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwilioExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Fallo de Twilio";
        TwilioException exception = new TwilioException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Fallo de Twilio";
        Throwable cause = new RuntimeException("Error de red");

        TwilioException exception = new TwilioException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}

package com.plazoleta.messaging.domain.util.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationExceptionTest {

    @Test
    void testConstructorWithMessage() {
        String message = "Error al enviar notificación";
        NotificationException exception = new NotificationException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testConstructorWithMessageAndCause() {
        String message = "Error al enviar notificación";
        Throwable cause = new RuntimeException("Causa original");

        NotificationException exception = new NotificationException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}

package com.plazoleta.messaging.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void constructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants> constructor = HttpConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void rolesConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Roles> constructor = HttpConstants.Roles.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void pathsConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Paths> constructor = HttpConstants.Paths.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void messagesConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Messages> constructor = HttpConstants.Messages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void validationConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<HttpConstants.Validation> constructor = HttpConstants.Validation.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void constants_ShouldHaveCorrectValues() {
        // Then
        assertEquals("ADMINISTRADOR", HttpConstants.Roles.ADMINISTRADOR);
        assertEquals("PROPIETARIO", HttpConstants.Roles.PROPIETARIO);
        assertEquals("CLIENTE", HttpConstants.Roles.CLIENTE);
        assertEquals("EMPLEADO", HttpConstants.Roles.EMPLEADO);

        assertEquals("/notifications", HttpConstants.Paths.NOTIFICATIONS);
        assertEquals("/sms/order-ready", HttpConstants.Paths.SMS_ORDER_READY);

        assertEquals("^\\+?[1-9]\\d{1,14}$", HttpConstants.Validation.PHONE_PATTERN);
        assertEquals("^\\d{4}$", HttpConstants.Validation.PIN_PATTERN);
    }
}

package com.plazoleta.messaging.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void constructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void notificationConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<DomainConstants.Notification> constructor = DomainConstants.Notification.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void errorMessagesConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<DomainConstants.ErrorMessages> constructor = DomainConstants.ErrorMessages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void templatesConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<DomainConstants.Templates> constructor = DomainConstants.Templates.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void constants_ShouldHaveCorrectValues() {
        // Then
        assertEquals(3, DomainConstants.Notification.MAX_RETRY_ATTEMPTS);
        assertEquals(30, DomainConstants.Notification.SMS_TIMEOUT_SECONDS);
        assertEquals("10", DomainConstants.Notification.DEFAULT_TIME_ESTIMATE);
        assertEquals(4, DomainConstants.Notification.PIN_LENGTH);

        assertEquals("Su pedido #{orderId} está listo. PIN: #{pin}. Restaurante: #{restaurantName}. Retire en #{timeEstimate} minutos.",
                     DomainConstants.Templates.ORDER_READY_TEMPLATE);
        assertEquals("#{orderId}", DomainConstants.Templates.ORDER_ID_PLACEHOLDER);
        assertEquals("#{pin}", DomainConstants.Templates.PIN_PLACEHOLDER);
        assertEquals("#{restaurantName}", DomainConstants.Templates.RESTAURANT_NAME_PLACEHOLDER);
        assertEquals("#{timeEstimate}", DomainConstants.Templates.TIME_ESTIMATE_PLACEHOLDER);
    }
}
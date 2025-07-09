package com.plazoleta.messaging.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SmsNotificationTest {

    @Test
    void defaultConstructor_ShouldInitializeDefaultValues() {
        // When
        SmsNotification notification = new SmsNotification();

        // Then
        assertNotNull(notification.getCreatedAt());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void parameterizedConstructor_ShouldInitializeValues() {
        // When
        SmsNotification notification = new SmsNotification(
                "order-123", "+1234567890", "Test message", "1234", "Test Restaurant");

        // Then
        assertEquals("order-123", notification.getOrderId());
        assertEquals("+1234567890", notification.getClientPhone());
        assertEquals("Test message", notification.getMessage());
        assertEquals("1234", notification.getPin());
        assertEquals("Test Restaurant", notification.getRestaurantName());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertEquals(0, notification.getRetryAttempts());
        assertNotNull(notification.getCreatedAt());
    }

    @Test
    void markAsSent_ShouldUpdateStatusAndSentAt() {
        // Given
        SmsNotification notification = new SmsNotification();

        // When
        notification.markAsSent();

        // Then
        assertEquals(NotificationStatus.SENT, notification.getStatus());
        assertNotNull(notification.getSentAt());
    }

    @Test
    void markAsFailed_ShouldUpdateStatusAndErrorMessage() {
        // Given
        SmsNotification notification = new SmsNotification();
        String errorMessage = "Test error";

        // When
        notification.markAsFailed(errorMessage);

        // Then
        assertEquals(NotificationStatus.FAILED, notification.getStatus());
        assertEquals(errorMessage, notification.getErrorMessage());
    }

    @Test
    void incrementRetryAttempt_ShouldIncrementCounter() {
        // Given
        SmsNotification notification = new SmsNotification();
        int initialAttempts = notification.getRetryAttempts();

        // When
        notification.incrementRetryAttempt();

        // Then
        assertEquals(initialAttempts + 1, notification.getRetryAttempts());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        SmsNotification notification = new SmsNotification();
        LocalDateTime now = LocalDateTime.now();

        // When
        notification.setId("test-id");
        notification.setOrderId("order-123");
        notification.setClientPhone("+1234567890");
        notification.setMessage("Test message");
        notification.setPin("1234");
        notification.setRestaurantName("Test Restaurant");
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(now);
        notification.setCreatedAt(now);
        notification.setErrorMessage("Test error");
        notification.setRetryAttempts(2);

        // Then
        assertEquals("test-id", notification.getId());
        assertEquals("order-123", notification.getOrderId());
        assertEquals("+1234567890", notification.getClientPhone());
        assertEquals("Test message", notification.getMessage());
        assertEquals("1234", notification.getPin());
        assertEquals("Test Restaurant", notification.getRestaurantName());
        assertEquals(NotificationStatus.SENT, notification.getStatus());
        assertEquals(now, notification.getSentAt());
        assertEquals(now, notification.getCreatedAt());
        assertEquals("Test error", notification.getErrorMessage());
        assertEquals(2, notification.getRetryAttempts());
    }
}
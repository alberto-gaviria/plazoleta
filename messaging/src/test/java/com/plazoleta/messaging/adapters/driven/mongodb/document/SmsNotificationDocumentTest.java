package com.plazoleta.messaging.adapters.driven.mongodb.document;

import com.plazoleta.messaging.domain.model.NotificationStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SmsNotificationDocumentTest {

    @Test
    void constructor_ShouldInitializeDefaultValues() {
        // When
        SmsNotificationDocument document = new SmsNotificationDocument();

        // Then
        assertEquals(0, document.getRetryAttempts());
        assertEquals(NotificationStatus.PENDING, document.getStatus());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        SmsNotificationDocument document = new SmsNotificationDocument();
        LocalDateTime now = LocalDateTime.now();

        // When
        document.setId("test-id");
        document.setOrderId("order-123");
        document.setClientPhone("+1234567890");
        document.setMessage("Test message");
        document.setPin("1234");
        document.setRestaurantName("Test Restaurant");
        document.setStatus(NotificationStatus.SENT);
        document.setSentAt(now);
        document.setCreatedAt(now);
        document.setUpdatedAt(now);
        document.setErrorMessage("Test error");
        document.setRetryAttempts(2);

        // Then
        assertEquals("test-id", document.getId());
        assertEquals("order-123", document.getOrderId());
        assertEquals("+1234567890", document.getClientPhone());
        assertEquals("Test message", document.getMessage());
        assertEquals("1234", document.getPin());
        assertEquals("Test Restaurant", document.getRestaurantName());
        assertEquals(NotificationStatus.SENT, document.getStatus());
        assertEquals(now, document.getSentAt());
        assertEquals(now, document.getCreatedAt());
        assertEquals(now, document.getUpdatedAt());
        assertEquals("Test error", document.getErrorMessage());
        assertEquals(2, document.getRetryAttempts());
    }
}
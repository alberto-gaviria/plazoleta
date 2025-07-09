package com.plazoleta.messaging.adapters.driving.http.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderReadyNotificationRequestTest {

    @Test
    void defaultConstructor_ShouldCreateEmptyRequest() {
        // When
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();

        // Then
        assertNotNull(request);
    }

    @Test
    void parameterizedConstructor_ShouldCreateRequestWithValues() {
        // When
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest(
                "order-123", "+1234567890", "1234", "Test Restaurant");

        // Then
        assertEquals("order-123", request.getOrderId());
        assertEquals("+1234567890", request.getClientPhone());
        assertEquals("1234", request.getPin());
        assertEquals("Test Restaurant", request.getRestaurantName());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();

        // When
        request.setOrderId("order-123");
        request.setClientPhone("+1234567890");
        request.setPin("1234");
        request.setRestaurantName("Test Restaurant");

        // Then
        assertEquals("order-123", request.getOrderId());
        assertEquals("+1234567890", request.getClientPhone());
        assertEquals("1234", request.getPin());
        assertEquals("Test Restaurant", request.getRestaurantName());
    }
}

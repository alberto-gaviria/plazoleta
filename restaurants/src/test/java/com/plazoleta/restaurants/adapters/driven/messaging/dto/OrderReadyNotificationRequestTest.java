package com.plazoleta.restaurants.adapters.driven.messaging.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderReadyNotificationRequestTest {

    @Test
    void constructorAndGetters_ShouldReturnCorrectValues() {
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest(
                "order123", "1234567890", "1234", "Pizza Place"
        );

        assertEquals("order123", request.getOrderId());
        assertEquals("1234567890", request.getClientPhone());
        assertEquals("1234", request.getPin());
        assertEquals("Pizza Place", request.getRestaurantName());
    }

    @Test
    void setters_ShouldModifyFields() {
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();

        request.setOrderId("order456");
        request.setClientPhone("0987654321");
        request.setPin("4321");
        request.setRestaurantName("Burger Spot");

        assertEquals("order456", request.getOrderId());
        assertEquals("0987654321", request.getClientPhone());
        assertEquals("4321", request.getPin());
        assertEquals("Burger Spot", request.getRestaurantName());
    }
}

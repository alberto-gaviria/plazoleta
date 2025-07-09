package com.plazoleta.restaurants.adapters.driven.messaging.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationResponseTest {

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        NotificationResponse response = new NotificationResponse();
        LocalDateTime now = LocalDateTime.now();

        response.setId("notif-001");
        response.setOrderId("order789");
        response.setClientPhone("3216549870");
        response.setStatus("SENT");
        response.setSentAt(now);
        response.setErrorMessage("No error");

        assertEquals("notif-001", response.getId());
        assertEquals("order789", response.getOrderId());
        assertEquals("3216549870", response.getClientPhone());
        assertEquals("SENT", response.getStatus());
        assertEquals(now, response.getSentAt());
        assertEquals("No error", response.getErrorMessage());
    }
}

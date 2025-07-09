package com.plazoleta.restaurants.adapters.driven.messaging.adapter;

import com.plazoleta.restaurants.adapters.driven.messaging.client.IMessagingServiceClient;
import com.plazoleta.restaurants.adapters.driven.messaging.dto.NotificationResponse;
import com.plazoleta.restaurants.adapters.driven.messaging.dto.OrderReadyNotificationRequest;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessagingServiceAdapterTest {

    private IMessagingServiceClient messagingServiceClient;
    private MessagingServiceAdapter adapter;

    @BeforeEach
    void setUp() {
        messagingServiceClient = mock(IMessagingServiceClient.class);
        adapter = new MessagingServiceAdapter(messagingServiceClient);
    }

    @Test
    void sendOrderReadyNotification_ShouldReturnTrue_WhenSuccessfulResponse() {
        OrderReadyNotificationRequest expectedRequest = new OrderReadyNotificationRequest("1", "3001234567", "1234", "Pizza Hut");
        when(messagingServiceClient.sendOrderReadyNotification(any(OrderReadyNotificationRequest.class)))
                .thenReturn(new ResponseEntity<>(new NotificationResponse(), HttpStatus.OK));

        boolean result = adapter.sendOrderReadyNotification("1", "3001234567", "1234", "Pizza Hut");

        assertTrue(result);
        verify(messagingServiceClient, times(1)).sendOrderReadyNotification(any(OrderReadyNotificationRequest.class));
    }

    @Test
    void sendOrderReadyNotification_ShouldReturnFalse_WhenResponseNotSuccessful() {
        when(messagingServiceClient.sendOrderReadyNotification(any(OrderReadyNotificationRequest.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));

        boolean result = adapter.sendOrderReadyNotification("1", "3001234567", "1234", "Pizza Hut");

        assertFalse(result);
    }

    @Test
    void sendOrderReadyNotification_ShouldReturnFalse_WhenFeignExceptionThrown() {
        when(messagingServiceClient.sendOrderReadyNotification(any(OrderReadyNotificationRequest.class)))
                .thenThrow(mock(FeignException.class));

        boolean result = adapter.sendOrderReadyNotification("1", "3001234567", "1234", "Pizza Hut");

        assertFalse(result);
    }

    @Test
    void sendOrderReadyNotification_ShouldReturnFalse_WhenUnexpectedExceptionThrown() {
        when(messagingServiceClient.sendOrderReadyNotification(any(OrderReadyNotificationRequest.class)))
                .thenThrow(new RuntimeException("Unexpected"));

        boolean result = adapter.sendOrderReadyNotification("1", "3001234567", "1234", "Pizza Hut");

        assertFalse(result);
    }
}

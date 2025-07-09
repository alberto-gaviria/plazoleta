package com.plazoleta.messaging.adapters.driving.http.controller;

import com.plazoleta.messaging.adapters.driving.http.dto.request.OrderReadyNotificationRequest;
import com.plazoleta.messaging.adapters.driving.http.dto.response.NotificationResponse;
import com.plazoleta.messaging.adapters.driving.http.mapper.INotificationResponseMapper;
import com.plazoleta.messaging.domain.api.INotificationServicePort;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationControllerTest {

    private INotificationServicePort notificationServicePort;
    private INotificationResponseMapper responseMapper;
    private NotificationController controller;

    @BeforeEach
    void setUp() {
        notificationServicePort = mock(INotificationServicePort.class);
        responseMapper = mock(INotificationResponseMapper.class);
        controller = new NotificationController(notificationServicePort, responseMapper);
    }

    @Test
    void testSendOrderReadyNotification() {
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("order123");
        request.setClientPhone("+1234567890");
        request.setPin("1234");
        request.setRestaurantName("Testaurant");

        SmsNotification notification = new SmsNotification();
        NotificationResponse response = new NotificationResponse();

        when(notificationServicePort.sendOrderReadyNotification(
                request.getOrderId(), request.getClientPhone(), request.getPin(), request.getRestaurantName()))
                .thenReturn(notification);
        when(responseMapper.toResponse(notification)).thenReturn(response);

        ResponseEntity<NotificationResponse> result = controller.sendOrderReadyNotification(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(notificationServicePort).sendOrderReadyNotification(
                eq("order123"), eq("+1234567890"), eq("1234"), eq("Testaurant"));
        verify(responseMapper).toResponse(notification);
    }

    @Test
    void testGetNotificationsByOrderId() {
        String orderId = "order456";
        SmsNotification notification = new SmsNotification();
        List<SmsNotification> notifications = Collections.singletonList(notification);
        NotificationResponse response = new NotificationResponse();
        List<NotificationResponse> responseList = Collections.singletonList(response);

        when(notificationServicePort.getNotificationsByOrderId(orderId)).thenReturn(notifications);
        when(responseMapper.toResponseList(notifications)).thenReturn(responseList);

        ResponseEntity<List<NotificationResponse>> result = controller.getNotificationsByOrderId(orderId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
        verify(notificationServicePort).getNotificationsByOrderId(orderId);
        verify(responseMapper).toResponseList(notifications);
    }

    @Test
    void testGetNotificationById() {
        String notificationId = "notif789";
        SmsNotification notification = new SmsNotification();
        NotificationResponse response = new NotificationResponse();

        when(notificationServicePort.getNotificationById(notificationId)).thenReturn(notification);
        when(responseMapper.toResponse(notification)).thenReturn(response);

        ResponseEntity<NotificationResponse> result = controller.getNotificationById(notificationId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(notificationServicePort).getNotificationById(notificationId);
        verify(responseMapper).toResponse(notification);
    }

    @Test
    void testRetryFailedNotification() {
        String notificationId = "fail123";

        ResponseEntity<Void> result = controller.retryFailedNotification(notificationId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
        verify(notificationServicePort).retryFailedNotification(notificationId);
    }
}

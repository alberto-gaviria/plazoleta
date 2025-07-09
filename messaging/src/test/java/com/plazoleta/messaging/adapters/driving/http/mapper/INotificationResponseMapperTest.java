package com.plazoleta.messaging.adapters.driving.http.mapper;

import com.plazoleta.messaging.adapters.driven.mongodb.document.SmsNotificationDocument;
import com.plazoleta.messaging.adapters.driving.http.dto.response.NotificationResponse;
import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class INotificationResponseMapperTest {

    private INotificationResponseMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(INotificationResponseMapper.class);
    }

    @Test
    void toResponse_WithCompleteNotification_ShouldMapAllFields() {
        // Given
        SmsNotification notification = createCompleteNotification();

        // When
        NotificationResponse response = mapper.toResponse(notification);

        // Then
        assertNotNull(response);
        assertEquals(notification.getId(), response.getId());
        assertEquals(notification.getOrderId(), response.getOrderId());
        assertEquals(notification.getClientPhone(), response.getClientPhone());
        assertEquals(notification.getMessage(), response.getMessage());
        assertEquals(notification.getPin(), response.getPin());
        assertEquals(notification.getRestaurantName(), response.getRestaurantName());
        assertEquals(notification.getStatus(), response.getStatus());
        assertEquals(notification.getSentAt(), response.getSentAt());
        assertEquals(notification.getCreatedAt(), response.getCreatedAt());
        assertEquals(notification.getErrorMessage(), response.getErrorMessage());
        assertEquals(notification.getRetryAttempts(), response.getRetryAttempts());
    }

    @Test
    void toResponse_WithMinimalNotification_ShouldMapAvailableFields() {
        // Given
        SmsNotification notification = new SmsNotification();
        notification.setId("test-id");
        notification.setOrderId("order-123");
        notification.setStatus(NotificationStatus.PENDING);

        // When
        NotificationResponse response = mapper.toResponse(notification);

        // Then
        assertNotNull(response);
        assertEquals("test-id", response.getId());
        assertEquals("order-123", response.getOrderId());
        assertEquals(NotificationStatus.PENDING, response.getStatus());
        assertNull(response.getClientPhone());
        assertNull(response.getMessage());
        assertNull(response.getPin());
        assertNull(response.getRestaurantName());
        assertNull(response.getSentAt());
        assertNull(response.getErrorMessage());
    }

    @Test
    void toResponse_WithNullNotification_ShouldReturnNull() {
        // When
        NotificationResponse response = mapper.toResponse(null);

        // Then
        assertNull(response);
    }

    @Test
    void toResponse_WithNullFields_ShouldHandleGracefully() {
        // Given
        SmsNotification notification = new SmsNotification();
        notification.setId(null);
        notification.setOrderId(null);
        notification.setClientPhone(null);
        notification.setMessage(null);
        notification.setPin(null);
        notification.setRestaurantName(null);
        notification.setStatus(null);
        notification.setSentAt(null);
        notification.setCreatedAt(null);
        notification.setErrorMessage(null);
        notification.setRetryAttempts(null);

        // When
        NotificationResponse response = mapper.toResponse(notification);

        // Then
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getOrderId());
        assertNull(response.getClientPhone());
        assertNull(response.getMessage());
        assertNull(response.getPin());
        assertNull(response.getRestaurantName());
        assertNull(response.getStatus());
        assertNull(response.getSentAt());
        assertNull(response.getCreatedAt());
        assertNull(response.getErrorMessage());
        assertNull(response.getRetryAttempts());
    }

    @Test
    void toResponseList_WithMultipleNotifications_ShouldMapAllItems() {
        // Given
        SmsNotification notification1 = createCompleteNotification();
        notification1.setId("id-1");
        notification1.setOrderId("order-1");

        SmsNotification notification2 = createCompleteNotification();
        notification2.setId("id-2");
        notification2.setOrderId("order-2");

        List<SmsNotification> notifications = Arrays.asList(notification1, notification2);

        // When
        List<NotificationResponse> responses = mapper.toResponseList(notifications);

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals("id-1", responses.get(0).getId());
        assertEquals("order-1", responses.get(0).getOrderId());

        assertEquals("id-2", responses.get(1).getId());
        assertEquals("order-2", responses.get(1).getOrderId());
    }

    @Test
    void toResponseList_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        List<SmsNotification> notifications = Collections.emptyList();

        // When
        List<NotificationResponse> responses = mapper.toResponseList(notifications);

        // Then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void toResponseList_WithNullList_ShouldReturnNull() {
        // When
        List<NotificationResponse> responses = mapper.toResponseList(null);

        // Then
        assertNull(responses);
    }

    @Test
    void toResponseList_WithListContainingNulls_ShouldHandleGracefully() {
        // Given
        SmsNotification validNotification = createCompleteNotification();
        List<SmsNotification> notifications = Arrays.asList(validNotification, null);

        // When
        List<NotificationResponse> responses = mapper.toResponseList(notifications);

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertNotNull(responses.get(0));
        assertNull(responses.get(1));
    }

    @Test
    void toResponseList_WithSingleNotification_ShouldReturnSingleItemList() {
        // Given
        SmsNotification notification = createCompleteNotification();
        List<SmsNotification> notifications = Collections.singletonList(notification);

        // When
        List<NotificationResponse> responses = mapper.toResponseList(notifications);

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(notification.getId(), responses.get(0).getId());
    }

    private SmsNotification createCompleteNotification() {
        SmsNotification notification = new SmsNotification();
        notification.setId("test-id");
        notification.setOrderId("order-123");
        notification.setClientPhone("+1234567890");
        notification.setMessage("Test message");
        notification.setPin("1234");
        notification.setRestaurantName("Test Restaurant");
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
        notification.setCreatedAt(LocalDateTime.now());
        notification.setErrorMessage("Test error");
        notification.setRetryAttempts(1);
        return notification;
    }

    private SmsNotificationDocument createCompleteDocument() {
        SmsNotificationDocument document = new SmsNotificationDocument();
        document.setId("test-id");
        document.setOrderId("order-123");
        document.setClientPhone("+1234567890");
        document.setMessage("Test message");
        document.setPin("1234");
        document.setRestaurantName("Test Restaurant");
        document.setStatus(NotificationStatus.SENT);
        document.setSentAt(LocalDateTime.now());
        document.setCreatedAt(LocalDateTime.now());
        document.setUpdatedAt(LocalDateTime.now());
        document.setErrorMessage("Test error");
        document.setRetryAttempts(1);
        return document;
    }
}
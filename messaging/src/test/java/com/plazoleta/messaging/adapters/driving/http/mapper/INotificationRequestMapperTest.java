package com.plazoleta.messaging.adapters.driving.http.mapper;

import com.plazoleta.messaging.adapters.driving.http.dto.request.OrderReadyNotificationRequest;
import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class INotificationRequestMapperTest {

    private INotificationRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(INotificationRequestMapper.class);
    }

    @Test
    void toModel_WithCompleteRequest_ShouldMapMappedFields() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("order-123");
        request.setClientPhone("+1234567890");
        request.setPin("1234");
        request.setRestaurantName("Test Restaurant");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertEquals(request.getOrderId(), notification.getOrderId());
        assertEquals(request.getClientPhone(), notification.getClientPhone());
        assertEquals(request.getPin(), notification.getPin());
        assertEquals(request.getRestaurantName(), notification.getRestaurantName());
    }

    @Test
    void toModel_WithCompleteRequest_ShouldIgnoreSpecifiedFields() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("order-123");
        request.setClientPhone("+1234567890");
        request.setPin("1234");
        request.setRestaurantName("Test Restaurant");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);

        // Verify that @Mapping(target = "...", ignore = true) fields are null or default values
        assertNull(notification.getId(), "ID should be ignored");
        assertNull(notification.getMessage(), "Message should be ignored");
        // Note: status and retryAttempts have default values in SmsNotification constructor
        // so we verify they have the expected default values, not null
        assertEquals(NotificationStatus.PENDING, notification.getStatus(), "Status should have default value");
        assertNull(notification.getSentAt(), "SentAt should be ignored");
        assertNotNull(notification.getCreatedAt(), "CreatedAt has default value from constructor");
        assertNull(notification.getErrorMessage(), "ErrorMessage should be ignored");
        assertEquals(0, notification.getRetryAttempts(), "RetryAttempts should have default value");
    }

    @Test
    void toModel_WithNullRequest_ShouldReturnNull() {
        // When
        SmsNotification notification = mapper.toModel(null);

        // Then
        assertNull(notification);
    }

    @Test
    void toModel_WithEmptyRequest_ShouldMapEmptyFields() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertNull(notification.getOrderId());
        assertNull(notification.getClientPhone());
        assertNull(notification.getPin());
        assertNull(notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithPartialRequest_ShouldMapAvailableFields() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("order-123");
        request.setPin("1234");
        // clientPhone and restaurantName left null

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertEquals("order-123", notification.getOrderId());
        assertEquals("1234", notification.getPin());
        assertNull(notification.getClientPhone());
        assertNull(notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithNullFields_ShouldHandleGracefully() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId(null);
        request.setClientPhone(null);
        request.setPin(null);
        request.setRestaurantName(null);

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertNull(notification.getOrderId());
        assertNull(notification.getClientPhone());
        assertNull(notification.getPin());
        assertNull(notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithParameterizedRequest_ShouldMapCorrectly() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest(
                "order-456", "+0987654321", "5678", "Another Restaurant");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertEquals("order-456", notification.getOrderId());
        assertEquals("+0987654321", notification.getClientPhone());
        assertEquals("5678", notification.getPin());
        assertEquals("Another Restaurant", notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithOnlyOrderId_ShouldMapOnlyOrderId() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("only-order-id");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertEquals("only-order-id", notification.getOrderId());
        assertNull(notification.getClientPhone());
        assertNull(notification.getPin());
        assertNull(notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithOnlyClientPhone_ShouldMapOnlyClientPhone() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setClientPhone("+9876543210");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertNull(notification.getOrderId());
        assertEquals("+9876543210", notification.getClientPhone());
        assertNull(notification.getPin());
        assertNull(notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithOnlyPin_ShouldMapOnlyPin() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setPin("9999");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertNull(notification.getOrderId());
        assertNull(notification.getClientPhone());
        assertEquals("9999", notification.getPin());
        assertNull(notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithOnlyRestaurantName_ShouldMapOnlyRestaurantName() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setRestaurantName("Sole Restaurant");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertNull(notification.getOrderId());
        assertNull(notification.getClientPhone());
        assertNull(notification.getPin());
        assertEquals("Sole Restaurant", notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithEmptyStrings_ShouldMapEmptyStrings() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("");
        request.setClientPhone("");
        request.setPin("");
        request.setRestaurantName("");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertEquals("", notification.getOrderId());
        assertEquals("", notification.getClientPhone());
        assertEquals("", notification.getPin());
        assertEquals("", notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_WithSpecialCharacters_ShouldMapCorrectly() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("order-123-áéíóú");
        request.setClientPhone("+1-234-567-890");
        request.setPin("1234");
        request.setRestaurantName("Restaurante José María & Café");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);
        assertEquals("order-123-áéíóú", notification.getOrderId());
        assertEquals("+1-234-567-890", notification.getClientPhone());
        assertEquals("1234", notification.getPin());
        assertEquals("Restaurante José María & Café", notification.getRestaurantName());

        // Verify ignored fields have default values
        assertNull(notification.getId());
        assertNull(notification.getMessage());
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertNull(notification.getSentAt());
        assertNotNull(notification.getCreatedAt());
        assertNull(notification.getErrorMessage());
        assertEquals(0, notification.getRetryAttempts());
    }

    @Test
    void toModel_ShouldMapExactlyFourFields() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest(
                "order-123", "+1234567890", "1234", "Test Restaurant");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);

        // Count non-null mapped fields
        int mappedFieldsCount = 0;
        if (notification.getOrderId() != null) mappedFieldsCount++;
        if (notification.getClientPhone() != null) mappedFieldsCount++;
        if (notification.getPin() != null) mappedFieldsCount++;
        if (notification.getRestaurantName() != null) mappedFieldsCount++;

        assertEquals(4, mappedFieldsCount, "Should map exactly 4 fields from request");

        // Verify ignored fields behavior
        assertNull(notification.getId(), "ID should be ignored");
        assertNull(notification.getMessage(), "Message should be ignored");
        assertEquals(NotificationStatus.PENDING, notification.getStatus(), "Status has default value");
        assertNull(notification.getSentAt(), "SentAt should be ignored");
        assertNotNull(notification.getCreatedAt(), "CreatedAt has default value");
        assertNull(notification.getErrorMessage(), "ErrorMessage should be ignored");
        assertEquals(0, notification.getRetryAttempts(), "RetryAttempts has default value");
    }

    @Test
    void toModel_ShouldCreateNewInstanceWithDefaultValues() {
        // Given
        OrderReadyNotificationRequest request = new OrderReadyNotificationRequest();
        request.setOrderId("test-order");

        // When
        SmsNotification notification = mapper.toModel(request);

        // Then
        assertNotNull(notification);

        // Verify it's a properly constructed SmsNotification with defaults
        assertEquals(NotificationStatus.PENDING, notification.getStatus());
        assertEquals(0, notification.getRetryAttempts());
        assertNotNull(notification.getCreatedAt());

        // Verify mapped field
        assertEquals("test-order", notification.getOrderId());
    }
}
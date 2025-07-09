package com.plazoleta.messaging.domain.usecase;

import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;
import com.plazoleta.messaging.domain.spi.INotificationPersistencePort;
import com.plazoleta.messaging.domain.spi.ISmsProviderPort;
import com.plazoleta.messaging.domain.util.exceptions.NotificationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationUseCaseTest {

    @Mock
    private INotificationPersistencePort notificationPersistencePort;

    @Mock
    private ISmsProviderPort smsProviderPort;

    @InjectMocks
    private NotificationUseCase notificationUseCase;

    @Test
    void sendOrderReadyNotification_WhenSmsSuccessful_ShouldReturnSentNotification() {
        // Given
        String orderId = "order-123";
        String clientPhone = "+1234567890";
        String pin = "1234";
        String restaurantName = "Test Restaurant";

        SmsNotification savedNotification = createSmsNotification();
        savedNotification.setId("saved-id");

        when(notificationPersistencePort.save(any(SmsNotification.class))).thenReturn(savedNotification);
        when(smsProviderPort.sendSms(any(SmsNotification.class))).thenReturn(true);

        // When
        SmsNotification result = notificationUseCase.sendOrderReadyNotification(orderId, clientPhone, pin, restaurantName);

        // Then
        assertNotNull(result);
        verify(notificationPersistencePort, times(2)).save(any(SmsNotification.class));
        verify(smsProviderPort).sendSms(any(SmsNotification.class));
    }

    @Test
    void sendOrderReadyNotification_WhenSmsFailed_ShouldReturnFailedNotification() {
        // Given
        String orderId = "order-123";
        String clientPhone = "+1234567890";
        String pin = "1234";
        String restaurantName = "Test Restaurant";

        SmsNotification savedNotification = createSmsNotification();
        savedNotification.setId("saved-id");

        when(notificationPersistencePort.save(any(SmsNotification.class))).thenReturn(savedNotification);
        when(smsProviderPort.sendSms(any(SmsNotification.class))).thenReturn(false);

        // When
        SmsNotification result = notificationUseCase.sendOrderReadyNotification(orderId, clientPhone, pin, restaurantName);

        // Then
        assertNotNull(result);
        verify(notificationPersistencePort, times(2)).save(any(SmsNotification.class));
        verify(smsProviderPort).sendSms(any(SmsNotification.class));
    }

    @Test
    void sendOrderReadyNotification_WithNullOrderId_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification(null, "+1234567890", "1234", "Restaurant"));
    }

    @Test
    void sendOrderReadyNotification_WithEmptyOrderId_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification("", "+1234567890", "1234", "Restaurant"));
    }

    @Test
    void sendOrderReadyNotification_WithNullClientPhone_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification("order-123", null, "1234", "Restaurant"));
    }

    @Test
    void sendOrderReadyNotification_WithEmptyClientPhone_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification("order-123", "", "1234", "Restaurant"));
    }

    @Test
    void sendOrderReadyNotification_WithNullPin_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification("order-123", "+1234567890", null, "Restaurant"));
    }

    @Test
    void sendOrderReadyNotification_WithEmptyPin_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification("order-123", "+1234567890", "", "Restaurant"));
    }

    @Test
    void sendOrderReadyNotification_WithNullRestaurantName_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification("order-123", "+1234567890", "1234", null));
    }

    @Test
    void sendOrderReadyNotification_WithEmptyRestaurantName_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.sendOrderReadyNotification("order-123", "+1234567890", "1234", ""));
    }

    @Test
    void getNotificationsByOrderId_ShouldReturnNotificationList() {
        // Given
        String orderId = "order-123";
        List<SmsNotification> notifications = Arrays.asList(createSmsNotification());

        when(notificationPersistencePort.findByOrderId(orderId)).thenReturn(notifications);

        // When
        List<SmsNotification> result = notificationUseCase.getNotificationsByOrderId(orderId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(notificationPersistencePort).findByOrderId(orderId);
    }

    @Test
    void getNotificationsByOrderId_WithNullOrderId_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.getNotificationsByOrderId(null));
    }

    @Test
    void getNotificationsByOrderId_WithEmptyOrderId_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.getNotificationsByOrderId(""));
    }

    @Test
    void getNotificationById_WhenExists_ShouldReturnNotification() {
        // Given
        String notificationId = "notification-123";
        SmsNotification notification = createSmsNotification();

        when(notificationPersistencePort.findById(notificationId)).thenReturn(Optional.of(notification));

        // When
        SmsNotification result = notificationUseCase.getNotificationById(notificationId);

        // Then
        assertNotNull(result);
        assertEquals(notification, result);
        verify(notificationPersistencePort).findById(notificationId);
    }

    @Test
    void getNotificationById_WhenNotExists_ShouldThrowException() {
        // Given
        String notificationId = "notification-123";

        when(notificationPersistencePort.findById(notificationId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.getNotificationById(notificationId));
    }

    @Test
    void getNotificationById_WithNullId_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.getNotificationById(null));
    }

    @Test
    void getNotificationById_WithEmptyId_ShouldThrowException() {
        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.getNotificationById(""));
    }

    @Test
    void retryFailedNotification_WhenSuccessful_ShouldUpdateToSent() {
        // Given
        String notificationId = "notification-123";
        SmsNotification notification = createSmsNotification();
        notification.setStatus(NotificationStatus.FAILED);
        notification.setRetryAttempts(1);

        when(notificationPersistencePort.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationPersistencePort.save(any(SmsNotification.class))).thenReturn(notification);
        when(smsProviderPort.sendSms(any(SmsNotification.class))).thenReturn(true);

        // When
        notificationUseCase.retryFailedNotification(notificationId);

        // Then
        verify(notificationPersistencePort).findById(notificationId);
        verify(notificationPersistencePort, times(2)).save(any(SmsNotification.class));
        verify(smsProviderPort).sendSms(any(SmsNotification.class));
    }

    @Test
    void retryFailedNotification_WhenFailed_ShouldUpdateToFailed() {
        // Given
        String notificationId = "notification-123";
        SmsNotification notification = createSmsNotification();
        notification.setStatus(NotificationStatus.FAILED);
        notification.setRetryAttempts(1);

        when(notificationPersistencePort.findById(notificationId)).thenReturn(Optional.of(notification));
        when(notificationPersistencePort.save(any(SmsNotification.class))).thenReturn(notification);
        when(smsProviderPort.sendSms(any(SmsNotification.class))).thenReturn(false);

        // When
        notificationUseCase.retryFailedNotification(notificationId);

        // Then
        verify(notificationPersistencePort).findById(notificationId);
        verify(notificationPersistencePort, times(2)).save(any(SmsNotification.class));
        verify(smsProviderPort).sendSms(any(SmsNotification.class));
    }

    @Test
    void retryFailedNotification_WhenNotFailed_ShouldThrowException() {
        // Given
        String notificationId = "notification-123";
        SmsNotification notification = createSmsNotification();
        notification.setStatus(NotificationStatus.SENT);

        when(notificationPersistencePort.findById(notificationId)).thenReturn(Optional.of(notification));

        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.retryFailedNotification(notificationId));
    }

    @Test
    void retryFailedNotification_WhenMaxRetriesExceeded_ShouldThrowException() {
        // Given
        String notificationId = "notification-123";
        SmsNotification notification = createSmsNotification();
        notification.setStatus(NotificationStatus.FAILED);
        notification.setRetryAttempts(3); // MAX_RETRY_ATTEMPTS

        when(notificationPersistencePort.findById(notificationId)).thenReturn(Optional.of(notification));

        // When & Then
        assertThrows(NotificationException.class, () ->
                notificationUseCase.retryFailedNotification(notificationId));
    }

    private SmsNotification createSmsNotification() {
        SmsNotification notification = new SmsNotification();
        notification.setId("test-id");
        notification.setOrderId("order-123");
        notification.setClientPhone("+1234567890");
        notification.setMessage("Test message");
        notification.setPin("1234");
        notification.setRestaurantName("Test Restaurant");
        notification.setStatus(NotificationStatus.PENDING);
        return notification;
    }
}

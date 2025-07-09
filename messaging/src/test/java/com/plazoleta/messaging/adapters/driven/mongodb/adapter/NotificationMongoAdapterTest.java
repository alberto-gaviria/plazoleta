package com.plazoleta.messaging.adapters.driven.mongodb.adapter;

import com.plazoleta.messaging.adapters.driven.mongodb.document.SmsNotificationDocument;
import com.plazoleta.messaging.adapters.driven.mongodb.mapper.INotificationDocumentMapper;
import com.plazoleta.messaging.adapters.driven.mongodb.repository.INotificationRepository;
import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationMongoAdapterTest {

    @Mock
    private INotificationRepository notificationRepository;

    @Mock
    private INotificationDocumentMapper notificationDocumentMapper;

    @InjectMocks
    private NotificationMongoAdapter notificationMongoAdapter;

    @Test
    void save_ShouldReturnSavedNotification() {
        // Given
        SmsNotification notification = createSmsNotification();
        SmsNotificationDocument document = createSmsNotificationDocument();
        SmsNotificationDocument savedDocument = createSmsNotificationDocument();
        savedDocument.setId("saved-id");

        when(notificationDocumentMapper.toDocument(notification)).thenReturn(document);
        when(notificationRepository.save(document)).thenReturn(savedDocument);
        when(notificationDocumentMapper.toModel(savedDocument)).thenReturn(notification);

        // When
        SmsNotification result = notificationMongoAdapter.save(notification);

        // Then
        assertNotNull(result);
        verify(notificationDocumentMapper).toDocument(notification);
        verify(notificationRepository).save(document);
        verify(notificationDocumentMapper).toModel(savedDocument);
    }

    @Test
    void findById_WhenDocumentExists_ShouldReturnNotification() {
        // Given
        String id = "test-id";
        SmsNotificationDocument document = createSmsNotificationDocument();
        SmsNotification notification = createSmsNotification();

        when(notificationRepository.findById(id)).thenReturn(Optional.of(document));
        when(notificationDocumentMapper.toModel(document)).thenReturn(notification);

        // When
        Optional<SmsNotification> result = notificationMongoAdapter.findById(id);

        // Then
        assertTrue(result.isPresent());
        assertEquals(notification, result.get());
        verify(notificationRepository).findById(id);
        verify(notificationDocumentMapper).toModel(document);
    }

    @Test
    void findById_WhenDocumentDoesNotExist_ShouldReturnEmpty() {
        // Given
        String id = "non-existent-id";
        when(notificationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<SmsNotification> result = notificationMongoAdapter.findById(id);

        // Then
        assertFalse(result.isPresent());
        verify(notificationRepository).findById(id);
        verify(notificationDocumentMapper, never()).toModel(any());
    }

    @Test
    void findByOrderId_ShouldReturnNotificationList() {
        // Given
        String orderId = "order-123";
        List<SmsNotificationDocument> documents = Arrays.asList(
                createSmsNotificationDocument(),
                createSmsNotificationDocument()
        );
        List<SmsNotification> notifications = Arrays.asList(
                createSmsNotification(),
                createSmsNotification()
        );

        when(notificationRepository.findByOrderIdOrderByCreatedAtDesc(orderId)).thenReturn(documents);
        when(notificationDocumentMapper.toModelList(documents)).thenReturn(notifications);

        // When
        List<SmsNotification> result = notificationMongoAdapter.findByOrderId(orderId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(notificationRepository).findByOrderIdOrderByCreatedAtDesc(orderId);
        verify(notificationDocumentMapper).toModelList(documents);
    }

    @Test
    void findByStatus_ShouldReturnNotificationList() {
        // Given
        NotificationStatus status = NotificationStatus.PENDING;
        List<SmsNotificationDocument> documents = Arrays.asList(createSmsNotificationDocument());
        List<SmsNotification> notifications = Arrays.asList(createSmsNotification());

        when(notificationRepository.findByStatus(status)).thenReturn(documents);
        when(notificationDocumentMapper.toModelList(documents)).thenReturn(notifications);

        // When
        List<SmsNotification> result = notificationMongoAdapter.findByStatus(status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(notificationRepository).findByStatus(status);
        verify(notificationDocumentMapper).toModelList(documents);
    }

    @Test
    void findFailedNotificationsForRetry_ShouldReturnNotificationList() {
        // Given
        List<SmsNotificationDocument> documents = Arrays.asList(createSmsNotificationDocument());
        List<SmsNotification> notifications = Arrays.asList(createSmsNotification());

        when(notificationRepository.findFailedNotificationsForRetry(any(LocalDateTime.class))).thenReturn(documents);
        when(notificationDocumentMapper.toModelList(documents)).thenReturn(notifications);

        // When
        List<SmsNotification> result = notificationMongoAdapter.findFailedNotificationsForRetry();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(notificationRepository).findFailedNotificationsForRetry(any(LocalDateTime.class));
        verify(notificationDocumentMapper).toModelList(documents);
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

    private SmsNotificationDocument createSmsNotificationDocument() {
        SmsNotificationDocument document = new SmsNotificationDocument();
        document.setId("test-id");
        document.setOrderId("order-123");
        document.setClientPhone("+1234567890");
        document.setMessage("Test message");
        document.setPin("1234");
        document.setRestaurantName("Test Restaurant");
        document.setStatus(NotificationStatus.PENDING);
        return document;
    }
}
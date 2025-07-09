package com.plazoleta.messaging.adapters.driven.mongodb.adapter;

import com.plazoleta.messaging.adapters.driven.mongodb.document.SmsNotificationDocument;
import com.plazoleta.messaging.adapters.driven.mongodb.mapper.INotificationDocumentMapper;
import com.plazoleta.messaging.adapters.driven.mongodb.repository.INotificationRepository;
import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;
import com.plazoleta.messaging.domain.spi.INotificationPersistencePort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class NotificationMongoAdapter implements INotificationPersistencePort {

    private final INotificationRepository notificationRepository;
    private final INotificationDocumentMapper notificationDocumentMapper;

    public NotificationMongoAdapter(INotificationRepository notificationRepository,
                                    INotificationDocumentMapper notificationDocumentMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationDocumentMapper = notificationDocumentMapper;
    }

    @Override
    public SmsNotification save(SmsNotification notification) {
        SmsNotificationDocument document = notificationDocumentMapper.toDocument(notification);
        SmsNotificationDocument savedDocument = notificationRepository.save(document);
        return notificationDocumentMapper.toModel(savedDocument);
    }

    @Override
    public Optional<SmsNotification> findById(String id) {
        Optional<SmsNotificationDocument> document = notificationRepository.findById(id);
        return document.map(notificationDocumentMapper::toModel);
    }

    @Override
    public List<SmsNotification> findByOrderId(String orderId) {
        List<SmsNotificationDocument> documents = notificationRepository.findByOrderIdOrderByCreatedAtDesc(orderId);
        return notificationDocumentMapper.toModelList(documents);
    }

    @Override
    public List<SmsNotification> findByStatus(NotificationStatus status) {
        List<SmsNotificationDocument> documents = notificationRepository.findByStatus(status);
        return notificationDocumentMapper.toModelList(documents);
    }

    @Override
    public List<SmsNotification> findFailedNotificationsForRetry() {
        LocalDateTime since = LocalDateTime.now().minusHours(24);
        List<SmsNotificationDocument> documents = notificationRepository.findFailedNotificationsForRetry(since);
        return notificationDocumentMapper.toModelList(documents);
    }
}
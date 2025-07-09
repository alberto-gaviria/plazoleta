package com.plazoleta.messaging.adapters.driven.mongodb.repository;

import com.plazoleta.messaging.adapters.driven.mongodb.document.SmsNotificationDocument;
import com.plazoleta.messaging.domain.model.NotificationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface INotificationRepository extends MongoRepository<SmsNotificationDocument, String> {

    List<SmsNotificationDocument> findByOrderIdOrderByCreatedAtDesc(String orderId);

    List<SmsNotificationDocument> findByStatus(NotificationStatus status);

    @Query("{ 'status': 'FAILED', 'retryAttempts': { $lt: 3 }, 'createdAt': { $gte: ?0 } }")
    List<SmsNotificationDocument> findFailedNotificationsForRetry(LocalDateTime since);
}
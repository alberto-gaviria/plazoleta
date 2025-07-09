package com.plazoleta.messaging.domain.spi;

import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;

import java.util.List;
import java.util.Optional;

public interface INotificationPersistencePort {
    SmsNotification save(SmsNotification notification);
    Optional<SmsNotification> findById(String id);
    List<SmsNotification> findByOrderId(String orderId);
    List<SmsNotification> findByStatus(NotificationStatus status);
    List<SmsNotification> findFailedNotificationsForRetry();
}
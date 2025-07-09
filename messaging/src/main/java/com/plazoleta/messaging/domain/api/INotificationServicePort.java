package com.plazoleta.messaging.domain.api;

import com.plazoleta.messaging.domain.model.SmsNotification;
import java.util.List;

public interface INotificationServicePort {
    SmsNotification sendOrderReadyNotification(String orderId, String clientPhone, String pin, String restaurantName);
    List<SmsNotification> getNotificationsByOrderId(String orderId);
    SmsNotification getNotificationById(String notificationId);
    void retryFailedNotification(String notificationId);
}
package com.plazoleta.messaging.domain.usecase;

import com.plazoleta.messaging.domain.api.INotificationServicePort;
import com.plazoleta.messaging.domain.model.NotificationStatus;
import com.plazoleta.messaging.domain.model.SmsNotification;
import com.plazoleta.messaging.domain.spi.INotificationPersistencePort;
import com.plazoleta.messaging.domain.spi.ISmsProviderPort;
import com.plazoleta.messaging.domain.util.DomainConstants;
import com.plazoleta.messaging.domain.util.exceptions.NotificationException;

import java.util.List;
import java.util.Optional;

public class NotificationUseCase implements INotificationServicePort {

    private final INotificationPersistencePort notificationPersistencePort;
    private final ISmsProviderPort smsProviderPort;

    public NotificationUseCase(INotificationPersistencePort notificationPersistencePort,
                               ISmsProviderPort smsProviderPort) {
        this.notificationPersistencePort = notificationPersistencePort;
        this.smsProviderPort = smsProviderPort;
    }

    @Override
    public SmsNotification sendOrderReadyNotification(String orderId, String clientPhone, String pin, String restaurantName) {
        validateParameters(orderId, clientPhone, pin, restaurantName);

        String message = buildMessage(orderId, pin, restaurantName);
        SmsNotification notification = new SmsNotification(orderId, clientPhone, message, pin, restaurantName);

        notification = notificationPersistencePort.save(notification);

        if (smsProviderPort.sendSms(notification)) {
            notification.markAsSent();
        } else {
            notification.markAsFailed(DomainConstants.ErrorMessages.ERROR_SMS_SEND_FAILED);
        }

        return notificationPersistencePort.save(notification);
    }

    @Override
    public List<SmsNotification> getNotificationsByOrderId(String orderId) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_ORDER_ID_REQUIRED);
        }
        return notificationPersistencePort.findByOrderId(orderId);
    }

    @Override
    public SmsNotification getNotificationById(String notificationId) {
        if (notificationId == null || notificationId.trim().isEmpty()) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_NOTIFICATION_ID_REQUIRED);
        }

        Optional<SmsNotification> notification = notificationPersistencePort.findById(notificationId);
        if (notification.isEmpty()) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_NOTIFICATION_NOT_FOUND);
        }

        return notification.get();
    }

    @Override
    public void retryFailedNotification(String notificationId) {
        SmsNotification notification = getNotificationById(notificationId);

        if (notification.getStatus() != NotificationStatus.FAILED) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_NOTIFICATION_NOT_FAILED);
        }

        if (notification.getRetryAttempts() >= DomainConstants.Notification.MAX_RETRY_ATTEMPTS) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_MAX_RETRIES_EXCEEDED);
        }

        notification.setStatus(NotificationStatus.RETRYING);
        notification.incrementRetryAttempt();
        notificationPersistencePort.save(notification);

        if (smsProviderPort.sendSms(notification)) {
            notification.markAsSent();
        } else {
            notification.markAsFailed(DomainConstants.ErrorMessages.ERROR_SMS_SEND_FAILED);
        }

        notificationPersistencePort.save(notification);
    }

    private void validateParameters(String orderId, String clientPhone, String pin, String restaurantName) {
        if (orderId == null || orderId.trim().isEmpty()) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_ORDER_ID_REQUIRED);
        }
        if (clientPhone == null || clientPhone.trim().isEmpty()) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_PHONE_REQUIRED);
        }
        if (pin == null || pin.trim().isEmpty()) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_PIN_REQUIRED);
        }
        if (restaurantName == null || restaurantName.trim().isEmpty()) {
            throw new NotificationException(DomainConstants.ErrorMessages.ERROR_RESTAURANT_NAME_REQUIRED);
        }
    }

    private String buildMessage(String orderId, String pin, String restaurantName) {
        return DomainConstants.Templates.ORDER_READY_TEMPLATE
                .replace(DomainConstants.Templates.ORDER_ID_PLACEHOLDER, orderId)
                .replace(DomainConstants.Templates.PIN_PLACEHOLDER, pin)
                .replace(DomainConstants.Templates.RESTAURANT_NAME_PLACEHOLDER, restaurantName)
                .replace(DomainConstants.Templates.TIME_ESTIMATE_PLACEHOLDER, DomainConstants.Notification.DEFAULT_TIME_ESTIMATE);
    }
}
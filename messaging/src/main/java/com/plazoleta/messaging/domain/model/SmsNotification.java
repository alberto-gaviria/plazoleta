package com.plazoleta.messaging.domain.model;

import java.time.LocalDateTime;

public class SmsNotification {

    private String id;
    private String orderId;
    private String clientPhone;
    private String message;
    private String pin;
    private String restaurantName;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
    private String errorMessage;
    private Integer retryAttempts;

    public SmsNotification() {
        this.createdAt = LocalDateTime.now();
        this.status = NotificationStatus.PENDING;
        this.retryAttempts = 0;
    }

    public SmsNotification(String orderId, String clientPhone, String message, String pin, String restaurantName) {
        this();
        this.orderId = orderId;
        this.clientPhone = clientPhone;
        this.message = message;
        this.pin = pin;
        this.restaurantName = restaurantName;
    }

    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    public void markAsFailed(String errorMessage) {
        this.status = NotificationStatus.FAILED;
        this.errorMessage = errorMessage;
    }

    public void incrementRetryAttempt() {
        this.retryAttempts++;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Integer getRetryAttempts() { return retryAttempts; }
    public void setRetryAttempts(Integer retryAttempts) { this.retryAttempts = retryAttempts; }
}
package com.plazoleta.messaging.adapters.driven.mongodb.document;

import com.plazoleta.messaging.domain.model.NotificationStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "sms_notifications")
public class SmsNotificationDocument {

    @Id
    private String id;

    @Field("order_id")
    private String orderId;

    @Field("client_phone")
    private String clientPhone;

    @Field("message")
    private String message;

    @Field("pin")
    private String pin;

    @Field("restaurant_name")
    private String restaurantName;

    @Field("status")
    private NotificationStatus status;

    @Field("sent_at")
    private LocalDateTime sentAt;

    @CreatedDate
    @Field("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Field("updated_at")
    private LocalDateTime updatedAt;

    @Field("error_message")
    private String errorMessage;

    @Field("retry_attempts")
    private Integer retryAttempts;

    public SmsNotificationDocument() {
        this.retryAttempts = 0;
        this.status = NotificationStatus.PENDING;
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

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Integer getRetryAttempts() { return retryAttempts; }
    public void setRetryAttempts(Integer retryAttempts) { this.retryAttempts = retryAttempts; }
}
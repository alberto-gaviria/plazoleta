package com.plazoleta.restaurants.adapters.driven.messaging.dto;

import java.time.LocalDateTime;

public class NotificationResponse {

    private String id;
    private String orderId;
    private String clientPhone;
    private String status;
    private LocalDateTime sentAt;
    private String errorMessage;

    public NotificationResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
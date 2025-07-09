package com.plazoleta.restaurants.adapters.driven.messaging.dto;

public class OrderReadyNotificationRequest {

    private String orderId;
    private String clientPhone;
    private String pin;
    private String restaurantName;

    public OrderReadyNotificationRequest() {}

    public OrderReadyNotificationRequest(String orderId, String clientPhone, String pin, String restaurantName) {
        this.orderId = orderId;
        this.clientPhone = clientPhone;
        this.pin = pin;
        this.restaurantName = restaurantName;
    }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }
}
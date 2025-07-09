package com.plazoleta.restaurants.domain.api;

public interface IMessagingServicePort {
    boolean sendOrderReadyNotification(String orderId, String clientPhone, String pin, String restaurantName);
}
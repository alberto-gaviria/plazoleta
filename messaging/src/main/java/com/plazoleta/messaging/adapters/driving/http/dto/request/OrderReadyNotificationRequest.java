package com.plazoleta.messaging.adapters.driving.http.dto.request;

import com.plazoleta.messaging.adapters.driving.http.util.HttpConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OrderReadyNotificationRequest {

    @NotBlank(message = HttpConstants.Messages.ORDER_ID_REQUIRED)
    private String orderId;

    @NotBlank(message = HttpConstants.Messages.CLIENT_PHONE_REQUIRED)
    @Pattern(regexp = HttpConstants.Validation.PHONE_PATTERN, message = HttpConstants.Messages.PHONE_FORMAT_INVALID)
    private String clientPhone;

    @NotBlank(message = HttpConstants.Messages.PIN_REQUIRED)
    @Pattern(regexp = HttpConstants.Validation.PIN_PATTERN, message = HttpConstants.Messages.PIN_FORMAT_INVALID)
    private String pin;

    @NotBlank(message = HttpConstants.Messages.RESTAURANT_NAME_REQUIRED)
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
package com.plazoleta.restaurants.adapters.driven.messaging.client;

import com.plazoleta.restaurants.adapters.driven.messaging.dto.NotificationResponse;
import com.plazoleta.restaurants.adapters.driven.messaging.dto.OrderReadyNotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "messaging-service",
        url = "${messaging.service.url}",
        configuration = MessagingServiceClientConfig.class
)
public interface IMessagingServiceClient {

    @PostMapping("/notifications/sms/order-ready")
    ResponseEntity<NotificationResponse> sendOrderReadyNotification(@RequestBody OrderReadyNotificationRequest request);
}
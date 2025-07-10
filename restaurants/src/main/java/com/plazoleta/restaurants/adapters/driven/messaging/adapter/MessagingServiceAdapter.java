package com.plazoleta.restaurants.adapters.driven.messaging.adapter;

import com.plazoleta.restaurants.adapters.driven.messaging.client.IMessagingServiceClient;
import com.plazoleta.restaurants.adapters.driven.messaging.dto.NotificationResponse;
import com.plazoleta.restaurants.adapters.driven.messaging.dto.OrderReadyNotificationRequest;
import com.plazoleta.restaurants.domain.api.IMessagingServicePort;
import feign.FeignException;
import org.springframework.http.ResponseEntity;

public class MessagingServiceAdapter implements IMessagingServicePort {

    private final IMessagingServiceClient messagingServiceClient;

    public MessagingServiceAdapter(IMessagingServiceClient messagingServiceClient) {
        this.messagingServiceClient = messagingServiceClient;
    }

    @Override
    public boolean sendOrderReadyNotification(String orderId, String clientPhone, String pin, String restaurantName) {
        try {
            OrderReadyNotificationRequest request = new OrderReadyNotificationRequest(
                    orderId, clientPhone, pin, restaurantName
            );

            ResponseEntity<NotificationResponse> response = messagingServiceClient.sendOrderReadyNotification(request);

            return response.getStatusCode().is2xxSuccessful();

        } catch (FeignException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
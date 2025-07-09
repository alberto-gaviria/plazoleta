package com.plazoleta.restaurants.adapters.driven.messaging.adapter;

import com.plazoleta.restaurants.adapters.driven.messaging.client.IMessagingServiceClient;
import com.plazoleta.restaurants.adapters.driven.messaging.dto.NotificationResponse;
import com.plazoleta.restaurants.adapters.driven.messaging.dto.OrderReadyNotificationRequest;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.domain.api.IMessagingServicePort;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class MessagingServiceAdapter implements IMessagingServicePort {

    private static final Logger logger = LoggerFactory.getLogger(MessagingServiceAdapter.class);
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

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info(AdapterConstants.LogMessages.NOTIFICATION_SUCCESS, orderId);
                return true;
            } else {
                logger.warn(AdapterConstants.LogMessages.NOTIFICATION_ERROR_STATUS, orderId, response.getStatusCode());
                return false;
            }

        } catch (FeignException e) {
            logger.error(AdapterConstants.LogMessages.NOTIFICATION_COMMUNICATION_ERROR, orderId, e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error(AdapterConstants.LogMessages.NOTIFICATION_UNEXPECTED_ERROR, orderId, e.getMessage());
            return false;
        }
    }
}
package com.plazoleta.restaurants.adapters.driven.messaging.util;

public class MessagingAdapterConstants {

    private MessagingAdapterConstants() {
    }

    public static final String NOTIFICATION_SUCCESS =
            "Notificación enviada exitosamente para el pedido: {}";

    public static final String NOTIFICATION_ERROR_STATUS =
            "Error al enviar notificación para el pedido: {}. Código de estado: {}";

    public static final String NOTIFICATION_COMMUNICATION_ERROR =
            "Error de comunicación con el servicio de mensajería para el pedido: {}. Error: {}";

    public static final String NOTIFICATION_UNEXPECTED_ERROR =
            "Error inesperado al enviar notificación para el pedido: {}. Error: {}";
}
package com.plazoleta.messaging.domain.util;

public final class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Notification {
        public static final int MAX_RETRY_ATTEMPTS = 3;
        public static final int SMS_TIMEOUT_SECONDS = 30;
        public static final String DEFAULT_TIME_ESTIMATE = "10";
        public static final int PIN_LENGTH = 4;

        private Notification() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class ErrorMessages {
        public static final String ERROR_ORDER_ID_REQUIRED = "El ID del pedido es obligatorio";
        public static final String ERROR_PHONE_REQUIRED = "El número de teléfono es obligatorio";
        public static final String ERROR_PHONE_INVALID = "El formato del número de teléfono es inválido";
        public static final String ERROR_PIN_REQUIRED = "El PIN de seguridad es obligatorio";
        public static final String ERROR_PIN_INVALID = "El PIN debe tener 4 dígitos numéricos";
        public static final String ERROR_RESTAURANT_NAME_REQUIRED = "El nombre del restaurante es obligatorio";
        public static final String ERROR_NOTIFICATION_ID_REQUIRED = "El ID de la notificación es obligatorio";
        public static final String ERROR_NOTIFICATION_NOT_FOUND = "No se encontró la notificación especificada";
        public static final String ERROR_NOTIFICATION_NOT_FAILED = "La notificación no está en estado fallido";
        public static final String ERROR_MAX_RETRIES_EXCEEDED = "Se ha excedido el número máximo de reintentos";
        public static final String ERROR_SMS_SEND_FAILED = "Error al enviar el SMS";
        public static final String ERROR_TWILIO_NOT_CONFIGURED = "Twilio no está configurado correctamente";
        public static final String ERROR_MESSAGE_TEMPLATE_REQUIRED = "La plantilla del mensaje es obligatoria";

        private ErrorMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Templates {
        public static final String ORDER_READY_TEMPLATE = "Su pedido #{orderId} está listo. PIN: #{pin}. " +
                "Restaurante: #{restaurantName}. Retire en #{timeEstimate} minutos.";
        public static final String ORDER_ID_PLACEHOLDER = "#{orderId}";
        public static final String PIN_PLACEHOLDER = "#{pin}";
        public static final String RESTAURANT_NAME_PLACEHOLDER = "#{restaurantName}";
        public static final String TIME_ESTIMATE_PLACEHOLDER = "#{timeEstimate}";

        private Templates() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
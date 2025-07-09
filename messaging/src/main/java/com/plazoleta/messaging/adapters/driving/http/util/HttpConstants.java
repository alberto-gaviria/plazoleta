package com.plazoleta.messaging.adapters.driving.http.util;

public final class HttpConstants {

    private HttpConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Roles {
        public static final String ADMINISTRADOR = "ADMINISTRADOR";
        public static final String PROPIETARIO = "PROPIETARIO";
        public static final String CLIENTE = "CLIENTE";
        public static final String EMPLEADO = "EMPLEADO";

        private Roles() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Paths {
        public static final String NOTIFICATIONS = "/notifications";
        public static final String SMS_ORDER_READY = "/sms/order-ready";
        public static final String ORDER_NOTIFICATIONS = "/order/{orderId}";
        public static final String NOTIFICATION_BY_ID = "/{notificationId}";
        public static final String RETRY_NOTIFICATION = "/{notificationId}/retry";

        private Paths() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Messages {
        public static final String NOTIFICATION_SENT_SUCCESS = "Notificación enviada exitosamente";
        public static final String GET_NOTIFICATIONS_SUCCESS = "Lista de notificaciones obtenida exitosamente";
        public static final String GET_NOTIFICATION_SUCCESS = "Notificación obtenida exitosamente";
        public static final String RETRY_SUCCESS = "Reintento de notificación realizado exitosamente";

        public static final String INVALID_INPUT = "Datos de entrada inválidos";
        public static final String INVALID_RETRY_REQUEST = "Solicitud de reintento inválida";
        public static final String UNAUTHORIZED = "No autorizado - Token requerido";
        public static final String FORBIDDEN = "Prohibido - No tiene permisos para esta acción";
        public static final String FORBIDDEN_EMPLOYEE = "Prohibido - Solo empleados pueden realizar esta acción";

        public static final String ORDER_NOT_FOUND = "Pedido no encontrado";
        public static final String NOTIFICATION_NOT_FOUND = "Notificación no encontrada";
        public static final String MAX_RETRIES_EXCEEDED = "Se ha excedido el número máximo de reintentos";
        public static final String INTERNAL_ERROR = "Error interno del servidor";

        public static final String ORDER_ID_REQUIRED = "El ID del pedido es obligatorio";
        public static final String CLIENT_PHONE_REQUIRED = "El teléfono del cliente es obligatorio";
        public static final String PHONE_FORMAT_INVALID = "Formato de teléfono inválido";
        public static final String PIN_REQUIRED = "El PIN de seguridad es obligatorio";
        public static final String PIN_FORMAT_INVALID = "El PIN debe tener 4 dígitos";
        public static final String RESTAURANT_NAME_REQUIRED = "El nombre del restaurante es obligatorio";

        private Messages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Validation {
        public static final String PHONE_PATTERN = "^\\+?[1-9]\\d{1,14}$";
        public static final String PIN_PATTERN = "^\\d{4}$";

        private Validation() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
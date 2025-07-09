package com.plazoleta.messaging.adapters.driven.mongodb.util;

public final class AdapterConstants {

    private AdapterConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Database {
        public static final String SMS_NOTIFICATIONS_COLLECTION = "sms_notifications";
        public static final String ORDER_ID_FIELD = "order_id";
        public static final String CLIENT_PHONE_FIELD = "client_phone";
        public static final String STATUS_FIELD = "status";
        public static final String CREATED_AT_FIELD = "created_at";
        public static final String RETRY_ATTEMPTS_FIELD = "retry_attempts";

        private Database() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Retry {
        public static final int RETRY_HOURS_LIMIT = 24;
        public static final int MAX_RETRY_ATTEMPTS = 3;

        private Retry() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class ErrorMessages {
        public static final String NOTIFICATION_NOT_FOUND = "No se encontró la notificación especificada";
        public static final String DATABASE_CONNECTION_ERROR = "Error de conexión con la base de datos";

        private ErrorMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}

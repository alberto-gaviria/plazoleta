package com.plazoleta.messaging.adapters.driven.twilio.util;

public final class TwilioConstants {

    private TwilioConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Messages {
        public static final String TWILIO_INITIALIZED_SUCCESS = "Twilio inicializado correctamente";
        public static final String TWILIO_INITIALIZATION_ERROR = "Error inicializando Twilio: {}";
        public static final String TWILIO_DEVELOPMENT_MODE = "Twilio no está habilitado o configurado - Modo desarrollo activo";
        public static final String TWILIO_SMS_SIMULATED = "Twilio no configurado - SMS simulado para: {}";
        public static final String SMS_SENT_SUCCESS = "SMS enviado exitosamente. SID: {}, Estado: {}";
        public static final String TWILIO_SMS_ERROR = "Error enviando SMS via Twilio: {}";
        public static final String SMS_UNEXPECTED_ERROR = "Error inesperado enviando SMS: {}";

        private Messages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Config {
        public static final int CONNECTION_TIMEOUT = 30000;
        public static final int READ_TIMEOUT = 60000;

        private Config() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
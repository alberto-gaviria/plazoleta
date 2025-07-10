package com.plazoleta.traceability.adapters.driven.mongodb.util;

public class TraceabilityAdapterConstants {

    private TraceabilityAdapterConstants() {

    }

    public static final String TIMESTAMP_FIELD = "timestamp";

    public static class ErrorMessages {
        public static final String TRACEABILITY_SAVE_ERROR =
                "Error al guardar la trazabilidad en MongoDB";

        public static final String TRACEABILITY_RETRIEVE_ERROR =
                "Error al recuperar la trazabilidad desde MongoDB";

        public static final String TRACEABILITY_UNEXPECTED_ERROR =
                "Error inesperado al acceder a MongoDB";

        private ErrorMessages() {

        }
    }


    public static class FeignMessages {
        public static final String TRACEABILITY_SUCCESS =
                "Trazabilidad registrada exitosamente para el pedido: {}";
        public static final String TRACEABILITY_CLIENT_ERROR =
                "Error del cliente al registrar trazabilidad para el pedido: {}. Status: {}";
        public static final String TRACEABILITY_FEIGN_ERROR =
                "Error de comunicación con servicio de trazabilidad para el pedido: {}. Error: {}";
        public static final String TRACEABILITY_UNEXPECTED_ERROR =
                "Error inesperado al registrar trazabilidad para el pedido: {}. Error: {}";

        private FeignMessages() {

        }
    }
}
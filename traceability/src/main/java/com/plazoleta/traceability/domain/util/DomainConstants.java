package com.plazoleta.traceability.domain.util;

public final class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Traceability {
        public static final int MAX_PAGE_SIZE = 100;
        public static final int MIN_PAGE_NUMBER = 0;
        public static final int MIN_PAGE_SIZE = 1;
        public static final int DEFAULT_PAGE_SIZE = 10;

        public static final String ERROR_ORDER_ID_REQUIRED = "El ID del pedido es obligatorio";
        public static final String ERROR_CLIENT_ID_REQUIRED = "El ID del cliente es obligatorio";
        public static final String ERROR_CLIENT_EMAIL_REQUIRED = "El email del cliente es obligatorio";
        public static final String ERROR_NEW_STATUS_REQUIRED = "El nuevo estado es obligatorio";
        public static final String ERROR_ORDER_NOT_BELONGS_TO_CLIENT = "El pedido no pertenece al cliente especificado";
        public static final String ERROR_ORDER_NOT_FOUND = "No se encontró el pedido especificado";
        public static final String ERROR_NO_TRACEABILITY_FOUND = "No se encontró información de trazabilidad para este pedido";

        public static final String ERROR_PAGE_NUMBER_INVALID = "El número de página debe ser mayor o igual a " + MIN_PAGE_NUMBER;
        public static final String ERROR_PAGE_SIZE_INVALID = "El tamaño de página debe ser mayor o igual a " + MIN_PAGE_SIZE;
        public static final String ERROR_PAGE_SIZE_TOO_LARGE = "El tamaño de página no puede ser mayor a " + MAX_PAGE_SIZE;

        public static final String STATUS_PENDIENTE = "PENDIENTE";
        public static final String STATUS_EN_PREPARACION = "EN_PREPARACION";
        public static final String STATUS_LISTO = "LISTO";
        public static final String STATUS_ENTREGADO = "ENTREGADO";
        public static final String STATUS_CANCELADO = "CANCELADO";

        private Traceability() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
package com.plazoleta.traceability.adapters.driving.http.util;

public final class HttpConstants {

    private HttpConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Roles {
        public static final String CLIENTE = "CLIENTE";

        private Roles() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Paths {
        public static final String TRACEABILITY = "/traceability";
        public static final String ORDER_TRACEABILITY = "/order/{orderId}";

        private Paths() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Pagination {
        public static final String DEFAULT_PAGE_VALUE = "0";
        public static final String DEFAULT_SIZE_VALUE = "10";
        public static final int MIN_PAGE = 0;
        public static final int MIN_SIZE = 1;
        public static final int MAX_SIZE = 100;

        private Pagination() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Messages {
        public static final String GET_TRACEABILITY_SUCCESS = "Trazabilidad del pedido obtenida exitosamente";
        public static final String INVALID_PAGINATION = "Parámetros de paginación inválidos";
        public static final String UNAUTHORIZED = "No autorizado - Token requerido";
        public static final String FORBIDDEN = "Prohibido - No tiene permisos para esta acción";
        public static final String ORDER_NOT_FOUND = "Pedido no encontrado";
        public static final String INTERNAL_ERROR = "Error interno del servidor";

        private Messages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
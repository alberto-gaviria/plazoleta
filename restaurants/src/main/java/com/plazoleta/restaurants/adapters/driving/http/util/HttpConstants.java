package com.plazoleta.restaurants.adapters.driving.http.util;

public final class HttpConstants {

    private HttpConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Roles {
        public static final String ADMINISTRADOR = "ADMINISTRADOR";
        public static final String PROPIETARIO = "PROPIETARIO";
        public static final String CLIENTE = "CLIENTE";
        public static final String EMPLEADO = "EMPLEADO";

        private Roles() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Paths {
        public static final String PLATOS = "/platos";
        public static final String DISH_BY_ID = "/{dishId}";
        public static final String DISH_STATUS = "/{dishId}/estado";
        public static final String DISH_BY_RESTAURANT = "/restaurante/{restaurantId}";
        public static final String PEDIDOS = "/pedidos";

        public static final String ASSIGN_EMPLOYEE = "/asignar";

        private Paths() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Pagination {
        public static final String DEFAULT_PAGE_VALUE = "0";
        public static final String DEFAULT_SIZE_VALUE = "10";
        public static final int MIN_PAGE = 0;
        public static final int MIN_SIZE = 1;
        public static final int MAX_SIZE = 50;

        private Pagination() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Messages {

        public static final String CREATE_ORDER_SUCCESS = "Pedido creado exitosamente";
        public static final String GET_ORDERS_SUCCESS = "Lista de pedidos obtenida exitosamente";

        public static final String ASSIGN_EMPLOYEE_SUCCESS = "Empleado asignado exitosamente al pedido";

        public static final String INVALID_ORDER_STATUS = "Estado de pedido inválido: ";
        public static final String INVALID_INPUT = "Datos de entrada inválidos";
        public static final String INVALID_PAGINATION = "Parámetros de paginación inválidos";
        public static final String UNAUTHORIZED = "No autorizado - Token requerido";
        public static final String FORBIDDEN_EMPLOYEE = "Prohibido - Solo empleados pueden listar pedidos";
        public static final String FORBIDDEN_CLIENT = "Prohibido - Solo clientes pueden crear pedidos";
        public static final String EMPLOYEE_WITHOUT_RESTAURANT = "Empleado sin restaurante asignado";
        public static final String CLIENT_HAS_ACTIVE_ORDER = "El cliente ya tiene un pedido activo";
        public static final String ORDER_NOT_FOUND = "Pedido no encontrado";
        public static final String DISH_NOT_FOUND = "Plato o restaurante no encontrado";
        public static final String INTERNAL_ERROR = "Error interno del servidor";

        private Messages() { throw new IllegalStateException("Constants class"); }
    }
}
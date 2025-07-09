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

        private Roles() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Paths {
        public static final String PLATOS = "/platos";
        public static final String DISH_BY_ID = "/{dishId}";
        public static final String DISH_STATUS = "/{dishId}/estado";
        public static final String DISH_BY_RESTAURANT = "/restaurante/{restaurantId}";
        public static final String PEDIDOS = "/pedidos";
        public static final String ASSIGN_EMPLOYEE = "/asignar";
        public static final String MARK_ORDER_READY = "/marcar-listo";
        public static final String DELIVER_ORDER = "/entregar";

        private Paths() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Pagination {
        public static final String DEFAULT_PAGE_VALUE = "0";
        public static final String DEFAULT_SIZE_VALUE = "10";
        public static final int MIN_PAGE = 0;
        public static final int MIN_SIZE = 1;
        public static final int MAX_SIZE = 50;

        private Pagination() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Messages {
        // Success Messages
        public static final String CREATE_ORDER_SUCCESS = "Pedido creado exitosamente";
        public static final String GET_ORDERS_SUCCESS = "Lista de pedidos obtenida exitosamente";
        public static final String ASSIGN_EMPLOYEE_SUCCESS = "Empleado asignado exitosamente al pedido";
        public static final String ORDER_READY_SUCCESS = "Pedido marcado como listo y notificación enviada";
        public static final String DELIVER_ORDER_SUCCESS = "Pedido entregado exitosamente";
        public static final String CREATE_DISH_SUCCESS = "Plato creado exitosamente";
        public static final String UPDATE_DISH_SUCCESS = "Plato actualizado exitosamente";
        public static final String TOGGLE_DISH_SUCCESS = "Estado del plato actualizado exitosamente";
        public static final String GET_DISHES_SUCCESS = "Lista de platos obtenida exitosamente";
        public static final String CREATE_RESTAURANT_SUCCESS = "Restaurante creado exitosamente";
        public static final String GET_RESTAURANTS_SUCCESS = "Lista de restaurantes obtenida exitosamente";

        // Error Messages
        public static final String INVALID_ORDER_STATUS = "Estado de pedido inválido: ";
        public static final String INVALID_INPUT = "Datos de entrada inválidos";
        public static final String INVALID_PAGINATION = "Parámetros de paginación inválidos";
        public static final String UNAUTHORIZED = "No autorizado - Token requerido";
        public static final String FORBIDDEN = "Prohibido - No tiene permisos para esta acción";
        public static final String FORBIDDEN_EMPLOYEE = "Prohibido - Solo empleados pueden realizar esta acción";
        public static final String FORBIDDEN_CLIENT = "Prohibido - Solo clientes pueden crear pedidos";
        public static final String FORBIDDEN_OWNER = "Prohibido - Solo propietarios pueden realizar esta acción";
        public static final String FORBIDDEN_ADMIN = "Prohibido - Solo administradores pueden realizar esta acción";
        public static final String EMPLOYEE_WITHOUT_RESTAURANT = "Empleado sin restaurante asignado";
        public static final String CLIENT_HAS_ACTIVE_ORDER = "El cliente ya tiene un pedido activo";
        public static final String ORDER_NOT_FOUND = "Pedido no encontrado";
        public static final String DISH_NOT_FOUND = "Plato no encontrado";
        public static final String RESTAURANT_NOT_FOUND = "Restaurante no encontrado";
        public static final String CATEGORY_NOT_FOUND = "Categoría no encontrada";
        public static final String INTERNAL_ERROR = "Error interno del servidor";
        public static final String NOTIFICATION_ERROR = "Error enviando notificación";
        public static final String INVALID_PIN = "PIN de seguridad inválido";

        private Messages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class ValidationPatterns {
        public static final String PHONE_PATTERN = "^\\+?[0-9]{1,13}$";
        public static final String NIT_PATTERN = "^[0-9]+$";
        public static final String PIN_PATTERN = "^\\d{4}$";
        public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

        private ValidationPatterns() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Headers {
        public static final String AUTHORIZATION = "Authorization";
        public static final String BEARER_PREFIX = "Bearer ";
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String APPLICATION_JSON = "application/json";

        private Headers() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class HttpStatusMessages {
        public static final String BAD_REQUEST = "Solicitud incorrecta";
        public static final String UNAUTHORIZED = "No autorizado";
        public static final String FORBIDDEN = "Prohibido";
        public static final String NOT_FOUND = "No encontrado";
        public static final String CONFLICT = "Conflicto";
        public static final String INTERNAL_SERVER_ERROR = "Error interno del servidor";

        private HttpStatusMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
package com.plazoleta.restaurants.adapters.driving.http.util;

public final class HttpConstants {

    private HttpConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Roles {
        public static final String ADMINISTRADOR = "ADMINISTRADOR";
        public static final String PROPIETARIO = "PROPIETARIO";
        public static final String CLIENTE = "CLIENTE";

        private Roles() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Paths {
        public static final String PLATOS = "/platos";
        public static final String DISH_BY_ID = "/{dishId}";
        public static final String DISH_STATUS = "/{dishId}/estado";

        private Paths() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Messages {

        public static final String CREATE_DISH_SUCCESS = "Plato creado exitosamente";
        public static final String UPDATE_DISH_SUCCESS = "Plato actualizado exitosamente";
        public static final String UPDATE_STATUS_SUCCESS = "Estado del plato actualizado exitosamente";

        public static final String INVALID_INPUT = "Datos de entrada inválidos";
        public static final String UNAUTHORIZED = "No autorizado - Token requerido";
        public static final String FORBIDDEN_CREATE_DISH = "Prohibido - Solo propietarios pueden crear platos";
        public static final String FORBIDDEN_UPDATE_DISH = "Prohibido - Solo el propietario del restaurante puede modificar platos";
        public static final String FORBIDDEN_TOGGLE_DISH = "Prohibido - Solo el propietario del restaurante puede cambiar el estado de platos";
        public static final String DISH_NOT_FOUND = "Plato no encontrado";
        public static final String RESTAURANT_NOT_FOUND = "Restaurante no encontrado";
        public static final String INTERNAL_ERROR = "Error interno del servidor";

        private Messages() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Pagination {
        public static final String DEFAULT_PAGE_VALUE = "0";
        public static final String DEFAULT_SIZE_VALUE = "10";
        public static final int MIN_PAGE = 0;
        public static final int MIN_SIZE = 1;
        public static final int MAX_SIZE = 50;

        private Pagination() { throw new IllegalStateException("Constants class"); }
    }
}
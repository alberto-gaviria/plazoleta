package com.plazoleta.restaurants.adapters.driven.mysql.util;

public final class AdapterConstants {

    private AdapterConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class DatabaseColumns {
        public static final String NOMBRE_COLUMN = "nombre";
        public static final String NIT_COLUMN = "nit";
        public static final String ID_COLUMN = "id";
        public static final String FECHA_COLUMN = "fecha";
        public static final String ESTADO_COLUMN = "estado";
        public static final String ID_CLIENTE_COLUMN = "id_cliente";
        public static final String ID_RESTAURANTE_COLUMN = "id_restaurante";
        public static final String ID_PLATO_COLUMN = "id_plato";
        public static final String ID_PEDIDO_COLUMN = "id_pedido";
        public static final String CANTIDAD_COLUMN = "cantidad";
        public static final String ACTIVO_COLUMN = "activo";

        private DatabaseColumns() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class ErrorMessages {
        public static final String RESTAURANT_NIT_DUPLICADO = "Ya existe un restaurante con ese NIT";
        public static final String RESTAURANT_NOMBRE_DUPLICADO = "Ya existe un restaurante con ese nombre";
        public static final String RESTAURANT_NO_ENCONTRADO = "No se encontró el restaurante solicitado";
        public static final String PROPIETARIO_NO_ENCONTRADO = "No se encontró el propietario solicitado";
        public static final String DISH_NO_ENCONTRADO = "No se encontró el plato solicitado";
        public static final String ORDER_NO_ENCONTRADO = "No se encontró el pedido solicitado";
        public static final String CLIENTE_NO_ENCONTRADO = "No se encontró el cliente solicitado";
        public static final String CATEGORIA_NO_ENCONTRADA = "No se encontró la categoría solicitada";
        public static final String PLATO_NO_ACTIVO = "El plato no está disponible";
        public static final String PLATOS_RESTAURANTE_DIFERENTE = "Los platos deben ser del mismo restaurante";
        public static final String CLIENTE_CON_PEDIDO_ACTIVO = "El cliente ya tiene un pedido activo";
        public static final String EMPLEADO_SIN_RESTAURANTE = "El empleado no tiene un restaurante asignado";

        private ErrorMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class QueryConstants {
        public static final String FIND_ACTIVE_ORDER_BY_CLIENT =
                "SELECT o FROM OrderEntity o WHERE o.idCliente = :clientId AND o.estado IN ('PENDIENTE', 'EN_PREPARACION', 'LISTO')";
        public static final String FIND_ACTIVE_DISHES_BY_RESTAURANT_AND_CATEGORY =
                "SELECT d FROM DishEntity d WHERE d.idRestaurante = :restaurantId AND d.activo = true AND (:categoryId IS NULL OR d.idCategoria = :categoryId)";
        public static final String FIND_ORDERS_BY_PEDIDO_ID =
                "SELECT od FROM OrderDishEntity od WHERE od.idPedido = :pedidoId";

        private QueryConstants() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class ValidationMessages {
        public static final String INVALID_PAGE_NUMBER = "Número de página inválido";
        public static final String INVALID_PAGE_SIZE = "Tamaño de página inválido";
        public static final String INVALID_ORDER_STATUS = "Estado de pedido inválido";
        public static final String REQUIRED_FIELD_MISSING = "Campo obligatorio faltante";
        public static final String INVALID_DISH_QUANTITY = "La cantidad del plato debe ser mayor a 0";
        public static final String EMPTY_ORDER = "El pedido debe contener al menos un plato";

        private ValidationMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class OrderConstants {
        public static final String ESTADO_PENDIENTE = "PENDIENTE";
        public static final String ESTADO_EN_PREPARACION = "EN_PREPARACION";
        public static final String ESTADO_LISTO = "LISTO";
        public static final String ESTADO_ENTREGADO = "ENTREGADO";
        public static final String ESTADO_CANCELADO = "CANCELADO";

        private OrderConstants() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
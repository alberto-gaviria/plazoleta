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
        public static final String PIN_SEGURIDAD_COLUMN = "pin_seguridad";

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
        public static final String CATEGORY_NO_ENCONTRADA = "Categoría no encontrada";
        public static final String PLATO_NO_ACTIVO = "El plato no está disponible";
        public static final String PLATOS_RESTAURANTE_DIFERENTE = "Los platos deben ser del mismo restaurante";
        public static final String CLIENTE_CON_PEDIDO_ACTIVO = "El cliente ya tiene un pedido activo";
        public static final String EMPLEADO_SIN_RESTAURANTE = "El empleado no tiene un restaurante asignado";

        private ErrorMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class LogMessages {
        public static final String NOTIFICATION_SUCCESS = "Notificación enviada exitosamente para pedido: {}";
        public static final String NOTIFICATION_ERROR_STATUS = "Error enviando notificación para pedido: {}. Status: {}";
        public static final String NOTIFICATION_COMMUNICATION_ERROR = "Error de comunicación con messaging service para pedido: {}. Error: {}";
        public static final String NOTIFICATION_UNEXPECTED_ERROR = "Error inesperado enviando notificación para pedido: {}. Error: {}";

        private LogMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class TemporaryData {
        public static final String DEFAULT_CLIENT_PHONE = "+573001234567";
        public static final String DUMMY_CLIENT_PHONE = "+573107096798";
        public static final String DEFAULT_CLIENT_EMAIL_PREFIX = "cliente.";
        public static final String DEFAULT_EMPLOYEE_EMAIL_PREFIX = "empleado.";
        public static final String DEFAULT_EMAIL_DOMAIN = "@plazoleta.com";

        private TemporaryData() {
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

    public static final class EfficiencyConstants {

        public static final int ORDER_ID_INDEX = 0;
        public static final int START_DATE_INDEX = 1;
        public static final int END_DATE_INDEX = 2;
        public static final int EMPLOYEE_ID_INDEX = 3;
        public static final int STATUS_INDEX = 4;

        public static final int EMPLOYEE_STAT_ID_INDEX = 0;
        public static final int TOTAL_ORDERS_INDEX = 1;
        public static final int AVG_TIME_INDEX = 2;

        public static final Long DEFAULT_TIME_MINUTES = 0L;
        public static final String DEFAULT_EMPLOYEE_NAME = "Empleado";

        public static final int RANKING_START_INDEX = 0;
        public static final int RANKING_INCREMENT = 1;

        public static final String EMAIL_SEPARATOR = "@";
        public static final int EMAIL_PREFIX_INDEX = 0;

        public static final int MIN_DELIVERY_TIME = 30;
        public static final int MAX_DELIVERY_TIME = 90;
        public static final int MIN_CANCELLED_TIME = 5;
        public static final int MAX_CANCELLED_TIME = 25;
        public static final int DEFAULT_PROCESSING_TIME = 45;

        private EfficiencyConstants() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class TimeFormatConstants {
        public static final String MINUTES_SINGULAR = "minuto";
        public static final String MINUTES_PLURAL = "minutos";
        public static final String SECONDS_SINGULAR = "segundo";
        public static final String SECONDS_PLURAL = "segundos";
        public static final String TIME_SEPARATOR = " ";
        public static final int SECONDS_PER_MINUTE = 60;
        public static final int MIN_SECONDS_FOR_DISPLAY = 1;

        private TimeFormatConstants() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class EfficiencyQueryConstants {
        public static final String COMPLETED_STATES = "('ENTREGADO', 'CANCELADO')";
        public static final String ORDER_BY_DATE_DESC = "ORDER BY o.fecha DESC";
        public static final String ORDER_BY_AVG_TIME_ASC = "ORDER BY tiempo_promedio_minutos ASC";
        public static final String GROUP_BY_EMPLOYEE = "GROUP BY o.id_empleado";
        public static final String HAVING_COUNT_GREATER_ZERO = "HAVING COUNT(o.id) > 0";

        private EfficiencyQueryConstants() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class EfficiencyValidationConstants {
        public static final int MIN_ORDERS_FOR_RANKING = 1;
        public static final double MIN_TIME_MINUTES = 0.0;
        public static final double MAX_TIME_MINUTES = 1440.0; // 24 horas
        public static final int MAX_RANKING_POSITION = 1000;

        private EfficiencyValidationConstants() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
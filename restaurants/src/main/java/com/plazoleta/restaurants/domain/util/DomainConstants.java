package com.plazoleta.restaurants.domain.util;

public final class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Restaurant {

        public static final String SOLO_NUMEROS_PATTERN = "^[0-9]+$";
        public static final String TELEFONO_PATTERN = "^\\+?[0-9]{1,13}$";

        public static final String ROL_PROPIETARIO = "PROPIETARIO";
        public static final String ROL_ADMINISTRADOR = "ADMINISTRADOR";
        public static final String ROL_CLIENTE = "CLIENTE";

        public static final Long ROL_ADMINISTRADOR_ID = 1L;
        public static final Long ROL_PROPIETARIO_ID = 2L;
        public static final Long ROL_CLIENTE_ID = 4L;

        public static final int MAX_PAGE_SIZE = 100;
        public static final int DEFAULT_PAGE_SIZE = 10;
        public static final int MIN_PAGE_NUMBER = 0;
        public static final int MIN_PAGE_SIZE = 1;

        public static final String ERROR_RESTAURANT_NULO = "El restaurante no puede ser nulo";
        public static final String ERROR_NOMBRE_REQUERIDO = "El nombre es obligatorio";
        public static final String ERROR_NIT_REQUERIDO = "El NIT es obligatorio";
        public static final String ERROR_DIRECCION_REQUERIDA = "La dirección es obligatoria";
        public static final String ERROR_TELEFONO_REQUERIDO = "El teléfono es obligatorio";
        public static final String ERROR_URL_LOGO_REQUERIDA = "La URL del logo es obligatoria";
        public static final String ERROR_ID_PROPIETARIO_REQUERIDO = "El ID del propietario es obligatorio";
        public static final String ERROR_PROPIETARIO_NO_VALIDO = "El ID del propietario no corresponde a un usuario con rol propietario";
        public static final String ERROR_PROPIETARIO_NO_ENCONTRADO = "No se encontró el usuario propietario especificado";
        public static final String ERROR_NOMBRE_SOLO_NUMEROS = "El nombre del restaurante no puede contener sólo números";
        public static final String ERROR_NIT_FORMATO_INVALIDO = "El NIT debe contener únicamente números";
        public static final String ERROR_TELEFONO_FORMATO_INVALIDO = "El teléfono debe contener máximo 13 caracteres numéricos y puede incluir el símbolo +";

        public static final String ERROR_ADMIN_ID_REQUERIDO = "El ID del administrador es obligatorio";
        public static final String ERROR_ADMINISTRADOR_NO_ENCONTRADO = "No se encontró el usuario administrador especificado";
        public static final String ERROR_ADMINISTRADOR_NO_VALIDO = "El ID del administrador no corresponde a un usuario con rol administrador";

        public static final String ERROR_PAGE_NUMBER_INVALID = "El número de página debe ser mayor o igual a " + MIN_PAGE_NUMBER;
        public static final String ERROR_PAGE_SIZE_INVALID = "El tamaño de página debe ser mayor o igual a " + MIN_PAGE_SIZE;
        public static final String ERROR_PAGE_SIZE_TOO_LARGE = "El tamaño de página no puede ser mayor a " + MAX_PAGE_SIZE;

        private Restaurant() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Dish {

        public static final String ROL_PROPIETARIO = "PROPIETARIO";

        public static final int MIN_PRICE_VALUE = 0;

        public static final int MAX_PAGE_SIZE = 100;
        public static final int MIN_PAGE_NUMBER = 0;
        public static final int MIN_PAGE_SIZE = 1;

        public static final String ERROR_DISH_NULO = "El plato no puede ser nulo";
        public static final String ERROR_NOMBRE_REQUERIDO = "El nombre del plato es obligatorio";
        public static final String ERROR_PRECIO_REQUERIDO = "El precio del plato es obligatorio";
        public static final String ERROR_PRECIO_POSITIVO = "El precio del plato debe ser un número entero positivo " +
                "y mayor a " + MIN_PRICE_VALUE;
        public static final String ERROR_DESCRIPCION_REQUERIDA = "La descripción del plato es obligatoria";
        public static final String ERROR_URL_IMAGEN_REQUERIDA = "La URL de la imagen es obligatoria";
        public static final String ERROR_CATEGORIA_REQUERIDA = "La categoría es obligatoria";
        public static final String ERROR_RESTAURANTE_REQUERIDO = "El restaurante es obligatorio";
        public static final String ERROR_RESTAURANTE_NO_ENCONTRADO = "No se encontró el restaurante especificado";
        public static final String ERROR_PROPIETARIO_NO_AUTORIZADO = "Solo el propietario del restaurante puede crear " +
                "o modificar platos";
        public static final String ERROR_DISH_NO_ENCONTRADO = "No se encontró el plato especificado";
        public static final String ERROR_DISH_ID_REQUERIDO = "El ID del plato es obligatorio";
        public static final String ERROR_USUARIO_REQUERIDO = "El ID del usuario es obligatorio";
        public static final String ERROR_ESTADO_REQUERIDO = "El estado del plato (activo/inactivo) es obligatorio";
        public static final String ERROR_PLATO_OTRO_RESTAURANTE = "No se pueden modificar platos de otros restaurantes";

        public static final String ERROR_PAGE_NUMBER_INVALID = "El número de página debe ser mayor o igual a "
                + MIN_PAGE_NUMBER;
        public static final String ERROR_PAGE_SIZE_INVALID = "El tamaño de página debe ser mayor o igual a "
                + MIN_PAGE_SIZE;
        public static final String ERROR_PAGE_SIZE_TOO_LARGE = "El tamaño de página no puede ser mayor a "
                + MAX_PAGE_SIZE;

        private Dish() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Order {

        public static final String ROL_CLIENTE = "CLIENTE";

        public static final String ERROR_ORDER_NULO = "El pedido no puede ser nulo";
        public static final String ERROR_CLIENTE_REQUERIDO = "El ID del cliente es obligatorio";
        public static final String ERROR_RESTAURANTE_REQUERIDO = "El restaurante es obligatorio";
        public static final String ERROR_PLATOS_REQUERIDOS = "Los platos son obligatorios";
        public static final String ERROR_PLATO_ID_REQUERIDO = "El ID del plato es obligatorio";
        public static final String ERROR_CANTIDAD_POSITIVA = "La cantidad debe ser mayor a 0";
        public static final String ERROR_PLATOS_MISMO_RESTAURANTE = "Todos los platos deben ser del mismo restaurante";
        public static final String ERROR_RESTAURANTE_NO_COINCIDE = "El restaurante del pedido no coincide con el de los platos";
        public static final String ERROR_PLATO_NO_ENCONTRADO = "No se encontró el plato especificado";
        public static final String ERROR_PLATO_NO_ACTIVO = "El plato no está disponible";
        public static final String ERROR_CLIENTE_TIENE_PEDIDO_ACTIVO = "El cliente ya tiene un pedido en proceso (pendiente, en preparación o listo)";

        private Order() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
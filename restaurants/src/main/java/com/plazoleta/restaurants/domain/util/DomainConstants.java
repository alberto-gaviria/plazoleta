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

        public static final Long ROL_ADMINISTRADOR_ID = 1L;
        public static final Long ROL_PROPIETARIO_ID = 2L;

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

        private Restaurant() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Dish {

        public static final String ROL_PROPIETARIO = "PROPIETARIO";

        public static final String ERROR_DISH_NULO = "El plato no puede ser nulo";
        public static final String ERROR_NOMBRE_REQUERIDO = "El nombre del plato es obligatorio";
        public static final String ERROR_PRECIO_REQUERIDO = "El precio del plato es obligatorio";
        public static final String ERROR_PRECIO_POSITIVO = "El precio del plato debe ser un número entero positivo y mayor a 0";
        public static final String ERROR_DESCRIPCION_REQUERIDA = "La descripción del plato es obligatoria";
        public static final String ERROR_URL_IMAGEN_REQUERIDA = "La URL de la imagen es obligatoria";
        public static final String ERROR_CATEGORIA_REQUERIDA = "La categoría es obligatoria";
        public static final String ERROR_RESTAURANTE_REQUERIDO = "El restaurante es obligatorio";
        public static final String ERROR_RESTAURANTE_NO_ENCONTRADO = "No se encontró el restaurante especificado";
        public static final String ERROR_PROPIETARIO_NO_AUTORIZADO = "Solo el propietario del restaurante puede crear o modificar platos";
        public static final String ERROR_DISH_NO_ENCONTRADO = "No se encontró el plato especificado";
        public static final String ERROR_DISH_ID_REQUERIDO = "El ID del plato es obligatorio";
        public static final String ERROR_USUARIO_REQUERIDO = "El ID del usuario es obligatorio";
        public static final String ERROR_ESTADO_REQUERIDO = "El estado del plato (activo/inactivo) es obligatorio";
        public static final String ERROR_PLATO_OTRO_RESTAURANTE = "No se pueden modificar platos de otros restaurantes";


        private Dish() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
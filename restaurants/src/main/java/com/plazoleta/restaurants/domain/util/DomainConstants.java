package com.plazoleta.restaurants.domain.util;

public final class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Restaurant {

        // Patrones de validación
        public static final String SOLO_NUMEROS_PATTERN = "^[0-9]+$";
        public static final String TELEFONO_PATTERN = "^\\+?[0-9]{1,13}$";

        // Roles de dominio
        public static final String ROL_PROPIETARIO = "PROPIETARIO";

        // Mensajes de error
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

        private Restaurant() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
package com.plazoleta.restaurants.adapters.driving.http.util;

public final class HttpConstants {

    private HttpConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class RestaurantValidation {

        public static final String NIT_PATTERN = "^[0-9]+$";
        public static final String TELEFONO_PATTERN = "^\\+?[0-9]{1,13}$";

        public static final String NOMBRE_REQUERIDO = "El nombre es obligatorio";
        public static final String NIT_REQUERIDO = "El NIT es obligatorio";
        public static final String NIT_NUMERICO = "El NIT debe ser únicamente numérico";
        public static final String DIRECCION_REQUERIDA = "La dirección es obligatoria";
        public static final String TELEFONO_REQUERIDO = "El teléfono es obligatorio";
        public static final String TELEFONO_FORMATO = "El teléfono debe contener un máximo de 13 caracteres y puede contener el símbolo +";
        public static final String URL_LOGO_REQUERIDA = "La URL del logo es obligatoria";
        public static final String ID_PROPIETARIO_REQUERIDO = "El ID del propietario es obligatorio";

        private RestaurantValidation() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
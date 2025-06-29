package com.plazoleta.users.adapters.driving.http.util;

public final class HttpConstants {

    private HttpConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class UsuarioValidation {

        public static final String DOCUMENTO_PATTERN = "^[0-9]+$";
        public static final String CELULAR_PATTERN = "^\\+?[0-9]{1,13}$";

        public static final String NOMBRE_REQUERIDO = "El nombre es obligatorio";
        public static final String APELLIDO_REQUERIDO = "El apellido es obligatorio";
        public static final String DOCUMENTO_REQUERIDO = "El número de documento es obligatorio";
        public static final String DOCUMENTO_NUMERICO = "El documento de identidad debe ser únicamente numérico";
        public static final String CELULAR_REQUERIDO = "El celular es obligatorio";
        public static final String CELULAR_FORMATO = "El teléfono debe contener un máximo de 13 caracteres y puede contener el símbolo +";
        public static final String FECHA_NACIMIENTO_REQUERIDA = "La fecha de nacimiento es obligatoria";
        public static final String CORREO_REQUERIDO = "El correo es obligatorio";
        public static final String CORREO_FORMATO = "Debe tener estructura de email válida";
        public static final String CLAVE_REQUERIDA = "La clave es obligatoria";

        private UsuarioValidation() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}

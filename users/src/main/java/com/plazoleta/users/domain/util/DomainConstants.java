package com.plazoleta.users.domain.util;

public final class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Usuario {
        public static final int EDAD_MINIMA = 18;

        public static final String ERROR_USUARIO_NULO = "El usuario no puede ser nulo";
        public static final String ERROR_NOMBRE_REQUERIDO = "El nombre es obligatorio";
        public static final String ERROR_APELLIDO_REQUERIDO = "El apellido es obligatorio";
        public static final String ERROR_DOCUMENTO_REQUERIDO = "El número de documento es obligatorio";
        public static final String ERROR_CELULAR_REQUERIDO = "El celular es obligatorio";
        public static final String ERROR_CORREO_REQUERIDO = "El correo es obligatorio";
        public static final String ERROR_CLAVE_REQUERIDA = "La clave es obligatoria";
        public static final String ERROR_FECHA_NACIMIENTO_REQUERIDA = "La fecha de nacimiento es obligatoria";
        public static final String ERROR_MENOR_EDAD = "El usuario debe ser mayor de edad";
        public static final String ERROR_USUARIO_NO_ENCONTRADO = "Usuario no encontrado con ID: ";

        private Usuario() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Role {
        public static final Long ADMINISTRADOR_ID = 1L;
        public static final Long PROPIETARIO_ID = 2L;
        public static final Long EMPLEADO_ID = 3L;
        public static final Long CLIENTE_ID = 4L;

        public static final String ADMINISTRADOR_AUTHORITY = "ADMINISTRADOR";
        public static final String PROPIETARIO_AUTHORITY = "PROPIETARIO";
        public static final String EMPLEADO_AUTHORITY = "EMPLEADO";
        public static final String CLIENTE_AUTHORITY = "CLIENTE";

        public static final String ERROR_INVALID_ROLE_ID = "ID de rol inválido: ";

        private Role() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
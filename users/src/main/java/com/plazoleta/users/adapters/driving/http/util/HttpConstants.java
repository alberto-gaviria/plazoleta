package com.plazoleta.users.adapters.driving.http.util;

public final class HttpConstants {

    private HttpConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class Roles {
        public static final String ADMINISTRADOR = "ADMINISTRADOR";
        public static final String PROPIETARIO = "PROPIETARIO";

        private Roles() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Paths {
        public static final String USUARIOS = "/usuarios";
        public static final String SUB_PROPIETARIO = "/propietario";
        public static final String SUB_CLIENTE = "/cliente";
        public static final String SUB_EMPLEADO = "/empleado";
        public static final String BY_ID = "/{id}";
        public static final String ME = "/me";

        public static final String AUTH = "/auth";
        public static final String LOGIN = "/login";

        private Paths() { throw new IllegalStateException("Constants class"); }
    }

    public static final class Messages {

        public static final String CREATE_OWNER_SUCCESS = "Propietario creado exitosamente";
        public static final String CREATE_CLIENT_SUCCESS = "Cliente registrado exitosamente";
        public static final String CREATE_EMPLOYEE_SUCCESS = "Empleado creado exitosamente";
        public static final String AUTH_SUCCESS = "Autenticación exitosa";
        public static final String USER_FOUND = "Usuario encontrado";

        public static final String INVALID_INPUT = "Datos de entrada inválidos";
        public static final String INTERNAL_ERROR = "Error interno del servidor";
        public static final String FORBIDDEN_ONLY_ADMIN = "Solo administradores pueden crear propietarios";
        public static final String FORBIDDEN_ONLY_OWNER = "Solo propietarios pueden crear empleados";
        public static final String CREDENTIALS_INVALID = "Credenciales inválidas";
        public static final String USER_NOT_FOUND = "Usuario no encontrado";
        public static final String UNAUTHORIZED = "No autenticado";

        private Messages() { throw new IllegalStateException("Constants class"); }
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

        private UsuarioValidation() { throw new IllegalStateException("Clase de constantes"); }
    }
}
package com.plazoleta.restaurants.adapters.driven.feign.util;

public final class UserAdapterConstants {

    private UserAdapterConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class ErrorMessages {

        public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado con ID: ";
        public static final String ERROR_OBTENIENDO_USUARIO = "Error obteniendo usuario con ID: ";
        public static final String ERROR_VALIDANDO_ROL = "Error validando rol del usuario con ID: ";
        public static final String ERROR_COMUNICACION_USERS_SERVICE = "Error de comunicación con el servicio de usuarios: ";
        public static final String MENSAJE_SEPARATOR = " - ";

        private ErrorMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }

    public static final class Roles {

        public static final String ADMINISTRADOR = "ADMINISTRADOR";
        public static final String PROPIETARIO = "PROPIETARIO";
        public static final String EMPLEADO = "EMPLEADO";
        public static final String CLIENTE = "CLIENTE";

        private Roles() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
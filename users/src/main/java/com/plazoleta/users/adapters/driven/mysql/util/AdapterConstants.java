package com.plazoleta.users.adapters.driven.mysql.util;

public final class AdapterConstants {

    private AdapterConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class ErrorMessages {
        public static final String USUARIO_CORREO_DUPLICADO = "Ya existe un usuario con ese correo electrónico";
        public static final String USUARIO_DOCUMENTO_DUPLICADO = "Ya existe un usuario con ese número de documento";
        public static final String USUARIO_NO_ENCONTRADO = "No se encontró el usuario solicitado";

        private ErrorMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
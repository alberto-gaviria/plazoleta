package com.plazoleta.restaurants.adapters.driven.mysql.util;

public final class AdapterConstants {

    private AdapterConstants() {
        throw new IllegalStateException("Clase de constantes");
    }

    public static final class DatabaseColumns {
        public static final String NOMBRE_COLUMN = "nombre";
        public static final String NIT_COLUMN = "nit";
        public static final String ID_COLUMN = "id";

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

        private ErrorMessages() {
            throw new IllegalStateException("Clase de constantes");
        }
    }
}
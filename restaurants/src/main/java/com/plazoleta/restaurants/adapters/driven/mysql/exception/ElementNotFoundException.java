package com.plazoleta.restaurants.adapters.driven.mysql.exception;

public class ElementNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Elemento no encontrado";

    public ElementNotFoundException(String message) {
        super(message);
    }

    public ElementNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}


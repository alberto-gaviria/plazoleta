package com.plazoleta.restaurants.adapters.driven.mysql.exception;

public class NoDataFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "No se encontraron datos";

    public NoDataFoundException(String message) {
        super(message);
    }

    public NoDataFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
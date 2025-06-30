package com.plazoleta.restaurants.adapters.driven.feign.exception;

public class FeignUserException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Error en la comunicación con el servicio de usuarios";

    public FeignUserException(String message) {
        super(message);
    }

    public FeignUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public FeignUserException() {
        super(DEFAULT_MESSAGE);
    }
}
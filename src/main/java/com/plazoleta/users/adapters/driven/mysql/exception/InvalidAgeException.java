package com.plazoleta.users.adapters.driven.mysql.exception;

public class InvalidAgeException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Edad inválida";

    public InvalidAgeException(String message) {
        super(message);
    }

    public InvalidAgeException() {
        super(DEFAULT_MESSAGE);
    }
}
package com.plazoleta.users.adapters.driven.mysql.exception;

public class UserAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "El usuario ya existe";

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
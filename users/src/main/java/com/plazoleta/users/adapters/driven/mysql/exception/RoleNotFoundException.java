package com.plazoleta.users.adapters.driven.mysql.exception;

public class RoleNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Rol no encontrado";

    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

package com.plazoleta.users.adapters.driven.mysql.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
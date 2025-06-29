package com.plazoleta.users.domain.util.exceptions;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}

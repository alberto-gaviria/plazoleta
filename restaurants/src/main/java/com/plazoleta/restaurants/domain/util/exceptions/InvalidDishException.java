package com.plazoleta.restaurants.domain.util.exceptions;

public class InvalidDishException extends DomainException {
    public InvalidDishException(String message) {
        super(message);
    }
}
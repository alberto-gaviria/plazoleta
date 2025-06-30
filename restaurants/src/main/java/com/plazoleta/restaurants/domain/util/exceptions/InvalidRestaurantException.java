package com.plazoleta.restaurants.domain.util.exceptions;

public class InvalidRestaurantException extends DomainException {
    public InvalidRestaurantException(String message) {
        super(message);
    }
}
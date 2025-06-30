package com.plazoleta.restaurants.adapters.driven.mysql.exception;

public class RestaurantAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "El restaurante ya existe";

    public RestaurantAlreadyExistsException(String message) {
        super(message);
    }

    public RestaurantAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
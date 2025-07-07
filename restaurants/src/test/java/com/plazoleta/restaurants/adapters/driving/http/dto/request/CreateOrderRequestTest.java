package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderRequestTest {

    @Test
    void constructor_DefaultConstructor_ShouldCreateEmptyObject() {
        // Act
        CreateOrderRequest request = new CreateOrderRequest();

        // Assert
        assertNull(request.getIdRestaurante());
        assertNull(request.getPlatos());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long idRestaurante = 1L;
        List<OrderDishRequest> platos = Arrays.asList(new OrderDishRequest(1L, 2));

        // Act
        CreateOrderRequest request = new CreateOrderRequest(idRestaurante, platos);

        // Assert
        assertEquals(idRestaurante, request.getIdRestaurante());
        assertEquals(platos, request.getPlatos());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        CreateOrderRequest request = new CreateOrderRequest();
        Long idRestaurante = 1L;
        List<OrderDishRequest> platos = Arrays.asList(new OrderDishRequest(1L, 2));

        // Act
        request.setIdRestaurante(idRestaurante);
        request.setPlatos(platos);

        // Assert
        assertEquals(idRestaurante, request.getIdRestaurante());
        assertEquals(platos, request.getPlatos());
    }
}
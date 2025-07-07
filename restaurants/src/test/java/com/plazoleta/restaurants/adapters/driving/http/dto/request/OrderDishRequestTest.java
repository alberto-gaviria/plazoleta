package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDishRequestTest {

    @Test
    void constructor_DefaultConstructor_ShouldCreateEmptyObject() {
        // Act
        OrderDishRequest request = new OrderDishRequest();

        // Assert
        assertNull(request.getIdPlato());
        assertNull(request.getCantidad());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long idPlato = 1L;
        Integer cantidad = 2;

        // Act
        OrderDishRequest request = new OrderDishRequest(idPlato, cantidad);

        // Assert
        assertEquals(idPlato, request.getIdPlato());
        assertEquals(cantidad, request.getCantidad());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        OrderDishRequest request = new OrderDishRequest();
        Long idPlato = 1L;
        Integer cantidad = 2;

        // Act
        request.setIdPlato(idPlato);
        request.setCantidad(cantidad);

        // Assert
        assertEquals(idPlato, request.getIdPlato());
        assertEquals(cantidad, request.getCantidad());
    }
}
package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDishResponseTest {

    @Test
    void constructor_DefaultConstructor_ShouldCreateEmptyObject() {
        // Act
        OrderDishResponse response = new OrderDishResponse();

        // Assert
        assertNull(response.getId());
        assertNull(response.getIdPlato());
        assertNull(response.getCantidad());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long id = 1L;
        Long idPlato = 2L;
        Integer cantidad = 3;

        // Act
        OrderDishResponse response = new OrderDishResponse(id, idPlato, cantidad);

        // Assert
        assertEquals(id, response.getId());
        assertEquals(idPlato, response.getIdPlato());
        assertEquals(cantidad, response.getCantidad());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        OrderDishResponse response = new OrderDishResponse();
        Long id = 1L;
        Long idPlato = 2L;
        Integer cantidad = 3;

        // Act
        response.setId(id);
        response.setIdPlato(idPlato);
        response.setCantidad(cantidad);

        // Assert
        assertEquals(id, response.getId());
        assertEquals(idPlato, response.getIdPlato());
        assertEquals(cantidad, response.getCantidad());
    }
}
package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDishEntityTest {

    @Test
    void constructor_DefaultConstructor_ShouldCreateEmptyObject() {
        // Act
        OrderDishEntity entity = new OrderDishEntity();

        // Assert
        assertNull(entity.getId());
        assertNull(entity.getIdPedido());
        assertNull(entity.getIdPlato());
        assertNull(entity.getCantidad());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long id = 1L;
        Long idPedido = 2L;
        Long idPlato = 3L;
        Integer cantidad = 4;

        // Act
        OrderDishEntity entity = new OrderDishEntity(id, idPedido, idPlato, cantidad);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals(idPedido, entity.getIdPedido());
        assertEquals(idPlato, entity.getIdPlato());
        assertEquals(cantidad, entity.getCantidad());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        OrderDishEntity entity = new OrderDishEntity();
        Long id = 1L;
        Long idPedido = 2L;
        Long idPlato = 3L;
        Integer cantidad = 4;

        // Act
        entity.setId(id);
        entity.setIdPedido(idPedido);
        entity.setIdPlato(idPlato);
        entity.setCantidad(cantidad);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals(idPedido, entity.getIdPedido());
        assertEquals(idPlato, entity.getIdPlato());
        assertEquals(cantidad, entity.getCantidad());
    }
}

package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDishTest {

    @Test
    void constructor_DefaultConstructor_ShouldCreateEmptyObject() {
        // Act
        OrderDish orderDish = new OrderDish();

        // Assert
        assertNull(orderDish.getId());
        assertNull(orderDish.getIdPedido());
        assertNull(orderDish.getIdPlato());
        assertNull(orderDish.getCantidad());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long id = 1L;
        Long idPedido = 2L;
        Long idPlato = 3L;
        Integer cantidad = 5;

        // Act
        OrderDish orderDish = new OrderDish(id, idPedido, idPlato, cantidad);

        // Assert
        assertEquals(id, orderDish.getId());
        assertEquals(idPedido, orderDish.getIdPedido());
        assertEquals(idPlato, orderDish.getIdPlato());
        assertEquals(cantidad, orderDish.getCantidad());
    }

    @Test
    void constructor_PartialConstructor_ShouldSetSpecifiedValuesOnly() {
        // Arrange
        Long idPlato = 3L;
        Integer cantidad = 5;

        // Act
        OrderDish orderDish = new OrderDish(idPlato, cantidad);

        // Assert
        assertNull(orderDish.getId());
        assertNull(orderDish.getIdPedido());
        assertEquals(idPlato, orderDish.getIdPlato());
        assertEquals(cantidad, orderDish.getCantidad());
    }

    @Test
    void constructor_PartialConstructor_WithNullValues_ShouldAcceptNulls() {
        // Act
        OrderDish orderDish = new OrderDish(null, null);

        // Assert
        assertNull(orderDish.getId());
        assertNull(orderDish.getIdPedido());
        assertNull(orderDish.getIdPlato());
        assertNull(orderDish.getCantidad());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        OrderDish orderDish = new OrderDish();
        Long id = 1L;
        Long idPedido = 2L;
        Long idPlato = 3L;
        Integer cantidad = 4;

        // Act
        orderDish.setId(id);
        orderDish.setIdPedido(idPedido);
        orderDish.setIdPlato(idPlato);
        orderDish.setCantidad(cantidad);

        // Assert
        assertEquals(id, orderDish.getId());
        assertEquals(idPedido, orderDish.getIdPedido());
        assertEquals(idPlato, orderDish.getIdPlato());
        assertEquals(cantidad, orderDish.getCantidad());
    }

    @Test
    void setId_WithNullValue_ShouldAcceptNull() {
        // Arrange
        OrderDish orderDish = new OrderDish();

        // Act
        orderDish.setId(null);

        // Assert
        assertNull(orderDish.getId());
    }

    @Test
    void setIdPedido_WithNullValue_ShouldAcceptNull() {
        // Arrange
        OrderDish orderDish = new OrderDish();

        // Act
        orderDish.setIdPedido(null);

        // Assert
        assertNull(orderDish.getIdPedido());
    }

    @Test
    void setIdPlato_WithNullValue_ShouldAcceptNull() {
        // Arrange
        OrderDish orderDish = new OrderDish();

        // Act
        orderDish.setIdPlato(null);

        // Assert
        assertNull(orderDish.getIdPlato());
    }

    @Test
    void setCantidad_WithNullValue_ShouldAcceptNull() {
        // Arrange
        OrderDish orderDish = new OrderDish();

        // Act
        orderDish.setCantidad(null);

        // Assert
        assertNull(orderDish.getCantidad());
    }

    @Test
    void setCantidad_WithZeroValue_ShouldAcceptZero() {
        // Arrange
        OrderDish orderDish = new OrderDish();

        // Act
        orderDish.setCantidad(0);

        // Assert
        assertEquals(0, orderDish.getCantidad());
    }

    @Test
    void setCantidad_WithNegativeValue_ShouldAcceptNegative() {
        // Arrange
        OrderDish orderDish = new OrderDish();

        // Act
        orderDish.setCantidad(-1);

        // Assert
        assertEquals(-1, orderDish.getCantidad());
    }

    @Test
    void setCantidad_WithPositiveValue_ShouldAcceptPositive() {
        // Arrange
        OrderDish orderDish = new OrderDish();

        // Act
        orderDish.setCantidad(10);

        // Assert
        assertEquals(10, orderDish.getCantidad());
    }
}
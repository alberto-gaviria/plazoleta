package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void values_ShouldContainAllExpectedValues() {
        // Act
        OrderStatus[] values = OrderStatus.values();

        // Assert
        assertEquals(5, values.length);
        assertArrayEquals(
                new OrderStatus[]{
                        OrderStatus.PENDIENTE,
                        OrderStatus.EN_PREPARACION,
                        OrderStatus.LISTO,
                        OrderStatus.ENTREGADO,
                        OrderStatus.CANCELADO
                },
                values
        );
    }

    @Test
    void valueOf_ValidValues_ShouldReturnCorrectEnum() {
        // Act & Assert
        assertEquals(OrderStatus.PENDIENTE, OrderStatus.valueOf("PENDIENTE"));
        assertEquals(OrderStatus.EN_PREPARACION, OrderStatus.valueOf("EN_PREPARACION"));
        assertEquals(OrderStatus.LISTO, OrderStatus.valueOf("LISTO"));
        assertEquals(OrderStatus.ENTREGADO, OrderStatus.valueOf("ENTREGADO"));
        assertEquals(OrderStatus.CANCELADO, OrderStatus.valueOf("CANCELADO"));
    }

    @Test
    void valueOf_InvalidValue_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            OrderStatus.valueOf("INVALID_STATUS");
        });
    }

    @Test
    void name_ShouldReturnCorrectString() {
        // Act & Assert
        assertEquals("PENDIENTE", OrderStatus.PENDIENTE.name());
        assertEquals("EN_PREPARACION", OrderStatus.EN_PREPARACION.name());
        assertEquals("LISTO", OrderStatus.LISTO.name());
        assertEquals("ENTREGADO", OrderStatus.ENTREGADO.name());
        assertEquals("CANCELADO", OrderStatus.CANCELADO.name());
    }

    @Test
    void ordinal_ShouldReturnCorrectOrder() {
        // Act & Assert
        assertEquals(0, OrderStatus.PENDIENTE.ordinal());
        assertEquals(1, OrderStatus.EN_PREPARACION.ordinal());
        assertEquals(2, OrderStatus.LISTO.ordinal());
        assertEquals(3, OrderStatus.ENTREGADO.ordinal());
        assertEquals(4, OrderStatus.CANCELADO.ordinal());
    }

    @Test
    void toString_ShouldReturnName() {
        // Act & Assert
        assertEquals("PENDIENTE", OrderStatus.PENDIENTE.toString());
        assertEquals("EN_PREPARACION", OrderStatus.EN_PREPARACION.toString());
        assertEquals("LISTO", OrderStatus.LISTO.toString());
        assertEquals("ENTREGADO", OrderStatus.ENTREGADO.toString());
        assertEquals("CANCELADO", OrderStatus.CANCELADO.toString());
    }
}
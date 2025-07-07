package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {

    @Test
    void values_ShouldContainAllExpectedValues() {
        // Act
        OrderStatus[] values = OrderStatus.values();

        // Assert
        assertEquals(5, values.length);
        assertTrue(java.util.Arrays.asList(values).contains(OrderStatus.PENDIENTE));
        assertTrue(java.util.Arrays.asList(values).contains(OrderStatus.EN_PREPARACION));
        assertTrue(java.util.Arrays.asList(values).contains(OrderStatus.LISTO));
        assertTrue(java.util.Arrays.asList(values).contains(OrderStatus.ENTREGADO));
        assertTrue(java.util.Arrays.asList(values).contains(OrderStatus.CANCELADO));
    }

    @Test
    void valueOf_ShouldReturnCorrectEnum() {
        // Act & Assert
        assertEquals(OrderStatus.PENDIENTE, OrderStatus.valueOf("PENDIENTE"));
        assertEquals(OrderStatus.EN_PREPARACION, OrderStatus.valueOf("EN_PREPARACION"));
        assertEquals(OrderStatus.LISTO, OrderStatus.valueOf("LISTO"));
        assertEquals(OrderStatus.ENTREGADO, OrderStatus.valueOf("ENTREGADO"));
        assertEquals(OrderStatus.CANCELADO, OrderStatus.valueOf("CANCELADO"));
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
}
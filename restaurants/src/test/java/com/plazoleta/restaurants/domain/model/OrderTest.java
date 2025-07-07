package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void constructor_DefaultConstructor_ShouldSetDefaultValues() {
        // Act
        Order order = new Order();

        // Assert
        assertNotNull(order.getFecha());
        assertEquals(OrderStatus.PENDIENTE, order.getEstado());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long id = 1L;
        Long idCliente = 2L;
        LocalDateTime fecha = LocalDateTime.now();
        OrderStatus estado = OrderStatus.EN_PREPARACION;
        Long idChef = 3L;
        Long idRestaurante = 4L;
        String pinSeguridad = "1234";
        List<OrderDish> platos = Arrays.asList(new OrderDish(1L, 2));

        // Act
        Order order = new Order(id, idCliente, fecha, estado, idChef, idRestaurante, pinSeguridad, platos);

        // Assert
        assertEquals(id, order.getId());
        assertEquals(idCliente, order.getIdCliente());
        assertEquals(fecha, order.getFecha());
        assertEquals(estado, order.getEstado());
        assertEquals(idChef, order.getIdChef());
        assertEquals(idRestaurante, order.getIdRestaurante());
        assertEquals(pinSeguridad, order.getPinSeguridad());
        assertEquals(platos, order.getPlatos());
    }

    @Test
    void constructor_WithNullFecha_ShouldSetCurrentTime() {
        // Act
        Order order = new Order(1L, 2L, null, OrderStatus.PENDIENTE, 3L, 4L, "1234", null);

        // Assert
        assertNotNull(order.getFecha());
    }

    @Test
    void constructor_WithNullEstado_ShouldSetPendiente() {
        // Act
        Order order = new Order(1L, 2L, LocalDateTime.now(), null, 3L, 4L, "1234", null);

        // Assert
        assertEquals(OrderStatus.PENDIENTE, order.getEstado());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        Order order = new Order();
        Long id = 1L;
        Long idCliente = 2L;
        LocalDateTime fecha = LocalDateTime.now();
        OrderStatus estado = OrderStatus.LISTO;
        Long idChef = 3L;
        Long idRestaurante = 4L;
        String pinSeguridad = "5678";
        List<OrderDish> platos = Arrays.asList(new OrderDish(1L, 3));

        // Act
        order.setId(id);
        order.setIdCliente(idCliente);
        order.setFecha(fecha);
        order.setEstado(estado);
        order.setIdChef(idChef);
        order.setIdRestaurante(idRestaurante);
        order.setPinSeguridad(pinSeguridad);
        order.setPlatos(platos);

        // Assert
        assertEquals(id, order.getId());
        assertEquals(idCliente, order.getIdCliente());
        assertEquals(fecha, order.getFecha());
        assertEquals(estado, order.getEstado());
        assertEquals(idChef, order.getIdChef());
        assertEquals(idRestaurante, order.getIdRestaurante());
        assertEquals(pinSeguridad, order.getPinSeguridad());
        assertEquals(platos, order.getPlatos());
    }
}
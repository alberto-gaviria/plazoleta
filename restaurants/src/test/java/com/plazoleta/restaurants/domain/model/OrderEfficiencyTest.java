package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderEfficiencyTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Given
        Long orderId = 1L;
        LocalDateTime inicio = LocalDateTime.of(2025, 7, 10, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 7, 10, 10, 30);
        Long minutos = 30L;
        Long empleadoId = 5L;
        String email = "empleado@example.com";
        String estado = "ENTREGADO";

        // When
        OrderEfficiency order = new OrderEfficiency(orderId, inicio, fin, minutos, empleadoId, email, estado);

        // Then
        assertEquals(orderId, order.getOrderId());
        assertEquals(inicio, order.getFechaInicio());
        assertEquals(fin, order.getFechaFin());
        assertEquals(minutos, order.getTiempoTotalMinutos());
        assertEquals(empleadoId, order.getEmpleadoId());
        assertEquals(email, order.getEmpleadoEmail());
        assertEquals(estado, order.getEstado());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        OrderEfficiency order = new OrderEfficiency();

        Long orderId = 10L;
        LocalDateTime inicio = LocalDateTime.of(2025, 7, 10, 9, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 7, 10, 9, 45);
        Long minutos = 45L;
        Long empleadoId = 3L;
        String email = "user@correo.com";
        String estado = "LISTO";

        // When
        order.setOrderId(orderId);
        order.setFechaInicio(inicio);
        order.setFechaFin(fin);
        order.setTiempoTotalMinutos(minutos);
        order.setEmpleadoId(empleadoId);
        order.setEmpleadoEmail(email);
        order.setEstado(estado);

        // Then
        assertEquals(orderId, order.getOrderId());
        assertEquals(inicio, order.getFechaInicio());
        assertEquals(fin, order.getFechaFin());
        assertEquals(minutos, order.getTiempoTotalMinutos());
        assertEquals(empleadoId, order.getEmpleadoId());
        assertEquals(email, order.getEmpleadoEmail());
        assertEquals(estado, order.getEstado());
    }
}

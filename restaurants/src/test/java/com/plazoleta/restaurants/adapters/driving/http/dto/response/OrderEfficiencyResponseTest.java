package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderEfficiencyResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Given
        Long orderId = 1L;
        LocalDateTime fechaInicio = LocalDateTime.of(2025, 7, 10, 11, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2025, 7, 10, 11, 45);
        String tiempoTotal = "45 minutos";
        Long tiempoTotalMinutos = 45L;
        String empleadoEmail = "empleado@correo.com";
        String estado = "ENTREGADO";

        // When
        OrderEfficiencyResponse response = new OrderEfficiencyResponse(
                orderId, fechaInicio, fechaFin, tiempoTotal, tiempoTotalMinutos, empleadoEmail, estado
        );

        // Then
        assertEquals(orderId, response.getOrderId());
        assertEquals(fechaInicio, response.getFechaInicio());
        assertEquals(fechaFin, response.getFechaFin());
        assertEquals(tiempoTotal, response.getTiempoTotal());
        assertEquals(tiempoTotalMinutos, response.getTiempoTotalMinutos());
        assertEquals(empleadoEmail, response.getEmpleadoEmail());
        assertEquals(estado, response.getEstado());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        OrderEfficiencyResponse response = new OrderEfficiencyResponse();

        Long orderId = 2L;
        LocalDateTime fechaInicio = LocalDateTime.of(2025, 7, 10, 10, 0);
        LocalDateTime fechaFin = LocalDateTime.of(2025, 7, 10, 10, 30);
        String tiempoTotal = "30 minutos";
        Long tiempoTotalMinutos = 30L;
        String empleadoEmail = "otro@correo.com";
        String estado = "LISTO";

        // When
        response.setOrderId(orderId);
        response.setFechaInicio(fechaInicio);
        response.setFechaFin(fechaFin);
        response.setTiempoTotal(tiempoTotal);
        response.setTiempoTotalMinutos(tiempoTotalMinutos);
        response.setEmpleadoEmail(empleadoEmail);
        response.setEstado(estado);

        // Then
        assertEquals(orderId, response.getOrderId());
        assertEquals(fechaInicio, response.getFechaInicio());
        assertEquals(fechaFin, response.getFechaFin());
        assertEquals(tiempoTotal, response.getTiempoTotal());
        assertEquals(tiempoTotalMinutos, response.getTiempoTotalMinutos());
        assertEquals(empleadoEmail, response.getEmpleadoEmail());
        assertEquals(estado, response.getEstado());
    }
}

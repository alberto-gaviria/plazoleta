package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    @Test
    void constructor_DefaultConstructor_ShouldCreateEmptyObject() {
        // Act
        OrderResponse response = new OrderResponse();

        // Assert
        assertNull(response.getId());
        assertNull(response.getIdCliente());
        assertNull(response.getFecha());
        assertNull(response.getEstado());
        assertNull(response.getIdEmpleado());
        assertNull(response.getIdRestaurante());
        assertNull(response.getPinSeguridad());
        assertNull(response.getPlatos());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long id = 1L;
        Long idCliente = 2L;
        LocalDateTime fecha = LocalDateTime.now();
        String estado = "PENDIENTE";
        Long idChef = 3L;
        Long idRestaurante = 4L;
        String pinSeguridad = "1234";
        List<OrderDishResponse> platos = Arrays.asList(new OrderDishResponse(1L, 1L, 2));

        // Act
        OrderResponse response = new OrderResponse(id, idCliente, fecha, estado, idChef, idRestaurante, pinSeguridad, platos);

        // Assert
        assertEquals(id, response.getId());
        assertEquals(idCliente, response.getIdCliente());
        assertEquals(fecha, response.getFecha());
        assertEquals(estado, response.getEstado());
        assertEquals(idChef, response.getIdEmpleado());
        assertEquals(idRestaurante, response.getIdRestaurante());
        assertEquals(pinSeguridad, response.getPinSeguridad());
        assertEquals(platos, response.getPlatos());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        OrderResponse response = new OrderResponse();
        Long id = 1L;
        Long idCliente = 2L;
        LocalDateTime fecha = LocalDateTime.now();
        String estado = "LISTO";
        Long idChef = 3L;
        Long idRestaurante = 4L;
        String pinSeguridad = "5678";
        List<OrderDishResponse> platos = Arrays.asList(new OrderDishResponse(1L, 1L, 3));

        // Act
        response.setId(id);
        response.setIdCliente(idCliente);
        response.setFecha(fecha);
        response.setEstado(estado);
        response.setIdEmpleado(idChef);
        response.setIdRestaurante(idRestaurante);
        response.setPinSeguridad(pinSeguridad);
        response.setPlatos(platos);

        // Assert
        assertEquals(id, response.getId());
        assertEquals(idCliente, response.getIdCliente());
        assertEquals(fecha, response.getFecha());
        assertEquals(estado, response.getEstado());
        assertEquals(idChef, response.getIdEmpleado());
        assertEquals(idRestaurante, response.getIdRestaurante());
        assertEquals(pinSeguridad, response.getPinSeguridad());
        assertEquals(platos, response.getPlatos());
    }
}
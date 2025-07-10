package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeEfficiencyResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Given
        Long id = 1L;
        String nombre = "Carlos López";
        String email = "carlos@example.com";
        Integer totalPedidos = 15;
        String tiempoFormateado = "3 minutos 30 segundos";
        Double tiempoMinutos = 3.5;
        Integer ranking = 2;

        // When
        EmployeeEfficiencyResponse response = new EmployeeEfficiencyResponse(
                id, nombre, email, totalPedidos, tiempoFormateado, tiempoMinutos, ranking
        );

        // Then
        assertEquals(id, response.getEmpleadoId());
        assertEquals(nombre, response.getEmpleadoNombre());
        assertEquals(email, response.getEmpleadoEmail());
        assertEquals(totalPedidos, response.getTotalPedidos());
        assertEquals(tiempoFormateado, response.getTiempoPromedioFormateado());
        assertEquals(tiempoMinutos, response.getTiempoPromedioMinutos());
        assertEquals(ranking, response.getRanking());
    }

    @Test
    void testSettersAndGetters() {
        EmployeeEfficiencyResponse response = new EmployeeEfficiencyResponse();

        response.setEmpleadoId(2L);
        response.setEmpleadoNombre("Laura Torres");
        response.setEmpleadoEmail("laura@correo.com");
        response.setTotalPedidos(10);
        response.setTiempoPromedioFormateado("2 minutos");
        response.setTiempoPromedioMinutos(2.0);
        response.setRanking(1);

        assertEquals(2L, response.getEmpleadoId());
        assertEquals("Laura Torres", response.getEmpleadoNombre());
        assertEquals("laura@correo.com", response.getEmpleadoEmail());
        assertEquals(10, response.getTotalPedidos());
        assertEquals("2 minutos", response.getTiempoPromedioFormateado());
        assertEquals(2.0, response.getTiempoPromedioMinutos());
        assertEquals(1, response.getRanking());
    }
}

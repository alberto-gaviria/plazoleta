package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeEfficiencyTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Given
        Long empleadoId = 10L;
        String empleadoNombre = "Camila Ruiz";
        String empleadoEmail = "camila@correo.com";
        Integer totalPedidos = 50;
        Double tiempoPromedio = 27.5;

        // When
        EmployeeEfficiency employee = new EmployeeEfficiency(
                empleadoId, empleadoNombre, empleadoEmail, totalPedidos, tiempoPromedio
        );

        // Then
        assertEquals(empleadoId, employee.getEmpleadoId());
        assertEquals(empleadoNombre, employee.getEmpleadoNombre());
        assertEquals(empleadoEmail, employee.getEmpleadoEmail());
        assertEquals(totalPedidos, employee.getTotalPedidos());
        assertEquals(tiempoPromedio, employee.getTiempoPromedioMinutos());
        assertNull(employee.getRanking()); // el constructor no lo inicializa
    }

    @Test
    void testSettersAndGetters() {
        // Given
        EmployeeEfficiency employee = new EmployeeEfficiency();

        Long empleadoId = 22L;
        String empleadoNombre = "Juan Pérez";
        String empleadoEmail = "juan@correo.com";
        Integer totalPedidos = 35;
        Double tiempoPromedio = 33.0;
        Integer ranking = 2;

        // When
        employee.setEmpleadoId(empleadoId);
        employee.setEmpleadoNombre(empleadoNombre);
        employee.setEmpleadoEmail(empleadoEmail);
        employee.setTotalPedidos(totalPedidos);
        employee.setTiempoPromedioMinutos(tiempoPromedio);
        employee.setRanking(ranking);

        // Then
        assertEquals(empleadoId, employee.getEmpleadoId());
        assertEquals(empleadoNombre, employee.getEmpleadoNombre());
        assertEquals(empleadoEmail, employee.getEmpleadoEmail());
        assertEquals(totalPedidos, employee.getTotalPedidos());
        assertEquals(tiempoPromedio, employee.getTiempoPromedioMinutos());
        assertEquals(ranking, employee.getRanking());
    }
}

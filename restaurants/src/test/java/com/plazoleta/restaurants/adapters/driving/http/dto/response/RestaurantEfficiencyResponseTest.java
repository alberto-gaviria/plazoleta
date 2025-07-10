package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantEfficiencyResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Given
        Long restauranteId = 1L;
        String restauranteNombre = "Plazoleta Gourmet";
        Integer totalPedidos = 25;
        String tiempoPromedio = "30 minutos";
        Double tiempoMinutos = 30.0;

        OrderEfficiencyResponse pedido = new OrderEfficiencyResponse();
        EmployeeEfficiencyResponse empleado = new EmployeeEfficiencyResponse();

        List<OrderEfficiencyResponse> pedidos = Collections.singletonList(pedido);
        List<EmployeeEfficiencyResponse> empleados = Collections.singletonList(empleado);

        // When
        RestaurantEfficiencyResponse response = new RestaurantEfficiencyResponse(
                restauranteId,
                restauranteNombre,
                totalPedidos,
                tiempoPromedio,
                tiempoMinutos,
                pedidos,
                empleados
        );

        // Then
        assertEquals(restauranteId, response.getRestauranteId());
        assertEquals(restauranteNombre, response.getRestauranteNombre());
        assertEquals(totalPedidos, response.getTotalPedidosCompletados());
        assertEquals(tiempoPromedio, response.getTiempoPromedioGeneral());
        assertEquals(tiempoMinutos, response.getTiempoPromedioGeneralMinutos());
        assertEquals(pedidos, response.getPedidos());
        assertEquals(empleados, response.getRankingEmpleados());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        RestaurantEfficiencyResponse response = new RestaurantEfficiencyResponse();

        Long restauranteId = 10L;
        String restauranteNombre = "El Buen Sabor";
        Integer totalPedidos = 40;
        String tiempoPromedio = "45 minutos";
        Double tiempoMinutos = 45.0;

        OrderEfficiencyResponse pedido = new OrderEfficiencyResponse();
        EmployeeEfficiencyResponse empleado = new EmployeeEfficiencyResponse();

        List<OrderEfficiencyResponse> pedidos = Collections.singletonList(pedido);
        List<EmployeeEfficiencyResponse> empleados = Collections.singletonList(empleado);

        // When
        response.setRestauranteId(restauranteId);
        response.setRestauranteNombre(restauranteNombre);
        response.setTotalPedidosCompletados(totalPedidos);
        response.setTiempoPromedioGeneral(tiempoPromedio);
        response.setTiempoPromedioGeneralMinutos(tiempoMinutos);
        response.setPedidos(pedidos);
        response.setRankingEmpleados(empleados);

        // Then
        assertEquals(restauranteId, response.getRestauranteId());
        assertEquals(restauranteNombre, response.getRestauranteNombre());
        assertEquals(totalPedidos, response.getTotalPedidosCompletados());
        assertEquals(tiempoPromedio, response.getTiempoPromedioGeneral());
        assertEquals(tiempoMinutos, response.getTiempoPromedioGeneralMinutos());
        assertEquals(pedidos, response.getPedidos());
        assertEquals(empleados, response.getRankingEmpleados());
    }
}

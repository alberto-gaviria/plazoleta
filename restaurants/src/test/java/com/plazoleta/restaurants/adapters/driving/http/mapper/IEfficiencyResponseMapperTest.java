package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.EmployeeEfficiencyResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderEfficiencyResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantEfficiencyResponse;
import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IEfficiencyResponseMapperTest {

    private IEfficiencyResponseMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new IEfficiencyResponseMapperImpl();
    }

    @Test
    void testToOrderResponse_fullData() {
        OrderEfficiency orderEfficiency = new OrderEfficiency();
        orderEfficiency.setOrderId(1L);
        orderEfficiency.setFechaInicio(LocalDateTime.now().minusMinutes(10));
        orderEfficiency.setFechaFin(LocalDateTime.now());
        orderEfficiency.setTiempoTotalMinutos(10L);
        orderEfficiency.setEmpleadoEmail("empleado@dominio.com");
        orderEfficiency.setEstado("ENTREGADO");

        OrderEfficiencyResponse response = mapper.toOrderResponse(orderEfficiency);

        assertEquals(1L, response.getOrderId());
        assertEquals("10 minutos", response.getTiempoTotal());
        assertEquals(10L, response.getTiempoTotalMinutos());
        assertEquals("empleado@dominio.com", response.getEmpleadoEmail());
        assertEquals("ENTREGADO", response.getEstado());
        assertNotNull(response.getFechaInicio());
        assertNotNull(response.getFechaFin());
    }

    @Test
    void testToOrderResponse_nullTiempoTotal() {
        OrderEfficiency orderEfficiency = new OrderEfficiency();
        orderEfficiency.setOrderId(2L);
        orderEfficiency.setTiempoTotalMinutos(null);

        OrderEfficiencyResponse response = mapper.toOrderResponse(orderEfficiency);

        assertEquals("0 minutos", response.getTiempoTotal());
    }

    @Test
    void testToEmployeeResponse() {
        EmployeeEfficiency employee = new EmployeeEfficiency();
        employee.setEmpleadoId(5L);
        employee.setEmpleadoNombre("Ana");
        employee.setEmpleadoEmail("ana@example.com");
        employee.setTotalPedidos(7);
        employee.setTiempoPromedioMinutos(2.5);

        EmployeeEfficiencyResponse response = mapper.toEmployeeResponse(employee);

        assertEquals(5L, response.getEmpleadoId());
        assertEquals("Ana", response.getEmpleadoNombre());
        assertEquals("ana@example.com", response.getEmpleadoEmail());
        assertEquals(7, response.getTotalPedidos());
        assertEquals("2 minutos 30 segundos", response.getTiempoPromedioFormateado());
        assertEquals(2.5, response.getTiempoPromedioMinutos());
        assertNull(response.getRanking()); // ya que se ignora en el mapping
    }

    @Test
    void testToOrderResponseList() {
        List<OrderEfficiency> orders = List.of(
                new OrderEfficiency(1L, null, null, 3L, 101L, "a@b.com", "ENTREGADO"),
                new OrderEfficiency(2L, null, null, 5L, 102L, "c@d.com", "CANCELADO")
        );

        List<OrderEfficiencyResponse> responses = mapper.toOrderResponseList(orders);

        assertEquals(2, responses.size());
        assertEquals("3 minutos", responses.get(0).getTiempoTotal());
        assertEquals("5 minutos", responses.get(1).getTiempoTotal());
    }

    @Test
    void testToEmployeeResponseList() {
        List<EmployeeEfficiency> employees = List.of(
                new EmployeeEfficiency(1L, "Luis", "luis@mail.com", 3, 4.0),
                new EmployeeEfficiency(2L, "Sara", "sara@mail.com", 5, 6.5)
        );

        List<EmployeeEfficiencyResponse> responses = mapper.toEmployeeResponseList(employees);

        assertEquals(2, responses.size());
        assertEquals("4 minutos", responses.get(0).getTiempoPromedioFormateado());
        assertEquals("6 minutos 30 segundos", responses.get(1).getTiempoPromedioFormateado());
    }

    @Test
    void testToRestaurantResponse_fullData() {
        List<OrderEfficiency> orders = List.of(
                new OrderEfficiency(1L, null, null, 3L, 101L, "a@b.com", "ENTREGADO"),
                new OrderEfficiency(2L, null, null, 6L, 102L, "c@d.com", "CANCELADO")
        );

        List<EmployeeEfficiency> employees = List.of(
                new EmployeeEfficiency(10L, "Carlos", "carlos@mail.com", 4, 3.0),
                new EmployeeEfficiency(11L, "Eva", "eva@mail.com", 5, 2.0)
        );

        RestaurantEfficiencyResponse response = mapper.toRestaurantResponse(
                100L, "Plazoleta Central", orders, employees
        );

        assertEquals(100L, response.getRestauranteId());
        assertEquals("Plazoleta Central", response.getRestauranteNombre());
        assertEquals(2, response.getTotalPedidosCompletados());
        assertEquals("4 minutos 30 segundos", response.getTiempoPromedioGeneral()); // CORREGIDO
        assertEquals(4.5, response.getTiempoPromedioGeneralMinutos());
        assertEquals(2, response.getPedidos().size());
        assertEquals(2, response.getRankingEmpleados().size());
        assertEquals(1, response.getRankingEmpleados().get(0).getRanking());
        assertEquals(2, response.getRankingEmpleados().get(1).getRanking());
    }

    @Test
    void testFormatMinutesToDuration_edgeCases() {
        IEfficiencyResponseMapper mapper = new IEfficiencyResponseMapperImpl();

        assertEquals("0 minutos", mapper.formatMinutesToDuration(0.0));
        assertEquals("0 minutos", mapper.formatMinutesToDuration(null));
        assertEquals("1 minutos", mapper.formatMinutesToDuration(1.0));
        assertEquals("1 minutos 30 segundos", mapper.formatMinutesToDuration(1.5));
        assertEquals("30 segundos", mapper.formatMinutesToDuration(0.5));
        assertEquals("2 minutos", mapper.formatMinutesToDuration(2.0));
        assertEquals("2 minutos 45 segundos", mapper.formatMinutesToDuration(2.75));
    }


}

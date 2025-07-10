package com.plazoleta.traceability.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GetOrderTraceabilityResponseTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        GetOrderTraceabilityResponse response = new GetOrderTraceabilityResponse();

        String id = "abc123";
        Long orderId = 99L;
        LocalDateTime timestamp = LocalDateTime.now();
        String previousStatus = "PENDIENTE";
        String newStatus = "EN_PREPARACION";
        String employeeEmail = "empleado@correo.com";

        response.setId(id);
        response.setOrderId(orderId);
        response.setTimestamp(timestamp);
        response.setPreviousStatus(previousStatus);
        response.setNewStatus(newStatus);
        response.setEmployeeEmail(employeeEmail);

        assertEquals(id, response.getId());
        assertEquals(orderId, response.getOrderId());
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(previousStatus, response.getPreviousStatus());
        assertEquals(newStatus, response.getNewStatus());
        assertEquals(employeeEmail, response.getEmployeeEmail());
    }

    @Test
    void testAllArgsConstructor() {
        String id = "xyz789";
        Long orderId = 123L;
        LocalDateTime timestamp = LocalDateTime.now();
        String previousStatus = "LISTO";
        String newStatus = "ENTREGADO";
        String employeeEmail = "empleado2@correo.com";

        GetOrderTraceabilityResponse response = new GetOrderTraceabilityResponse(
                id, orderId, timestamp, previousStatus, newStatus, employeeEmail
        );

        assertEquals(id, response.getId());
        assertEquals(orderId, response.getOrderId());
        assertEquals(timestamp, response.getTimestamp());
        assertEquals(previousStatus, response.getPreviousStatus());
        assertEquals(newStatus, response.getNewStatus());
        assertEquals(employeeEmail, response.getEmployeeEmail());
    }
}

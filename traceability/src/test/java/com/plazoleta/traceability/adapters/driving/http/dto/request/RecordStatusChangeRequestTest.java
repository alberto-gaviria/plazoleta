package com.plazoleta.traceability.adapters.driving.http.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecordStatusChangeRequestTest {

    @Test
    void testAllGettersAndSetters() {
        RecordStatusChangeRequest request = new RecordStatusChangeRequest();

        request.setOrderId(1L);
        request.setClientId(2L);
        request.setClientEmail("cliente@example.com");
        request.setPreviousStatus("PENDIENTE");
        request.setNewStatus("EN_PREPARACION");
        request.setEmployeeId(3L);
        request.setEmployeeEmail("empleado@example.com");

        assertEquals(1L, request.getOrderId());
        assertEquals(2L, request.getClientId());
        assertEquals("cliente@example.com", request.getClientEmail());
        assertEquals("PENDIENTE", request.getPreviousStatus());
        assertEquals("EN_PREPARACION", request.getNewStatus());
        assertEquals(3L, request.getEmployeeId());
        assertEquals("empleado@example.com", request.getEmployeeEmail());

        String expectedToString = "RecordStatusChangeRequest{" +
                "orderId=1, clientId=2, clientEmail='cliente@example.com', " +
                "previousStatus='PENDIENTE', newStatus='EN_PREPARACION', " +
                "employeeId=3, employeeEmail='empleado@example.com'}";

        assertEquals(expectedToString, request.toString());
    }

    @Test
    void testConstructorWithArgs() {
        RecordStatusChangeRequest request = new RecordStatusChangeRequest(
                1L, 2L, "cliente@example.com",
                "PENDIENTE", "LISTO",
                3L, "empleado@example.com"
        );

        assertEquals(1L, request.getOrderId());
        assertEquals(2L, request.getClientId());
        assertEquals("cliente@example.com", request.getClientEmail());
        assertEquals("PENDIENTE", request.getPreviousStatus());
        assertEquals("LISTO", request.getNewStatus());
        assertEquals(3L, request.getEmployeeId());
        assertEquals("empleado@example.com", request.getEmployeeEmail());
    }
}

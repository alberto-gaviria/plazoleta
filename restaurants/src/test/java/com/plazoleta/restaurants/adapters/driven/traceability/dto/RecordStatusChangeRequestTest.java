package com.plazoleta.restaurants.adapters.driven.traceability.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecordStatusChangeRequestTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Arrange
        Long orderId = 1L;
        Long clientId = 2L;
        String clientEmail = "client@example.com";
        String previousStatus = "PENDING";
        String newStatus = "READY";
        Long employeeId = 3L;
        String employeeEmail = "employee@example.com";

        // Act
        RecordStatusChangeRequest request = new RecordStatusChangeRequest(
                orderId, clientId, clientEmail, previousStatus, newStatus, employeeId, employeeEmail
        );

        // Assert
        assertEquals(orderId, request.getOrderId());
        assertEquals(clientId, request.getClientId());
        assertEquals(clientEmail, request.getClientEmail());
        assertEquals(previousStatus, request.getPreviousStatus());
        assertEquals(newStatus, request.getNewStatus());
        assertEquals(employeeId, request.getEmployeeId());
        assertEquals(employeeEmail, request.getEmployeeEmail());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        RecordStatusChangeRequest request = new RecordStatusChangeRequest();

        // Act
        request.setOrderId(10L);
        request.setClientId(20L);
        request.setClientEmail("test@client.com");
        request.setPreviousStatus("COOKING");
        request.setNewStatus("DELIVERED");
        request.setEmployeeId(30L);
        request.setEmployeeEmail("test@employee.com");

        // Assert
        assertEquals(10L, request.getOrderId());
        assertEquals(20L, request.getClientId());
        assertEquals("test@client.com", request.getClientEmail());
        assertEquals("COOKING", request.getPreviousStatus());
        assertEquals("DELIVERED", request.getNewStatus());
        assertEquals(30L, request.getEmployeeId());
        assertEquals("test@employee.com", request.getEmployeeEmail());
    }

    @Test
    void testToString() {
        // Arrange
        RecordStatusChangeRequest request = new RecordStatusChangeRequest(
                1L, 2L, "client@example.com", "PENDING", "READY", 3L, "employee@example.com"
        );

        // Act
        String result = request.toString();

        // Assert
        assertTrue(result.contains("orderId=1"));
        assertTrue(result.contains("clientId=2"));
        assertTrue(result.contains("clientEmail='client@example.com'"));
        assertTrue(result.contains("previousStatus='PENDING'"));
        assertTrue(result.contains("newStatus='READY'"));
        assertTrue(result.contains("employeeId=3"));
        assertTrue(result.contains("employeeEmail='employee@example.com'"));
    }
}

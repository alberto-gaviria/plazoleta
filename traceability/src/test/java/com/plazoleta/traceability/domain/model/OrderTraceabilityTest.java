package com.plazoleta.traceability.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTraceabilityTest {

    @Test
    void testNoArgsConstructor() {
        OrderTraceability traceability = new OrderTraceability();

        assertNotNull(traceability.getTimestamp());
        assertNull(traceability.getId());
        assertNull(traceability.getOrderId());
        assertNull(traceability.getClientId());
        assertNull(traceability.getClientEmail());
        assertNull(traceability.getPreviousStatus());
        assertNull(traceability.getNewStatus());
        assertNull(traceability.getEmployeeId());
        assertNull(traceability.getEmployeeEmail());
    }

    @Test
    void testAllArgsConstructorWithTimestamp() {
        LocalDateTime now = LocalDateTime.now();

        OrderTraceability traceability = new OrderTraceability(
                "id123", 1L, 2L, "client@example.com",
                now, "PENDIENTE", "ENTREGADO",
                3L, "employee@example.com"
        );

        assertEquals("id123", traceability.getId());
        assertEquals(1L, traceability.getOrderId());
        assertEquals(2L, traceability.getClientId());
        assertEquals("client@example.com", traceability.getClientEmail());
        assertEquals(now, traceability.getTimestamp());
        assertEquals("PENDIENTE", traceability.getPreviousStatus());
        assertEquals("ENTREGADO", traceability.getNewStatus());
        assertEquals(3L, traceability.getEmployeeId());
        assertEquals("employee@example.com", traceability.getEmployeeEmail());
    }

    @Test
    void testAllArgsConstructorWithNullTimestamp() {
        OrderTraceability traceability = new OrderTraceability(
                "id456", 5L, 6L, "client@null.com",
                null, "EN_PREPARACION", "CANCELADO",
                7L, "employee@null.com"
        );

        assertNotNull(traceability.getTimestamp()); // timestamp se asigna automáticamente si es null
    }

    @Test
    void testSettersAndGetters() {
        OrderTraceability traceability = new OrderTraceability();

        traceability.setId("id789");
        traceability.setOrderId(10L);
        traceability.setClientId(11L);
        traceability.setClientEmail("client@set.com");
        LocalDateTime timestamp = LocalDateTime.now();
        traceability.setTimestamp(timestamp);
        traceability.setPreviousStatus("LISTO");
        traceability.setNewStatus("ENTREGADO");
        traceability.setEmployeeId(12L);
        traceability.setEmployeeEmail("employee@set.com");

        assertEquals("id789", traceability.getId());
        assertEquals(10L, traceability.getOrderId());
        assertEquals(11L, traceability.getClientId());
        assertEquals("client@set.com", traceability.getClientEmail());
        assertEquals(timestamp, traceability.getTimestamp());
        assertEquals("LISTO", traceability.getPreviousStatus());
        assertEquals("ENTREGADO", traceability.getNewStatus());
        assertEquals(12L, traceability.getEmployeeId());
        assertEquals("employee@set.com", traceability.getEmployeeEmail());
    }
}

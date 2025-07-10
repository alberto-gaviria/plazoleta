package com.plazoleta.traceability.adapters.driven.mongodb.document;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTraceabilityDocumentTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        OrderTraceabilityDocument doc = new OrderTraceabilityDocument();

        LocalDateTime now = LocalDateTime.now();

        doc.setId("123");
        doc.setOrderId(10L);
        doc.setClientId(20L);
        doc.setClientEmail("client@example.com");
        doc.setTimestamp(now);
        doc.setPreviousStatus("PENDING");
        doc.setNewStatus("CONFIRMED");
        doc.setEmployeeId(30L);
        doc.setEmployeeEmail("emp@example.com");

        assertEquals("123", doc.getId());
        assertEquals(10L, doc.getOrderId());
        assertEquals(20L, doc.getClientId());
        assertEquals("client@example.com", doc.getClientEmail());
        assertEquals(now, doc.getTimestamp());
        assertEquals("PENDING", doc.getPreviousStatus());
        assertEquals("CONFIRMED", doc.getNewStatus());
        assertEquals(30L, doc.getEmployeeId());
        assertEquals("emp@example.com", doc.getEmployeeEmail());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime ts = LocalDateTime.of(2025, 7, 9, 12, 34, 56);
        OrderTraceabilityDocument doc = new OrderTraceabilityDocument(
                "abc",
                11L,
                22L,
                "c@e.com",
                ts,
                "OLD",
                "NEW",
                33L,
                "e@e.com"
        );

        assertEquals("abc", doc.getId());
        assertEquals(11L, doc.getOrderId());
        assertEquals(22L, doc.getClientId());
        assertEquals("c@e.com", doc.getClientEmail());
        assertEquals(ts, doc.getTimestamp());
        assertEquals("OLD", doc.getPreviousStatus());
        assertEquals("NEW", doc.getNewStatus());
        assertEquals(33L, doc.getEmployeeId());
        assertEquals("e@e.com", doc.getEmployeeEmail());
    }

    @Test
    void testSetterOverridesExistingValues() {
        OrderTraceabilityDocument doc = new OrderTraceabilityDocument(
                "init",
                1L,
                2L,
                "init@c.com",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                "S1",
                "S2",
                3L,
                "init@e.com"
        );

        // Override
        doc.setId("newId");
        doc.setOrderId(100L);
        doc.setClientId(200L);
        doc.setClientEmail("new@c.com");
        LocalDateTime newTs = LocalDateTime.now();
        doc.setTimestamp(newTs);
        doc.setPreviousStatus("NEW_PREV");
        doc.setNewStatus("NEW_NEW");
        doc.setEmployeeId(300L);
        doc.setEmployeeEmail("new@e.com");

        assertEquals("newId", doc.getId());
        assertEquals(100L, doc.getOrderId());
        assertEquals(200L, doc.getClientId());
        assertEquals("new@c.com", doc.getClientEmail());
        assertEquals(newTs, doc.getTimestamp());
        assertEquals("NEW_PREV", doc.getPreviousStatus());
        assertEquals("NEW_NEW", doc.getNewStatus());
        assertEquals(300L, doc.getEmployeeId());
        assertEquals("new@e.com", doc.getEmployeeEmail());
    }

    @Test
    void testNullsAllowed() {
        OrderTraceabilityDocument doc = new OrderTraceabilityDocument();

        doc.setId(null);
        doc.setOrderId(null);
        doc.setClientId(null);
        doc.setClientEmail(null);
        doc.setTimestamp(null);
        doc.setPreviousStatus(null);
        doc.setNewStatus(null);
        doc.setEmployeeId(null);
        doc.setEmployeeEmail(null);

        assertNull(doc.getId());
        assertNull(doc.getOrderId());
        assertNull(doc.getClientId());
        assertNull(doc.getClientEmail());
        assertNull(doc.getTimestamp());
        assertNull(doc.getPreviousStatus());
        assertNull(doc.getNewStatus());
        assertNull(doc.getEmployeeId());
        assertNull(doc.getEmployeeEmail());
    }
}

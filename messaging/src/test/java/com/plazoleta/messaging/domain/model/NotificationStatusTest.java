package com.plazoleta.messaging.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationStatusTest {

    @Test
    void enum_ShouldHaveCorrectValues() {
        // Then
        assertEquals(4, NotificationStatus.values().length);
        assertEquals(NotificationStatus.PENDING, NotificationStatus.valueOf("PENDING"));
        assertEquals(NotificationStatus.SENT, NotificationStatus.valueOf("SENT"));
        assertEquals(NotificationStatus.FAILED, NotificationStatus.valueOf("FAILED"));
        assertEquals(NotificationStatus.RETRYING, NotificationStatus.valueOf("RETRYING"));
    }

    @Test
    void toString_ShouldReturnCorrectString() {
        // Then
        assertEquals("PENDING", NotificationStatus.PENDING.toString());
        assertEquals("SENT", NotificationStatus.SENT.toString());
        assertEquals("FAILED", NotificationStatus.FAILED.toString());
        assertEquals("RETRYING", NotificationStatus.RETRYING.toString());
    }
}

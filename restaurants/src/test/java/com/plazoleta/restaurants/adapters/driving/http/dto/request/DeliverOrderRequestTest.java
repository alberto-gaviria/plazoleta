package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeliverOrderRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        DeliverOrderRequest request = new DeliverOrderRequest();
        request.setIdPedido(123L);
        request.setPinSeguridad("1234");

        assertEquals(123L, request.getIdPedido());
        assertEquals("1234", request.getPinSeguridad());
    }

    @Test
    void testAllArgsConstructor() {
        DeliverOrderRequest request = new DeliverOrderRequest(456L, "5678");

        assertEquals(456L, request.getIdPedido());
        assertEquals("5678", request.getPinSeguridad());
    }
}

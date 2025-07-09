package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CancelOrderRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testConstructorYGettersSetters() {
        CancelOrderRequest request = new CancelOrderRequest();
        request.setIdPedido(10L);

        assertEquals(10L, request.getIdPedido());

        CancelOrderRequest request2 = new CancelOrderRequest(20L);
        assertEquals(20L, request2.getIdPedido());
    }

    @Test
    void testValidIdPedido() {
        CancelOrderRequest request = new CancelOrderRequest(5L);
        Set<ConstraintViolation<CancelOrderRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "No debe haber violaciones con ID válido");
    }

    @Test
    void testIdPedidoNull() {
        CancelOrderRequest request = new CancelOrderRequest(null);
        Set<ConstraintViolation<CancelOrderRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("obligatorio")));
    }

    @Test
    void testIdPedidoNegativo() {
        CancelOrderRequest request = new CancelOrderRequest(-1L);
        Set<ConstraintViolation<CancelOrderRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("positivo")));
    }
}

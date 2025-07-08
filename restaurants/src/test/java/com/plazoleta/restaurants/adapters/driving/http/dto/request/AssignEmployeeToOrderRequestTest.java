package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AssignEmployeeToOrderRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testDefaultConstructor() {
        // Given & When
        AssignEmployeeToOrderRequest request = new AssignEmployeeToOrderRequest();

        // Then
        assertNull(request.getIdPedido());
    }

    @Test
    void testParameterizedConstructor() {
        // Given
        Long expectedId = 123L;

        // When
        AssignEmployeeToOrderRequest request = new AssignEmployeeToOrderRequest(expectedId);

        // Then
        assertEquals(expectedId, request.getIdPedido());
    }

    @Test
    void testGetterAndSetter() {
        // Given
        AssignEmployeeToOrderRequest request = new AssignEmployeeToOrderRequest();
        Long expectedId = 456L;

        // When
        request.setIdPedido(expectedId);

        // Then
        assertEquals(expectedId, request.getIdPedido());
    }

    @Test
    void testValidationWithValidIdPedido() {
        // Given
        AssignEmployeeToOrderRequest request = new AssignEmployeeToOrderRequest(1L);

        // When
        Set<ConstraintViolation<AssignEmployeeToOrderRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testValidationWithNullIdPedido() {
        // Given
        AssignEmployeeToOrderRequest request = new AssignEmployeeToOrderRequest(null);

        // When
        Set<ConstraintViolation<AssignEmployeeToOrderRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<AssignEmployeeToOrderRequest> violation = violations.iterator().next();
        assertEquals("El ID del pedido es obligatorio", violation.getMessage());
        assertEquals("idPedido", violation.getPropertyPath().toString());
    }

    @Test
    void testValidationWithDefaultConstructor() {
        // Given
        AssignEmployeeToOrderRequest request = new AssignEmployeeToOrderRequest();

        // When
        Set<ConstraintViolation<AssignEmployeeToOrderRequest>> violations = validator.validate(request);

        // Then
        assertEquals(1, violations.size());
        ConstraintViolation<AssignEmployeeToOrderRequest> violation = violations.iterator().next();
        assertEquals("El ID del pedido es obligatorio", violation.getMessage());
        assertEquals("idPedido", violation.getPropertyPath().toString());
    }
}
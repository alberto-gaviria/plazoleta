package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ToggleDishStatusRequest Tests")
class ToggleDishStatusRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Debe crear request con constructor vacío")
    void shouldCreateRequestWithDefaultConstructor() {
        // Given & When
        ToggleDishStatusRequest request = new ToggleDishStatusRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getActivo());
    }

    @Test
    @DisplayName("Debe crear request con constructor parametrizado para valor true")
    void shouldCreateRequestWithParameterizedConstructorTrue() {
        // Given
        Boolean activo = true;

        // When
        ToggleDishStatusRequest request = new ToggleDishStatusRequest(activo);

        // Then
        assertNotNull(request);
        assertEquals(activo, request.getActivo());
        assertTrue(request.getActivo());
    }

    @Test
    @DisplayName("Debe crear request con constructor parametrizado para valor false")
    void shouldCreateRequestWithParameterizedConstructorFalse() {
        // Given
        Boolean activo = false;

        // When
        ToggleDishStatusRequest request = new ToggleDishStatusRequest(activo);

        // Then
        assertNotNull(request);
        assertEquals(activo, request.getActivo());
        assertFalse(request.getActivo());
    }

    @Test
    @DisplayName("Debe permitir setear estado activo como true")
    void shouldSetActivoToTrue() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest();
        Boolean activo = true;

        // When
        request.setActivo(activo);

        // Then
        assertEquals(activo, request.getActivo());
        assertTrue(request.getActivo());
    }

    @Test
    @DisplayName("Debe permitir setear estado activo como false")
    void shouldSetActivoToFalse() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest();
        Boolean activo = false;

        // When
        request.setActivo(activo);

        // Then
        assertEquals(activo, request.getActivo());
        assertFalse(request.getActivo());
    }

    @Test
    @DisplayName("Debe validar exitosamente cuando activo es true")
    void shouldValidateSuccessfullyWhenActivoIsTrue() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest(true);

        // When
        Set<ConstraintViolation<ToggleDishStatusRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debe validar exitosamente cuando activo es false")
    void shouldValidateSuccessfullyWhenActivoIsFalse() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest(false);

        // When
        Set<ConstraintViolation<ToggleDishStatusRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debe fallar validación cuando activo es null")
    void shouldFailValidationWhenActivoIsNull() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest();
        request.setActivo(null);

        // When
        Set<ConstraintViolation<ToggleDishStatusRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<ToggleDishStatusRequest> violation = violations.iterator().next();
        assertEquals("El estado activo es obligatorio", violation.getMessage());
        assertEquals("activo", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Debe fallar validación cuando request se crea con constructor vacío sin setear activo")
    void shouldFailValidationWhenRequestCreatedWithDefaultConstructorAndActivoNotSet() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest();

        // When
        Set<ConstraintViolation<ToggleDishStatusRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        ConstraintViolation<ToggleDishStatusRequest> violation = violations.iterator().next();
        assertEquals("El estado activo es obligatorio", violation.getMessage());
        assertEquals("activo", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("Debe permitir cambiar estado de true a false")
    void shouldAllowChangingStateFromTrueToFalse() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest(true);

        // When
        request.setActivo(false);

        // Then
        assertFalse(request.getActivo());

        // Validate
        Set<ConstraintViolation<ToggleDishStatusRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Debe permitir cambiar estado de false a true")
    void shouldAllowChangingStateFromFalseToTrue() {
        // Given
        ToggleDishStatusRequest request = new ToggleDishStatusRequest(false);

        // When
        request.setActivo(true);

        // Then
        assertTrue(request.getActivo());

        // Validate
        Set<ConstraintViolation<ToggleDishStatusRequest>> violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }
}
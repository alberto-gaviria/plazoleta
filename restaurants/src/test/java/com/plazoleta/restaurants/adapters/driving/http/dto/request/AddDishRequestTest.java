package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UpdateDishRequestTest {

    private Validator validator;
    private UpdateDishRequest validRequest;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validRequest = new UpdateDishRequest(
                BigDecimal.valueOf(25.50),
                "Descripción válida del plato"
        );
    }

    @Test
    void constructor_WithValidParameters_ShouldCreateRequest() {
        // Given
        BigDecimal precio = BigDecimal.valueOf(30.00);
        String descripcion = "Nueva descripción";

        // When
        UpdateDishRequest request = new UpdateDishRequest(precio, descripcion);

        // Then
        assertEquals(precio, request.getPrecio());
        assertEquals(descripcion, request.getDescripcion());
    }

    @Test
    void defaultConstructor_ShouldCreateRequest() {
        // When
        UpdateDishRequest request = new UpdateDishRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());
    }

    @Test
    void validRequest_ShouldPassValidation() {
        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void precio_WhenNull_ShouldFailValidation() {
        // Given
        validRequest.setPrecio(null);

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio del plato es obligatorio")));
    }

    @Test
    void precio_WhenZero_ShouldFailValidation() {
        // Given
        validRequest.setPrecio(BigDecimal.ZERO);

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio del plato debe ser un número positivo y mayor a 0")));
    }

    @Test
    void precio_WhenNegative_ShouldFailValidation() {
        // Given
        validRequest.setPrecio(BigDecimal.valueOf(-5.00));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio del plato debe ser un número positivo y mayor a 0")));
    }

    @Test
    void precio_WhenMinimumValid_ShouldPassValidation() {
        // Given
        validRequest.setPrecio(BigDecimal.valueOf(0.01));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void precio_WhenVeryLarge_ShouldPassValidation() {
        // Given
        validRequest.setPrecio(BigDecimal.valueOf(9999999999.99));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void precio_WhenInvalidDigits_ShouldFailValidation() {
        // Given - Más de 10 dígitos enteros
        validRequest.setPrecio(new BigDecimal("12345678901.99"));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio debe ser un número válido")));
    }

    @Test
    void precio_WhenTooManyDecimals_ShouldFailValidation() {
        // Given - Más de 2 decimales
        validRequest.setPrecio(new BigDecimal("25.123"));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio debe ser un número válido")));
    }

    @Test
    void descripcion_WhenNull_ShouldFailValidation() {
        // Given
        validRequest.setDescripcion(null);

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción del plato es obligatoria")));
    }

    @Test
    void descripcion_WhenEmpty_ShouldFailValidation() {
        // Given
        validRequest.setDescripcion("");

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción del plato es obligatoria")));
    }

    @Test
    void descripcion_WhenBlank_ShouldFailValidation() {
        // Given
        validRequest.setDescripcion("   ");

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción del plato es obligatoria")));
    }

    @Test
    void descripcion_WhenTooLong_ShouldFailValidation() {
        // Given - Más de 500 caracteres
        String longDescription = "a".repeat(501);
        validRequest.setDescripcion(longDescription);

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción no puede exceder 500 caracteres")));
    }

    @Test
    void descripcion_WhenMaxLength_ShouldPassValidation() {
        // Given - Exactamente 500 caracteres
        String maxDescription = "a".repeat(500);
        validRequest.setDescripcion(maxDescription);

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void descripcion_WhenValidLength_ShouldPassValidation() {
        // Given
        validRequest.setDescripcion("Descripción de longitud normal");

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest();
        BigDecimal expectedPrecio = BigDecimal.valueOf(15.75);
        String expectedDescripcion = "Nueva descripción para el test";

        // When
        request.setPrecio(expectedPrecio);
        request.setDescripcion(expectedDescripcion);

        // Then
        assertEquals(expectedPrecio, request.getPrecio());
        assertEquals(expectedDescripcion, request.getDescripcion());
    }

    @Test
    void multipleViolations_ShouldBeReported() {
        // Given
        validRequest.setPrecio(null);
        validRequest.setDescripcion(null);

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(2, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio del plato es obligatorio")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción del plato es obligatoria")));
    }

    @Test
    void validRequest_WithSpecialCharacters_ShouldPassValidation() {
        // Given
        validRequest.setDescripcion("Descripción con caracteres especiales: ñáéíóú ¡¿@#$%");

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void precio_WithExactPrecision_ShouldPassValidation() {
        // Given - Exactamente 10 dígitos enteros y 2 decimales
        validRequest.setPrecio(new BigDecimal("1234567890.99"));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }
}
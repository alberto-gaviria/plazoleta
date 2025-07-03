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

    // ============== TESTS ADICIONALES PARA 100% DE COBERTURA ==============

    @Test
    void getPrecio_WhenCalled_ShouldReturnCorrectValue() {
        // Given
        BigDecimal expectedPrecio = BigDecimal.valueOf(42.99);
        UpdateDishRequest request = new UpdateDishRequest();
        request.setPrecio(expectedPrecio);

        // When
        BigDecimal actualPrecio = request.getPrecio();

        // Then
        assertEquals(expectedPrecio, actualPrecio);
        assertSame(expectedPrecio, actualPrecio);
    }

    @Test
    void getDescripcion_WhenCalled_ShouldReturnCorrectValue() {
        // Given
        String expectedDescripcion = "Test descripción específica";
        UpdateDishRequest request = new UpdateDishRequest();
        request.setDescripcion(expectedDescripcion);

        // When
        String actualDescripcion = request.getDescripcion();

        // Then
        assertEquals(expectedDescripcion, actualDescripcion);
        assertSame(expectedDescripcion, actualDescripcion);
    }

    @Test
    void setPrecio_WhenCalledWithNull_ShouldSetNullValue() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest(BigDecimal.TEN, "Test");

        // When
        request.setPrecio(null);

        // Then
        assertNull(request.getPrecio());
    }

    @Test
    void setDescripcion_WhenCalledWithNull_ShouldSetNullValue() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest(BigDecimal.TEN, "Test");

        // When
        request.setDescripcion(null);

        // Then
        assertNull(request.getDescripcion());
    }

    @Test
    void constructor_WithNullParameters_ShouldCreateRequestWithNullValues() {
        // When
        UpdateDishRequest request = new UpdateDishRequest(null, null);

        // Then
        assertNotNull(request);
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());
    }

    @Test
    void setter_WhenCalledMultipleTimes_ShouldOverwriteValue() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest();
        BigDecimal firstPrecio = BigDecimal.valueOf(10.00);
        BigDecimal secondPrecio = BigDecimal.valueOf(20.00);
        String firstDescripcion = "Primera descripción";
        String secondDescripcion = "Segunda descripción";

        // When
        request.setPrecio(firstPrecio);
        request.setDescripcion(firstDescripcion);

        // Verify intermediate state
        assertEquals(firstPrecio, request.getPrecio());
        assertEquals(firstDescripcion, request.getDescripcion());

        // Override with new values
        request.setPrecio(secondPrecio);
        request.setDescripcion(secondDescripcion);

        // Then
        assertEquals(secondPrecio, request.getPrecio());
        assertEquals(secondDescripcion, request.getDescripcion());
        assertNotEquals(firstPrecio, request.getPrecio());
        assertNotEquals(firstDescripcion, request.getDescripcion());
    }

    @Test
    void defaultConstructor_ShouldInitializeAllFieldsToNull() {
        // When
        UpdateDishRequest request = new UpdateDishRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());
    }

    @Test
    void constructorWithParameters_ShouldAssignValuesCorrectly() {
        // Given
        BigDecimal precio = BigDecimal.valueOf(99.99);
        String descripcion = "Constructor test description";

        // When
        UpdateDishRequest request = new UpdateDishRequest(precio, descripcion);

        // Then
        assertNotNull(request);
        assertEquals(precio, request.getPrecio());
        assertEquals(descripcion, request.getDescripcion());
        assertSame(precio, request.getPrecio());
        assertSame(descripcion, request.getDescripcion());
    }

    @Test
    void getters_WhenCalledOnDefaultConstructor_ShouldReturnNull() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest();

        // When & Then
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());
    }

    @Test
    void settersAndGetters_ShouldMaintainObjectReference() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest();
        BigDecimal precio = new BigDecimal("123.45");
        String descripcion = "Test object reference";

        // When
        request.setPrecio(precio);
        request.setDescripcion(descripcion);

        // Then
        assertSame(precio, request.getPrecio());
        assertSame(descripcion, request.getDescripcion());
    }

    @Test
    void allFieldsCoverage_CompleteObjectLifecycle() {
        // Given - Test complete object lifecycle
        UpdateDishRequest request = new UpdateDishRequest();

        // Initially null
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());

        // Set values
        BigDecimal precio1 = BigDecimal.valueOf(50.00);
        String descripcion1 = "First description";
        request.setPrecio(precio1);
        request.setDescripcion(descripcion1);

        // Verify set
        assertEquals(precio1, request.getPrecio());
        assertEquals(descripcion1, request.getDescripcion());

        // Update values
        BigDecimal precio2 = BigDecimal.valueOf(75.00);
        String descripcion2 = "Updated description";
        request.setPrecio(precio2);
        request.setDescripcion(descripcion2);

        // Verify update
        assertEquals(precio2, request.getPrecio());
        assertEquals(descripcion2, request.getDescripcion());

        // Set to null
        request.setPrecio(null);
        request.setDescripcion(null);

        // Verify null
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());
    }

    @Test
    void precio_WhenVerySmallDecimals_ShouldPassValidation() {
        // Given
        validRequest.setPrecio(new BigDecimal("0.01"));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void precio_WhenExactlyTwoDecimals_ShouldPassValidation() {
        // Given
        validRequest.setPrecio(new BigDecimal("123.45"));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void precio_WhenOneDecimal_ShouldPassValidation() {
        // Given
        validRequest.setPrecio(new BigDecimal("123.4"));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void precio_WhenNoDecimals_ShouldPassValidation() {
        // Given
        validRequest.setPrecio(new BigDecimal("123"));

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void descripcion_WhenSingleCharacter_ShouldPassValidation() {
        // Given
        validRequest.setDescripcion("A");

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void descripcion_WhenNumericString_ShouldPassValidation() {
        // Given
        validRequest.setDescripcion("12345");

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void descripcion_WhenMixedCharacters_ShouldPassValidation() {
        // Given
        validRequest.setDescripcion("Descripción123 con números y símbolos!@#");

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_WhenAllFieldsAtBoundaryValues_ShouldPassValidation() {
        // Given
        validRequest.setPrecio(new BigDecimal("9999999999.99")); // Max valid
        validRequest.setDescripcion("a".repeat(500)); // Max length

        // When
        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void constructorAndFieldInitialization_ComprehensiveTest() {
        // Test all constructor scenarios and field states

        // Default constructor
        UpdateDishRequest defaultRequest = new UpdateDishRequest();
        assertNull(defaultRequest.getPrecio());
        assertNull(defaultRequest.getDescripcion());

        // Constructor with null values
        UpdateDishRequest nullRequest = new UpdateDishRequest(null, null);
        assertNull(nullRequest.getPrecio());
        assertNull(nullRequest.getDescripcion());

        // Constructor with mixed null and valid values
        UpdateDishRequest mixedRequest1 = new UpdateDishRequest(BigDecimal.TEN, null);
        assertEquals(BigDecimal.TEN, mixedRequest1.getPrecio());
        assertNull(mixedRequest1.getDescripcion());

        UpdateDishRequest mixedRequest2 = new UpdateDishRequest(null, "Description");
        assertNull(mixedRequest2.getPrecio());
        assertEquals("Description", mixedRequest2.getDescripcion());

        // Constructor with valid values
        UpdateDishRequest validRequest = new UpdateDishRequest(BigDecimal.valueOf(99.99), "Valid description");
        assertEquals(BigDecimal.valueOf(99.99), validRequest.getPrecio());
        assertEquals("Valid description", validRequest.getDescripcion());
    }

    @Test
    void setterChaining_WhenCalledSequentially_ShouldMaintainLatestValues() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest();

        // When - Chain multiple setter calls
        request.setPrecio(BigDecimal.valueOf(10.00));
        request.setDescripcion("First");
        request.setPrecio(BigDecimal.valueOf(20.00));
        request.setDescripcion("Second");
        request.setPrecio(BigDecimal.valueOf(30.00));
        request.setDescripcion("Third");

        // Then
        assertEquals(BigDecimal.valueOf(30.00), request.getPrecio());
        assertEquals("Third", request.getDescripcion());
    }

    @Test
    void objectState_AfterMultipleModifications_ShouldReflectLatestChanges() {
        // Given
        UpdateDishRequest request = new UpdateDishRequest(BigDecimal.valueOf(100.00), "Initial");

        // Verify initial state
        assertEquals(BigDecimal.valueOf(100.00), request.getPrecio());
        assertEquals("Initial", request.getDescripcion());

        // Modify only precio
        request.setPrecio(BigDecimal.valueOf(200.00));
        assertEquals(BigDecimal.valueOf(200.00), request.getPrecio());
        assertEquals("Initial", request.getDescripcion());

        // Modify only descripcion
        request.setDescripcion("Modified");
        assertEquals(BigDecimal.valueOf(200.00), request.getPrecio());
        assertEquals("Modified", request.getDescripcion());

        // Reset to null
        request.setPrecio(null);
        request.setDescripcion(null);
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());

        // Set new values
        request.setPrecio(BigDecimal.valueOf(300.00));
        request.setDescripcion("Final");
        assertEquals(BigDecimal.valueOf(300.00), request.getPrecio());
        assertEquals("Final", request.getDescripcion());
    }

    @Test
    void validation_EdgeCaseScenarios() {
        // Test edge case with zero length description after trim (if applicable)
        UpdateDishRequest request = new UpdateDishRequest();
        request.setPrecio(BigDecimal.valueOf(25.00));
        request.setDescripcion("   "); // Only whitespace

        Set<ConstraintViolation<UpdateDishRequest>> violations = validator.validate(request);
        assertFalse(violations.isEmpty());

        // Test very precise decimal values
        request.setDescripcion("Valid description");
        request.setPrecio(new BigDecimal("123.45"));
        violations = validator.validate(request);
        assertTrue(violations.isEmpty());
    }

    @Test
    void immutabilityOfFieldValues_WhenObjectReferencesAreUsed() {
        // Test that object references are maintained correctly
        BigDecimal originalPrecio = new BigDecimal("125.75");
        String originalDescripcion = "Original description";

        UpdateDishRequest request = new UpdateDishRequest(originalPrecio, originalDescripcion);

        // Verify same references are returned
        assertSame(originalPrecio, request.getPrecio());
        assertSame(originalDescripcion, request.getDescripcion());

        // Modify original objects (if they were mutable)
        // BigDecimal is immutable, but testing the reference behavior
        BigDecimal newPrecio = new BigDecimal("225.25");
        String newDescripcion = "New description";

        request.setPrecio(newPrecio);
        request.setDescripcion(newDescripcion);

        assertSame(newPrecio, request.getPrecio());
        assertSame(newDescripcion, request.getDescripcion());
        assertNotSame(originalPrecio, request.getPrecio());
        assertNotSame(originalDescripcion, request.getDescripcion());
    }
}
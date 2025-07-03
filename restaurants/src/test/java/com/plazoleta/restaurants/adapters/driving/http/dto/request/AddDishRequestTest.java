package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddDishRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void constructor_WithValidParameters_ShouldCreateInstance() {
        // Given
        String nombre = "Pizza Margherita";
        BigDecimal precio = new BigDecimal("15.50");
        String descripcion = "Deliciosa pizza italiana";
        String urlImagen = "https://example.com/pizza.jpg";
        Long idCategoria = 1L;
        Long idRestaurante = 1L;

        // When
        AddDishRequest request = new AddDishRequest(nombre, precio, descripcion,
                urlImagen, idCategoria, idRestaurante);

        // Then
        assertEquals(nombre, request.getNombre());
        assertEquals(precio, request.getPrecio());
        assertEquals(descripcion, request.getDescripcion());
        assertEquals(urlImagen, request.getUrlImagen());
        assertEquals(idCategoria, request.getIdCategoria());
        assertEquals(idRestaurante, request.getIdRestaurante());
    }

    @Test
    void defaultConstructor_ShouldCreateInstance() {
        // When
        AddDishRequest request = new AddDishRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getNombre());
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());
        assertNull(request.getUrlImagen());
        assertNull(request.getIdCategoria());
        assertNull(request.getIdRestaurante());
    }

    @Test
    void validation_WithValidData_ShouldPassValidation() {
        // Given
        AddDishRequest request = createValidRequest();

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_WithBlankNombre_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setNombre("");

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre del plato es obligatorio")));
    }

    @Test
    void validation_WithNullNombre_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setNombre(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre del plato es obligatorio")));
    }

    @Test
    void validation_WithNombreTooLong_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        String longName = "a".repeat(101); // 101 characters
        request.setNombre(longName);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El nombre no puede exceder 100 caracteres")));
    }

    @Test
    void validation_WithNullPrecio_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setPrecio(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio del plato es obligatorio")));
    }

    @Test
    void validation_WithZeroPrecio_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setPrecio(BigDecimal.ZERO);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio del plato debe ser un número positivo y mayor a 0")));
    }

    @Test
    void validation_WithNegativePrecio_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setPrecio(new BigDecimal("-5.00"));

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El precio del plato debe ser un número positivo y mayor a 0")));
    }

    @Test
    void validation_WithBlankDescripcion_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setDescripcion("");

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción del plato es obligatoria")));
    }

    @Test
    void validation_WithDescripcionTooLong_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        String longDescription = "a".repeat(501); // 501 characters
        request.setDescripcion(longDescription);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La descripción no puede exceder 500 caracteres")));
    }

    @Test
    void validation_WithBlankUrlImagen_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setUrlImagen("");

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La URL de la imagen es obligatoria")));
    }

    @Test
    void validation_WithNullIdCategoria_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setIdCategoria(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("La categoría es obligatoria")));
    }

    @Test
    void validation_WithNullIdRestaurante_ShouldFailValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setIdRestaurante(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("El restaurante es obligatorio")));
    }

    @Test
    void setters_ShouldSetValuesCorrectly() {
        // Given
        AddDishRequest request = new AddDishRequest();
        String nombre = "Test Dish";
        BigDecimal precio = new BigDecimal("10.99");
        String descripcion = "Test Description";
        String urlImagen = "https://test.com/image.jpg";
        Long idCategoria = 2L;
        Long idRestaurante = 3L;

        // When
        request.setNombre(nombre);
        request.setPrecio(precio);
        request.setDescripcion(descripcion);
        request.setUrlImagen(urlImagen);
        request.setIdCategoria(idCategoria);
        request.setIdRestaurante(idRestaurante);

        // Then
        assertEquals(nombre, request.getNombre());
        assertEquals(precio, request.getPrecio());
        assertEquals(descripcion, request.getDescripcion());
        assertEquals(urlImagen, request.getUrlImagen());
        assertEquals(idCategoria, request.getIdCategoria());
        assertEquals(idRestaurante, request.getIdRestaurante());
    }

    @Test
    void validation_WithMinimumValidPrecio_ShouldPassValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        request.setPrecio(new BigDecimal("0.01"));

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_WithMaxValidNombre_ShouldPassValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        String maxName = "a".repeat(100); // 100 characters
        request.setNombre(maxName);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_WithMaxValidDescripcion_ShouldPassValidation() {
        // Given
        AddDishRequest request = createValidRequest();
        String maxDescription = "a".repeat(500); // 500 characters
        request.setDescripcion(maxDescription);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty());
    }

    private AddDishRequest createValidRequest() {
        return new AddDishRequest(
                "Pizza Margherita",
                new BigDecimal("15.50"),
                "Deliciosa pizza italiana con tomate y mozzarella",
                "https://example.com/pizza.jpg",
                1L,
                1L
        );
    }
}
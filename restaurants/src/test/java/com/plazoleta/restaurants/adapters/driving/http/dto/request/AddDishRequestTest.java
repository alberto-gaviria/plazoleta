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

class AddDishRequestTest {

    private Validator validator;
    private AddDishRequest validRequest;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        validRequest = new AddDishRequest();
        validRequest.setNombre("Pizza Margherita");
        validRequest.setPrecio(new BigDecimal("25.50"));
        validRequest.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        validRequest.setUrlImagen("https://example.com/pizza.jpg");
        validRequest.setIdCategoria(2L);
        validRequest.setIdRestaurante(1L);
    }

    @Test
    void testDefaultConstructor() {
        // Given & When
        AddDishRequest request = new AddDishRequest();

        // Then
        assertNull(request.getNombre());
        assertNull(request.getPrecio());
        assertNull(request.getDescripcion());
        assertNull(request.getUrlImagen());
        assertNull(request.getIdCategoria());
        assertNull(request.getIdRestaurante());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        String nombre = "Hamburguesa Clásica";
        BigDecimal precio = new BigDecimal("15.00");
        String descripcion = "Hamburguesa con carne, lechuga, tomate y queso";
        String urlImagen = "https://example.com/hamburguesa.jpg";
        Long idCategoria = 2L;
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
    void testValidRequest_ShouldHaveNoViolations() {
        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNombreBlank_ShouldHaveViolation() {
        // Given
        validRequest.setNombre("");

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El nombre del plato es obligatorio", violations.iterator().next().getMessage());
    }

    @Test
    void testNombreNull_ShouldHaveViolation() {
        // Given
        validRequest.setNombre(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El nombre del plato es obligatorio", violations.iterator().next().getMessage());
    }

    @Test
    void testNombreTooLong_ShouldHaveViolation() {
        // Given
        validRequest.setNombre("a".repeat(101));

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El nombre no puede exceder 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void testPrecioNull_ShouldHaveViolation() {
        // Given
        validRequest.setPrecio(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El precio del plato es obligatorio", violations.iterator().next().getMessage());
    }

    @Test
    void testPrecioZero_ShouldHaveViolation() {
        // Given
        validRequest.setPrecio(BigDecimal.ZERO);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El precio del plato debe ser un número positivo y mayor a 0", violations.iterator().next().getMessage());
    }

    @Test
    void testPrecioNegative_ShouldHaveViolation() {
        // Given
        validRequest.setPrecio(new BigDecimal("-10.00"));

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El precio del plato debe ser un número positivo y mayor a 0", violations.iterator().next().getMessage());
    }

    @Test
    void testDescripcionBlank_ShouldHaveViolation() {
        // Given
        validRequest.setDescripcion("");

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La descripción del plato es obligatoria", violations.iterator().next().getMessage());
    }

    @Test
    void testDescripcionNull_ShouldHaveViolation() {
        // Given
        validRequest.setDescripcion(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La descripción del plato es obligatoria", violations.iterator().next().getMessage());
    }

    @Test
    void testDescripcionTooLong_ShouldHaveViolation() {
        // Given
        validRequest.setDescripcion("a".repeat(501));

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La descripción no puede exceder 500 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void testUrlImagenBlank_ShouldHaveViolation() {
        // Given
        validRequest.setUrlImagen("");

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La URL de la imagen es obligatoria", violations.iterator().next().getMessage());
    }

    @Test
    void testUrlImagenNull_ShouldHaveViolation() {
        // Given
        validRequest.setUrlImagen(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La URL de la imagen es obligatoria", violations.iterator().next().getMessage());
    }

    @Test
    void testIdCategoriaNull_ShouldHaveViolation() {
        // Given
        validRequest.setIdCategoria(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("La categoría es obligatoria", violations.iterator().next().getMessage());
    }

    @Test
    void testIdRestauranteNull_ShouldHaveViolation() {
        // Given
        validRequest.setIdRestaurante(null);

        // When
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(validRequest);

        // Then
        assertEquals(1, violations.size());
        assertEquals("El restaurante es obligatorio", violations.iterator().next().getMessage());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        AddDishRequest request = new AddDishRequest();
        String nombre = "Test Dish";
        BigDecimal precio = new BigDecimal("10.00");
        String descripcion = "Test Description";
        String urlImagen = "https://test.com/image.jpg";
        Long idCategoria = 1L;
        Long idRestaurante = 2L;

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
}
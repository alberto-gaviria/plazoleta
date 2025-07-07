package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DishWithCategoryResponseTest {

    private DishWithCategoryResponse dishWithCategoryResponse;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        dishWithCategoryResponse = new DishWithCategoryResponse();
        categoryResponse = new CategoryResponse(1L, "Pizzas", "Pizzas artesanales");
    }

    @Test
    void testDefaultConstructor() {
        // When
        DishWithCategoryResponse response = new DishWithCategoryResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getNombre());
        assertNull(response.getPrecio());
        assertNull(response.getDescripcion());
        assertNull(response.getUrlImagen());
        assertNull(response.getCategoria());
        assertNull(response.getActivo());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long expectedId = 1L;
        String expectedNombre = "Pizza Hawaiana";
        BigDecimal expectedPrecio = new BigDecimal("25500.00");
        String expectedDescripcion = "Pizza con jamón, piña, queso mozzarella y salsa de tomate";
        String expectedUrlImagen = "https://example.com/pizza-hawaiana.jpg";
        Boolean expectedActivo = true;

        // When
        DishWithCategoryResponse response = new DishWithCategoryResponse(
                expectedId, expectedNombre, expectedPrecio, expectedDescripcion,
                expectedUrlImagen, categoryResponse, expectedActivo
        );

        // Then
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        assertEquals(expectedNombre, response.getNombre());
        assertEquals(expectedPrecio, response.getPrecio());
        assertEquals(expectedDescripcion, response.getDescripcion());
        assertEquals(expectedUrlImagen, response.getUrlImagen());
        assertEquals(categoryResponse, response.getCategoria());
        assertEquals(expectedActivo, response.getActivo());
    }

    @Test
    void testSetAndGetId() {
        // Given
        Long expectedId = 1L;

        // When
        dishWithCategoryResponse.setId(expectedId);

        // Then
        assertEquals(expectedId, dishWithCategoryResponse.getId());
    }

    @Test
    void testSetAndGetNombre() {
        // Given
        String expectedNombre = "Pizza Margherita";

        // When
        dishWithCategoryResponse.setNombre(expectedNombre);

        // Then
        assertEquals(expectedNombre, dishWithCategoryResponse.getNombre());
    }

    @Test
    void testSetAndGetPrecio() {
        // Given
        BigDecimal expectedPrecio = new BigDecimal("23000.00");

        // When
        dishWithCategoryResponse.setPrecio(expectedPrecio);

        // Then
        assertEquals(expectedPrecio, dishWithCategoryResponse.getPrecio());
    }

    @Test
    void testSetAndGetDescripcion() {
        // Given
        String expectedDescripcion = "Pizza clásica con albahaca, tomate y mozzarella";

        // When
        dishWithCategoryResponse.setDescripcion(expectedDescripcion);

        // Then
        assertEquals(expectedDescripcion, dishWithCategoryResponse.getDescripcion());
    }

    @Test
    void testSetAndGetUrlImagen() {
        // Given
        String expectedUrlImagen = "https://example.com/pizza-margherita.jpg";

        // When
        dishWithCategoryResponse.setUrlImagen(expectedUrlImagen);

        // Then
        assertEquals(expectedUrlImagen, dishWithCategoryResponse.getUrlImagen());
    }

    @Test
    void testSetAndGetCategoria() {
        // When
        dishWithCategoryResponse.setCategoria(categoryResponse);

        // Then
        assertEquals(categoryResponse, dishWithCategoryResponse.getCategoria());
    }

    @Test
    void testSetAndGetActivo() {
        // Given
        Boolean expectedActivo = false;

        // When
        dishWithCategoryResponse.setActivo(expectedActivo);

        // Then
        assertEquals(expectedActivo, dishWithCategoryResponse.getActivo());
    }

    @Test
    void testNullValues() {
        // When
        dishWithCategoryResponse.setId(null);
        dishWithCategoryResponse.setNombre(null);
        dishWithCategoryResponse.setPrecio(null);
        dishWithCategoryResponse.setDescripcion(null);
        dishWithCategoryResponse.setUrlImagen(null);
        dishWithCategoryResponse.setCategoria(null);
        dishWithCategoryResponse.setActivo(null);

        // Then
        assertNull(dishWithCategoryResponse.getId());
        assertNull(dishWithCategoryResponse.getNombre());
        assertNull(dishWithCategoryResponse.getPrecio());
        assertNull(dishWithCategoryResponse.getDescripcion());
        assertNull(dishWithCategoryResponse.getUrlImagen());
        assertNull(dishWithCategoryResponse.getCategoria());
        assertNull(dishWithCategoryResponse.getActivo());
    }

    @Test
    void testEmptyStrings() {
        // Given
        String emptyString = "";

        // When
        dishWithCategoryResponse.setNombre(emptyString);
        dishWithCategoryResponse.setDescripcion(emptyString);
        dishWithCategoryResponse.setUrlImagen(emptyString);

        // Then
        assertEquals(emptyString, dishWithCategoryResponse.getNombre());
        assertEquals(emptyString, dishWithCategoryResponse.getDescripcion());
        assertEquals(emptyString, dishWithCategoryResponse.getUrlImagen());
    }
}
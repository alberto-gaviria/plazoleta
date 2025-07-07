package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryResponseTest {

    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        categoryResponse = new CategoryResponse();
    }

    @Test
    void testDefaultConstructor() {
        // When
        CategoryResponse response = new CategoryResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getNombre());
        assertNull(response.getDescripcion());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long expectedId = 1L;
        String expectedNombre = "Pizzas";
        String expectedDescripcion = "Pizzas artesanales";

        // When
        CategoryResponse response = new CategoryResponse(expectedId, expectedNombre, expectedDescripcion);

        // Then
        assertNotNull(response);
        assertEquals(expectedId, response.getId());
        assertEquals(expectedNombre, response.getNombre());
        assertEquals(expectedDescripcion, response.getDescripcion());
    }

    @Test
    void testSetAndGetId() {
        // Given
        Long expectedId = 1L;

        // When
        categoryResponse.setId(expectedId);

        // Then
        assertEquals(expectedId, categoryResponse.getId());
    }

    @Test
    void testSetAndGetNombre() {
        // Given
        String expectedNombre = "Hamburguesas";

        // When
        categoryResponse.setNombre(expectedNombre);

        // Then
        assertEquals(expectedNombre, categoryResponse.getNombre());
    }

    @Test
    void testSetAndGetDescripcion() {
        // Given
        String expectedDescripcion = "Hamburguesas gourmet";

        // When
        categoryResponse.setDescripcion(expectedDescripcion);

        // Then
        assertEquals(expectedDescripcion, categoryResponse.getDescripcion());
    }

    @Test
    void testNullValues() {
        // When
        categoryResponse.setId(null);
        categoryResponse.setNombre(null);
        categoryResponse.setDescripcion(null);

        // Then
        assertNull(categoryResponse.getId());
        assertNull(categoryResponse.getNombre());
        assertNull(categoryResponse.getDescripcion());
    }

    @Test
    void testEmptyStrings() {
        // Given
        String emptyString = "";

        // When
        categoryResponse.setNombre(emptyString);
        categoryResponse.setDescripcion(emptyString);

        // Then
        assertEquals(emptyString, categoryResponse.getNombre());
        assertEquals(emptyString, categoryResponse.getDescripcion());
    }
}
package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void testDefaultConstructor() {
        // When
        Category category = new Category();

        // Then
        assertNotNull(category);
        assertNull(category.getId());
        assertNull(category.getNombre());
        assertNull(category.getDescripcion());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long expectedId = 1L;
        String expectedNombre = "Pizzas";
        String expectedDescripcion = "Pizzas artesanales";

        // When
        Category category = new Category(expectedId, expectedNombre, expectedDescripcion);

        // Then
        assertNotNull(category);
        assertEquals(expectedId, category.getId());
        assertEquals(expectedNombre, category.getNombre());
        assertEquals(expectedDescripcion, category.getDescripcion());
    }

    @Test
    void testSetAndGetId() {
        // Given
        Long expectedId = 1L;

        // When
        category.setId(expectedId);

        // Then
        assertEquals(expectedId, category.getId());
    }

    @Test
    void testSetAndGetNombre() {
        // Given
        String expectedNombre = "Hamburguesas";

        // When
        category.setNombre(expectedNombre);

        // Then
        assertEquals(expectedNombre, category.getNombre());
    }

    @Test
    void testSetAndGetDescripcion() {
        // Given
        String expectedDescripcion = "Hamburguesas gourmet";

        // When
        category.setDescripcion(expectedDescripcion);

        // Then
        assertEquals(expectedDescripcion, category.getDescripcion());
    }

    @Test
    void testNullValues() {
        // When
        category.setId(null);
        category.setNombre(null);
        category.setDescripcion(null);

        // Then
        assertNull(category.getId());
        assertNull(category.getNombre());
        assertNull(category.getDescripcion());
    }

    @Test
    void testEmptyStrings() {
        // Given
        String emptyString = "";

        // When
        category.setNombre(emptyString);
        category.setDescripcion(emptyString);

        // Then
        assertEquals(emptyString, category.getNombre());
        assertEquals(emptyString, category.getDescripcion());
    }
}
package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DishWithCategoryTest {

    private DishWithCategory dishWithCategory;
    private Category category;

    @BeforeEach
    void setUp() {
        dishWithCategory = new DishWithCategory();
        category = new Category(1L, "Pizzas", "Pizzas artesanales");
    }

    @Test
    void testDefaultConstructor() {
        // When
        DishWithCategory dish = new DishWithCategory();

        // Then
        assertNotNull(dish);
        assertNull(dish.getId());
        assertNull(dish.getNombre());
        assertNull(dish.getPrecio());
        assertNull(dish.getDescripcion());
        assertNull(dish.getUrlImagen());
        assertNull(dish.getCategoria());
        assertNull(dish.getIdRestaurante());
        assertNull(dish.getActivo());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Long expectedId = 1L;
        String expectedNombre = "Pizza Hawaiana";
        BigDecimal expectedPrecio = new BigDecimal("25500.00");
        String expectedDescripcion = "Pizza con jamón, piña, queso mozzarella y salsa de tomate";
        String expectedUrlImagen = "https://example.com/pizza-hawaiana.jpg";
        Long expectedIdRestaurante = 1L;
        Boolean expectedActivo = true;

        // When
        DishWithCategory dish = new DishWithCategory(
                expectedId, expectedNombre, expectedPrecio, expectedDescripcion,
                expectedUrlImagen, category, expectedIdRestaurante, expectedActivo
        );

        // Then
        assertNotNull(dish);
        assertEquals(expectedId, dish.getId());
        assertEquals(expectedNombre, dish.getNombre());
        assertEquals(expectedPrecio, dish.getPrecio());
        assertEquals(expectedDescripcion, dish.getDescripcion());
        assertEquals(expectedUrlImagen, dish.getUrlImagen());
        assertEquals(category, dish.getCategoria());
        assertEquals(expectedIdRestaurante, dish.getIdRestaurante());
        assertEquals(expectedActivo, dish.getActivo());
    }

    @Test
    void testSetAndGetId() {
        // Given
        Long expectedId = 1L;

        // When
        dishWithCategory.setId(expectedId);

        // Then
        assertEquals(expectedId, dishWithCategory.getId());
    }

    @Test
    void testSetAndGetNombre() {
        // Given
        String expectedNombre = "Pizza Margherita";

        // When
        dishWithCategory.setNombre(expectedNombre);

        // Then
        assertEquals(expectedNombre, dishWithCategory.getNombre());
    }

    @Test
    void testSetAndGetPrecio() {
        // Given
        BigDecimal expectedPrecio = new BigDecimal("23000.00");

        // When
        dishWithCategory.setPrecio(expectedPrecio);

        // Then
        assertEquals(expectedPrecio, dishWithCategory.getPrecio());
    }

    @Test
    void testSetAndGetDescripcion() {
        // Given
        String expectedDescripcion = "Pizza clásica con albahaca, tomate y mozzarella";

        // When
        dishWithCategory.setDescripcion(expectedDescripcion);

        // Then
        assertEquals(expectedDescripcion, dishWithCategory.getDescripcion());
    }

    @Test
    void testSetAndGetUrlImagen() {
        // Given
        String expectedUrlImagen = "https://example.com/pizza-margherita.jpg";

        // When
        dishWithCategory.setUrlImagen(expectedUrlImagen);

        // Then
        assertEquals(expectedUrlImagen, dishWithCategory.getUrlImagen());
    }

    @Test
    void testSetAndGetCategoria() {
        // When
        dishWithCategory.setCategoria(category);

        // Then
        assertEquals(category, dishWithCategory.getCategoria());
    }

    @Test
    void testSetAndGetIdRestaurante() {
        // Given
        Long expectedIdRestaurante = 2L;

        // When
        dishWithCategory.setIdRestaurante(expectedIdRestaurante);

        // Then
        assertEquals(expectedIdRestaurante, dishWithCategory.getIdRestaurante());
    }

    @Test
    void testSetAndGetActivo() {
        // Given
        Boolean expectedActivo = false;

        // When
        dishWithCategory.setActivo(expectedActivo);

        // Then
        assertEquals(expectedActivo, dishWithCategory.getActivo());
    }

    @Test
    void testNullValues() {
        // When
        dishWithCategory.setId(null);
        dishWithCategory.setNombre(null);
        dishWithCategory.setPrecio(null);
        dishWithCategory.setDescripcion(null);
        dishWithCategory.setUrlImagen(null);
        dishWithCategory.setCategoria(null);
        dishWithCategory.setIdRestaurante(null);
        dishWithCategory.setActivo(null);

        // Then
        assertNull(dishWithCategory.getId());
        assertNull(dishWithCategory.getNombre());
        assertNull(dishWithCategory.getPrecio());
        assertNull(dishWithCategory.getDescripcion());
        assertNull(dishWithCategory.getUrlImagen());
        assertNull(dishWithCategory.getCategoria());
        assertNull(dishWithCategory.getIdRestaurante());
        assertNull(dishWithCategory.getActivo());
    }

    @Test
    void testEmptyStrings() {
        // Given
        String emptyString = "";

        // When
        dishWithCategory.setNombre(emptyString);
        dishWithCategory.setDescripcion(emptyString);
        dishWithCategory.setUrlImagen(emptyString);

        // Then
        assertEquals(emptyString, dishWithCategory.getNombre());
        assertEquals(emptyString, dishWithCategory.getDescripcion());
        assertEquals(emptyString, dishWithCategory.getUrlImagen());
    }
}
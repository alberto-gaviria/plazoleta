package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import jakarta.persistence.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CategoryEntity - Unit Tests")
class CategoryEntityTest {

    private CategoryEntity categoryEntity;

    @BeforeEach
    void setUp() {
        categoryEntity = new CategoryEntity();
    }

    @Test
    @DisplayName("Should create CategoryEntity with default constructor")
    void testDefaultConstructor() {
        // Given & When
        CategoryEntity entity = new CategoryEntity();

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getNombre());
        assertNull(entity.getDescripcion());
    }

    @Test
    @DisplayName("Should create CategoryEntity with all args constructor")
    void testAllArgsConstructor() {
        // Given
        Long expectedId = 1L;
        String expectedNombre = "Pizzas";
        String expectedDescripcion = "Pizzas artesanales";

        // When
        CategoryEntity entity = new CategoryEntity(expectedId, expectedNombre, expectedDescripcion);

        // Then
        assertNotNull(entity);
        assertEquals(expectedId, entity.getId());
        assertEquals(expectedNombre, entity.getNombre());
        assertEquals(expectedDescripcion, entity.getDescripcion());
    }

    @Test
    @DisplayName("Should set and get id correctly")
    void testSetAndGetId() {
        // Given
        Long expectedId = 1L;

        // When
        categoryEntity.setId(expectedId);

        // Then
        assertEquals(expectedId, categoryEntity.getId());
    }

    @Test
    @DisplayName("Should set and get nombre correctly")
    void testSetAndGetNombre() {
        // Given
        String expectedNombre = "Hamburguesas";

        // When
        categoryEntity.setNombre(expectedNombre);

        // Then
        assertEquals(expectedNombre, categoryEntity.getNombre());
    }

    @Test
    @DisplayName("Should set and get descripcion correctly")
    void testSetAndGetDescripcion() {
        // Given
        String expectedDescripcion = "Hamburguesas gourmet";

        // When
        categoryEntity.setDescripcion(expectedDescripcion);

        // Then
        assertEquals(expectedDescripcion, categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should handle null values correctly")
    void testNullValues() {
        // When
        categoryEntity.setId(null);
        categoryEntity.setNombre(null);
        categoryEntity.setDescripcion(null);

        // Then
        assertNull(categoryEntity.getId());
        assertNull(categoryEntity.getNombre());
        assertNull(categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should handle empty strings correctly")
    void testEmptyStrings() {
        // Given
        String emptyString = "";

        // When
        categoryEntity.setNombre(emptyString);
        categoryEntity.setDescripcion(emptyString);

        // Then
        assertEquals(emptyString, categoryEntity.getNombre());
        assertEquals(emptyString, categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should handle whitespace strings correctly")
    void testWhitespaceStrings() {
        // Given
        String whitespaceNombre = "   ";
        String whitespaceDescripcion = "\t\n";

        // When
        categoryEntity.setNombre(whitespaceNombre);
        categoryEntity.setDescripcion(whitespaceDescripcion);

        // Then
        assertEquals(whitespaceNombre, categoryEntity.getNombre());
        assertEquals(whitespaceDescripcion, categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should handle long strings correctly")
    void testLongStrings() {
        // Given
        String longNombre = "A".repeat(1000);
        String longDescripcion = "B".repeat(5000);

        // When
        categoryEntity.setNombre(longNombre);
        categoryEntity.setDescripcion(longDescripcion);

        // Then
        assertEquals(longNombre, categoryEntity.getNombre());
        assertEquals(longDescripcion, categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should handle special characters correctly")
    void testSpecialCharacters() {
        // Given
        String specialNombre = "Café & Té";
        String specialDescripcion = "Bebidas calientes: café, té, chocolate... ¡Deliciosos! 😋";

        // When
        categoryEntity.setNombre(specialNombre);
        categoryEntity.setDescripcion(specialDescripcion);

        // Then
        assertEquals(specialNombre, categoryEntity.getNombre());
        assertEquals(specialDescripcion, categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should maintain state after multiple operations")
    void testStateConsistency() {
        // Given
        Long id1 = 1L;
        String nombre1 = "Pizzas";
        String descripcion1 = "Pizzas artesanales";

        Long id2 = 2L;
        String nombre2 = "Bebidas";
        String descripcion2 = "Bebidas frías y calientes";

        // When & Then - First set
        categoryEntity.setId(id1);
        categoryEntity.setNombre(nombre1);
        categoryEntity.setDescripcion(descripcion1);

        assertEquals(id1, categoryEntity.getId());
        assertEquals(nombre1, categoryEntity.getNombre());
        assertEquals(descripcion1, categoryEntity.getDescripcion());

        // When & Then - Second set (overwrite)
        categoryEntity.setId(id2);
        categoryEntity.setNombre(nombre2);
        categoryEntity.setDescripcion(descripcion2);

        assertEquals(id2, categoryEntity.getId());
        assertEquals(nombre2, categoryEntity.getNombre());
        assertEquals(descripcion2, categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should test equals and hashCode behavior")
    void testEqualsAndHashCode() {
        // Given
        CategoryEntity entity1 = new CategoryEntity(1L, "Pizzas", "Pizzas artesanales");
        CategoryEntity entity2 = new CategoryEntity(1L, "Pizzas", "Pizzas artesanales");
        CategoryEntity entity3 = new CategoryEntity(2L, "Hamburguesas", "Hamburguesas gourmet");

        // Then - Test reflexivity
        assertEquals(entity1, entity1);

        // Test symmetry (Note: Lombok @Data generates equals/hashCode, but this entity doesn't have @Data)
        // Since the entity doesn't override equals/hashCode, they use Object's implementation
        assertNotEquals(entity1, entity2); // Different object references
        assertNotEquals(entity1, entity3);

        // Test hashCode consistency
        assertEquals(entity1.hashCode(), entity1.hashCode());
    }

    @Test
    @DisplayName("Should test toString behavior")
    void testToString() {
        // Given
        CategoryEntity entity = new CategoryEntity(1L, "Pizzas", "Pizzas artesanales");

        // When
        String toString = entity.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("CategoryEntity"));
        // Note: Since Lombok @ToString is not present, it uses Object's toString
    }

    @Test
    @DisplayName("Should handle boundary values for Long id")
    void testBoundaryLongValues() {
        // Given & When & Then
        categoryEntity.setId(Long.MIN_VALUE);
        assertEquals(Long.MIN_VALUE, categoryEntity.getId());

        categoryEntity.setId(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, categoryEntity.getId());

        categoryEntity.setId(0L);
        assertEquals(0L, categoryEntity.getId());

        categoryEntity.setId(-1L);
        assertEquals(-1L, categoryEntity.getId());
    }

    @Test
    @DisplayName("Should test field independence")
    void testFieldIndependence() {
        // Given
        Long expectedId = 1L;
        String expectedNombre = "Postres";

        // When - Set only some fields
        categoryEntity.setId(expectedId);
        categoryEntity.setNombre(expectedNombre);
        // descripcion remains null

        // Then
        assertEquals(expectedId, categoryEntity.getId());
        assertEquals(expectedNombre, categoryEntity.getNombre());
        assertNull(categoryEntity.getDescripcion());
    }

    @Test
    @DisplayName("Should test immutability of getter returns")
    void testGetterReturnImmutability() {
        // Given
        String originalNombre = "Bebidas";
        categoryEntity.setNombre(originalNombre);

        // When
        String retrievedNombre = categoryEntity.getNombre();

        // Then
        assertEquals(originalNombre, retrievedNombre);

        // Verify that modifying retrieved value doesn't affect original
        // (This is naturally true for String as they are immutable)
        assertSame(originalNombre, retrievedNombre);
    }
}
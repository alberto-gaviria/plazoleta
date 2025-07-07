package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.CategoryEntity;
import com.plazoleta.restaurants.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class ICategoryEntityMapperTest {

    private ICategoryEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ICategoryEntityMapper.class);
    }

    @Test
    void testToModel_ValidCategoryEntity_ShouldMapCorrectly() {
        // Given
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Pizzas", "Pizzas artesanales");

        // When
        Category result = mapper.toModel(categoryEntity);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizzas", result.getNombre());
        assertEquals("Pizzas artesanales", result.getDescripcion());
    }

    @Test
    void testToModel_NullCategoryEntity_ShouldReturnNull() {
        // When
        Category result = mapper.toModel(null);

        // Then
        assertNull(result);
    }

    @Test
    void testToModel_CategoryEntityWithNullFields_ShouldMapNullFields() {
        // Given
        CategoryEntity categoryEntity = new CategoryEntity(null, null, null);

        // When
        Category result = mapper.toModel(categoryEntity);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getDescripcion());
    }

    @Test
    void testToModel_CategoryEntityWithEmptyStrings_ShouldMapEmptyStrings() {
        // Given
        CategoryEntity categoryEntity = new CategoryEntity(2L, "", "");

        // When
        Category result = mapper.toModel(categoryEntity);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("", result.getNombre());
        assertEquals("", result.getDescripcion());
    }
}
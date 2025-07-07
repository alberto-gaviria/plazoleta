package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.domain.model.Category;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IDishWithCategoryMapperTest {

    private IDishWithCategoryMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IDishWithCategoryMapper.class);
    }

    @Test
    void testToModel_ValidDishEntityAndCategory_ShouldMapCorrectly() {
        // Given
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setNombre("Pizza Hawaiana");
        dishEntity.setPrecio(new BigDecimal("25500.00"));
        dishEntity.setDescripcion("Pizza con jamón, piña, queso mozzarella y salsa de tomate");
        dishEntity.setUrlImagen("https://example.com/pizza-hawaiana.jpg");
        dishEntity.setIdRestaurante(1L);
        dishEntity.setActivo(true);

        Category category = new Category(1L, "Pizzas", "Pizzas artesanales");

        // When
        DishWithCategory result = mapper.toModel(dishEntity, category);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizza Hawaiana", result.getNombre());
        assertEquals(new BigDecimal("25500.00"), result.getPrecio());
        assertEquals("Pizza con jamón, piña, queso mozzarella y salsa de tomate", result.getDescripcion());
        assertEquals("https://example.com/pizza-hawaiana.jpg", result.getUrlImagen());
        assertEquals(1L, result.getIdRestaurante());
        assertEquals(true, result.getActivo());
        assertEquals(category, result.getCategoria());
    }

    @Test
    void testToModel_NullDishEntity_ShouldReturnObjectWithNullFields() {
        // Given
        Category category = new Category(1L, "Pizzas", "Pizzas artesanales");

        // When
        DishWithCategory result = mapper.toModel(null, category);

        // Then
        // MapStruct puede crear un objeto incluso con DishEntity null
        // pero los campos del DishEntity serán null
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getPrecio());
        assertNull(result.getDescripcion());
        assertNull(result.getUrlImagen());
        assertNull(result.getIdRestaurante());
        assertNull(result.getActivo());
        assertEquals(category, result.getCategoria()); // Category se mapea correctamente
    }

    @Test
    void testToModel_NullCategory_ShouldMapWithNullCategory() {
        // Given
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setNombre("Pizza Sin Categoría");
        dishEntity.setPrecio(new BigDecimal("20000.00"));
        dishEntity.setDescripcion("Pizza sin categoría");
        dishEntity.setUrlImagen("https://example.com/pizza.jpg");
        dishEntity.setIdRestaurante(1L);
        dishEntity.setActivo(false);

        // When
        DishWithCategory result = mapper.toModel(dishEntity, null);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Pizza Sin Categoría", result.getNombre());
        assertEquals(new BigDecimal("20000.00"), result.getPrecio());
        assertEquals("Pizza sin categoría", result.getDescripcion());
        assertEquals("https://example.com/pizza.jpg", result.getUrlImagen());
        assertEquals(1L, result.getIdRestaurante());
        assertEquals(false, result.getActivo());
        assertNull(result.getCategoria());
    }

    @Test
    void testToModel_BothNull_ShouldReturnNull() {
        // When
        DishWithCategory result = mapper.toModel(null, null);

        // Then
        // MapStruct devuelve null cuando ambos parámetros son null
        assertNull(result);
    }

    @Test
    void testToModel_DishEntityWithNullFields_ShouldMapNullFields() {
        // Given
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(null);
        dishEntity.setNombre(null);
        dishEntity.setPrecio(null);
        dishEntity.setDescripcion(null);
        dishEntity.setUrlImagen(null);
        dishEntity.setIdRestaurante(null);
        dishEntity.setActivo(null);

        Category category = new Category(1L, "Pizzas", "Pizzas artesanales");

        // When
        DishWithCategory result = mapper.toModel(dishEntity, category);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getNombre());
        assertNull(result.getPrecio());
        assertNull(result.getDescripcion());
        assertNull(result.getUrlImagen());
        assertNull(result.getIdRestaurante());
        assertNull(result.getActivo());
        assertEquals(category, result.getCategoria());
    }
}
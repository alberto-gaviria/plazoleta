package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IDishEntityMapperTest {

    private IDishEntityMapper dishEntityMapper;

    @BeforeEach
    void setUp() {
        dishEntityMapper = Mappers.getMapper(IDishEntityMapper.class);
    }

    @Test
    void toModel_ShouldMapAllFieldsCorrectly() {
        // Given
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setNombre("Pizza Margherita");
        dishEntity.setPrecio(new BigDecimal("25.50"));
        dishEntity.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dishEntity.setUrlImagen("https://example.com/pizza.jpg");
        dishEntity.setIdCategoria(2L);
        dishEntity.setIdRestaurante(1L);
        dishEntity.setActivo(true);

        // When
        Dish dish = dishEntityMapper.toModel(dishEntity);

        // Then
        assertNotNull(dish);
        assertEquals(dishEntity.getId(), dish.getId());
        assertEquals(dishEntity.getNombre(), dish.getNombre());
        assertEquals(dishEntity.getPrecio(), dish.getPrecio());
        assertEquals(dishEntity.getDescripcion(), dish.getDescripcion());
        assertEquals(dishEntity.getUrlImagen(), dish.getUrlImagen());
        assertEquals(dishEntity.getIdCategoria(), dish.getIdCategoria());
        assertEquals(dishEntity.getIdRestaurante(), dish.getIdRestaurante());
        assertEquals(dishEntity.getActivo(), dish.getActivo());
    }

    @Test
    void toModel_WhenEntityIsNull_ShouldReturnNull() {
        // Given
        DishEntity dishEntity = null;

        // When
        Dish dish = dishEntityMapper.toModel(dishEntity);

        // Then
        assertNull(dish);
    }

    @Test
    void toEntity_ShouldMapAllFieldsCorrectly() {
        // Given
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setNombre("Pizza Margherita");
        dish.setPrecio(new BigDecimal("25.50"));
        dish.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dish.setUrlImagen("https://example.com/pizza.jpg");
        dish.setIdCategoria(2L);
        dish.setIdRestaurante(1L);
        dish.setActivo(true);

        // When
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);

        // Then
        assertNotNull(dishEntity);
        assertEquals(dish.getId(), dishEntity.getId());
        assertEquals(dish.getNombre(), dishEntity.getNombre());
        assertEquals(dish.getPrecio(), dishEntity.getPrecio());
        assertEquals(dish.getDescripcion(), dishEntity.getDescripcion());
        assertEquals(dish.getUrlImagen(), dishEntity.getUrlImagen());
        assertEquals(dish.getIdCategoria(), dishEntity.getIdCategoria());
        assertEquals(dish.getIdRestaurante(), dishEntity.getIdRestaurante());
        assertEquals(dish.getActivo(), dishEntity.getActivo());
    }

    @Test
    void toEntity_WhenModelIsNull_ShouldReturnNull() {
        // Given
        Dish dish = null;

        // When
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);

        // Then
        assertNull(dishEntity);
    }

    @Test
    void toModelList_ShouldMapAllEntitiesCorrectly() {
        // Given
        DishEntity entity1 = new DishEntity();
        entity1.setId(1L);
        entity1.setNombre("Pizza Margherita");
        entity1.setPrecio(new BigDecimal("25.50"));
        entity1.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        entity1.setUrlImagen("https://example.com/pizza.jpg");
        entity1.setIdCategoria(2L);
        entity1.setIdRestaurante(1L);
        entity1.setActivo(true);

        DishEntity entity2 = new DishEntity();
        entity2.setId(2L);
        entity2.setNombre("Hamburguesa Clásica");
        entity2.setPrecio(new BigDecimal("15.00"));
        entity2.setDescripcion("Hamburguesa con carne, lechuga, tomate y queso");
        entity2.setUrlImagen("https://example.com/hamburguesa.jpg");
        entity2.setIdCategoria(2L);
        entity2.setIdRestaurante(1L);
        entity2.setActivo(false);

        List<DishEntity> entities = Arrays.asList(entity1, entity2);

        // When
        List<Dish> dishes = dishEntityMapper.toModelList(entities);

        // Then
        assertNotNull(dishes);
        assertEquals(2, dishes.size());

        Dish dish1 = dishes.get(0);
        assertEquals(entity1.getId(), dish1.getId());
        assertEquals(entity1.getNombre(), dish1.getNombre());
        assertEquals(entity1.getPrecio(), dish1.getPrecio());
        assertEquals(entity1.getDescripcion(), dish1.getDescripcion());
        assertEquals(entity1.getUrlImagen(), dish1.getUrlImagen());
        assertEquals(entity1.getIdCategoria(), dish1.getIdCategoria());
        assertEquals(entity1.getIdRestaurante(), dish1.getIdRestaurante());
        assertEquals(entity1.getActivo(), dish1.getActivo());

        Dish dish2 = dishes.get(1);
        assertEquals(entity2.getId(), dish2.getId());
        assertEquals(entity2.getNombre(), dish2.getNombre());
        assertEquals(entity2.getPrecio(), dish2.getPrecio());
        assertEquals(entity2.getDescripcion(), dish2.getDescripcion());
        assertEquals(entity2.getUrlImagen(), dish2.getUrlImagen());
        assertEquals(entity2.getIdCategoria(), dish2.getIdCategoria());
        assertEquals(entity2.getIdRestaurante(), dish2.getIdRestaurante());
        assertEquals(entity2.getActivo(), dish2.getActivo());
    }

    @Test
    void toModelList_WhenListIsNull_ShouldReturnNull() {
        // Given
        List<DishEntity> entities = null;

        // When
        List<Dish> dishes = dishEntityMapper.toModelList(entities);

        // Then
        assertNull(dishes);
    }

    @Test
    void toModelList_WhenListIsEmpty_ShouldReturnEmptyList() {
        // Given
        List<DishEntity> entities = Arrays.asList();

        // When
        List<Dish> dishes = dishEntityMapper.toModelList(entities);

        // Then
        assertNotNull(dishes);
        assertTrue(dishes.isEmpty());
    }

    @Test
    void mappingWithNullFields_ShouldHandleGracefully() {
        // Given
        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(null);
        dishEntity.setNombre(null);
        dishEntity.setPrecio(null);
        dishEntity.setDescripcion(null);
        dishEntity.setUrlImagen(null);
        dishEntity.setIdCategoria(null);
        dishEntity.setIdRestaurante(null);
        dishEntity.setActivo(null);

        // When
        Dish dish = dishEntityMapper.toModel(dishEntity);

        // Then
        assertNotNull(dish);
        assertNull(dish.getId());
        assertNull(dish.getNombre());
        assertNull(dish.getPrecio());
        assertNull(dish.getDescripcion());
        assertNull(dish.getUrlImagen());
        assertNull(dish.getIdCategoria());
        assertNull(dish.getIdRestaurante());
        assertNull(dish.getActivo());
    }

    @Test
    void reverseMappingWithNullFields_ShouldHandleGracefully() {
        // Given
        Dish dish = new Dish();
        dish.setId(null);
        dish.setNombre(null);
        dish.setPrecio(null);
        dish.setDescripcion(null);
        dish.setUrlImagen(null);
        dish.setIdCategoria(null);
        dish.setIdRestaurante(null);
        dish.setActivo(null);

        // When
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);

        // Then
        assertNotNull(dishEntity);
        assertNull(dishEntity.getId());
        assertNull(dishEntity.getNombre());
        assertNull(dishEntity.getPrecio());
        assertNull(dishEntity.getDescripcion());
        assertNull(dishEntity.getUrlImagen());
        assertNull(dishEntity.getIdCategoria());
        assertNull(dishEntity.getIdRestaurante());
        assertNull(dishEntity.getActivo());
    }
}
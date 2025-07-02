package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishResponse;
import com.plazoleta.restaurants.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IDishResponseMapperTest {

    private IDishResponseMapper dishResponseMapper;

    @BeforeEach
    void setUp() {
        dishResponseMapper = Mappers.getMapper(IDishResponseMapper.class);
    }

    @Test
    void dishToResponse_ShouldMapAllFieldsCorrectly() {
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
        DishResponse response = dishResponseMapper.dishToResponse(dish);

        // Then
        assertNotNull(response);
        assertEquals(dish.getId(), response.getId());
        assertEquals(dish.getNombre(), response.getNombre());
        assertEquals(dish.getPrecio(), response.getPrecio());
        assertEquals(dish.getDescripcion(), response.getDescripcion());
        assertEquals(dish.getUrlImagen(), response.getUrlImagen());
        assertEquals(dish.getIdCategoria(), response.getIdCategoria());
        assertEquals(dish.getIdRestaurante(), response.getIdRestaurante());
        assertEquals(dish.getActivo(), response.getActivo());
    }

    @Test
    void dishToResponse_WhenDishIsNull_ShouldReturnNull() {
        // Given
        Dish dish = null;

        // When
        DishResponse response = dishResponseMapper.dishToResponse(dish);

        // Then
        assertNull(response);
    }

    @Test
    void dishListToResponseList_ShouldMapAllDishesCorrectly() {
        // Given
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setNombre("Pizza Margherita");
        dish1.setPrecio(new BigDecimal("25.50"));
        dish1.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dish1.setUrlImagen("https://example.com/pizza.jpg");
        dish1.setIdCategoria(2L);
        dish1.setIdRestaurante(1L);
        dish1.setActivo(true);

        Dish dish2 = new Dish();
        dish2.setId(2L);
        dish2.setNombre("Hamburguesa Clásica");
        dish2.setPrecio(new BigDecimal("15.00"));
        dish2.setDescripcion("Hamburguesa con carne, lechuga, tomate y queso");
        dish2.setUrlImagen("https://example.com/hamburguesa.jpg");
        dish2.setIdCategoria(2L);
        dish2.setIdRestaurante(1L);
        dish2.setActivo(false);

        List<Dish> dishes = Arrays.asList(dish1, dish2);

        // When
        List<DishResponse> responses = dishResponseMapper.dishListToResponseList(dishes);

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        DishResponse response1 = responses.get(0);
        assertEquals(dish1.getId(), response1.getId());
        assertEquals(dish1.getNombre(), response1.getNombre());
        assertEquals(dish1.getPrecio(), response1.getPrecio());
        assertEquals(dish1.getDescripcion(), response1.getDescripcion());
        assertEquals(dish1.getUrlImagen(), response1.getUrlImagen());
        assertEquals(dish1.getIdCategoria(), response1.getIdCategoria());
        assertEquals(dish1.getIdRestaurante(), response1.getIdRestaurante());
        assertEquals(dish1.getActivo(), response1.getActivo());

        DishResponse response2 = responses.get(1);
        assertEquals(dish2.getId(), response2.getId());
        assertEquals(dish2.getNombre(), response2.getNombre());
        assertEquals(dish2.getPrecio(), response2.getPrecio());
        assertEquals(dish2.getDescripcion(), response2.getDescripcion());
        assertEquals(dish2.getUrlImagen(), response2.getUrlImagen());
        assertEquals(dish2.getIdCategoria(), response2.getIdCategoria());
        assertEquals(dish2.getIdRestaurante(), response2.getIdRestaurante());
        assertEquals(dish2.getActivo(), response2.getActivo());
    }

    @Test
    void dishListToResponseList_WhenListIsNull_ShouldReturnNull() {
        // Given
        List<Dish> dishes = null;

        // When
        List<DishResponse> responses = dishResponseMapper.dishListToResponseList(dishes);

        // Then
        assertNull(responses);
    }

    @Test
    void dishListToResponseList_WhenListIsEmpty_ShouldReturnEmptyList() {
        // Given
        List<Dish> dishes = Arrays.asList();

        // When
        List<DishResponse> responses = dishResponseMapper.dishListToResponseList(dishes);

        // Then
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    @Test
    void dishToResponse_WithNullFields_ShouldHandleGracefully() {
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
        DishResponse response = dishResponseMapper.dishToResponse(dish);

        // Then
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getNombre());
        assertNull(response.getPrecio());
        assertNull(response.getDescripcion());
        assertNull(response.getUrlImagen());
        assertNull(response.getIdCategoria());
        assertNull(response.getIdRestaurante());
        assertNull(response.getActivo());
    }

    @Test
    void dishListToResponseList_WithMixedNullValues_ShouldHandleGracefully() {
        // Given
        Dish dish1 = new Dish();
        dish1.setId(1L);
        dish1.setNombre("Pizza Margherita");
        dish1.setPrecio(new BigDecimal("25.50"));

        Dish dish2 = new Dish();
        dish2.setId(null);
        dish2.setNombre(null);
        dish2.setPrecio(null);

        List<Dish> dishes = Arrays.asList(dish1, dish2);

        // When
        List<DishResponse> responses = dishResponseMapper.dishListToResponseList(dishes);

        // Then
        assertNotNull(responses);
        assertEquals(2, responses.size());

        DishResponse response1 = responses.get(0);
        assertEquals(dish1.getId(), response1.getId());
        assertEquals(dish1.getNombre(), response1.getNombre());
        assertEquals(dish1.getPrecio(), response1.getPrecio());

        DishResponse response2 = responses.get(1);
        assertNull(response2.getId());
        assertNull(response2.getNombre());
        assertNull(response2.getPrecio());
    }
}
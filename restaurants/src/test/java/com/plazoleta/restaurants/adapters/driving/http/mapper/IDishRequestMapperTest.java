package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddDishRequest;
import com.plazoleta.restaurants.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IDishRequestMapperTest {

    private IDishRequestMapper dishRequestMapper;

    @BeforeEach
    void setUp() {
        dishRequestMapper = Mappers.getMapper(IDishRequestMapper.class);
    }

    @Test
    void addRequestToDish_ShouldMapAllFieldsCorrectly() {
        // Given
        AddDishRequest request = new AddDishRequest();
        request.setNombre("Pizza Margherita");
        request.setPrecio(new BigDecimal("25.50"));
        request.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        request.setUrlImagen("https://example.com/pizza.jpg");
        request.setIdCategoria(2L);
        request.setIdRestaurante(1L);

        // When
        Dish dish = dishRequestMapper.addRequestToDish(request);

        // Then
        assertNotNull(dish);
        assertNull(dish.getId()); // Se ignora en el mapeo
        assertEquals(request.getNombre(), dish.getNombre());
        assertEquals(request.getPrecio(), dish.getPrecio());
        assertEquals(request.getDescripcion(), dish.getDescripcion());
        assertEquals(request.getUrlImagen(), dish.getUrlImagen());
        assertEquals(request.getIdCategoria(), dish.getIdCategoria());
        assertEquals(request.getIdRestaurante(), dish.getIdRestaurante());
        assertTrue(dish.getActivo()); // Se setea como constante en true
    }

    @Test
    void addRequestToDish_WhenRequestIsNull_ShouldReturnNull() {
        // Given
        AddDishRequest request = null;

        // When
        Dish dish = dishRequestMapper.addRequestToDish(request);

        // Then
        assertNull(dish);
    }

    @Test
    void addRequestToDish_WithNullFields_ShouldHandleGracefully() {
        // Given
        AddDishRequest request = new AddDishRequest();
        request.setNombre(null);
        request.setPrecio(null);
        request.setDescripcion(null);
        request.setUrlImagen(null);
        request.setIdCategoria(null);
        request.setIdRestaurante(null);

        // When
        Dish dish = dishRequestMapper.addRequestToDish(request);

        // Then
        assertNotNull(dish);
        assertNull(dish.getId());
        assertNull(dish.getNombre());
        assertNull(dish.getPrecio());
        assertNull(dish.getDescripcion());
        assertNull(dish.getUrlImagen());
        assertNull(dish.getIdCategoria());
        assertNull(dish.getIdRestaurante());
        assertTrue(dish.getActivo()); // Siempre true por la constante
    }

    @Test
    void addRequestToDish_WithValidData_ShouldAlwaysSetActivoToTrue() {
        // Given
        AddDishRequest request1 = new AddDishRequest();
        request1.setNombre("Plato 1");
        request1.setPrecio(new BigDecimal("10.00"));
        request1.setDescripcion("Descripción 1");
        request1.setUrlImagen("http://example.com/1.jpg");
        request1.setIdCategoria(1L);
        request1.setIdRestaurante(1L);

        AddDishRequest request2 = new AddDishRequest();
        request2.setNombre("Plato 2");
        request2.setPrecio(new BigDecimal("20.00"));
        request2.setDescripcion("Descripción 2");
        request2.setUrlImagen("http://example.com/2.jpg");
        request2.setIdCategoria(2L);
        request2.setIdRestaurante(2L);

        // When
        Dish dish1 = dishRequestMapper.addRequestToDish(request1);
        Dish dish2 = dishRequestMapper.addRequestToDish(request2);

        // Then
        assertTrue(dish1.getActivo());
        assertTrue(dish2.getActivo());
    }

    @Test
    void addRequestToDish_ShouldIgnoreIdField() {
        // Given
        AddDishRequest request = new AddDishRequest();
        request.setNombre("Pizza Test");
        request.setPrecio(new BigDecimal("15.00"));
        request.setDescripcion("Pizza de prueba");
        request.setUrlImagen("http://example.com/pizza.jpg");
        request.setIdCategoria(1L);
        request.setIdRestaurante(1L);

        // When
        Dish dish = dishRequestMapper.addRequestToDish(request);

        // Then
        assertNull(dish.getId()); // ID siempre debe ser null para nuevos platos
        assertNotNull(dish.getNombre());
        assertNotNull(dish.getPrecio());
    }
}
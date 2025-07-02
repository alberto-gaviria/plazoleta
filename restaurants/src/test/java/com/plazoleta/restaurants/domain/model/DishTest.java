package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DishTest {

    @Test
    void testDefaultConstructor() {
        // Given & When
        Dish dish = new Dish();

        // Then
        assertNull(dish.getId());
        assertNull(dish.getNombre());
        assertNull(dish.getPrecio());
        assertNull(dish.getDescripcion());
        assertNull(dish.getUrlImagen());
        assertNull(dish.getIdCategoria());
        assertNull(dish.getIdRestaurante());
        assertTrue(dish.getActivo());
    }

    @Test
    void testConstructorWithAllParameters() {
        // Given
        Long id = 1L;
        String nombre = "Pizza Margherita";
        BigDecimal precio = new BigDecimal("25.50");
        String descripcion = "Pizza con salsa de tomate, mozzarella y albahaca";
        String urlImagen = "https://example.com/pizza.jpg";
        Long idCategoria = 2L;
        Long idRestaurante = 1L;
        Boolean activo = true;

        // When
        Dish dish = new Dish(id, nombre, precio, descripcion, urlImagen, idCategoria, idRestaurante, activo);

        // Then
        assertEquals(id, dish.getId());
        assertEquals(nombre, dish.getNombre());
        assertEquals(precio, dish.getPrecio());
        assertEquals(descripcion, dish.getDescripcion());
        assertEquals(urlImagen, dish.getUrlImagen());
        assertEquals(idCategoria, dish.getIdCategoria());
        assertEquals(idRestaurante, dish.getIdRestaurante());
        assertEquals(activo, dish.getActivo());
    }

    @Test
    void testConstructorWithNullActivoSetsTrue() {
        // Given
        Long id = 1L;
        String nombre = "Pizza Margherita";
        BigDecimal precio = new BigDecimal("25.50");
        String descripcion = "Pizza con salsa de tomate, mozzarella y albahaca";
        String urlImagen = "https://example.com/pizza.jpg";
        Long idCategoria = 2L;
        Long idRestaurante = 1L;
        Boolean activo = null;

        // When
        Dish dish = new Dish(id, nombre, precio, descripcion, urlImagen, idCategoria, idRestaurante, activo);

        // Then
        assertTrue(dish.getActivo());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Dish dish = new Dish();
        Long id = 1L;
        String nombre = "Hamburguesa Clásica";
        BigDecimal precio = new BigDecimal("15.00");
        String descripcion = "Hamburguesa con carne, lechuga, tomate y queso";
        String urlImagen = "https://example.com/hamburguesa.jpg";
        Long idCategoria = 2L;
        Long idRestaurante = 1L;
        Boolean activo = false;

        // When
        dish.setId(id);
        dish.setNombre(nombre);
        dish.setPrecio(precio);
        dish.setDescripcion(descripcion);
        dish.setUrlImagen(urlImagen);
        dish.setIdCategoria(idCategoria);
        dish.setIdRestaurante(idRestaurante);
        dish.setActivo(activo);

        // Then
        assertEquals(id, dish.getId());
        assertEquals(nombre, dish.getNombre());
        assertEquals(precio, dish.getPrecio());
        assertEquals(descripcion, dish.getDescripcion());
        assertEquals(urlImagen, dish.getUrlImagen());
        assertEquals(idCategoria, dish.getIdCategoria());
        assertEquals(idRestaurante, dish.getIdRestaurante());
        assertEquals(activo, dish.getActivo());
    }
}
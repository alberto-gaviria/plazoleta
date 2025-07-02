package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DishEntityTest {

    @Test
    void testDefaultConstructor() {
        // Given & When
        DishEntity dishEntity = new DishEntity();

        // Then
        assertNull(dishEntity.getId());
        assertNull(dishEntity.getNombre());
        assertNull(dishEntity.getPrecio());
        assertNull(dishEntity.getDescripcion());
        assertNull(dishEntity.getUrlImagen());
        assertNull(dishEntity.getIdCategoria());
        assertNull(dishEntity.getIdRestaurante());
        assertNull(dishEntity.getActivo());
    }

    @Test
    void testAllArgsConstructor() {
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
        DishEntity dishEntity = new DishEntity(id, nombre, precio, descripcion,
                urlImagen, idCategoria, idRestaurante, activo);

        // Then
        assertEquals(id, dishEntity.getId());
        assertEquals(nombre, dishEntity.getNombre());
        assertEquals(precio, dishEntity.getPrecio());
        assertEquals(descripcion, dishEntity.getDescripcion());
        assertEquals(urlImagen, dishEntity.getUrlImagen());
        assertEquals(idCategoria, dishEntity.getIdCategoria());
        assertEquals(idRestaurante, dishEntity.getIdRestaurante());
        assertEquals(activo, dishEntity.getActivo());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        DishEntity dishEntity = new DishEntity();
        Long id = 1L;
        String nombre = "Hamburguesa Clásica";
        BigDecimal precio = new BigDecimal("15.00");
        String descripcion = "Hamburguesa con carne, lechuga, tomate y queso";
        String urlImagen = "https://example.com/hamburguesa.jpg";
        Long idCategoria = 2L;
        Long idRestaurante = 1L;
        Boolean activo = false;

        // When
        dishEntity.setId(id);
        dishEntity.setNombre(nombre);
        dishEntity.setPrecio(precio);
        dishEntity.setDescripcion(descripcion);
        dishEntity.setUrlImagen(urlImagen);
        dishEntity.setIdCategoria(idCategoria);
        dishEntity.setIdRestaurante(idRestaurante);
        dishEntity.setActivo(activo);

        // Then
        assertEquals(id, dishEntity.getId());
        assertEquals(nombre, dishEntity.getNombre());
        assertEquals(precio, dishEntity.getPrecio());
        assertEquals(descripcion, dishEntity.getDescripcion());
        assertEquals(urlImagen, dishEntity.getUrlImagen());
        assertEquals(idCategoria, dishEntity.getIdCategoria());
        assertEquals(idRestaurante, dishEntity.getIdRestaurante());
        assertEquals(activo, dishEntity.getActivo());
    }

    @Test
    void testPrePersist() {
        // Given
        DishEntity dishEntity = new DishEntity();
        dishEntity.setActivo(null);

        // When
        dishEntity.prePersist();

        // Then
        assertTrue(dishEntity.getActivo());
    }

    @Test
    void testPrePersistDoesNotOverrideExistingValue() {
        // Given
        DishEntity dishEntity = new DishEntity();
        dishEntity.setActivo(false);

        // When
        dishEntity.prePersist();

        // Then
        assertFalse(dishEntity.getActivo());
    }
}
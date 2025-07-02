package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DishResponseTest {

    @Test
    void testDefaultConstructor() {
        // Given & When
        DishResponse response = new DishResponse();

        // Then
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
        DishResponse response = new DishResponse(id, nombre, precio, descripcion,
                urlImagen, idCategoria, idRestaurante, activo);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(precio, response.getPrecio());
        assertEquals(descripcion, response.getDescripcion());
        assertEquals(urlImagen, response.getUrlImagen());
        assertEquals(idCategoria, response.getIdCategoria());
        assertEquals(idRestaurante, response.getIdRestaurante());
        assertEquals(activo, response.getActivo());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        DishResponse response = new DishResponse();
        Long id = 1L;
        String nombre = "Hamburguesa Clásica";
        BigDecimal precio = new BigDecimal("15.00");
        String descripcion = "Hamburguesa con carne, lechuga, tomate y queso";
        String urlImagen = "https://example.com/hamburguesa.jpg";
        Long idCategoria = 2L;
        Long idRestaurante = 1L;
        Boolean activo = false;

        // When
        response.setId(id);
        response.setNombre(nombre);
        response.setPrecio(precio);
        response.setDescripcion(descripcion);
        response.setUrlImagen(urlImagen);
        response.setIdCategoria(idCategoria);
        response.setIdRestaurante(idRestaurante);
        response.setActivo(activo);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(precio, response.getPrecio());
        assertEquals(descripcion, response.getDescripcion());
        assertEquals(urlImagen, response.getUrlImagen());
        assertEquals(idCategoria, response.getIdCategoria());
        assertEquals(idRestaurante, response.getIdRestaurante());
        assertEquals(activo, response.getActivo());
    }
}
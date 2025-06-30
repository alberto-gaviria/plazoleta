package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void shouldCreateRestaurantWithDefaultConstructor() {
        // When
        Restaurant restaurant = new Restaurant();

        // Then
        assertNotNull(restaurant);
        assertNull(restaurant.getId());
        assertNull(restaurant.getNombre());
        assertNull(restaurant.getNit());
        assertNull(restaurant.getDireccion());
        assertNull(restaurant.getTelefono());
        assertNull(restaurant.getUrlLogo());
        assertNull(restaurant.getIdPropietario());
    }

    @Test
    void shouldCreateRestaurantWithAllArgsConstructor() {
        // Given
        Long id = 1L;
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        Restaurant restaurant = new Restaurant(id, nombre, nit, direccion, telefono, urlLogo, idPropietario);

        // Then
        assertEquals(id, restaurant.getId());
        assertEquals(nombre, restaurant.getNombre());
        assertEquals(nit, restaurant.getNit());
        assertEquals(direccion, restaurant.getDireccion());
        assertEquals(telefono, restaurant.getTelefono());
        assertEquals(urlLogo, restaurant.getUrlLogo());
        assertEquals(idPropietario, restaurant.getIdPropietario());
    }

    @Test
    void shouldSetAndGetId() {
        // Given
        Restaurant restaurant = new Restaurant();
        Long id = 1L;

        // When
        restaurant.setId(id);

        // Then
        assertEquals(id, restaurant.getId());
    }

    @Test
    void shouldSetAndGetNombre() {
        // Given
        Restaurant restaurant = new Restaurant();
        String nombre = "Restaurante Test";

        // When
        restaurant.setNombre(nombre);

        // Then
        assertEquals(nombre, restaurant.getNombre());
    }

    @Test
    void shouldSetAndGetNit() {
        // Given
        Restaurant restaurant = new Restaurant();
        String nit = "123456789";

        // When
        restaurant.setNit(nit);

        // Then
        assertEquals(nit, restaurant.getNit());
    }

    @Test
    void shouldSetAndGetDireccion() {
        // Given
        Restaurant restaurant = new Restaurant();
        String direccion = "Calle 123";

        // When
        restaurant.setDireccion(direccion);

        // Then
        assertEquals(direccion, restaurant.getDireccion());
    }

    @Test
    void shouldSetAndGetTelefono() {
        // Given
        Restaurant restaurant = new Restaurant();
        String telefono = "3001234567";

        // When
        restaurant.setTelefono(telefono);

        // Then
        assertEquals(telefono, restaurant.getTelefono());
    }

    @Test
    void shouldSetAndGetUrlLogo() {
        // Given
        Restaurant restaurant = new Restaurant();
        String urlLogo = "http://logo.com";

        // When
        restaurant.setUrlLogo(urlLogo);

        // Then
        assertEquals(urlLogo, restaurant.getUrlLogo());
    }

    @Test
    void shouldSetAndGetIdPropietario() {
        // Given
        Restaurant restaurant = new Restaurant();
        Long idPropietario = 1L;

        // When
        restaurant.setIdPropietario(idPropietario);

        // Then
        assertEquals(idPropietario, restaurant.getIdPropietario());
    }

    @Test
    void shouldSetAndGetAllFields() {
        // Given
        Restaurant restaurant = new Restaurant();
        Long id = 1L;
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        restaurant.setId(id);
        restaurant.setNombre(nombre);
        restaurant.setNit(nit);
        restaurant.setDireccion(direccion);
        restaurant.setTelefono(telefono);
        restaurant.setUrlLogo(urlLogo);
        restaurant.setIdPropietario(idPropietario);

        // Then
        assertEquals(id, restaurant.getId());
        assertEquals(nombre, restaurant.getNombre());
        assertEquals(nit, restaurant.getNit());
        assertEquals(direccion, restaurant.getDireccion());
        assertEquals(telefono, restaurant.getTelefono());
        assertEquals(urlLogo, restaurant.getUrlLogo());
        assertEquals(idPropietario, restaurant.getIdPropietario());
    }
}
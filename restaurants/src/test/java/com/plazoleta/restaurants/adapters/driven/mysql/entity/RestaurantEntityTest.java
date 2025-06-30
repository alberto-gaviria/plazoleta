package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantEntityTest {

    @Test
    void shouldCreateRestaurantEntityWithDefaultConstructor() {
        // When
        RestaurantEntity restaurantEntity = new RestaurantEntity();

        // Then
        assertNotNull(restaurantEntity);
        assertNull(restaurantEntity.getId());
        assertNull(restaurantEntity.getNombre());
        assertNull(restaurantEntity.getNit());
        assertNull(restaurantEntity.getDireccion());
        assertNull(restaurantEntity.getTelefono());
        assertNull(restaurantEntity.getUrlLogo());
        assertNull(restaurantEntity.getIdPropietario());
    }

    @Test
    void shouldCreateRestaurantEntityWithAllArgsConstructor() {
        // Given
        Long id = 1L;
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        RestaurantEntity restaurantEntity = new RestaurantEntity(id, nombre, nit, direccion, telefono, urlLogo, idPropietario);

        // Then
        assertEquals(id, restaurantEntity.getId());
        assertEquals(nombre, restaurantEntity.getNombre());
        assertEquals(nit, restaurantEntity.getNit());
        assertEquals(direccion, restaurantEntity.getDireccion());
        assertEquals(telefono, restaurantEntity.getTelefono());
        assertEquals(urlLogo, restaurantEntity.getUrlLogo());
        assertEquals(idPropietario, restaurantEntity.getIdPropietario());
    }

    @Test
    void shouldSetAndGetAllFields() {
        // Given
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        Long id = 1L;
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        restaurantEntity.setId(id);
        restaurantEntity.setNombre(nombre);
        restaurantEntity.setNit(nit);
        restaurantEntity.setDireccion(direccion);
        restaurantEntity.setTelefono(telefono);
        restaurantEntity.setUrlLogo(urlLogo);
        restaurantEntity.setIdPropietario(idPropietario);

        // Then
        assertEquals(id, restaurantEntity.getId());
        assertEquals(nombre, restaurantEntity.getNombre());
        assertEquals(nit, restaurantEntity.getNit());
        assertEquals(direccion, restaurantEntity.getDireccion());
        assertEquals(telefono, restaurantEntity.getTelefono());
        assertEquals(urlLogo, restaurantEntity.getUrlLogo());
        assertEquals(idPropietario, restaurantEntity.getIdPropietario());
    }
}
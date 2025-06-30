package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantResponseTest {

    @Test
    void shouldCreateRestaurantResponseWithDefaultConstructor() {
        // When
        RestaurantResponse response = new RestaurantResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getNombre());
        assertNull(response.getNit());
        assertNull(response.getDireccion());
        assertNull(response.getTelefono());
        assertNull(response.getUrlLogo());
        assertNull(response.getIdPropietario());
    }

    @Test
    void shouldCreateRestaurantResponseWithAllArgsConstructor() {
        // Given
        Long id = 1L;
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        RestaurantResponse response = new RestaurantResponse(id, nombre, nit, direccion, telefono, urlLogo, idPropietario);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(nit, response.getNit());
        assertEquals(direccion, response.getDireccion());
        assertEquals(telefono, response.getTelefono());
        assertEquals(urlLogo, response.getUrlLogo());
        assertEquals(idPropietario, response.getIdPropietario());
    }

    @Test
    void shouldSetAndGetAllFields() {
        // Given
        RestaurantResponse response = new RestaurantResponse();
        Long id = 1L;
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        response.setId(id);
        response.setNombre(nombre);
        response.setNit(nit);
        response.setDireccion(direccion);
        response.setTelefono(telefono);
        response.setUrlLogo(urlLogo);
        response.setIdPropietario(idPropietario);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(nit, response.getNit());
        assertEquals(direccion, response.getDireccion());
        assertEquals(telefono, response.getTelefono());
        assertEquals(urlLogo, response.getUrlLogo());
        assertEquals(idPropietario, response.getIdPropietario());
    }
}
package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddRestaurantRequestTest {

    @Test
    void shouldCreateAddRestaurantRequestWithDefaultConstructor() {
        // When
        AddRestaurantRequest request = new AddRestaurantRequest();

        // Then
        assertNotNull(request);
        assertNull(request.getNombre());
        assertNull(request.getNit());
        assertNull(request.getDireccion());
        assertNull(request.getTelefono());
        assertNull(request.getUrlLogo());
        assertNull(request.getIdPropietario());
    }

    @Test
    void shouldCreateAddRestaurantRequestWithAllArgsConstructor() {
        // Given
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        AddRestaurantRequest request = new AddRestaurantRequest(nombre, nit, direccion, telefono, urlLogo, idPropietario);

        // Then
        assertEquals(nombre, request.getNombre());
        assertEquals(nit, request.getNit());
        assertEquals(direccion, request.getDireccion());
        assertEquals(telefono, request.getTelefono());
        assertEquals(urlLogo, request.getUrlLogo());
        assertEquals(idPropietario, request.getIdPropietario());
    }

    @Test
    void shouldSetAndGetAllFields() {
        // Given
        AddRestaurantRequest request = new AddRestaurantRequest();
        String nombre = "Restaurante Test";
        String nit = "123456789";
        String direccion = "Calle 123";
        String telefono = "3001234567";
        String urlLogo = "http://logo.com";
        Long idPropietario = 1L;

        // When
        request.setNombre(nombre);
        request.setNit(nit);
        request.setDireccion(direccion);
        request.setTelefono(telefono);
        request.setUrlLogo(urlLogo);
        request.setIdPropietario(idPropietario);

        // Then
        assertEquals(nombre, request.getNombre());
        assertEquals(nit, request.getNit());
        assertEquals(direccion, request.getDireccion());
        assertEquals(telefono, request.getTelefono());
        assertEquals(urlLogo, request.getUrlLogo());
        assertEquals(idPropietario, request.getIdPropietario());
    }
}
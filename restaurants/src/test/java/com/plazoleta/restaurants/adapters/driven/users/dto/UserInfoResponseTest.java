package com.plazoleta.restaurants.adapters.driven.users.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoResponseTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Given
        Long id = 1L;
        String nombre = "Laura";
        String apellido = "Gómez";
        String email = "laura.gomez@correo.com";

        // When
        UserInfoResponse response = new UserInfoResponse(id, nombre, apellido, email);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(apellido, response.getApellido());
        assertEquals(email, response.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        UserInfoResponse response = new UserInfoResponse();

        Long id = 5L;
        String nombre = "Carlos";
        String apellido = "Rodríguez";
        String email = "carlos.rodriguez@correo.com";

        // When
        response.setId(id);
        response.setNombre(nombre);
        response.setApellido(apellido);
        response.setEmail(email);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(apellido, response.getApellido());
        assertEquals(email, response.getEmail());
    }
}

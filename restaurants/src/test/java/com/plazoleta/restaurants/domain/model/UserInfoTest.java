package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        // Given
        Long id = 1L;
        String nombre = "Pedro";
        String apellido = "Ramírez";
        String email = "pedro@example.com";

        // When
        UserInfo user = new UserInfo(id, nombre, apellido, email);

        // Then
        assertEquals(id, user.getId());
        assertEquals(nombre, user.getNombre());
        assertEquals(apellido, user.getApellido());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        UserInfo user = new UserInfo();

        // When
        user.setId(5L);
        user.setNombre("Laura");
        user.setApellido("Gómez");
        user.setEmail("laura@correo.com");

        // Then
        assertEquals(5L, user.getId());
        assertEquals("Laura", user.getNombre());
        assertEquals("Gómez", user.getApellido());
        assertEquals("laura@correo.com", user.getEmail());
    }

    @Test
    void testGetNombreCompleto_whenNombreYApellidoPresentes() {
        // Given
        UserInfo user = new UserInfo();
        user.setNombre("Ana");
        user.setApellido("Torres");
        user.setEmail("ana@correo.com");

        // When
        String result = user.getNombreCompleto();

        // Then
        assertEquals("Ana Torres", result);
    }

    @Test
    void testGetNombreCompleto_whenNombreIsNull() {
        // Given
        UserInfo user = new UserInfo();
        user.setNombre(null);
        user.setApellido("Ríos");
        user.setEmail("rios@correo.com");

        // When
        String result = user.getNombreCompleto();

        // Then
        assertEquals("rios@correo.com", result);
    }

    @Test
    void testGetNombreCompleto_whenApellidoIsNull() {
        // Given
        UserInfo user = new UserInfo();
        user.setNombre("Luis");
        user.setApellido(null);
        user.setEmail("luis@correo.com");

        // When
        String result = user.getNombreCompleto();

        // Then
        assertEquals("luis@correo.com", result);
    }

    @Test
    void testGetNombreCompleto_whenAmbosNull() {
        // Given
        UserInfo user = new UserInfo();
        user.setNombre(null);
        user.setApellido(null);
        user.setEmail("ninguno@correo.com");

        // When
        String result = user.getNombreCompleto();

        // Then
        assertEquals("ninguno@correo.com", result);
    }
}

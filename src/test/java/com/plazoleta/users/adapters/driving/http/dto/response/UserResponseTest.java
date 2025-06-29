package com.plazoleta.users.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void testUsuarioResponseCreation() {
        // Given
        Long id = 1L;
        String nombre = "Juan";
        String apellido = "Pérez";
        String numeroDocumento = "12345678";
        String celular = "+573001234567";
        LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
        String correo = "juan@email.com";
        Long idRol = 2L;

        // When
        UserResponse response = new UserResponse(id, nombre, apellido, numeroDocumento,
                celular, fechaNacimiento, correo, idRol);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(apellido, response.getApellido());
        assertEquals(numeroDocumento, response.getNumeroDocumento());
        assertEquals(celular, response.getCelular());
        assertEquals(fechaNacimiento, response.getFechaNacimiento());
        assertEquals(correo, response.getCorreo());
        assertEquals(idRol, response.getIdRol());
    }

    @Test
    void testUsuarioResponseNoArgsConstructor() {
        // When
        UserResponse response = new UserResponse();

        // Then
        assertNull(response.getId());
        assertNull(response.getNombre());
        assertNull(response.getApellido());
        assertNull(response.getNumeroDocumento());
        assertNull(response.getCelular());
        assertNull(response.getFechaNacimiento());
        assertNull(response.getCorreo());
        assertNull(response.getIdRol());
    }

    @Test
    void testUsuarioResponseSettersAndGetters() {
        // Given
        UserResponse response = new UserResponse();

        // When
        response.setId(1L);
        response.setNombre("Juan");
        response.setApellido("Pérez");
        response.setNumeroDocumento("12345678");
        response.setCelular("+573001234567");
        response.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        response.setCorreo("juan@email.com");
        response.setIdRol(2L);

        // Then
        assertEquals(1L, response.getId());
        assertEquals("Juan", response.getNombre());
        assertEquals("Pérez", response.getApellido());
        assertEquals("12345678", response.getNumeroDocumento());
        assertEquals("+573001234567", response.getCelular());
        assertEquals(LocalDate.of(1990, 1, 1), response.getFechaNacimiento());
        assertEquals("juan@email.com", response.getCorreo());
        assertEquals(2L, response.getIdRol());
    }
}
package com.plazoleta.restaurants.adapters.driven.feign.dto.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
    }

    @Test
    void shouldCreateUserResponseWithDefaultConstructor() {
        // When
        UserResponse response = new UserResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getNombre());
        assertNull(response.getApellido());
        assertNull(response.getNumeroDocumento());
        assertNull(response.getCelular());
        assertNull(response.getFechaNacimiento());
        assertNull(response.getCorreo());
        assertNull(response.getIdRol());
        assertNull(response.getRolNombre());
    }

    @Test
    void shouldSetAndGetId() {
        // Given
        Long expectedId = 1L;

        // When
        userResponse.setId(expectedId);

        // Then
        assertEquals(expectedId, userResponse.getId());
    }

    @Test
    void shouldSetAndGetNombre() {
        // Given
        String expectedNombre = "Juan";

        // When
        userResponse.setNombre(expectedNombre);

        // Then
        assertEquals(expectedNombre, userResponse.getNombre());
    }

    @Test
    void shouldSetAndGetApellido() {
        // Given
        String expectedApellido = "Pérez";

        // When
        userResponse.setApellido(expectedApellido);

        // Then
        assertEquals(expectedApellido, userResponse.getApellido());
    }

    @Test
    void shouldSetAndGetNumeroDocumento() {
        // Given
        String expectedDocumento = "12345678";

        // When
        userResponse.setNumeroDocumento(expectedDocumento);

        // Then
        assertEquals(expectedDocumento, userResponse.getNumeroDocumento());
    }

    @Test
    void shouldSetAndGetCelular() {
        // Given
        String expectedCelular = "3001234567";

        // When
        userResponse.setCelular(expectedCelular);

        // Then
        assertEquals(expectedCelular, userResponse.getCelular());
    }

    @Test
    void shouldSetAndGetFechaNacimiento() {
        // Given
        LocalDate expectedFecha = LocalDate.of(1990, 5, 15);

        // When
        userResponse.setFechaNacimiento(expectedFecha);

        // Then
        assertEquals(expectedFecha, userResponse.getFechaNacimiento());
    }

    @Test
    void shouldSetAndGetCorreo() {
        // Given
        String expectedCorreo = "juan.perez@example.com";

        // When
        userResponse.setCorreo(expectedCorreo);

        // Then
        assertEquals(expectedCorreo, userResponse.getCorreo());
    }

    @Test
    void shouldSetAndGetIdRol() {
        // Given
        Long expectedIdRol = 2L;

        // When
        userResponse.setIdRol(expectedIdRol);

        // Then
        assertEquals(expectedIdRol, userResponse.getIdRol());
    }

    @Test
    void shouldSetAndGetRolNombre() {
        // Given
        String expectedRolNombre = "PROPIETARIO";

        // When
        userResponse.setRolNombre(expectedRolNombre);

        // Then
        assertEquals(expectedRolNombre, userResponse.getRolNombre());
    }

    @Test
    void shouldAllowNullValues() {
        // When
        userResponse.setId(null);
        userResponse.setNombre(null);
        userResponse.setApellido(null);
        userResponse.setNumeroDocumento(null);
        userResponse.setCelular(null);
        userResponse.setFechaNacimiento(null);
        userResponse.setCorreo(null);
        userResponse.setIdRol(null);
        userResponse.setRolNombre(null);

        // Then
        assertNull(userResponse.getId());
        assertNull(userResponse.getNombre());
        assertNull(userResponse.getApellido());
        assertNull(userResponse.getNumeroDocumento());
        assertNull(userResponse.getCelular());
        assertNull(userResponse.getFechaNacimiento());
        assertNull(userResponse.getCorreo());
        assertNull(userResponse.getIdRol());
        assertNull(userResponse.getRolNombre());
    }

    @Test
    void shouldSetCompleteUserData() {
        // Given
        Long id = 1L;
        String nombre = "Juan";
        String apellido = "Pérez";
        String documento = "12345678";
        String celular = "3001234567";
        LocalDate fechaNacimiento = LocalDate.of(1990, 5, 15);
        String correo = "juan.perez@example.com";
        Long idRol = 2L;
        String rolNombre = "PROPIETARIO";

        // When
        userResponse.setId(id);
        userResponse.setNombre(nombre);
        userResponse.setApellido(apellido);
        userResponse.setNumeroDocumento(documento);
        userResponse.setCelular(celular);
        userResponse.setFechaNacimiento(fechaNacimiento);
        userResponse.setCorreo(correo);
        userResponse.setIdRol(idRol);
        userResponse.setRolNombre(rolNombre);

        // Then
        assertEquals(id, userResponse.getId());
        assertEquals(nombre, userResponse.getNombre());
        assertEquals(apellido, userResponse.getApellido());
        assertEquals(documento, userResponse.getNumeroDocumento());
        assertEquals(celular, userResponse.getCelular());
        assertEquals(fechaNacimiento, userResponse.getFechaNacimiento());
        assertEquals(correo, userResponse.getCorreo());
        assertEquals(idRol, userResponse.getIdRol());
        assertEquals(rolNombre, userResponse.getRolNombre());
    }
}
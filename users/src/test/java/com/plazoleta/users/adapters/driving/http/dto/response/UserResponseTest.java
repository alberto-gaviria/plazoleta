package com.plazoleta.users.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {

    @Test
    void testUserResponseCreationWithAllArgs() {
        // Given
        Long id = 1L;
        String nombre = "Juan";
        String apellido = "Pérez";
        String numeroDocumento = "12345678";
        String celular = "+573001234567";
        LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
        String correo = "juan@email.com";
        Long idRol = 2L;
        String rolNombre = "PROPIETARIO";

        // When - Usando constructor con todos los argumentos (9 parámetros)
        UserResponse response = new UserResponse(id, nombre, apellido, numeroDocumento,
                celular, fechaNacimiento, correo, idRol, rolNombre);

        // Then
        assertEquals(id, response.getId());
        assertEquals(nombre, response.getNombre());
        assertEquals(apellido, response.getApellido());
        assertEquals(numeroDocumento, response.getNumeroDocumento());
        assertEquals(celular, response.getCelular());
        assertEquals(fechaNacimiento, response.getFechaNacimiento());
        assertEquals(correo, response.getCorreo());
        assertEquals(idRol, response.getIdRol());
        assertEquals(rolNombre, response.getRolNombre());
    }

    @Test
    void testUserResponseNoArgsConstructor() {
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
        assertNull(response.getRolNombre());
    }

    @Test
    void testUserResponseSettersAndGetters() {
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
        response.setRolNombre("PROPIETARIO");

        // Then
        assertEquals(1L, response.getId());
        assertEquals("Juan", response.getNombre());
        assertEquals("Pérez", response.getApellido());
        assertEquals("12345678", response.getNumeroDocumento());
        assertEquals("+573001234567", response.getCelular());
        assertEquals(LocalDate.of(1990, 1, 1), response.getFechaNacimiento());
        assertEquals("juan@email.com", response.getCorreo());
        assertEquals(2L, response.getIdRol());
        assertEquals("PROPIETARIO", response.getRolNombre());
    }

    @Test
    void testUserResponseWithNullValues() {
        // Given
        UserResponse response = new UserResponse();

        // When - Estableciendo valores null explícitamente
        response.setId(null);
        response.setNombre(null);
        response.setApellido(null);
        response.setNumeroDocumento(null);
        response.setCelular(null);
        response.setFechaNacimiento(null);
        response.setCorreo(null);
        response.setIdRol(null);
        response.setRolNombre(null);

        // Then
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
    void testUserResponseFieldModification() {
        // Given
        UserResponse response = new UserResponse();
        response.setNombre("Juan");
        response.setApellido("Pérez");

        // When - Modificando valores
        response.setNombre("Carlos");
        response.setApellido("González");

        // Then
        assertEquals("Carlos", response.getNombre());
        assertEquals("González", response.getApellido());
    }

    @Test
    void testUserResponseWithEmptyStrings() {
        // Given
        UserResponse response = new UserResponse();

        // When
        response.setNombre("");
        response.setApellido("");
        response.setNumeroDocumento("");
        response.setCelular("");
        response.setCorreo("");
        response.setRolNombre("");

        // Then
        assertEquals("", response.getNombre());
        assertEquals("", response.getApellido());
        assertEquals("", response.getNumeroDocumento());
        assertEquals("", response.getCelular());
        assertEquals("", response.getCorreo());
        assertEquals("", response.getRolNombre());
    }

    @Test
    void testUserResponseEquality() {
        // Given
        UserResponse response1 = new UserResponse();
        response1.setId(1L);
        response1.setNombre("Juan");
        response1.setRolNombre("PROPIETARIO");

        UserResponse response2 = new UserResponse();
        response2.setId(1L);
        response2.setNombre("Juan");
        response2.setRolNombre("PROPIETARIO");

        // When & Then
        assertEquals(response1.getId(), response2.getId());
        assertEquals(response1.getNombre(), response2.getNombre());
        assertEquals(response1.getRolNombre(), response2.getRolNombre());
    }

    @Test
    void testUserResponseWithDifferentRoles() {
        // Given
        UserResponse adminResponse = new UserResponse();
        adminResponse.setIdRol(1L);
        adminResponse.setRolNombre("ADMINISTRADOR");

        UserResponse propietarioResponse = new UserResponse();
        propietarioResponse.setIdRol(2L);
        propietarioResponse.setRolNombre("PROPIETARIO");

        UserResponse empleadoResponse = new UserResponse();
        empleadoResponse.setIdRol(3L);
        empleadoResponse.setRolNombre("EMPLEADO");

        UserResponse clienteResponse = new UserResponse();
        clienteResponse.setIdRol(4L);
        clienteResponse.setRolNombre("CLIENTE");

        // When & Then
        assertEquals(1L, adminResponse.getIdRol());
        assertEquals("ADMINISTRADOR", adminResponse.getRolNombre());

        assertEquals(2L, propietarioResponse.getIdRol());
        assertEquals("PROPIETARIO", propietarioResponse.getRolNombre());

        assertEquals(3L, empleadoResponse.getIdRol());
        assertEquals("EMPLEADO", empleadoResponse.getRolNombre());

        assertEquals(4L, clienteResponse.getIdRol());
        assertEquals("CLIENTE", clienteResponse.getRolNombre());
    }

    @Test
    void testUserResponseWithCompleteUserData() {
        // Given & When
        UserResponse response = new UserResponse(
                100L,
                "Ana",
                "Martínez",
                "98765432",
                "+573009876543",
                LocalDate.of(1985, 12, 15),
                "ana.martinez@email.com",
                1L,
                "ADMINISTRADOR"
        );

        // Then
        assertEquals(100L, response.getId());
        assertEquals("Ana", response.getNombre());
        assertEquals("Martínez", response.getApellido());
        assertEquals("98765432", response.getNumeroDocumento());
        assertEquals("+573009876543", response.getCelular());
        assertEquals(LocalDate.of(1985, 12, 15), response.getFechaNacimiento());
        assertEquals("ana.martinez@email.com", response.getCorreo());
        assertEquals(1L, response.getIdRol());
        assertEquals("ADMINISTRADOR", response.getRolNombre());
    }
}
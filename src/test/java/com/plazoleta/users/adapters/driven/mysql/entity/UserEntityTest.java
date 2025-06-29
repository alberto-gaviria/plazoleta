package com.plazoleta.users.adapters.driven.mysql.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class UserEntityTest {

    @Test
    void testUsuarioEntityCreation() {
        // Given
        Long id = 1L;
        String nombre = "Juan";
        String apellido = "Pérez";
        String numeroDocumento = "12345678";
        String celular = "+573001234567";
        LocalDate fechaNacimiento = LocalDate.of(1990, 1, 1);
        String correo = "juan@email.com";
        String clave = "password123";
        Long idRol = 2L;

        // When
        UserEntity userEntity = new UserEntity(id, nombre, apellido, numeroDocumento,
                celular, fechaNacimiento, correo, clave, idRol);

        // Then
        assertEquals(id, userEntity.getId());
        assertEquals(nombre, userEntity.getNombre());
        assertEquals(apellido, userEntity.getApellido());
        assertEquals(numeroDocumento, userEntity.getNumeroDocumento());
        assertEquals(celular, userEntity.getCelular());
        assertEquals(fechaNacimiento, userEntity.getFechaNacimiento());
        assertEquals(correo, userEntity.getCorreo());
        assertEquals(clave, userEntity.getClave());
        assertEquals(idRol, userEntity.getIdRol());
    }

    @Test
    void testUsuarioEntityNoArgsConstructor() {
        // When
        UserEntity userEntity = new UserEntity();

        // Then
        assertNull(userEntity.getId());
        assertNull(userEntity.getNombre());
        assertNull(userEntity.getApellido());
        assertNull(userEntity.getNumeroDocumento());
        assertNull(userEntity.getCelular());
        assertNull(userEntity.getFechaNacimiento());
        assertNull(userEntity.getCorreo());
        assertNull(userEntity.getClave());
        assertNull(userEntity.getIdRol());
    }

    @Test
    void testUsuarioEntityFieldsAreNotNull() {
        // Given & When
        UserEntity userEntity = new UserEntity(
                1L, "Juan", "Pérez", "12345678",
                "+573001234567", LocalDate.of(1990, 1, 1),
                "juan@email.com", "password123", 2L
        );

        // Then
        assertNotNull(userEntity.getId());
        assertNotNull(userEntity.getNombre());
        assertNotNull(userEntity.getApellido());
        assertNotNull(userEntity.getNumeroDocumento());
        assertNotNull(userEntity.getCelular());
        assertNotNull(userEntity.getFechaNacimiento());
        assertNotNull(userEntity.getCorreo());
        assertNotNull(userEntity.getClave());
        assertNotNull(userEntity.getIdRol());
    }
}
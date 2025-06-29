package com.plazoleta.users.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUsuarioCreation() {
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
        User user = new User(id, nombre, apellido, numeroDocumento,
                celular, fechaNacimiento, correo, clave, idRol);

        // Then
        assertEquals(id, user.getId());
        assertEquals(nombre, user.getNombre());
        assertEquals(apellido, user.getApellido());
        assertEquals(numeroDocumento, user.getNumeroDocumento());
        assertEquals(celular, user.getCelular());
        assertEquals(fechaNacimiento, user.getFechaNacimiento());
        assertEquals(correo, user.getCorreo());
        assertEquals(clave, user.getClave());
        assertEquals(idRol, user.getIdRol());
    }

    @Test
    void testUsuarioNoArgsConstructor() {
        // When
        User user = new User();

        // Then
        assertNull(user.getId());
        assertNull(user.getNombre());
        assertNull(user.getApellido());
        assertNull(user.getNumeroDocumento());
        assertNull(user.getCelular());
        assertNull(user.getFechaNacimiento());
        assertNull(user.getCorreo());
        assertNull(user.getClave());
        assertNull(user.getIdRol());
    }

    @Test
    void testUsuarioSettersAndGetters() {
        // Given
        User user = new User();

        // When
        user.setId(1L);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan@email.com");
        user.setClave("password123");
        user.setIdRol(2L);

        // Then
        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getNombre());
        assertEquals("Pérez", user.getApellido());
        assertEquals("12345678", user.getNumeroDocumento());
        assertEquals("+573001234567", user.getCelular());
        assertEquals(LocalDate.of(1990, 1, 1), user.getFechaNacimiento());
        assertEquals("juan@email.com", user.getCorreo());
        assertEquals("password123", user.getClave());
        assertEquals(2L, user.getIdRol());
    }

    @Test
    void getRoleType_WhenValidIdRol_ShouldReturnCorrectRoleType() {
        // Given
        User user = new User();
        user.setIdRol(2L);

        // When
        RoleType roleType = user.getRoleType();

        // Then
        assertEquals(RoleType.PROPIETARIO, roleType);
    }

    @Test
    void getRoleType_WhenInvalidIdRol_ShouldThrowIllegalArgumentException() {
        // Given
        User user = new User();
        user.setIdRol(999L);

        // When & Then
        assertThrows(IllegalArgumentException.class, user::getRoleType);
    }

    @Test
    void getRoleType_WhenIdRolIsNull_ShouldReturnNull() {
        // Given
        User user = new User();
        user.setIdRol(null);

        // When
        RoleType roleType = user.getRoleType();

        // Then
        assertNull(roleType);
    }

    @Test
    void setRoleType_WhenValidRoleType_ShouldSetCorrectIdRol() {
        // Given
        User user = new User();

        // When
        user.setRoleType(RoleType.PROPIETARIO);

        // Then
        assertEquals(2L, user.getIdRol());
    }

    @Test
    void setRoleType_WhenNullRoleType_ShouldSetIdRolToNull() {
        // Given
        User user = new User();
        user.setIdRol(2L); // Asignar primero un valor

        // When
        user.setRoleType(null);

        // Then
        assertNull(user.getIdRol());
    }

    @Test
    void setRoleType_WithDifferentRoles_ShouldSetCorrectIds() {
        // Given
        User user = new User();

        // When & Then
        user.setRoleType(RoleType.ADMINISTRADOR);
        assertEquals(1L, user.getIdRol());

        user.setRoleType(RoleType.EMPLEADO);
        assertEquals(3L, user.getIdRol());

        user.setRoleType(RoleType.CLIENTE);
        assertEquals(4L, user.getIdRol());
    }

    @Test
    void hasRole_WhenUserHasRole_ShouldReturnTrue() {
        // Given
        User user = new User();
        user.setIdRol(2L);

        // When & Then
        assertTrue(user.hasRole(RoleType.PROPIETARIO));
    }

    @Test
    void hasRole_WhenUserDoesNotHaveRole_ShouldReturnFalse() {
        // Given
        User user = new User();
        user.setIdRol(2L);

        // When & Then
        assertFalse(user.hasRole(RoleType.ADMINISTRADOR));
        assertFalse(user.hasRole(RoleType.EMPLEADO));
        assertFalse(user.hasRole(RoleType.CLIENTE));
    }

    @Test
    void hasRole_WhenIdRolIsNull_ShouldReturnFalse() {
        // Given
        User user = new User();
        user.setIdRol(null);

        // When & Then
        assertFalse(user.hasRole(RoleType.PROPIETARIO));
    }

    @Test
    void hasRole_WhenRoleTypeIsNull_ShouldReturnFalse() {
        // Given
        User user = new User();
        user.setIdRol(2L);

        // When & Then
        assertFalse(user.hasRole(null));
    }

    @Test
    void setFechaNacimiento_WhenNull_ShouldAcceptNull() {
        // Given
        User user = new User();

        // When & Then - NO debe lanzar excepción
        assertDoesNotThrow(() -> user.setFechaNacimiento(null));
        assertNull(user.getFechaNacimiento());
    }
}
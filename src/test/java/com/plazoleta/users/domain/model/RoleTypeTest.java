package com.plazoleta.users.domain.model;

import com.plazoleta.users.domain.util.DomainConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTypeTest {

    @Test
    void getRoleType_WhenValidRoleType_ShouldReturnCorrectId() {
        // Given & When & Then
        assertEquals(DomainConstants.Role.ADMINISTRADOR_ID, RoleType.ADMINISTRADOR.getId());
        assertEquals(DomainConstants.Role.PROPIETARIO_ID, RoleType.PROPIETARIO.getId());
        assertEquals(DomainConstants.Role.EMPLEADO_ID, RoleType.EMPLEADO.getId());
        assertEquals(DomainConstants.Role.CLIENTE_ID, RoleType.CLIENTE.getId());
    }

    @Test
    void getAuthority_WhenValidRoleType_ShouldReturnCorrectAuthority() {
        // Given & When & Then
        assertEquals(DomainConstants.Role.ADMINISTRADOR_AUTHORITY, RoleType.ADMINISTRADOR.getAuthority());
        assertEquals(DomainConstants.Role.PROPIETARIO_AUTHORITY, RoleType.PROPIETARIO.getAuthority());
        assertEquals(DomainConstants.Role.EMPLEADO_AUTHORITY, RoleType.EMPLEADO.getAuthority());
        assertEquals(DomainConstants.Role.CLIENTE_AUTHORITY, RoleType.CLIENTE.getAuthority());
    }

    @Test
    void fromId_WhenValidId_ShouldReturnCorrectRoleType() {
        // Given & When & Then
        assertEquals(RoleType.ADMINISTRADOR, RoleType.fromId(1L));
        assertEquals(RoleType.PROPIETARIO, RoleType.fromId(2L));
        assertEquals(RoleType.EMPLEADO, RoleType.fromId(3L));
        assertEquals(RoleType.CLIENTE, RoleType.fromId(4L));
    }

    @Test
    void fromId_WhenInvalidId_ShouldThrowIllegalArgumentException() {
        // Given
        Long invalidId = 999L;

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> RoleType.fromId(invalidId)
        );

        assertTrue(exception.getMessage().contains("Rol no válido con ID: " + invalidId));
    }

    @Test
    void fromId_WhenNullId_ShouldThrowIllegalArgumentException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> RoleType.fromId(null));
    }

    @Test
    void values_ShouldReturnAllRoleTypes() {
        // When
        RoleType[] roles = RoleType.values();

        // Then
        assertEquals(4, roles.length);
        assertEquals(RoleType.ADMINISTRADOR, roles[0]);
        assertEquals(RoleType.PROPIETARIO, roles[1]);
        assertEquals(RoleType.EMPLEADO, roles[2]);
        assertEquals(RoleType.CLIENTE, roles[3]);
    }
}
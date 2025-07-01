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

        // Verificar que la excepción contiene información sobre el ID inválido
        assertNotNull(exception.getMessage());
        // En lugar de asumir el mensaje exacto, verificamos que contenga el ID
        assertTrue(exception.getMessage().contains(invalidId.toString()));
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

    @Test
    void getRoleType_ShouldReturnUniqueIds() {
        // Given
        RoleType[] roles = RoleType.values();

        // When & Then - Verificar que todos los IDs son únicos
        assertEquals(1L, RoleType.ADMINISTRADOR.getId());
        assertEquals(2L, RoleType.PROPIETARIO.getId());
        assertEquals(3L, RoleType.EMPLEADO.getId());
        assertEquals(4L, RoleType.CLIENTE.getId());

        // Verificar que no hay duplicados
        assertNotEquals(RoleType.ADMINISTRADOR.getId(), RoleType.PROPIETARIO.getId());
        assertNotEquals(RoleType.ADMINISTRADOR.getId(), RoleType.EMPLEADO.getId());
        assertNotEquals(RoleType.ADMINISTRADOR.getId(), RoleType.CLIENTE.getId());
        assertNotEquals(RoleType.PROPIETARIO.getId(), RoleType.EMPLEADO.getId());
        assertNotEquals(RoleType.PROPIETARIO.getId(), RoleType.CLIENTE.getId());
        assertNotEquals(RoleType.EMPLEADO.getId(), RoleType.CLIENTE.getId());
    }

    @Test
    void getAuthority_ShouldReturnUniqueAuthorities() {
        // When & Then
        assertEquals("ADMINISTRADOR", RoleType.ADMINISTRADOR.getAuthority());
        assertEquals("PROPIETARIO", RoleType.PROPIETARIO.getAuthority());
        assertEquals("EMPLEADO", RoleType.EMPLEADO.getAuthority());
        assertEquals("CLIENTE", RoleType.CLIENTE.getAuthority());

        // Verificar que no hay duplicados
        assertNotEquals(RoleType.ADMINISTRADOR.getAuthority(), RoleType.PROPIETARIO.getAuthority());
        assertNotEquals(RoleType.ADMINISTRADOR.getAuthority(), RoleType.EMPLEADO.getAuthority());
        assertNotEquals(RoleType.ADMINISTRADOR.getAuthority(), RoleType.CLIENTE.getAuthority());
        assertNotEquals(RoleType.PROPIETARIO.getAuthority(), RoleType.EMPLEADO.getAuthority());
        assertNotEquals(RoleType.PROPIETARIO.getAuthority(), RoleType.CLIENTE.getAuthority());
        assertNotEquals(RoleType.EMPLEADO.getAuthority(), RoleType.CLIENTE.getAuthority());
    }

    @Test
    void fromId_WithBoundaryValues_ShouldWork() {
        // Test valores límite
        assertEquals(RoleType.ADMINISTRADOR, RoleType.fromId(1L));
        assertEquals(RoleType.CLIENTE, RoleType.fromId(4L));

        // Test valores fuera del rango
        assertThrows(IllegalArgumentException.class, () -> RoleType.fromId(0L));
        assertThrows(IllegalArgumentException.class, () -> RoleType.fromId(5L));
        assertThrows(IllegalArgumentException.class, () -> RoleType.fromId(-1L));
    }

    @Test
    void fromId_WithMultipleInvalidIds_ShouldThrowException() {
        // Test varios IDs inválidos
        Long[] invalidIds = {0L, 5L, 10L, -1L, 100L};

        for (Long invalidId : invalidIds) {
            assertThrows(IllegalArgumentException.class,
                    () -> RoleType.fromId(invalidId),
                    "Should throw exception for invalid ID: " + invalidId);
        }
    }

    @Test
    void enum_ShouldHaveCorrectOrdinalValues() {
        // When & Then
        assertEquals(0, RoleType.ADMINISTRADOR.ordinal());
        assertEquals(1, RoleType.PROPIETARIO.ordinal());
        assertEquals(2, RoleType.EMPLEADO.ordinal());
        assertEquals(3, RoleType.CLIENTE.ordinal());
    }

    @Test
    void enum_ShouldHaveCorrectStringRepresentation() {
        // When & Then
        assertEquals("ADMINISTRADOR", RoleType.ADMINISTRADOR.name());
        assertEquals("PROPIETARIO", RoleType.PROPIETARIO.name());
        assertEquals("EMPLEADO", RoleType.EMPLEADO.name());
        assertEquals("CLIENTE", RoleType.CLIENTE.name());
    }
}
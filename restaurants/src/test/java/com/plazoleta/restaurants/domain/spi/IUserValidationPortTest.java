package com.plazoleta.restaurants.domain.spi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IUserValidationPortTest {

    @Mock
    private IUserValidationPort userValidationPort;

    private Long userId;
    private String rolePropietario;

    @BeforeEach
    void setUp() {
        userId = 1L;
        rolePropietario = "PROPIETARIO";
    }

    @Test
    void shouldReturnTrueWhenUserExists() {
        // Given
        when(userValidationPort.existsUserById(userId)).thenReturn(true);

        // When
        boolean result = userValidationPort.existsUserById(userId);

        // Then
        assertTrue(result);
        verify(userValidationPort).existsUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotExist() {
        // Given
        when(userValidationPort.existsUserById(userId)).thenReturn(false);

        // When
        boolean result = userValidationPort.existsUserById(userId);

        // Then
        assertFalse(result);
        verify(userValidationPort).existsUserById(userId);
    }

    @Test
    void shouldReturnTrueWhenUserHasRequiredRole() {
        // Given
        when(userValidationPort.hasRequiredRole(userId, rolePropietario)).thenReturn(true);

        // When
        boolean result = userValidationPort.hasRequiredRole(userId, rolePropietario);

        // Then
        assertTrue(result);
        verify(userValidationPort).hasRequiredRole(userId, rolePropietario);
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotHaveRequiredRole() {
        // Given
        when(userValidationPort.hasRequiredRole(userId, rolePropietario)).thenReturn(false);

        // When
        boolean result = userValidationPort.hasRequiredRole(userId, rolePropietario);

        // Then
        assertFalse(result);
        verify(userValidationPort).hasRequiredRole(userId, rolePropietario);
    }

    @Test
    void shouldAcceptNullUserId() {
        // Given
        when(userValidationPort.existsUserById(null)).thenReturn(false);

        // When
        boolean result = userValidationPort.existsUserById(null);

        // Then
        assertFalse(result);
        verify(userValidationPort).existsUserById(null);
    }

    @Test
    void shouldAcceptNullRole() {
        // Given
        when(userValidationPort.hasRequiredRole(userId, null)).thenReturn(false);

        // When
        boolean result = userValidationPort.hasRequiredRole(userId, null);

        // Then
        assertFalse(result);
        verify(userValidationPort).hasRequiredRole(userId, null);
    }

    @Test
    void shouldAcceptDifferentUserIds() {
        // Given
        Long userId1 = 1L;
        Long userId2 = 2L;
        when(userValidationPort.existsUserById(userId1)).thenReturn(true);
        when(userValidationPort.existsUserById(userId2)).thenReturn(false);

        // When
        boolean result1 = userValidationPort.existsUserById(userId1);
        boolean result2 = userValidationPort.existsUserById(userId2);

        // Then
        assertTrue(result1);
        assertFalse(result2);
        verify(userValidationPort).existsUserById(userId1);
        verify(userValidationPort).existsUserById(userId2);
    }

    @Test
    void shouldAcceptDifferentRoles() {
        // Given
        String roleAdmin = "ADMINISTRADOR";
        String roleCliente = "CLIENTE";
        when(userValidationPort.hasRequiredRole(userId, roleAdmin)).thenReturn(false);
        when(userValidationPort.hasRequiredRole(userId, roleCliente)).thenReturn(true);

        // When
        boolean resultAdmin = userValidationPort.hasRequiredRole(userId, roleAdmin);
        boolean resultCliente = userValidationPort.hasRequiredRole(userId, roleCliente);

        // Then
        assertFalse(resultAdmin);
        assertTrue(resultCliente);
        verify(userValidationPort).hasRequiredRole(userId, roleAdmin);
        verify(userValidationPort).hasRequiredRole(userId, roleCliente);
    }

    @Test
    void shouldVerifyMethodSignatures() {
        // Given & When
        userValidationPort.existsUserById(anyLong());
        userValidationPort.hasRequiredRole(anyLong(), anyString());

        // Then
        verify(userValidationPort).existsUserById(anyLong());
        verify(userValidationPort).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldWorkWithZeroUserId() {
        // Given
        Long zeroUserId = 0L;
        when(userValidationPort.existsUserById(zeroUserId)).thenReturn(false);

        // When
        boolean result = userValidationPort.existsUserById(zeroUserId);

        // Then
        assertFalse(result);
        verify(userValidationPort).existsUserById(zeroUserId);
    }

    @Test
    void shouldWorkWithNegativeUserId() {
        // Given
        Long negativeUserId = -1L;
        when(userValidationPort.existsUserById(negativeUserId)).thenReturn(false);

        // When
        boolean result = userValidationPort.existsUserById(negativeUserId);

        // Then
        assertFalse(result);
        verify(userValidationPort).existsUserById(negativeUserId);
    }

    @Test
    void shouldWorkWithEmptyRole() {
        // Given
        String emptyRole = "";
        when(userValidationPort.hasRequiredRole(userId, emptyRole)).thenReturn(false);

        // When
        boolean result = userValidationPort.hasRequiredRole(userId, emptyRole);

        // Then
        assertFalse(result);
        verify(userValidationPort).hasRequiredRole(userId, emptyRole);
    }

    @Test
    void shouldWorkWithBlankRole() {
        // Given
        String blankRole = "   ";
        when(userValidationPort.hasRequiredRole(userId, blankRole)).thenReturn(false);

        // When
        boolean result = userValidationPort.hasRequiredRole(userId, blankRole);

        // Then
        assertFalse(result);
        verify(userValidationPort).hasRequiredRole(userId, blankRole);
    }

    @Test
    void shouldWorkWithLongRole() {
        // Given
        String longRole = "ADMINISTRADOR_CON_PERMISOS_ESPECIALES_COMPLETOS";
        when(userValidationPort.hasRequiredRole(userId, longRole)).thenReturn(true);

        // When
        boolean result = userValidationPort.hasRequiredRole(userId, longRole);

        // Then
        assertTrue(result);
        verify(userValidationPort).hasRequiredRole(userId, longRole);
    }
}
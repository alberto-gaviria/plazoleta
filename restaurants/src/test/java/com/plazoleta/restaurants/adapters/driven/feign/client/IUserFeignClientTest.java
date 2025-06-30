package com.plazoleta.restaurants.adapters.driven.feign.client;

import com.plazoleta.restaurants.adapters.driven.feign.dto.response.UserResponse;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IUserFeignClientTest {

    @Mock
    private IUserFeignClient userFeignClient;

    private UserResponse userResponse;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setNombre("Juan");
        userResponse.setApellido("Pérez");
        userResponse.setNumeroDocumento("12345678");
        userResponse.setCelular("3001234567");
        userResponse.setFechaNacimiento(LocalDate.of(1990, 5, 15));
        userResponse.setCorreo("juan.perez@example.com");
        userResponse.setIdRol(2L);
        userResponse.setRolNombre("PROPIETARIO");
    }

    @Test
    void shouldReturnUserResponseWhenUserExists() {
        // Given
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        UserResponse result = userFeignClient.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Juan", result.getNombre());
        assertEquals("Pérez", result.getApellido());
        assertEquals("12345678", result.getNumeroDocumento());
        assertEquals("3001234567", result.getCelular());
        assertEquals(LocalDate.of(1990, 5, 15), result.getFechaNacimiento());
        assertEquals("juan.perez@example.com", result.getCorreo());
        assertEquals(2L, result.getIdRol());
        assertEquals("PROPIETARIO", result.getRolNombre());
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnNullWhenUserNotFound() {
        // Given
        when(userFeignClient.getUserById(userId)).thenReturn(null);

        // When
        UserResponse result = userFeignClient.getUserById(userId);

        // Then
        assertNull(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldThrowFeignExceptionWhenServiceUnavailable() {
        // Given
        FeignException feignException = mock(FeignException.class);
        when(userFeignClient.getUserById(userId)).thenThrow(feignException);

        // When & Then
        assertThrows(FeignException.class, () -> userFeignClient.getUserById(userId));
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserDoesNotExist() {
        // Given
        FeignException.NotFound notFoundException = mock(FeignException.NotFound.class);
        when(userFeignClient.getUserById(userId)).thenThrow(notFoundException);

        // When & Then
        assertThrows(FeignException.NotFound.class, () -> userFeignClient.getUserById(userId));
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldAcceptNullUserId() {
        // Given
        when(userFeignClient.getUserById(null)).thenReturn(null);

        // When
        UserResponse result = userFeignClient.getUserById(null);

        // Then
        assertNull(result);
        verify(userFeignClient).getUserById(null);
    }

    @Test
    void shouldAcceptZeroUserId() {
        // Given
        Long zeroUserId = 0L;
        when(userFeignClient.getUserById(zeroUserId)).thenReturn(null);

        // When
        UserResponse result = userFeignClient.getUserById(zeroUserId);

        // Then
        assertNull(result);
        verify(userFeignClient).getUserById(zeroUserId);
    }

    @Test
    void shouldAcceptNegativeUserId() {
        // Given
        Long negativeUserId = -1L;
        when(userFeignClient.getUserById(negativeUserId)).thenReturn(null);

        // When
        UserResponse result = userFeignClient.getUserById(negativeUserId);

        // Then
        assertNull(result);
        verify(userFeignClient).getUserById(negativeUserId);
    }

    @Test
    void shouldReturnDifferentUsersForDifferentIds() {
        // Given
        Long userId1 = 1L;
        Long userId2 = 2L;

        UserResponse user1 = new UserResponse();
        user1.setId(userId1);
        user1.setNombre("Juan");
        user1.setRolNombre("PROPIETARIO");

        UserResponse user2 = new UserResponse();
        user2.setId(userId2);
        user2.setNombre("María");
        user2.setRolNombre("ADMINISTRADOR");

        when(userFeignClient.getUserById(userId1)).thenReturn(user1);
        when(userFeignClient.getUserById(userId2)).thenReturn(user2);

        // When
        UserResponse result1 = userFeignClient.getUserById(userId1);
        UserResponse result2 = userFeignClient.getUserById(userId2);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(userId1, result1.getId());
        assertEquals(userId2, result2.getId());
        assertEquals("Juan", result1.getNombre());
        assertEquals("María", result2.getNombre());
        assertEquals("PROPIETARIO", result1.getRolNombre());
        assertEquals("ADMINISTRADOR", result2.getRolNombre());
        verify(userFeignClient).getUserById(userId1);
        verify(userFeignClient).getUserById(userId2);
    }

    @Test
    void shouldReturnUserWithMinimalData() {
        // Given
        UserResponse minimalUser = new UserResponse();
        minimalUser.setId(userId);
        when(userFeignClient.getUserById(userId)).thenReturn(minimalUser);

        // When
        UserResponse result = userFeignClient.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertNull(result.getNombre());
        assertNull(result.getApellido());
        assertNull(result.getNumeroDocumento());
        assertNull(result.getCelular());
        assertNull(result.getFechaNacimiento());
        assertNull(result.getCorreo());
        assertNull(result.getIdRol());
        assertNull(result.getRolNombre());
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnUserWithAllRoleTypes() {
        // Given
        String[] roles = {"ADMINISTRADOR", "PROPIETARIO", "EMPLEADO", "CLIENTE"};

        for (int i = 0; i < roles.length; i++) {
            Long currentUserId = (long) (i + 1);
            UserResponse user = new UserResponse();
            user.setId(currentUserId);
            user.setRolNombre(roles[i]);
            when(userFeignClient.getUserById(currentUserId)).thenReturn(user);

            // When
            UserResponse result = userFeignClient.getUserById(currentUserId);

            // Then
            assertNotNull(result);
            assertEquals(currentUserId, result.getId());
            assertEquals(roles[i], result.getRolNombre());
        }
    }

    @Test
    void shouldVerifyMethodSignature() {
        // Given & When
        userFeignClient.getUserById(anyLong());

        // Then
        verify(userFeignClient).getUserById(anyLong());
    }

    @Test
    void shouldHandleMultipleCallsToSameUser() {
        // Given
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        UserResponse result1 = userFeignClient.getUserById(userId);
        UserResponse result2 = userFeignClient.getUserById(userId);
        UserResponse result3 = userFeignClient.getUserById(userId);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);
        assertEquals(result1.getId(), result2.getId());
        assertEquals(result2.getId(), result3.getId());
        verify(userFeignClient, times(3)).getUserById(userId);
    }

    @Test
    void shouldHandleUserWithCompleteData() {
        // Given
        UserResponse completeUser = new UserResponse();
        completeUser.setId(userId);
        completeUser.setNombre("Ana");
        completeUser.setApellido("García");
        completeUser.setNumeroDocumento("87654321");
        completeUser.setCelular("3109876543");
        completeUser.setFechaNacimiento(LocalDate.of(1985, 12, 25));
        completeUser.setCorreo("ana.garcia@empresa.com");
        completeUser.setIdRol(1L);
        completeUser.setRolNombre("ADMINISTRADOR");

        when(userFeignClient.getUserById(userId)).thenReturn(completeUser);

        // When
        UserResponse result = userFeignClient.getUserById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Ana", result.getNombre());
        assertEquals("García", result.getApellido());
        assertEquals("87654321", result.getNumeroDocumento());
        assertEquals("3109876543", result.getCelular());
        assertEquals(LocalDate.of(1985, 12, 25), result.getFechaNacimiento());
        assertEquals("ana.garcia@empresa.com", result.getCorreo());
        assertEquals(1L, result.getIdRol());
        assertEquals("ADMINISTRADOR", result.getRolNombre());
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldHandleLargeUserId() {
        // Given
        Long largeUserId = Long.MAX_VALUE;
        when(userFeignClient.getUserById(largeUserId)).thenReturn(null);

        // When
        UserResponse result = userFeignClient.getUserById(largeUserId);

        // Then
        assertNull(result);
        verify(userFeignClient).getUserById(largeUserId);
    }
}
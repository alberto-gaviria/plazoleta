package com.plazoleta.restaurants.adapters.driven.feign.adapter;

import com.plazoleta.restaurants.adapters.driven.feign.client.IUserFeignClient;
import com.plazoleta.restaurants.adapters.driven.feign.dto.response.UserResponse;
import com.plazoleta.restaurants.adapters.driven.feign.exception.FeignUserException;
import com.plazoleta.restaurants.adapters.driven.feign.util.UserAdapterConstants;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserValidationAdapterTest {

    @Mock
    private IUserFeignClient userFeignClient;

    @InjectMocks
    private UserValidationAdapter userValidationAdapter;

    private UserResponse userResponse;
    private Long userId;

    @BeforeEach
    void setUp() {
        userId = 1L;
        userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setNombre("Juan");
        userResponse.setApellido("Pérez");
        userResponse.setRolNombre("PROPIETARIO");
    }

    @Test
    void shouldReturnTrueWhenUserExists() {
        // Given
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        boolean result = userValidationAdapter.existsUserById(userId);

        // Then
        assertTrue(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenUserNotFound() {
        // Given
        when(userFeignClient.getUserById(userId)).thenThrow(FeignException.NotFound.class);

        // When
        boolean result = userValidationAdapter.existsUserById(userId);

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenUserResponseIsNull() {
        // Given
        when(userFeignClient.getUserById(userId)).thenReturn(null);

        // When
        boolean result = userValidationAdapter.existsUserById(userId);

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenUserIdInResponseIsNull() {
        // Given
        userResponse.setId(null);
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        boolean result = userValidationAdapter.existsUserById(userId);

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldThrowFeignUserExceptionWhenFeignExceptionOccurs() {
        // Given
        FeignException feignException = mock(FeignException.class);
        when(feignException.getMessage()).thenReturn("Service unavailable");
        when(userFeignClient.getUserById(userId)).thenThrow(feignException);

        // When & Then
        FeignUserException exception = assertThrows(FeignUserException.class,
                () -> userValidationAdapter.existsUserById(userId));

        assertTrue(exception.getMessage().contains(UserAdapterConstants.ErrorMessages.ERROR_OBTENIENDO_USUARIO));
        assertTrue(exception.getMessage().contains(userId.toString()));
        assertTrue(exception.getMessage().contains("Service unavailable"));
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldThrowFeignUserExceptionWhenGenericExceptionOccurs() {
        // Given
        RuntimeException genericException = new RuntimeException("Generic error");
        when(userFeignClient.getUserById(userId)).thenThrow(genericException);

        // When & Then
        FeignUserException exception = assertThrows(FeignUserException.class,
                () -> userValidationAdapter.existsUserById(userId));

        assertTrue(exception.getMessage().contains(UserAdapterConstants.ErrorMessages.ERROR_COMUNICACION_USERS_SERVICE));
        assertTrue(exception.getMessage().contains("Generic error"));
        assertEquals(genericException, exception.getCause());
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnTrueWhenUserHasRequiredRole() {
        // Given
        String requiredRole = "PROPIETARIO";
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        boolean result = userValidationAdapter.hasRequiredRole(userId, requiredRole);

        // Then
        assertTrue(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotHaveRequiredRole() {
        // Given
        String requiredRole = "ADMINISTRADOR";
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        boolean result = userValidationAdapter.hasRequiredRole(userId, requiredRole);

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenUserNotFoundInHasRequiredRole() {
        // Given
        String requiredRole = "PROPIETARIO";
        when(userFeignClient.getUserById(userId)).thenThrow(FeignException.NotFound.class);

        // When
        boolean result = userValidationAdapter.hasRequiredRole(userId, requiredRole);

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenUserResponseIsNullInHasRequiredRole() {
        // Given
        String requiredRole = "PROPIETARIO";
        when(userFeignClient.getUserById(userId)).thenReturn(null);

        // When
        boolean result = userValidationAdapter.hasRequiredRole(userId, requiredRole);

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldReturnFalseWhenRolNombreIsNullInResponse() {
        // Given
        String requiredRole = "PROPIETARIO";
        userResponse.setRolNombre(null);
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        boolean result = userValidationAdapter.hasRequiredRole(userId, requiredRole);

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldThrowFeignUserExceptionWhenFeignExceptionOccursInHasRequiredRole() {
        // Given
        String requiredRole = "PROPIETARIO";
        FeignException feignException = mock(FeignException.class);
        when(feignException.getMessage()).thenReturn("Service unavailable");
        when(userFeignClient.getUserById(userId)).thenThrow(feignException);

        // When & Then
        FeignUserException exception = assertThrows(FeignUserException.class,
                () -> userValidationAdapter.hasRequiredRole(userId, requiredRole));

        assertTrue(exception.getMessage().contains(UserAdapterConstants.ErrorMessages.ERROR_VALIDANDO_ROL));
        assertTrue(exception.getMessage().contains(userId.toString()));
        assertTrue(exception.getMessage().contains("Service unavailable"));
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldThrowFeignUserExceptionWhenGenericExceptionOccursInHasRequiredRole() {
        // Given
        String requiredRole = "PROPIETARIO";
        RuntimeException genericException = new RuntimeException("Generic error");
        when(userFeignClient.getUserById(userId)).thenThrow(genericException);

        // When & Then
        FeignUserException exception = assertThrows(FeignUserException.class,
                () -> userValidationAdapter.hasRequiredRole(userId, requiredRole));

        assertTrue(exception.getMessage().contains(UserAdapterConstants.ErrorMessages.ERROR_COMUNICACION_USERS_SERVICE));
        assertTrue(exception.getMessage().contains("Generic error"));
        assertEquals(genericException, exception.getCause());
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldThrowExceptionWhenRequiredRoleIsNull() {
        // Given
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When & Then
        FeignUserException exception = assertThrows(FeignUserException.class,
                () -> userValidationAdapter.hasRequiredRole(userId, null));

        assertTrue(exception.getMessage().contains(UserAdapterConstants.ErrorMessages.ERROR_COMUNICACION_USERS_SERVICE));
        assertTrue(exception.getMessage().contains("Cannot invoke \"String.equals(Object)\" because \"requiredRole\" is null"));
        assertInstanceOf(NullPointerException.class, exception.getCause());
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldHandleEmptyRequiredRole() {
        // Given
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        boolean result = userValidationAdapter.hasRequiredRole(userId, "");

        // Then
        assertFalse(result);
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldBeCaseSensitiveForRoleComparison() {
        // Given
        String requiredRole = "propietario"; // lowercase
        when(userFeignClient.getUserById(userId)).thenReturn(userResponse);

        // When
        boolean result = userValidationAdapter.hasRequiredRole(userId, requiredRole);

        // Then
        assertFalse(result); // Should be false because it's case sensitive
        verify(userFeignClient).getUserById(userId);
    }

    @Test
    void shouldCreateAdapterWithConstructor() {
        // When
        UserValidationAdapter adapter = new UserValidationAdapter(userFeignClient);

        // Then
        assertNotNull(adapter);
    }

    @Test
    void shouldHandleAllDefinedRoles() {
        // Given
        userResponse.setRolNombre("ADMINISTRADOR");
        when(userFeignClient.getUserById(anyLong())).thenReturn(userResponse);

        // When & Then
        assertTrue(userValidationAdapter.hasRequiredRole(userId, "ADMINISTRADOR"));
        assertFalse(userValidationAdapter.hasRequiredRole(userId, "PROPIETARIO"));
        assertFalse(userValidationAdapter.hasRequiredRole(userId, "EMPLEADO"));
        assertFalse(userValidationAdapter.hasRequiredRole(userId, "CLIENTE"));
    }
}
package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.util.DomainConstants;
import com.plazoleta.users.domain.util.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserQueryUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @InjectMocks
    private UserQueryUseCase userQueryUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setRoleType(RoleType.CLIENTE);
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Given
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        // When
        User result = userQueryUseCase.getUserById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userPersistencePort).findById(1L);
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldThrowException() {
        // Given
        when(userPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userQueryUseCase.getUserById(99L));

        assertTrue(exception.getMessage().contains(DomainConstants.Usuario.ERROR_USUARIO_NO_ENCONTRADO));
        verify(userPersistencePort).findById(99L);
    }

    @Test
    void validateUserRole_WhenRoleMatches_ShouldReturnTrue() {
        // Given
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        // When
        boolean isValid = userQueryUseCase.validateUserRole(1L, "CLIENTE");

        // Then
        assertTrue(isValid);
        verify(userPersistencePort).findById(1L);
    }

    @Test
    void validateUserRole_WhenRoleDoesNotMatch_ShouldReturnFalse() {
        // Given
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        // When
        boolean isValid = userQueryUseCase.validateUserRole(1L, "ADMIN");

        // Then
        assertFalse(isValid);
        verify(userPersistencePort).findById(1L);
    }

    @Test
    void validateUserRole_WhenUserRoleIsNull_ShouldReturnFalse() {
        // Given
        user.setRoleType(null);
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        // When
        boolean isValid = userQueryUseCase.validateUserRole(1L, "CLIENTE");

        // Then
        assertFalse(isValid);
        verify(userPersistencePort).findById(1L);
    }
}

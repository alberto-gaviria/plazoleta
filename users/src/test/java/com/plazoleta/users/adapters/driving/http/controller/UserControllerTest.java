package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.util.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private IUserQueryServicePort userQueryServicePort;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @Mock
    private Authentication authentication;

    private User testUser;
    private UserResponse testUserResponse;

    @BeforeEach
    void setUp() {
        testUser = new User();

        testUserResponse = new UserResponse();
        testUserResponse.setId(1L);
        testUserResponse.setNombre("John");
        testUserResponse.setApellido("Doe");
        testUserResponse.setCorreo("john.doe@email.com");
        testUserResponse.setCelular("+573001234567");
        testUserResponse.setNumeroDocumento("12345678");
        testUserResponse.setFechaNacimiento(LocalDate.parse("1990-01-01"));
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnOkWithUserResponse() {
        // Given
        Long userId = 1L;
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(testUserResponse);

        // When
        ResponseEntity<UserResponse> result = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(testUserResponse, result.getBody());
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getUserById_WhenUserNotFound_ShouldThrowException() {
        // Given
        Long userId = 999L;
        when(userQueryServicePort.getUserById(userId))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            userController.getUserById(userId);
        });

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getUserById_WhenDifferentUser_ShouldReturnCorrectUser() {
        // Given
        Long userId = 2L;
        UserResponse differentUserResponse = new UserResponse();
        differentUserResponse.setId(2L);
        differentUserResponse.setNombre("Jane");
        differentUserResponse.setApellido("Smith");
        differentUserResponse.setCorreo("jane.smith@email.com");

        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(differentUserResponse);

        // When
        ResponseEntity<UserResponse> result = userController.getUserById(userId);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(differentUserResponse, result.getBody());
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getCurrentUser_WhenAuthenticatedUser_ShouldReturnOkWithUserResponse() {
        // Given
        Long userId = 1L;
        when(authentication.getName()).thenReturn(userId.toString());
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(testUserResponse);

        // When
        ResponseEntity<UserResponse> result = userController.getCurrentUser(authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(testUserResponse, result.getBody());
        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getCurrentUser_WhenUserNotFound_ShouldThrowException() {
        // Given
        Long userId = 999L;
        when(authentication.getName()).thenReturn(userId.toString());
        when(userQueryServicePort.getUserById(userId))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            userController.getCurrentUser(authentication);
        });

        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getCurrentUser_WhenInvalidUserIdInAuthentication_ShouldThrowException() {
        // Given
        when(authentication.getName()).thenReturn("invalid-id");

        // When & Then
        assertThrows(NumberFormatException.class, () -> {
            userController.getCurrentUser(authentication);
        });

        verify(authentication).getName();
        verify(userQueryServicePort, never()).getUserById(any(Long.class));
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getCurrentUser_WhenNullAuthenticationName_ShouldThrowException() {
        // Given
        when(authentication.getName()).thenReturn(null);

        // When & Then
        assertThrows(NumberFormatException.class, () -> {
            userController.getCurrentUser(authentication);
        });

        verify(authentication).getName();
        verify(userQueryServicePort, never()).getUserById(any(Long.class));
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getCurrentUser_WhenServiceThrowsRuntimeException_ShouldThrowException() {
        // Given
        Long userId = 1L;
        when(authentication.getName()).thenReturn(userId.toString());
        when(userQueryServicePort.getUserById(userId))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userController.getCurrentUser(authentication);
        });

        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getCurrentUser_WhenMapperThrowsException_ShouldThrowException() {
        // Given
        Long userId = 1L;
        when(authentication.getName()).thenReturn(userId.toString());
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser))
                .thenThrow(new RuntimeException("Mapping error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userController.getCurrentUser(authentication);
        });

        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getCurrentUser_WhenDifferentAuthenticatedUser_ShouldReturnCorrectUser() {
        // Given
        Long userId = 5L;
        UserResponse differentUserResponse = new UserResponse();
        differentUserResponse.setId(5L);
        differentUserResponse.setNombre("Maria");
        differentUserResponse.setApellido("Garcia");
        differentUserResponse.setCorreo("maria.garcia@email.com");

        when(authentication.getName()).thenReturn(userId.toString());
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(differentUserResponse);

        // When
        ResponseEntity<UserResponse> result = userController.getCurrentUser(authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(differentUserResponse, result.getBody());
        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getCurrentUser_WhenAuthenticatedUserWithAllFields_ShouldReturnCompleteResponse() {
        // Given
        Long userId = 10L;
        UserResponse completeUserResponse = new UserResponse();
        completeUserResponse.setId(10L);
        completeUserResponse.setNombre("Administrator");
        completeUserResponse.setApellido("System");
        completeUserResponse.setCorreo("admin@system.com");
        completeUserResponse.setCelular("+573001111111");
        completeUserResponse.setNumeroDocumento("11111111");
        completeUserResponse.setFechaNacimiento(LocalDate.parse("1980-01-01"));
        completeUserResponse.setIdRol(1L);
        completeUserResponse.setRolNombre("ADMINISTRADOR");

        when(authentication.getName()).thenReturn(userId.toString());
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(completeUserResponse);

        // When
        ResponseEntity<UserResponse> result = userController.getCurrentUser(authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(completeUserResponse, result.getBody());
        assertEquals(10L, result.getBody().getId());
        assertEquals("Administrator", result.getBody().getNombre());
        assertEquals("ADMINISTRADOR", result.getBody().getRolNombre());
        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getCurrentUser_WhenAuthenticationReturnsValidId_ShouldExtractIdCorrectly() {
        // Given
        Long expectedUserId = 123L;
        when(authentication.getName()).thenReturn(expectedUserId.toString());
        when(userQueryServicePort.getUserById(expectedUserId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(testUserResponse);

        // When
        ResponseEntity<UserResponse> result = userController.getCurrentUser(authentication);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(expectedUserId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getCurrentUser_WhenZeroId_ShouldCallService() {
        // Given
        Long zeroId = 0L;
        when(authentication.getName()).thenReturn(zeroId.toString());
        when(userQueryServicePort.getUserById(zeroId))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            userController.getCurrentUser(authentication);
        });

        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(zeroId);
    }

    @Test
    void getCurrentUser_WhenNegativeId_ShouldCallService() {
        // Given
        Long negativeId = -1L;
        when(authentication.getName()).thenReturn(negativeId.toString());
        when(userQueryServicePort.getUserById(negativeId))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        assertThrows(UserNotFoundException.class, () -> {
            userController.getCurrentUser(authentication);
        });

        verify(authentication).getName();
        verify(userQueryServicePort).getUserById(negativeId);
    }
}
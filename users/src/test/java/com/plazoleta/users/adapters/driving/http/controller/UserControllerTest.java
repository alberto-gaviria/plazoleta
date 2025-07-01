package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private IAdminUserManagementServicePort adminServicePort;

    @Mock
    private IUserQueryServicePort userQueryServicePort;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setCorreo("juan@email.com");
        user.setRoleType(RoleType.CLIENTE);

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setNombre("Juan");
        userResponse.setApellido("Pérez");
        userResponse.setCorreo("juan@email.com");
        userResponse.setRolNombre("CLIENTE"); // ← CORREGIDO
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUserResponse() {
        // Given
        when(userQueryServicePort.getUserById(1L)).thenReturn(user);
        when(userResponseMapper.userToDto(user)).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> response = userController.getUserById(1L);

        // Then
        assertEquals(200, response.getStatusCode().value()); // .getStatusCodeValue() está deprecated
        assertNotNull(response.getBody());
        assertEquals("Juan", response.getBody().getNombre());
        assertEquals("Pérez", response.getBody().getApellido());
        assertEquals("juan@email.com", response.getBody().getCorreo());
        assertEquals("CLIENTE", response.getBody().getRolNombre()); // ← CORREGIDO

        verify(userQueryServicePort).getUserById(1L);
        verify(userResponseMapper).userToDto(user);
    }
}


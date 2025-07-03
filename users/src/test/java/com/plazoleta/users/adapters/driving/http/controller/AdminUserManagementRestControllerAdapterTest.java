package com.plazoleta.users.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.model.RoleType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUserManagementRestControllerAdapterTest {

    @InjectMocks
    private AdminUserManagementRestControllerAdapter controller;

    @Mock
    private IAdminUserManagementServicePort usuarioServicePort;

    @Mock
    private IUserRequestMapper usuarioRequestMapper;

    private AddUserRequest addUserRequest;
    private User user;

    @BeforeEach
    void setUp() {
        addUserRequest = new AddUserRequest();
        addUserRequest.setNombre("Juan");
        addUserRequest.setApellido("Pérez");
        addUserRequest.setNumeroDocumento("12345678");
        addUserRequest.setCelular("+573001234567");
        addUserRequest.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        addUserRequest.setCorreo("juan@email.com");
        addUserRequest.setClave("password123");

        user = new User();
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan@email.com");
        user.setClave("password123");
        user.setRoleType(RoleType.PROPIETARIO);
    }

    @Test
    void createPropietario_WhenValidRequest_ShouldReturnCreated() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        doNothing().when(usuarioServicePort).savePropietario(any(User.class));

        // When
        ResponseEntity<Void> result = controller.createPropietario(addUserRequest);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(usuarioRequestMapper).addRequestToUsuario(any(AddUserRequest.class));
        verify(usuarioServicePort).savePropietario(any(User.class));
    }

    @Test
    void createPropietario_WhenMapperReturnsUser_ShouldCallServiceAndReturnCreated() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(addUserRequest)).thenReturn(user);
        doNothing().when(usuarioServicePort).savePropietario(user);

        // When
        ResponseEntity<Void> result = controller.createPropietario(addUserRequest);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(usuarioRequestMapper).addRequestToUsuario(addUserRequest);
        verify(usuarioServicePort).savePropietario(user);
    }

    @Test
    void createPropietario_WhenServiceCalled_ShouldDelegateToCorrectService() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);

        // When
        controller.createPropietario(addUserRequest);

        // Then
        verify(usuarioServicePort, times(1)).savePropietario(user);
    }

    @Test
    void createPropietario_WhenCalled_ShouldMapRequestCorrectly() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(addUserRequest)).thenReturn(user);
        doNothing().when(usuarioServicePort).savePropietario(any(User.class));

        // When
        controller.createPropietario(addUserRequest);

        // Then
        verify(usuarioRequestMapper, times(1)).addRequestToUsuario(addUserRequest);
    }

    @Test
    void createPropietario_WhenServiceExecutesSuccessfully_ShouldReturnCreatedStatus() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        doNothing().when(usuarioServicePort).savePropietario(any(User.class));

        // When
        ResponseEntity<Void> response = controller.createPropietario(addUserRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void createPropietario_WhenRequestHasAllFields_ShouldProcessSuccessfully() {
        // Given
        AddUserRequest fullRequest = new AddUserRequest();
        fullRequest.setNombre("María");
        fullRequest.setApellido("González");
        fullRequest.setNumeroDocumento("87654321");
        fullRequest.setCelular("+573009876543");
        fullRequest.setFechaNacimiento(LocalDate.of(1985, 5, 15));
        fullRequest.setCorreo("maria@email.com");
        fullRequest.setClave("securePassword");

        User mappedUser = new User();
        mappedUser.setRoleType(RoleType.PROPIETARIO);

        when(usuarioRequestMapper.addRequestToUsuario(fullRequest)).thenReturn(mappedUser);
        doNothing().when(usuarioServicePort).savePropietario(mappedUser);

        // When
        ResponseEntity<Void> result = controller.createPropietario(fullRequest);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(usuarioRequestMapper).addRequestToUsuario(fullRequest);
        verify(usuarioServicePort).savePropietario(mappedUser);
    }

    @Test
    void createPropietario_WhenControllerMethodCalled_ShouldFollowExpectedFlow() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        doNothing().when(usuarioServicePort).savePropietario(any(User.class));

        // When
        ResponseEntity<Void> result = controller.createPropietario(addUserRequest);

        // Then
        // Verify the complete flow
        verify(usuarioRequestMapper, times(1)).addRequestToUsuario(addUserRequest);
        verify(usuarioServicePort, times(1)).savePropietario(user);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(null, result.getBody());
    }

    @Test
    void createPropietario_WhenDifferentUserData_ShouldMapAndSaveCorrectly() {
        // Given
        AddUserRequest customRequest = new AddUserRequest();
        customRequest.setNombre("Carlos");
        customRequest.setApellido("Rodríguez");
        customRequest.setNumeroDocumento("11223344");
        customRequest.setCelular("+573001112233");
        customRequest.setFechaNacimiento(LocalDate.of(1992, 12, 3));
        customRequest.setCorreo("carlos@email.com");
        customRequest.setClave("myPassword");

        User customUser = new User();
        customUser.setNombre("Carlos");
        customUser.setRoleType(RoleType.PROPIETARIO);

        when(usuarioRequestMapper.addRequestToUsuario(customRequest)).thenReturn(customUser);
        doNothing().when(usuarioServicePort).savePropietario(customUser);

        // When
        ResponseEntity<Void> result = controller.createPropietario(customRequest);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(usuarioRequestMapper).addRequestToUsuario(customRequest);
        verify(usuarioServicePort).savePropietario(customUser);
    }

    @Test
    void createPropietario_WhenMockingDependencies_ShouldInteractCorrectly() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);

        // When
        controller.createPropietario(addUserRequest);

        // Then
        verify(usuarioRequestMapper).addRequestToUsuario(addUserRequest);
        verify(usuarioServicePort).savePropietario(user);
        verifyNoMoreInteractions(usuarioRequestMapper, usuarioServicePort);
    }

    @Test
    void createPropietario_WhenValidExecution_ShouldReturnCorrectResponseEntity() {
        // Given
        when(usuarioRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        doNothing().when(usuarioServicePort).savePropietario(any(User.class));

        // When
        ResponseEntity<Void> response = controller.createPropietario(addUserRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(null, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
    }
}
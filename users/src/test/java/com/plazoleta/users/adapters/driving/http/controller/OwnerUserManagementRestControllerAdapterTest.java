package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IOwnerUserManagementServicePort;
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
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerUserManagementRestControllerAdapterTest {

    @InjectMocks
    private OwnerUserManagementRestControllerAdapter controller;

    @Mock
    private IOwnerUserManagementServicePort ownerUserManagementServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @Mock
    private Authentication authentication;

    private AddUserRequest addUserRequest;
    private User user;
    private UserResponse userResponse;
    private Long propietarioId;

    @BeforeEach
    void setUp() {
        propietarioId = 1L;

        addUserRequest = new AddUserRequest();
        addUserRequest.setNombre("Juan");
        addUserRequest.setApellido("Pérez");
        addUserRequest.setNumeroDocumento("12345678");
        addUserRequest.setCelular("+573001234567");
        addUserRequest.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        addUserRequest.setCorreo("juan@email.com");
        addUserRequest.setClave("password123");

        user = new User();
        user.setId(1L);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan@email.com");
        user.setClave("password123");
        user.setRoleType(RoleType.EMPLEADO);

        userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setNombre("Juan");
        userResponse.setApellido("Pérez");
        userResponse.setIdRol(3L);
        userResponse.setRolNombre("EMPLEADO");
    }

    @Test
    void createEmpleado_WhenValidRequest_ShouldReturnCreated() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(any(User.class), anyLong())).thenReturn(user);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> result = controller.createEmpleado(addUserRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(userResponse, result.getBody());
        verify(authentication).getName();
        verify(userRequestMapper).addRequestToUsuario(any(AddUserRequest.class));
        verify(ownerUserManagementServicePort).saveEmpleado(any(User.class), anyLong());
        verify(userResponseMapper).userToDto(any(User.class));
    }

    @Test
    void createEmpleado_WhenMapperReturnsUser_ShouldCallServiceAndReturnCreated() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(addUserRequest)).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(user, propietarioId)).thenReturn(user);
        when(userResponseMapper.userToDto(user)).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> result = controller.createEmpleado(addUserRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
        verify(authentication).getName();
        verify(userRequestMapper).addRequestToUsuario(addUserRequest);
        verify(ownerUserManagementServicePort).saveEmpleado(user, propietarioId);
        verify(userResponseMapper).userToDto(user);
    }

    @Test
    void createEmpleado_WhenServiceCalled_ShouldDelegateToCorrectService() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(user, propietarioId)).thenReturn(user);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When
        controller.createEmpleado(addUserRequest, authentication);

        // Then
        verify(ownerUserManagementServicePort, times(1)).saveEmpleado(user, propietarioId);
    }

    @Test
    void createEmpleado_WhenCalled_ShouldMapRequestCorrectly() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(addUserRequest)).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(any(User.class), anyLong())).thenReturn(user);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When
        controller.createEmpleado(addUserRequest, authentication);

        // Then
        verify(userRequestMapper, times(1)).addRequestToUsuario(addUserRequest);
    }

    @Test
    void createEmpleado_WhenServiceExecutesSuccessfully_ShouldReturnCreatedStatus() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(any(User.class), anyLong())).thenReturn(user);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> response = controller.createEmpleado(addUserRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
    }

    @Test
    void createEmpleado_WhenRequestHasAllFields_ShouldProcessSuccessfully() {
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
        mappedUser.setRoleType(RoleType.EMPLEADO);

        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(fullRequest)).thenReturn(mappedUser);
        when(ownerUserManagementServicePort.saveEmpleado(mappedUser, propietarioId)).thenReturn(mappedUser);
        when(userResponseMapper.userToDto(mappedUser)).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> result = controller.createEmpleado(fullRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
        verify(authentication).getName();
        verify(userRequestMapper).addRequestToUsuario(fullRequest);
        verify(ownerUserManagementServicePort).saveEmpleado(mappedUser, propietarioId);
        verify(userResponseMapper).userToDto(mappedUser);
    }

    @Test
    void createEmpleado_WhenControllerMethodCalled_ShouldFollowExpectedFlow() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(any(User.class), anyLong())).thenReturn(user);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> result = controller.createEmpleado(addUserRequest, authentication);

        // Then
        // Verify the complete flow
        verify(authentication, times(1)).getName();
        verify(userRequestMapper, times(1)).addRequestToUsuario(addUserRequest);
        verify(ownerUserManagementServicePort, times(1)).saveEmpleado(user, propietarioId);
        verify(userResponseMapper, times(1)).userToDto(user);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
    }

    @Test
    void createEmpleado_WhenDifferentUserData_ShouldMapAndSaveCorrectly() {
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
        customUser.setRoleType(RoleType.EMPLEADO);

        Long customPropietarioId = 2L;

        when(authentication.getName()).thenReturn(customPropietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(customRequest)).thenReturn(customUser);
        when(ownerUserManagementServicePort.saveEmpleado(customUser, customPropietarioId)).thenReturn(customUser);
        when(userResponseMapper.userToDto(customUser)).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> result = controller.createEmpleado(customRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userResponse, result.getBody());
        verify(authentication).getName();
        verify(userRequestMapper).addRequestToUsuario(customRequest);
        verify(ownerUserManagementServicePort).saveEmpleado(customUser, customPropietarioId);
        verify(userResponseMapper).userToDto(customUser);
    }

    @Test
    void createEmpleado_WhenMockingDependencies_ShouldInteractCorrectly() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(user, propietarioId)).thenReturn(user);
        when(userResponseMapper.userToDto(user)).thenReturn(userResponse);

        // When
        controller.createEmpleado(addUserRequest, authentication);

        // Then
        verify(authentication).getName();
        verify(userRequestMapper).addRequestToUsuario(addUserRequest);
        verify(ownerUserManagementServicePort).saveEmpleado(user, propietarioId);
        verify(userResponseMapper).userToDto(user);
        verifyNoMoreInteractions(authentication, userRequestMapper, ownerUserManagementServicePort, userResponseMapper);
    }

    @Test
    void createEmpleado_WhenValidExecution_ShouldReturnCorrectResponseEntity() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(any(User.class), anyLong())).thenReturn(user);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When
        ResponseEntity<UserResponse> response = controller.createEmpleado(addUserRequest, authentication);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void createEmpleado_WhenAuthenticationReturnsValidId_ShouldExtractPropietarioIdCorrectly() {
        // Given
        Long expectedPropietarioId = 5L;
        when(authentication.getName()).thenReturn(expectedPropietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(any(User.class), anyLong())).thenReturn(user);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When
        controller.createEmpleado(addUserRequest, authentication);

        // Then
        verify(authentication).getName();
        verify(ownerUserManagementServicePort).saveEmpleado(user, expectedPropietarioId);
    }

    @Test
    void createEmpleado_WhenAllParametersValid_ShouldPassCorrectParametersToService() {
        // Given
        when(authentication.getName()).thenReturn(propietarioId.toString());
        when(userRequestMapper.addRequestToUsuario(addUserRequest)).thenReturn(user);
        when(ownerUserManagementServicePort.saveEmpleado(user, propietarioId)).thenReturn(user);
        when(userResponseMapper.userToDto(user)).thenReturn(userResponse);

        // When
        controller.createEmpleado(addUserRequest, authentication);

        // Then
        verify(ownerUserManagementServicePort).saveEmpleado(user, propietarioId);
    }
}
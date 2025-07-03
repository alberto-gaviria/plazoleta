package com.plazoleta.users.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserRequestMapper;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IClientUserManagementServicePort;
import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import com.plazoleta.users.infrastructure.configuration.exceptionhandler.ControllerAdvisor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClientUserManagementRestControllerAdapterTest {

    @Mock
    private IClientUserManagementServicePort clientUserManagementServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @InjectMocks
    private ClientUserManagementRestControllerAdapter controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createCliente_ValidRequest_ShouldReturnCreatedClientWithUserResponse() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        User userDomain = createUserDomain();
        User savedUser = createSavedUser();
        UserResponse userResponse = createUserResponse();

        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(userDomain);
        when(clientUserManagementServicePort.saveCliente(any(User.class))).thenReturn(savedUser);
        when(userResponseMapper.userToDto(any(User.class))).thenReturn(userResponse);

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Pérez"))
                .andExpect(jsonPath("$.correo").value("juan.perez@email.com"))
                .andExpect(jsonPath("$.idRol").value(4L))
                .andExpect(jsonPath("$.rolNombre").value("CLIENTE"));

        verify(userRequestMapper, times(1)).addRequestToUsuario(any(AddUserRequest.class));
        verify(clientUserManagementServicePort, times(1)).saveCliente(any(User.class));
        verify(userResponseMapper, times(1)).userToDto(any(User.class));
    }

    @Test
    void createCliente_InvalidRequest_EmptyNombre_ShouldReturnBadRequest() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        request.setNombre("");

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userRequestMapper, never()).addRequestToUsuario(any());
        verify(clientUserManagementServicePort, never()).saveCliente(any());
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_InvalidRequest_NullNombre_ShouldReturnBadRequest() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        request.setNombre(null);

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userRequestMapper, never()).addRequestToUsuario(any());
        verify(clientUserManagementServicePort, never()).saveCliente(any());
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_InvalidRequest_EmptyApellido_ShouldReturnBadRequest() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        request.setApellido("");

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userRequestMapper, never()).addRequestToUsuario(any());
        verify(clientUserManagementServicePort, never()).saveCliente(any());
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_InvalidRequest_InvalidEmail_ShouldReturnBadRequest() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        request.setCorreo("email-invalido");

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userRequestMapper, never()).addRequestToUsuario(any());
        verify(clientUserManagementServicePort, never()).saveCliente(any());
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_InvalidRequest_InvalidDocumento_ShouldReturnBadRequest() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        request.setNumeroDocumento("12345ABC");

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userRequestMapper, never()).addRequestToUsuario(any());
        verify(clientUserManagementServicePort, never()).saveCliente(any());
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_InvalidRequest_InvalidCelular_ShouldReturnBadRequest() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        request.setCelular("12345678901234567890"); // Más de 13 caracteres

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(userRequestMapper, never()).addRequestToUsuario(any());
        verify(clientUserManagementServicePort, never()).saveCliente(any());
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_ServiceThrowsException_ShouldReturnBadRequest() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        User userDomain = createUserDomain();

        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(userDomain);
        when(clientUserManagementServicePort.saveCliente(any(User.class)))
                .thenThrow(new InvalidUsuarioException("Error de validación"));

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Error de validación"));

        verify(userRequestMapper, times(1)).addRequestToUsuario(any(AddUserRequest.class));
        verify(clientUserManagementServicePort, times(1)).saveCliente(any(User.class));
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_MapperThrowsException_ShouldReturnInternalServerError() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();

        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class)))
                .thenThrow(new RuntimeException("Error de mapeo"));

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Error de mapeo"));

        verify(userRequestMapper, times(1)).addRequestToUsuario(any(AddUserRequest.class));
        verify(clientUserManagementServicePort, never()).saveCliente(any());
        verify(userResponseMapper, never()).userToDto(any());
    }

    @Test
    void createCliente_ResponseMapperThrowsException_ShouldReturnInternalServerError() throws Exception {
        // Given
        AddUserRequest request = createValidAddUserRequest();
        User userDomain = createUserDomain();
        User savedUser = createSavedUser();

        when(userRequestMapper.addRequestToUsuario(any(AddUserRequest.class))).thenReturn(userDomain);
        when(clientUserManagementServicePort.saveCliente(any(User.class))).thenReturn(savedUser);
        when(userResponseMapper.userToDto(any(User.class)))
                .thenThrow(new RuntimeException("Error de mapeo de respuesta"));

        // When & Then
        mockMvc.perform(post("/usuarios/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Error de mapeo de respuesta"));

        verify(userRequestMapper, times(1)).addRequestToUsuario(any(AddUserRequest.class));
        verify(clientUserManagementServicePort, times(1)).saveCliente(any(User.class));
        verify(userResponseMapper, times(1)).userToDto(any(User.class));
    }

    // Helper methods
    private AddUserRequest createValidAddUserRequest() {
        AddUserRequest request = new AddUserRequest();
        request.setNombre("Juan");
        request.setApellido("Pérez");
        request.setNumeroDocumento("12345678");
        request.setCelular("+573001234567");
        request.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        request.setCorreo("juan.perez@email.com");
        request.setClave("password123");
        return request;
    }

    private User createUserDomain() {
        User user = new User();
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan.perez@email.com");
        user.setClave("password123");
        return user;
    }

    private User createSavedUser() {
        User user = new User();
        user.setId(1L);
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan.perez@email.com");
        user.setClave("$2a$10$encodedPassword");
        user.setRoleType(RoleType.CLIENTE);
        return user;
    }

    private UserResponse createUserResponse() {
        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setNombre("Juan");
        response.setApellido("Pérez");
        response.setNumeroDocumento("12345678");
        response.setCelular("+573001234567");
        response.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        response.setCorreo("juan.perez@email.com");
        response.setIdRol(4L);
        response.setRolNombre("CLIENTE");
        return response;
    }
}
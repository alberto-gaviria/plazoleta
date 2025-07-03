package com.plazoleta.users.adapters.driving.http.controller;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IUserResponseMapper;
import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.ITokenServicePort;
import com.plazoleta.users.domain.util.exceptions.UserNotFoundException;
import com.plazoleta.users.infrastructure.configuration.exceptionhandler.ControllerAdvisor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(ControllerAdvisor.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserQueryServicePort userQueryServicePort;

    @MockBean
    private IUserResponseMapper userResponseMapper;

    @MockBean
    private ITokenServicePort tokenServicePort;

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
    void getUserById_WhenUserExists_ShouldReturnOkWithUserResponse() throws Exception {
        // Given
        Long userId = 1L;
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(testUserResponse);

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("John"))
                .andExpect(jsonPath("$.apellido").value("Doe"))
                .andExpect(jsonPath("$.correo").value("john.doe@email.com"))
                .andExpect(jsonPath("$.celular").value("+573001234567"))
                .andExpect(jsonPath("$.numeroDocumento").value("12345678"))
                .andExpect(jsonPath("$.fechaNacimiento").value("1990-01-01"));

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getUserById_WhenUserNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        Long userId = 999L;
        when(userQueryServicePort.getUserById(userId))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuario no encontrado"))
                .andExpect(jsonPath("$.status").value("404 NOT_FOUND"));

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getUserById_WhenInvalidIdFormat_ShouldReturnInternalServerError() throws Exception {
        // When & Then
        mockMvc.perform(get("/usuarios/{id}", "invalid-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(userQueryServicePort, never()).getUserById(anyLong());
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getUserById_WhenNegativeId_ShouldCallService() throws Exception {
        // Given
        Long negativeId = -1L;
        when(userQueryServicePort.getUserById(negativeId))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", negativeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userQueryServicePort).getUserById(negativeId);
    }

    @Test
    void getUserById_WhenZeroId_ShouldCallService() throws Exception {
        // Given
        Long zeroId = 0L;
        when(userQueryServicePort.getUserById(zeroId))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", zeroId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(userQueryServicePort).getUserById(zeroId);
    }

    @Test
    void getUserById_WhenServiceThrowsRuntimeException_ShouldReturnInternalServerError() throws Exception {
        // Given
        Long userId = 1L;
        when(userQueryServicePort.getUserById(userId))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper, never()).userToDto(any(User.class));
    }

    @Test
    void getUserById_WhenMapperThrowsException_ShouldReturnInternalServerError() throws Exception {
        // Given
        Long userId = 1L;
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser))
                .thenThrow(new RuntimeException("Mapping error"));

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getUserById_WhenLargeId_ShouldWork() throws Exception {
        // Given
        Long largeId = 999999999L;
        when(userQueryServicePort.getUserById(largeId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(testUserResponse);

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", largeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(userQueryServicePort).getUserById(largeId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getUserById_WhenDifferentUser_ShouldReturnCorrectUser() throws Exception {
        // Given
        Long userId = 2L;
        UserResponse differentUserResponse = new UserResponse();
        differentUserResponse.setId(2L);
        differentUserResponse.setNombre("Jane");
        differentUserResponse.setApellido("Smith");
        differentUserResponse.setCorreo("jane.smith@email.com");
        differentUserResponse.setCelular("+573009876543");
        differentUserResponse.setNumeroDocumento("87654321");
        differentUserResponse.setFechaNacimiento(LocalDate.parse("1995-05-15"));

        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(differentUserResponse);

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nombre").value("Jane"))
                .andExpect(jsonPath("$.apellido").value("Smith"))
                .andExpect(jsonPath("$.correo").value("jane.smith@email.com"));

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getUserById_WhenUserExistsWithAllFields_ShouldReturnCompleteResponse() throws Exception {
        // Given
        Long userId = 3L;
        UserResponse completeUserResponse = new UserResponse();
        completeUserResponse.setId(3L);
        completeUserResponse.setNombre("Carlos");
        completeUserResponse.setApellido("Rodriguez");
        completeUserResponse.setCorreo("carlos.rodriguez@email.com");
        completeUserResponse.setCelular("+573501234567");
        completeUserResponse.setNumeroDocumento("12345678");
        completeUserResponse.setFechaNacimiento(LocalDate.parse("1985-12-25"));
        completeUserResponse.setIdRol(1L);
        completeUserResponse.setRolNombre("CLIENTE");

        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(completeUserResponse);

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Carlos"))
                .andExpect(jsonPath("$.apellido").value("Rodriguez"))
                .andExpect(jsonPath("$.correo").value("carlos.rodriguez@email.com"))
                .andExpect(jsonPath("$.celular").value("+573501234567"))
                .andExpect(jsonPath("$.numeroDocumento").value("12345678"))
                .andExpect(jsonPath("$.fechaNacimiento").value("1985-12-25"))
                .andExpect(jsonPath("$.idRol").value(1))
                .andExpect(jsonPath("$.rolNombre").value("CLIENTE"));

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

    @Test
    void getUserById_WhenRequestWithoutContentType_ShouldWork() throws Exception {
        // Given
        Long userId = 1L;
        when(userQueryServicePort.getUserById(userId)).thenReturn(testUser);
        when(userResponseMapper.userToDto(testUser)).thenReturn(testUserResponse);

        // When & Then
        mockMvc.perform(get("/usuarios/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(userQueryServicePort).getUserById(userId);
        verify(userResponseMapper).userToDto(testUser);
    }

}
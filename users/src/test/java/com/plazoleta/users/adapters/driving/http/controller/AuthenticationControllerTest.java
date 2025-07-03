package com.plazoleta.users.adapters.driving.http.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plazoleta.users.adapters.driving.http.dto.request.LoginRequest;
import com.plazoleta.users.adapters.driving.http.dto.response.AuthResponse;
import com.plazoleta.users.adapters.driving.http.mapper.IAuthResponseMapper;
import com.plazoleta.users.domain.api.IAuthenticationServicePort;
import com.plazoleta.users.domain.model.Authentication;
import com.plazoleta.users.domain.spi.ITokenServicePort;
import com.plazoleta.users.domain.util.exceptions.InvalidCredentialsException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = AuthenticationController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(ControllerAdvisor.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAuthenticationServicePort authenticationServicePort;

    @MockBean
    private IAuthResponseMapper authResponseMapper;

    @MockBean
    private ITokenServicePort tokenServicePort;

    private ObjectMapper objectMapper;
    private LoginRequest loginRequest;
    private Authentication authentication;
    private AuthResponse authResponse;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@email.com");
        loginRequest.setPassword("password123");

        authentication = new Authentication();
        authentication.setUserId(1L);
        authentication.setEmail("test@email.com");
        authentication.setRole("CLIENTE");
        authentication.setToken("jwt-token");

        authResponse = new AuthResponse();
        authResponse.setToken("jwt-token");
        authResponse.setUserId(1L);
        authResponse.setEmail("test@email.com");
        authResponse.setRole("CLIENTE");
        authResponse.setType("Bearer");
    }

    @Test
    void login_WhenValidCredentials_ShouldReturnOkWithAuthResponse() throws Exception {
        // Given
        when(authenticationServicePort.authenticate(anyString(), anyString())).thenReturn(authentication);
        when(authResponseMapper.authToResponse(any(Authentication.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.email").value("test@email.com"))
                .andExpect(jsonPath("$.role").value("CLIENTE"))
                .andExpect(jsonPath("$.type").value("Bearer"));

        verify(authenticationServicePort).authenticate("test@email.com", "password123");
        verify(authResponseMapper).authToResponse(authentication);
    }

    @Test
    void login_WhenInvalidCredentials_ShouldReturnUnauthorized() throws Exception {
        // Given
        when(authenticationServicePort.authenticate(anyString(), anyString()))
                .thenThrow(new InvalidCredentialsException("Credenciales inválidas"));

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Credenciales inválidas"))
                .andExpect(jsonPath("$.status").value("401 UNAUTHORIZED"));

        verify(authenticationServicePort).authenticate("test@email.com", "password123");
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenEmailIsNull_ShouldReturnBadRequest() throws Exception {
        // Given
        loginRequest.setEmail(null);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenEmailIsEmpty_ShouldReturnBadRequest() throws Exception {
        // Given
        loginRequest.setEmail("");

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenEmailIsBlank_ShouldReturnBadRequest() throws Exception {
        // Given
        loginRequest.setEmail("   ");

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenEmailIsInvalid_ShouldReturnBadRequest() throws Exception {
        // Given
        loginRequest.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenPasswordIsNull_ShouldReturnBadRequest() throws Exception {
        // Given
        loginRequest.setPassword(null);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenPasswordIsEmpty_ShouldReturnBadRequest() throws Exception {
        // Given
        loginRequest.setPassword("");

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenPasswordIsBlank_ShouldReturnBadRequest() throws Exception {
        // Given
        loginRequest.setPassword("   ");

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenRequestBodyIsEmpty_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenInvalidJson_ShouldReturnInternalServerError() throws Exception {
        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{invalid json}"))
                .andExpect(status().isInternalServerError());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenMissingContentType_ShouldReturnInternalServerError() throws Exception {
        // When & Then
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError());

        verify(authenticationServicePort, never()).authenticate(anyString(), anyString());
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenServiceThrowsRuntimeException_ShouldReturnInternalServerError() throws Exception {
        // Given
        when(authenticationServicePort.authenticate(anyString(), anyString()))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError());

        verify(authenticationServicePort).authenticate("test@email.com", "password123");
        verify(authResponseMapper, never()).authToResponse(any(Authentication.class));
    }

    @Test
    void login_WhenMapperThrowsException_ShouldReturnInternalServerError() throws Exception {
        // Given
        when(authenticationServicePort.authenticate(anyString(), anyString())).thenReturn(authentication);
        when(authResponseMapper.authToResponse(any(Authentication.class)))
                .thenThrow(new RuntimeException("Mapping error"));

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isInternalServerError());

        verify(authenticationServicePort).authenticate("test@email.com", "password123");
        verify(authResponseMapper).authToResponse(authentication);
    }

    @Test
    void login_WhenValidCredentialsWithDifferentRole_ShouldReturnOkWithCorrectRole() throws Exception {
        // Given
        Authentication adminAuth = new Authentication();
        adminAuth.setUserId(2L);
        adminAuth.setEmail("admin@email.com");
        adminAuth.setRole("ADMINISTRADOR");
        adminAuth.setToken("admin-token");

        AuthResponse adminResponse = new AuthResponse();
        adminResponse.setToken("admin-token");
        adminResponse.setUserId(2L);
        adminResponse.setEmail("admin@email.com");
        adminResponse.setRole("ADMINISTRADOR");
        adminResponse.setType("Bearer");

        loginRequest.setEmail("admin@email.com");

        when(authenticationServicePort.authenticate(anyString(), anyString())).thenReturn(adminAuth);
        when(authResponseMapper.authToResponse(any(Authentication.class))).thenReturn(adminResponse);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ADMINISTRADOR"))
                .andExpect(jsonPath("$.userId").value(2))
                .andExpect(jsonPath("$.email").value("admin@email.com"));

        verify(authenticationServicePort).authenticate("admin@email.com", "password123");
        verify(authResponseMapper).authToResponse(adminAuth);
    }

    @Test
    void login_WhenDifferentValidEmail_ShouldWork() throws Exception {
        // Given
        loginRequest.setEmail("user@domain.co");
        when(authenticationServicePort.authenticate(anyString(), anyString())).thenReturn(authentication);
        when(authResponseMapper.authToResponse(any(Authentication.class))).thenReturn(authResponse);

        // When & Then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"));

        verify(authenticationServicePort).authenticate("user@domain.co", "password123");
        verify(authResponseMapper).authToResponse(authentication);
    }
}
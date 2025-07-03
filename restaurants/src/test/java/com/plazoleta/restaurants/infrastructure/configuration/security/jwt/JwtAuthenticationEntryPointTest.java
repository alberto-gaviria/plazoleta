package com.plazoleta.restaurants.infrastructure.configuration.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationEntryPointTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @BeforeEach
    void setUp() {
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
    }

    @Test
    void commence_ShouldSetUnauthorizedStatusAndJsonResponse() throws IOException, ServletException {
        // Given
        String servletPath = "/api/restaurants";
        jakarta.servlet.ServletOutputStream outputStream = mock(jakarta.servlet.ServletOutputStream.class);

        when(request.getServletPath()).thenReturn(servletPath);
        when(response.getOutputStream()).thenReturn(outputStream);

        // When
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Then
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(request).getServletPath();
        verify(response).getOutputStream();
    }

    @Test
    void commence_ShouldWriteCorrectJsonStructure() throws IOException, ServletException {
        // Given
        String servletPath = "/api/restaurants";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(request.getServletPath()).thenReturn(servletPath);
        when(response.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // When
        jwtAuthenticationEntryPoint.commence(request, response, authException);

        // Then
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(401);
        verify(response).getOutputStream();
    }

    @Test
    void commence_ShouldHandleNullServletPath() throws IOException, ServletException {
        // Given
        when(request.getServletPath()).thenReturn(null);
        when(response.getOutputStream()).thenReturn(mock(jakarta.servlet.ServletOutputStream.class));

        // When & Then
        assertDoesNotThrow(() -> jwtAuthenticationEntryPoint.commence(request, response, authException));
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
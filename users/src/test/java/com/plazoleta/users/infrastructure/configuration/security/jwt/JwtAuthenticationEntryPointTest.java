package com.plazoleta.users.infrastructure.configuration.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.security.core.AuthenticationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest {

    @Test
    void commence_shouldReturnUnauthorizedJsonResponse() throws Exception {
        // Arrange
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException exception = mock(AuthenticationException.class);

        when(request.getServletPath()).thenReturn("/api/test");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DelegatingServletOutputStream servletOutputStream = new DelegatingServletOutputStream(outputStream);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        // Act
        entryPoint.commence(request, response, exception);

        // Assert
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType("application/json");

        String responseBody = outputStream.toString();
        assertTrue(responseBody.contains("Acceso denegado"));
        assertTrue(responseBody.contains("\"status\":\"401\""));
        assertTrue(responseBody.contains("\"path\":\"/api/test\""));
    }
}

package com.plazoleta.traceability.infrastructure.configuration.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest {

    private JwtAuthenticationEntryPoint entryPoint;

    @BeforeEach
    void setUp() {
        entryPoint = new JwtAuthenticationEntryPoint();
    }

    @Test
    void commence_shouldWriteUnauthorizedJsonResponse() throws IOException, ServletException {
        // Mocks
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        // Configuración del request
        when(request.getServletPath()).thenReturn("/api/test");

        // Simulación del output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                // No-op para test
            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };

        // Configuración del response
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        // Ejecutar
        entryPoint.commence(request, response, authException);

        // Verificaciones
        verify(response).setContentType("application/json");
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(request).getServletPath();

        // Validación del contenido JSON
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(outputStream.toString(), Map.class);

        assertEquals("Acceso denegado - Token requerido", result.get("message"));
        assertEquals("401", result.get("status"));
        assertEquals("/api/test", result.get("path"));
        assertNotNull(result.get("timestamp"));
    }
}

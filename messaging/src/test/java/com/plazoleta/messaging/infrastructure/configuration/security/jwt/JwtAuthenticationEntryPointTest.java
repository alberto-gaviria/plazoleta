package com.plazoleta.messaging.infrastructure.configuration.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest {

    @Test
    void testCommence_ShouldReturnUnauthorizedJsonResponse() throws Exception {
        JwtAuthenticationEntryPoint entryPoint = new JwtAuthenticationEntryPoint();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(request.getServletPath()).thenReturn("/api/test");
        when(response.getOutputStream()).thenReturn(new DelegatingServletOutputStream(stringWriter));
        when(response.getWriter()).thenReturn(printWriter);

        entryPoint.commence(request, response, authException);

        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String json = stringWriter.toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<?, ?> body = mapper.readValue(json, Map.class);

        assertEquals("401", body.get("status"));
        assertEquals("Acceso denegado - Token requerido", body.get("message"));
        assertEquals("/api/test", body.get("path"));
        assertNotNull(body.get("timestamp"));
    }

    // Clase auxiliar para simular ServletOutputStream
    static class DelegatingServletOutputStream extends jakarta.servlet.ServletOutputStream {
        private final StringWriter writer;

        DelegatingServletOutputStream(StringWriter writer) {
            this.writer = writer;
        }

        @Override
        public void write(int b) {
            writer.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(jakarta.servlet.WriteListener writeListener) {
        }
    }
}

package com.plazoleta.users.infrastructure.configuration.security;

import com.plazoleta.users.domain.spi.ITokenServicePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private ITokenServicePort tokenServicePort;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        tokenServicePort = mock(ITokenServicePort.class);
        filter = new JwtAuthenticationFilter(tokenServicePort);
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_shouldAuthenticateWhenValidToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String token = "valid.token.value";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenServicePort.validateToken(token)).thenReturn(true);
        when(tokenServicePort.getUserIdFromToken(token)).thenReturn(123L);
        when(tokenServicePort.getRoleFromToken(token)).thenReturn("ROLE_USER");

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("123", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertEquals("ROLE_USER", SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority());

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldNotAuthenticateWhenNoToken() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilterInternal_shouldNotAuthenticateWhenTokenIsInvalid() throws Exception {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        String token = "invalid.token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(tokenServicePort.validateToken(token)).thenReturn(false);

        // Act
        filter.doFilterInternal(request, response, chain);

        // Assert
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }
}

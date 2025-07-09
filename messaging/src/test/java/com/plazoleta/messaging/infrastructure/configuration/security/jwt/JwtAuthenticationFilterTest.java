package com.plazoleta.messaging.infrastructure.configuration.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtTokenProvider tokenProvider;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setUp() {
        tokenProvider = mock(JwtTokenProvider.class);
        filter = new JwtAuthenticationFilter(tokenProvider);
        SecurityContextHolder.clearContext();
    }

    @Test
    void testDoFilterInternal_WithValidToken_ShouldAuthenticate() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer valid-token");
        when(tokenProvider.validateToken("valid-token")).thenReturn(true);
        when(tokenProvider.getUserIdFromToken("valid-token")).thenReturn(10L);
        when(tokenProvider.getRoleFromToken("valid-token")).thenReturn("CLIENTE");

        filter.doFilterInternal(request, response, chain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("10", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertEquals("CLIENTE", SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority());

        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithInvalidToken_ShouldNotAuthenticate() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer invalid-token");
        when(tokenProvider.validateToken("invalid-token")).thenReturn(false);

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_WithoutAuthorizationHeader() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain chain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(chain).doFilter(request, response);
    }
}

package com.plazoleta.traceability.infrastructure.configuration.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private String secret = "mysecretkeymysecretkeymysecretkey123456"; // mínimo 32 bytes
    private Key key;

    private String validToken;
    private String invalidToken = "invalid.token.value";

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secret);
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // Crear token válido para pruebas
        validToken = Jwts.builder()
                .setSubject("test@example.com")
                .claim("userId", 123L)
                .claim("role", "ROLE_USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Test
    void validateToken_shouldReturnTrueForValidToken() {
        assertTrue(jwtTokenProvider.validateToken(validToken));
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        assertFalse(jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    void getEmailFromToken_shouldReturnCorrectEmail() {
        String email = jwtTokenProvider.getEmailFromToken(validToken);
        assertEquals("test@example.com", email);
    }

    @Test
    void getUserIdFromToken_shouldReturnCorrectUserId() {
        Long userId = jwtTokenProvider.getUserIdFromToken(validToken);
        assertEquals(123L, userId);
    }

    @Test
    void getRoleFromToken_shouldReturnCorrectRole() {
        String role = jwtTokenProvider.getRoleFromToken(validToken);
        assertEquals("ROLE_USER", role);
    }
}

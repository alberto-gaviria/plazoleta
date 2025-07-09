package com.plazoleta.messaging.infrastructure.configuration.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private String secret = "MySuperSecretKeyForJwtSigningWhichIsLongEnough123456";

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secret);
    }

    private String createValidToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", 123L);
        claims.put("role", "CLIENTE");

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createInvalidToken() {
        return "invalid.token.value";
    }

    @Test
    void testValidateToken_WithValidToken_ShouldReturnTrue() {
        String token = createValidToken();
        assertTrue(jwtTokenProvider.validateToken(token));
    }

    @Test
    void testValidateToken_WithInvalidToken_ShouldReturnFalse() {
        assertFalse(jwtTokenProvider.validateToken(createInvalidToken()));
    }

    @Test
    void testGetUserIdFromToken_ShouldReturnCorrectId() {
        String token = createValidToken();
        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        assertEquals(123L, userId);
    }

    @Test
    void testGetRoleFromToken_ShouldReturnCorrectRole() {
        String token = createValidToken();
        String role = jwtTokenProvider.getRoleFromToken(token);
        assertEquals("CLIENTE", role);
    }


}

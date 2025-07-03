package com.plazoleta.restaurants.infrastructure.configuration.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private Key key;
    private String jwtSecret = "mySecretKeyForJWTTokenGenerationAndValidationThatShouldBeLongEnough";

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(jwtSecret);
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        // Given
        String validToken = createValidToken();

        // When
        boolean result = jwtTokenProvider.validateToken(validToken);

        // Then
        assertTrue(result);
    }

    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        // Given
        String invalidToken = "invalid.token.here";

        // When
        boolean result = jwtTokenProvider.validateToken(invalidToken);

        // Then
        assertFalse(result);
    }

    @Test
    void validateToken_WithExpiredToken_ShouldReturnFalse() {
        // Given
        Date pastDate = new Date(System.currentTimeMillis() - 10000); // 10 seconds ago
        String expiredToken = Jwts.builder()
                .setSubject("test@example.com")
                .claim("userId", 1L)
                .claim("role", "ROLE_USER")
                .setExpiration(pastDate)
                .signWith(key)
                .compact();

        // When
        boolean result = jwtTokenProvider.validateToken(expiredToken);

        // Then
        assertFalse(result);
    }

    @Test
    void validateToken_WithNullToken_ShouldReturnFalse() {
        // When
        boolean result = jwtTokenProvider.validateToken(null);

        // Then
        assertFalse(result);
    }

    @Test
    void validateToken_WithEmptyToken_ShouldReturnFalse() {
        // When
        boolean result = jwtTokenProvider.validateToken("");

        // Then
        assertFalse(result);
    }

    @Test
    void getEmailFromToken_WithValidToken_ShouldReturnEmail() {
        // Given
        String email = "test@example.com";
        String token = createTokenWithEmail(email);

        // When
        String result = jwtTokenProvider.getEmailFromToken(token);

        // Then
        assertEquals(email, result);
    }

    @Test
    void getUserIdFromToken_WithValidToken_ShouldReturnUserId() {
        // Given
        Long userId = 123L;
        String token = createTokenWithUserId(userId);

        // When
        Long result = jwtTokenProvider.getUserIdFromToken(token);

        // Then
        assertEquals(userId, result);
    }

    @Test
    void getRoleFromToken_WithValidToken_ShouldReturnRole() {
        // Given
        String role = "ROLE_ADMIN";
        String token = createTokenWithRole(role);

        // When
        String result = jwtTokenProvider.getRoleFromToken(token);

        // Then
        assertEquals(role, result);
    }

    @Test
    void getEmailFromToken_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(JwtException.class, () ->
                jwtTokenProvider.getEmailFromToken(invalidToken));
    }

    @Test
    void getUserIdFromToken_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(JwtException.class, () ->
                jwtTokenProvider.getUserIdFromToken(invalidToken));
    }

    @Test
    void getRoleFromToken_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(JwtException.class, () ->
                jwtTokenProvider.getRoleFromToken(invalidToken));
    }

    @Test
    void constructor_WithValidSecret_ShouldCreateInstance() {
        // When
        JwtTokenProvider provider = new JwtTokenProvider(jwtSecret);

        // Then
        assertNotNull(provider);
    }

    private String createValidToken() {
        return Jwts.builder()
                .setSubject("test@example.com")
                .claim("userId", 1L)
                .claim("role", "ROLE_USER")
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(key)
                .compact();
    }

    private String createTokenWithEmail(String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", 1L)
                .claim("role", "ROLE_USER")
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    private String createTokenWithUserId(Long userId) {
        return Jwts.builder()
                .setSubject("test@example.com")
                .claim("userId", userId)
                .claim("role", "ROLE_USER")
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    private String createTokenWithRole(String role) {
        return Jwts.builder()
                .setSubject("test@example.com")
                .claim("userId", 1L)
                .claim("role", role)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }
}
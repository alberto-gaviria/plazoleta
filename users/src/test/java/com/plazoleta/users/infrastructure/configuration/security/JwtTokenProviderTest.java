package com.plazoleta.users.infrastructure.configuration.security;

import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.infrastructure.configuration.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    // Clave válida de 64 bytes (512 bits)
    private final String jwtSecret = "mySecretKey123456789012345678901234567890123456789012345678901234567890";
    private final long jwtExpiration = 86400000; // 1 día

    private User user;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(jwtSecret, jwtExpiration);

        user = new User();
        user.setId(1L);
        user.setCorreo("test@example.com");
        user.setRoleType(RoleType.CLIENTE);
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtTokenProvider.generateToken(user);

        assertThat(token).isNotBlank();
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void shouldExtractEmailFromToken() {
        String token = jwtTokenProvider.generateToken(user);

        String email = jwtTokenProvider.getEmailFromToken(token);

        assertThat(email).isEqualTo("test@example.com");
    }

    @Test
    void shouldExtractUserIdFromToken() {
        String token = jwtTokenProvider.generateToken(user);

        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        assertThat(userId).isEqualTo(1L);
    }

    @Test
    void shouldExtractRoleFromToken() {
        String token = jwtTokenProvider.generateToken(user);

        String role = jwtTokenProvider.getRoleFromToken(token);

        assertThat(role).isEqualTo("CLIENTE");
    }

    @Test
    void shouldInvalidateMalformedToken() {
        String malformedToken = "invalid.token.here";

        boolean isValid = jwtTokenProvider.validateToken(malformedToken);

        assertThat(isValid).isFalse();
    }

    @Test
    void shouldInvalidateExpiredToken() throws InterruptedException {
        // Crear provider con expiración de 1 ms
        JwtTokenProvider shortLivedProvider = new JwtTokenProvider(jwtSecret, 1);
        String token = shortLivedProvider.generateToken(user);

        // Esperar a que expire
        Thread.sleep(10);

        boolean isValid = shortLivedProvider.validateToken(token);

        assertThat(isValid).isFalse();
    }
}
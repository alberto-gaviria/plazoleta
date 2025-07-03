package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.response.AuthResponse;
import com.plazoleta.users.domain.model.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IAuthResponseMapperTest {

    private IAuthResponseMapper authResponseMapper;

    @BeforeEach
    void setUp() {
        authResponseMapper = Mappers.getMapper(IAuthResponseMapper.class);
    }

    @Test
    void authToResponse_WhenValidAuthentication_ShouldMapCorrectly() {
        // Given
        Authentication authentication = new Authentication();
        authentication.setToken("jwt-token-example");
        authentication.setUserId(1L);
        authentication.setEmail("test@email.com");
        authentication.setRole("ADMIN");

        // When
        AuthResponse response = authResponseMapper.authToResponse(authentication);

        // Then
        assertNotNull(response);
        assertEquals("jwt-token-example", response.getToken());
        assertEquals(1L, response.getUserId());
        assertEquals("test@email.com", response.getEmail());
        assertEquals("ADMIN", response.getRole());
        assertEquals("Bearer", response.getType()); // Default value
    }

    @Test
    void authToResponse_WhenNullAuthentication_ShouldReturnNull() {
        // When
        AuthResponse response = authResponseMapper.authToResponse(null);

        // Then
        assertNull(response);
    }

    @Test
    void authToResponse_WhenAuthenticationWithNullFields_ShouldMapWithNullValues() {
        // Given
        Authentication authentication = new Authentication();
        authentication.setToken(null);
        authentication.setUserId(null);
        authentication.setEmail(null);
        authentication.setRole(null);

        // When
        AuthResponse response = authResponseMapper.authToResponse(authentication);

        // Then
        assertNotNull(response);
        assertNull(response.getToken());
        assertNull(response.getUserId());
        assertNull(response.getEmail());
        assertNull(response.getRole());
        assertEquals("Bearer", response.getType()); // Default value should still be set
    }

    @Test
    void authToResponse_WhenAuthenticationWithEmptyStrings_ShouldMapEmptyStrings() {
        // Given
        Authentication authentication = new Authentication();
        authentication.setToken("");
        authentication.setUserId(0L);
        authentication.setEmail("");
        authentication.setRole("");

        // When
        AuthResponse response = authResponseMapper.authToResponse(authentication);

        // Then
        assertNotNull(response);
        assertEquals("", response.getToken());
        assertEquals(0L, response.getUserId());
        assertEquals("", response.getEmail());
        assertEquals("", response.getRole());
        assertEquals("Bearer", response.getType());
    }

    @Test
    void authToResponse_WhenAuthenticationWithSpecialCharacters_ShouldMapCorrectly() {
        // Given
        Authentication authentication = new Authentication();
        authentication.setToken("special-token!@#$%^&*()");
        authentication.setUserId(999999L);
        authentication.setEmail("test+special@email.com");
        authentication.setRole("SUPER_ADMIN");

        // When
        AuthResponse response = authResponseMapper.authToResponse(authentication);

        // Then
        assertNotNull(response);
        assertEquals("special-token!@#$%^&*()", response.getToken());
        assertEquals(999999L, response.getUserId());
        assertEquals("test+special@email.com", response.getEmail());
        assertEquals("SUPER_ADMIN", response.getRole());
        assertEquals("Bearer", response.getType());
    }

    @Test
    void authToResponse_WhenAuthenticationWithLongValues_ShouldMapCorrectly() {
        // Given
        String longToken = "a".repeat(1000);
        String longEmail = "very.long.email.address.for.testing.purposes@extremely.long.domain.name.example.com";
        String longRole = "VERY_LONG_ROLE_NAME_FOR_TESTING_PURPOSES";

        Authentication authentication = new Authentication();
        authentication.setToken(longToken);
        authentication.setUserId(Long.MAX_VALUE);
        authentication.setEmail(longEmail);
        authentication.setRole(longRole);

        // When
        AuthResponse response = authResponseMapper.authToResponse(authentication);

        // Then
        assertNotNull(response);
        assertEquals(longToken, response.getToken());
        assertEquals(Long.MAX_VALUE, response.getUserId());
        assertEquals(longEmail, response.getEmail());
        assertEquals(longRole, response.getRole());
        assertEquals("Bearer", response.getType());
    }

    @Test
    void authToResponse_WhenAuthenticationWithNegativeUserId_ShouldMapCorrectly() {
        // Given
        Authentication authentication = new Authentication();
        authentication.setToken("token");
        authentication.setUserId(-1L);
        authentication.setEmail("test@email.com");
        authentication.setRole("USER");

        // When
        AuthResponse response = authResponseMapper.authToResponse(authentication);

        // Then
        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals(-1L, response.getUserId());
        assertEquals("test@email.com", response.getEmail());
        assertEquals("USER", response.getRole());
        assertEquals("Bearer", response.getType());
    }

    @Test
    void authToResponse_MapperInstance_ShouldNotBeNull() {
        // Then
        assertNotNull(authResponseMapper);
    }

    @Test
    void authToResponse_MultipleCallsWithSameData_ShouldProduceSameResults() {
        // Given
        Authentication authentication = new Authentication();
        authentication.setToken("consistent-token");
        authentication.setUserId(123L);
        authentication.setEmail("consistent@email.com");
        authentication.setRole("CONSISTENT_ROLE");

        // When
        AuthResponse response1 = authResponseMapper.authToResponse(authentication);
        AuthResponse response2 = authResponseMapper.authToResponse(authentication);

        // Then
        assertNotNull(response1);
        assertNotNull(response2);
        assertEquals(response1.getToken(), response2.getToken());
        assertEquals(response1.getUserId(), response2.getUserId());
        assertEquals(response1.getEmail(), response2.getEmail());
        assertEquals(response1.getRole(), response2.getRole());
        assertEquals(response1.getType(), response2.getType());
    }
}
package com.plazoleta.users.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthResponseTest {

    @Test
    void defaultConstructor_ShouldCreateObjectWithDefaultType() {
        // When
        AuthResponse response = new AuthResponse();

        // Then
        assertNotNull(response);
        assertNull(response.getToken());
        assertNull(response.getUserId());
        assertNull(response.getEmail());
        assertNull(response.getRole());
        assertEquals("Bearer", response.getType());
    }

    @Test
    void allArgsConstructor_ShouldCreateObjectWithAllFields() {
        // Given
        String token = "jwt-token";
        String type = "Custom";
        Long userId = 1L;
        String email = "test@email.com";
        String role = "ADMIN";

        // When
        AuthResponse response = new AuthResponse(token, type, userId, email, role);

        // Then
        assertNotNull(response);
        assertEquals(token, response.getToken());
        assertEquals(type, response.getType());
        assertEquals(userId, response.getUserId());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void customConstructor_ShouldCreateObjectWithBearerType() {
        // Given
        String token = "jwt-token";
        Long userId = 1L;
        String email = "test@email.com";
        String role = "USER";

        // When
        AuthResponse response = new AuthResponse(token, userId, email, role);

        // Then
        assertNotNull(response);
        assertEquals(token, response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(userId, response.getUserId());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void customConstructor_WithNullValues_ShouldCreateObjectWithNulls() {
        // When
        AuthResponse response = new AuthResponse(null, null, null, null);

        // Then
        assertNotNull(response);
        assertNull(response.getToken());
        assertEquals("Bearer", response.getType());
        assertNull(response.getUserId());
        assertNull(response.getEmail());
        assertNull(response.getRole());
    }

    @Test
    void setToken_ShouldUpdateTokenValue() {
        // Given
        AuthResponse response = new AuthResponse();
        String newToken = "new-jwt-token";

        // When
        response.setToken(newToken);

        // Then
        assertEquals(newToken, response.getToken());
    }

    @Test
    void setType_ShouldUpdateTypeValue() {
        // Given
        AuthResponse response = new AuthResponse();
        String newType = "Custom Bearer";

        // When
        response.setType(newType);

        // Then
        assertEquals(newType, response.getType());
    }

    @Test
    void setUserId_ShouldUpdateUserIdValue() {
        // Given
        AuthResponse response = new AuthResponse();
        Long newUserId = 999L;

        // When
        response.setUserId(newUserId);

        // Then
        assertEquals(newUserId, response.getUserId());
    }

    @Test
    void setEmail_ShouldUpdateEmailValue() {
        // Given
        AuthResponse response = new AuthResponse();
        String newEmail = "updated@email.com";

        // When
        response.setEmail(newEmail);

        // Then
        assertEquals(newEmail, response.getEmail());
    }

    @Test
    void setRole_ShouldUpdateRoleValue() {
        // Given
        AuthResponse response = new AuthResponse();
        String newRole = "SUPER_ADMIN";

        // When
        response.setRole(newRole);

        // Then
        assertEquals(newRole, response.getRole());
    }

    @Test
    void getToken_WhenTokenIsSet_ShouldReturnToken() {
        // Given
        AuthResponse response = new AuthResponse();
        String token = "test-token";
        response.setToken(token);

        // When
        String retrievedToken = response.getToken();

        // Then
        assertEquals(token, retrievedToken);
        assertSame(token, retrievedToken);
    }

    @Test
    void getType_WhenTypeIsSet_ShouldReturnType() {
        // Given
        AuthResponse response = new AuthResponse();
        String type = "Custom Type";
        response.setType(type);

        // When
        String retrievedType = response.getType();

        // Then
        assertEquals(type, retrievedType);
        assertSame(type, retrievedType);
    }

    @Test
    void getUserId_WhenUserIdIsSet_ShouldReturnUserId() {
        // Given
        AuthResponse response = new AuthResponse();
        Long userId = 123L;
        response.setUserId(userId);

        // When
        Long retrievedUserId = response.getUserId();

        // Then
        assertEquals(userId, retrievedUserId);
        assertSame(userId, retrievedUserId);
    }

    @Test
    void getEmail_WhenEmailIsSet_ShouldReturnEmail() {
        // Given
        AuthResponse response = new AuthResponse();
        String email = "getter@test.com";
        response.setEmail(email);

        // When
        String retrievedEmail = response.getEmail();

        // Then
        assertEquals(email, retrievedEmail);
        assertSame(email, retrievedEmail);
    }

    @Test
    void getRole_WhenRoleIsSet_ShouldReturnRole() {
        // Given
        AuthResponse response = new AuthResponse();
        String role = "ROLE_USER";
        response.setRole(role);

        // When
        String retrievedRole = response.getRole();

        // Then
        assertEquals(role, retrievedRole);
        assertSame(role, retrievedRole);
    }

    @Test
    void allArgsConstructor_WithNullValues_ShouldCreateObjectWithNulls() {
        // When
        AuthResponse response = new AuthResponse(null, null, null, null, null);

        // Then
        assertNotNull(response);
        assertNull(response.getToken());
        assertNull(response.getType());
        assertNull(response.getUserId());
        assertNull(response.getEmail());
        assertNull(response.getRole());
    }

    @Test
    void settersWithNull_ShouldSetNullValues() {
        // Given
        AuthResponse response = new AuthResponse("token", "type", 1L, "email", "role");

        // When
        response.setToken(null);
        response.setType(null);
        response.setUserId(null);
        response.setEmail(null);
        response.setRole(null);

        // Then
        assertNull(response.getToken());
        assertNull(response.getType());
        assertNull(response.getUserId());
        assertNull(response.getEmail());
        assertNull(response.getRole());
    }

    @Test
    void gettersOnDefaultConstructor_ShouldReturnExpectedValues() {
        // Given
        AuthResponse response = new AuthResponse();

        // When & Then
        assertNull(response.getToken());
        assertEquals("Bearer", response.getType());
        assertNull(response.getUserId());
        assertNull(response.getEmail());
        assertNull(response.getRole());
    }

    @Test
    void objectLifecycle_CompleteSetAndGetOperations() {
        // Given
        AuthResponse response = new AuthResponse();

        // Set initial values
        response.setToken("initial-token");
        response.setType("Initial");
        response.setUserId(1L);
        response.setEmail("initial@email.com");
        response.setRole("INITIAL");

        // Verify initial values
        assertEquals("initial-token", response.getToken());
        assertEquals("Initial", response.getType());
        assertEquals(1L, response.getUserId());
        assertEquals("initial@email.com", response.getEmail());
        assertEquals("INITIAL", response.getRole());

        // Update values
        response.setToken("updated-token");
        response.setType("Updated");
        response.setUserId(2L);
        response.setEmail("updated@email.com");
        response.setRole("UPDATED");

        // Verify updated values
        assertEquals("updated-token", response.getToken());
        assertEquals("Updated", response.getType());
        assertEquals(2L, response.getUserId());
        assertEquals("updated@email.com", response.getEmail());
        assertEquals("UPDATED", response.getRole());
    }

    @Test
    void constructors_ShouldProduceDifferentObjects() {
        // Given
        AuthResponse response1 = new AuthResponse();
        AuthResponse response2 = new AuthResponse("token", 1L, "email", "role");
        AuthResponse response3 = new AuthResponse("token", "type", 1L, "email", "role");

        // When & Then
        assertNotSame(response1, response2);
        assertNotSame(response2, response3);
        assertNotSame(response1, response3);
    }

    @Test
    void customConstructor_WithSpecialCharacters_ShouldHandleCorrectly() {
        // Given
        String token = "token!@#$%^&*()";
        Long userId = Long.MAX_VALUE;
        String email = "test+special@domain.co.uk";
        String role = "ROLE_ADMIN_SUPER_USER";

        // When
        AuthResponse response = new AuthResponse(token, userId, email, role);

        // Then
        assertEquals(token, response.getToken());
        assertEquals("Bearer", response.getType());
        assertEquals(userId, response.getUserId());
        assertEquals(email, response.getEmail());
        assertEquals(role, response.getRole());
    }

    @Test
    void settersOverwritePreviousValues() {
        // Given
        AuthResponse response = new AuthResponse("old-token", "old-type", 1L, "old@email.com", "OLD_ROLE");

        // When
        response.setToken("new-token");
        response.setType("new-type");
        response.setUserId(999L);
        response.setEmail("new@email.com");
        response.setRole("NEW_ROLE");

        // Then
        assertEquals("new-token", response.getToken());
        assertEquals("new-type", response.getType());
        assertEquals(999L, response.getUserId());
        assertEquals("new@email.com", response.getEmail());
        assertEquals("NEW_ROLE", response.getRole());
    }
}
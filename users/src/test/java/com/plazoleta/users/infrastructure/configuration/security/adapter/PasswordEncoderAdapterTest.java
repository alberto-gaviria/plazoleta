package com.plazoleta.users.infrastructure.configuration.security.adapter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderAdapterTest {

    private PasswordEncoderAdapter passwordEncoderAdapter;

    @BeforeEach
    void setUp() {
        passwordEncoderAdapter = new PasswordEncoderAdapter();
    }

    @Test
    void encode_ShouldReturnEncodedPassword() {
        // Given
        String rawPassword = "password123";

        // When
        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);

        // Then
        assertNotNull(encodedPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(encodedPassword.startsWith("$2a$")); // BCrypt prefix
    }

    @Test
    void encode_SamePlainPassword_ShouldReturnDifferentHashes() {
        // Given
        String rawPassword = "password123";

        // When
        String encodedPassword1 = passwordEncoderAdapter.encode(rawPassword);
        String encodedPassword2 = passwordEncoderAdapter.encode(rawPassword);

        // Then
        assertNotEquals(encodedPassword1, encodedPassword2);
        assertTrue(encodedPassword1.startsWith("$2a$"));
        assertTrue(encodedPassword2.startsWith("$2a$"));
    }

    @Test
    void encode_EmptyPassword_ShouldReturnEncodedHash() {
        // Given
        String rawPassword = "";

        // When
        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);

        // Then
        assertNotNull(encodedPassword);
        assertTrue(encodedPassword.startsWith("$2a$"));
    }

    @Test
    void encode_NullPassword_ShouldThrowException() {
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> passwordEncoderAdapter.encode(null));
    }

    @Test
    void matches_WhenPasswordMatches_ShouldReturnTrue() {
        // Given
        String rawPassword = "password123";
        String encodedPassword = passwordEncoderAdapter.encode(rawPassword);

        // When
        boolean result = passwordEncoderAdapter.matches(rawPassword, encodedPassword);

        // Then
        assertTrue(result);
    }

    @Test
    void matches_WhenPasswordDoesNotMatch_ShouldReturnFalse() {
        // Given
        String rawPassword = "password123";
        String encodedPassword = passwordEncoderAdapter.encode("differentPassword");

        // When
        boolean result = passwordEncoderAdapter.matches(rawPassword, encodedPassword);

        // Then
        assertFalse(result);
    }

    @Test
    void matches_WhenEncodedPasswordIsInvalid_ShouldReturnFalse() {
        // Given
        String rawPassword = "password123";
        String invalidEncodedPassword = "$2a$invalidFormatThatIsNotProperHash";

        // When
        boolean result = passwordEncoderAdapter.matches(rawPassword, invalidEncodedPassword);

        // Then
        assertFalse(result); // bcrypt returns false if format is invalid
    }
}
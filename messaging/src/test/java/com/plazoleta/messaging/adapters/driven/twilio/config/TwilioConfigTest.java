package com.plazoleta.messaging.adapters.driven.twilio.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwilioConfigTest {

    @Test
    void constructor_ShouldCreateEmptyConfig() {
        // When
        TwilioConfig config = new TwilioConfig();

        // Then
        assertNotNull(config);
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        TwilioConfig config = new TwilioConfig();

        // When
        config.setAccountSid("test-sid");
        config.setAuthToken("test-token");
        config.setFromPhoneNumber("+1234567890");
        config.setEnabled(true);

        // Then
        assertEquals("test-sid", config.getAccountSid());
        assertEquals("test-token", config.getAuthToken());
        assertEquals("+1234567890", config.getFromPhoneNumber());
        assertTrue(config.isEnabled());
    }

    @Test
    void isConfigured_WhenAllFieldsSet_ShouldReturnTrue() {
        // Given
        TwilioConfig config = new TwilioConfig();
        config.setAccountSid("test-sid");
        config.setAuthToken("test-token");
        config.setFromPhoneNumber("+1234567890");

        // When
        boolean result = config.isConfigured();

        // Then
        assertTrue(result);
    }

    @Test
    void isConfigured_WhenAccountSidIsNull_ShouldReturnFalse() {
        // Given
        TwilioConfig config = new TwilioConfig();
        config.setAccountSid(null);
        config.setAuthToken("test-token");
        config.setFromPhoneNumber("+1234567890");

        // When
        boolean result = config.isConfigured();

        // Then
        assertFalse(result);
    }

    @Test
    void isConfigured_WhenAccountSidIsEmpty_ShouldReturnFalse() {
        // Given
        TwilioConfig config = new TwilioConfig();
        config.setAccountSid("");
        config.setAuthToken("test-token");
        config.setFromPhoneNumber("+1234567890");

        // When
        boolean result = config.isConfigured();

        // Then
        assertFalse(result);
    }

    @Test
    void isConfigured_WhenAuthTokenIsNull_ShouldReturnFalse() {
        // Given
        TwilioConfig config = new TwilioConfig();
        config.setAccountSid("test-sid");
        config.setAuthToken(null);
        config.setFromPhoneNumber("+1234567890");

        // When
        boolean result = config.isConfigured();

        // Then
        assertFalse(result);
    }

    @Test
    void isConfigured_WhenAuthTokenIsEmpty_ShouldReturnFalse() {
        // Given
        TwilioConfig config = new TwilioConfig();
        config.setAccountSid("test-sid");
        config.setAuthToken("");
        config.setFromPhoneNumber("+1234567890");

        // When
        boolean result = config.isConfigured();

        // Then
        assertFalse(result);
    }

    @Test
    void isConfigured_WhenFromPhoneNumberIsNull_ShouldReturnFalse() {
        // Given
        TwilioConfig config = new TwilioConfig();
        config.setAccountSid("test-sid");
        config.setAuthToken("test-token");
        config.setFromPhoneNumber(null);

        // When
        boolean result = config.isConfigured();

        // Then
        assertFalse(result);
    }

    @Test
    void isConfigured_WhenFromPhoneNumberIsEmpty_ShouldReturnFalse() {
        // Given
        TwilioConfig config = new TwilioConfig();
        config.setAccountSid("test-sid");
        config.setAuthToken("test-token");
        config.setFromPhoneNumber("");

        // When
        boolean result = config.isConfigured();

        // Then
        assertFalse(result);
    }
}
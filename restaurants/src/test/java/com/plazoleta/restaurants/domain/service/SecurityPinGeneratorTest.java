package com.plazoleta.restaurants.domain.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityPinGeneratorTest {

    @Test
    void generate_ShouldReturnFourDigitPin() {
        for (int i = 0; i < 100; i++) {
            String pin = SecurityPinGenerator.generate();
            assertNotNull(pin);
            assertEquals(4, pin.length());
            int pinInt = Integer.parseInt(pin);
            assertTrue(pinInt >= 1000 && pinInt <= 9999);
        }
    }
}

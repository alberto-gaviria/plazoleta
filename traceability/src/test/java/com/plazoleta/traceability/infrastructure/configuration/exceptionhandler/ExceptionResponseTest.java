package com.plazoleta.traceability.infrastructure.configuration.exceptionhandler;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionResponseTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String expectedMessage = "Mensaje de error";
        String expectedStatus = "400 BAD_REQUEST";
        LocalDateTime expectedTimestamp = LocalDateTime.now();

        // Act
        ExceptionResponse response = new ExceptionResponse(expectedMessage, expectedStatus, expectedTimestamp);

        // Assert
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(expectedStatus, response.getStatus());
        assertEquals(expectedTimestamp, response.getTimestamp());
    }
}

package com.plazoleta.messaging.adapters.driven.twilio.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class TwilioConstantsTest {

    @Test
    void constructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<TwilioConstants> constructor = TwilioConstants.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void messagesConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<TwilioConstants.Messages> constructor = TwilioConstants.Messages.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void configConstructor_ShouldThrowException() {
        // When & Then
        assertThrows(InvocationTargetException.class, () -> {
            Constructor<TwilioConstants.Config> constructor = TwilioConstants.Config.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        });
    }

    @Test
    void constants_ShouldHaveCorrectValues() {
        // Then
        assertEquals("Twilio inicializado correctamente", TwilioConstants.Messages.TWILIO_INITIALIZED_SUCCESS);
        assertEquals("Error inicializando Twilio: {}", TwilioConstants.Messages.TWILIO_INITIALIZATION_ERROR);
        assertEquals(30000, TwilioConstants.Config.CONNECTION_TIMEOUT);
        assertEquals(60000, TwilioConstants.Config.READ_TIMEOUT);
    }
}
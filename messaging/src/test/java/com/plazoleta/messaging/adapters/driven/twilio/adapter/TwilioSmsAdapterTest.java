package com.plazoleta.messaging.adapters.driven.twilio.adapter;

import com.plazoleta.messaging.adapters.driven.twilio.config.TwilioConfig;
import com.plazoleta.messaging.domain.model.SmsNotification;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TwilioSmsAdapterTest {

    private TwilioConfig twilioConfig;
    private TwilioSmsAdapter smsAdapter;

    @BeforeEach
    void setUp() {
        twilioConfig = mock(TwilioConfig.class);
        smsAdapter = new TwilioSmsAdapter(twilioConfig);
    }

    @Test
    void testInitializeTwilio_WhenConfigured_ShouldInitializeTwilio() {
        when(twilioConfig.isEnabled()).thenReturn(true);
        when(twilioConfig.isConfigured()).thenReturn(true);
        when(twilioConfig.getAccountSid()).thenReturn("AC123");
        when(twilioConfig.getAuthToken()).thenReturn("token");

        try (MockedStatic<com.twilio.Twilio> twilio = Mockito.mockStatic(com.twilio.Twilio.class)) {
            smsAdapter.initializeTwilio();
            twilio.verify(() -> com.twilio.Twilio.init("AC123", "token"));
        }
    }

    @Test
    void testInitializeTwilio_WhenNotConfigured_ShouldLogAndSkipInit() {
        when(twilioConfig.isEnabled()).thenReturn(false);
        when(twilioConfig.isConfigured()).thenReturn(false);

        smsAdapter.initializeTwilio(); // Should not throw
    }

    @Test
    void testSendSms_WhenTwilioConfigured_ShouldSendSuccessfully() {
        SmsNotification notification = createSampleNotification();

        when(twilioConfig.isEnabled()).thenReturn(true);
        when(twilioConfig.isConfigured()).thenReturn(true);
        when(twilioConfig.getFromPhoneNumber()).thenReturn("+1000000000");

        Message mockMessage = mock(Message.class);
        when(mockMessage.getSid()).thenReturn("SM123");

        try (MockedStatic<Message> messageStatic = Mockito.mockStatic(Message.class)) {
            MessageCreator mockCreator = mock(MessageCreator.class);
            when(mockCreator.create()).thenReturn(mockMessage);

            messageStatic.when(() ->
                                       Message.creator(
                                               new PhoneNumber(notification.getClientPhone()),
                                               new PhoneNumber("+1000000000"),
                                               notification.getMessage())
            ).thenReturn(mockCreator);

            boolean result = smsAdapter.sendSms(notification);

            assertTrue(result);
            verify(mockCreator).create();
        }
    }

    @Test
    void testSendSms_WhenTwilioNotConfigured_ShouldSimulateSms() {
        SmsNotification notification = createSampleNotification();

        when(twilioConfig.isEnabled()).thenReturn(false);
        when(twilioConfig.isConfigured()).thenReturn(false);

        boolean result = smsAdapter.sendSms(notification);

        assertTrue(result); // Simulado
    }

    @Test
    void testSendSms_WhenExceptionThrown_ShouldReturnFalse() {
        SmsNotification notification = createSampleNotification();

        when(twilioConfig.isEnabled()).thenReturn(true);
        when(twilioConfig.isConfigured()).thenReturn(true);
        when(twilioConfig.getFromPhoneNumber()).thenReturn("+1000000000");

        try (MockedStatic<Message> messageStatic = Mockito.mockStatic(Message.class)) {
            MessageCreator mockCreator = mock(MessageCreator.class);
            when(mockCreator.create()).thenThrow(new RuntimeException("Twilio error"));

            messageStatic.when(() ->
                                       Message.creator(
                                               new PhoneNumber(notification.getClientPhone()),
                                               new PhoneNumber("+1000000000"),
                                               notification.getMessage())
            ).thenReturn(mockCreator);

            boolean result = smsAdapter.sendSms(notification);

            assertFalse(result);
        }
    }

    @Test
    void testIsConfigured_WhenConfigTrue_ShouldReturnTrue() {
        when(twilioConfig.isEnabled()).thenReturn(true);
        when(twilioConfig.isConfigured()).thenReturn(true);

        assertTrue(smsAdapter.isConfigured());
    }

    @Test
    void testIsConfigured_WhenConfigFalse_ShouldReturnFalse() {
        when(twilioConfig.isEnabled()).thenReturn(false);
        when(twilioConfig.isConfigured()).thenReturn(false);

        assertFalse(smsAdapter.isConfigured());
    }

    private SmsNotification createSampleNotification() {
        SmsNotification notification = new SmsNotification();
        notification.setClientPhone("+1234567890");
        notification.setMessage("Test SMS");
        return notification;
    }
}

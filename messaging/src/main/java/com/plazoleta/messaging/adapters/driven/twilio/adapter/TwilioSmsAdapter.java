package com.plazoleta.messaging.adapters.driven.twilio.adapter;

import com.plazoleta.messaging.adapters.driven.twilio.config.TwilioConfig;
import com.plazoleta.messaging.domain.model.SmsNotification;
import com.plazoleta.messaging.domain.spi.ISmsProviderPort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;

public class TwilioSmsAdapter implements ISmsProviderPort {

    private static final Logger logger = LoggerFactory.getLogger(TwilioSmsAdapter.class);

    private static final String TWILIO_INITIALIZED_MSG = "Twilio inicializado correctamente";
    private static final String TWILIO_NOT_CONFIGURED_MSG = "Twilio no configurado - Modo desarrollo";
    private static final String SMS_SIMULATED_MSG = "SMS simulado para: {}";
    private static final String SMS_SENT_SUCCESS_MSG = "SMS enviado. SID: {}";
    private static final String SMS_SEND_ERROR_MSG = "Error enviando SMS: {}";

    private final TwilioConfig twilioConfig;

    public TwilioSmsAdapter(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @PostConstruct
    public void initializeTwilio() {
        if (twilioConfig.isEnabled() && twilioConfig.isConfigured()) {
            Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
            logger.info(TWILIO_INITIALIZED_MSG);
        } else {
            logger.info(TWILIO_NOT_CONFIGURED_MSG);
        }
    }

    @Override
    public boolean sendSms(SmsNotification notification) {
        if (!isConfigured()) {
            logger.info(SMS_SIMULATED_MSG, notification.getClientPhone());
            return true;
        }

        try {
            Message message = Message.creator(
                    new PhoneNumber(notification.getClientPhone()),
                    new PhoneNumber(twilioConfig.getFromPhoneNumber()),
                    notification.getMessage()
            ).create();

            logger.info(SMS_SENT_SUCCESS_MSG, message.getSid());
            return true;

        } catch (Exception e) {
            logger.error(SMS_SEND_ERROR_MSG, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isConfigured() {
        return twilioConfig.isEnabled() && twilioConfig.isConfigured();
    }
}
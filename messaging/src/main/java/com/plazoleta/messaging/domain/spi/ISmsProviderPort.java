package com.plazoleta.messaging.domain.spi;

import com.plazoleta.messaging.domain.model.SmsNotification;

public interface ISmsProviderPort {
    boolean sendSms(SmsNotification notification);
    boolean isConfigured();
}
package com.plazoleta.messaging.infrastructure.configuration;

import com.plazoleta.messaging.adapters.driven.mongodb.adapter.NotificationMongoAdapter;
import com.plazoleta.messaging.adapters.driven.mongodb.mapper.INotificationDocumentMapper;
import com.plazoleta.messaging.adapters.driven.mongodb.repository.INotificationRepository;
import com.plazoleta.messaging.adapters.driven.twilio.adapter.TwilioSmsAdapter;
import com.plazoleta.messaging.adapters.driven.twilio.config.TwilioConfig;
import com.plazoleta.messaging.domain.api.INotificationServicePort;
import com.plazoleta.messaging.domain.spi.INotificationPersistencePort;
import com.plazoleta.messaging.domain.spi.ISmsProviderPort;
import com.plazoleta.messaging.domain.usecase.NotificationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public INotificationPersistencePort notificationPersistencePort(
            INotificationRepository notificationRepository,
            INotificationDocumentMapper notificationDocumentMapper) {
        return new NotificationMongoAdapter(notificationRepository, notificationDocumentMapper);
    }

    @Bean
    public ISmsProviderPort smsProviderPort(TwilioConfig twilioConfig) {
        return new TwilioSmsAdapter(twilioConfig);
    }

    @Bean
    public INotificationServicePort notificationServicePort(
            INotificationPersistencePort notificationPersistencePort,
            ISmsProviderPort smsProviderPort) {
        return new NotificationUseCase(notificationPersistencePort, smsProviderPort);
    }
}
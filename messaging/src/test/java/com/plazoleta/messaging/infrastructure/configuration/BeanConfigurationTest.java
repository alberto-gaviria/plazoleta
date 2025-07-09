// 1. BeanConfigurationTest.java - COMPLETO
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private INotificationRepository notificationRepository;

    @Mock
    private INotificationDocumentMapper notificationDocumentMapper;

    @Mock
    private TwilioConfig twilioConfig;

    @Mock
    private INotificationPersistencePort notificationPersistencePort;

    @Mock
    private ISmsProviderPort smsProviderPort;

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    void notificationPersistencePort_ShouldCreateNotificationMongoAdapter() {
        // When
        INotificationPersistencePort result = beanConfiguration.notificationPersistencePort(
                notificationRepository, notificationDocumentMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(NotificationMongoAdapter.class, result);
    }

    @Test
    void notificationPersistencePort_WithNullRepository_ShouldStillCreateAdapter() {
        // When
        INotificationPersistencePort result = beanConfiguration.notificationPersistencePort(
                null, notificationDocumentMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(NotificationMongoAdapter.class, result);
    }

    @Test
    void notificationPersistencePort_WithNullMapper_ShouldStillCreateAdapter() {
        // When
        INotificationPersistencePort result = beanConfiguration.notificationPersistencePort(
                notificationRepository, null);

        // Then
        assertNotNull(result);
        assertInstanceOf(NotificationMongoAdapter.class, result);
    }

    @Test
    void smsProviderPort_ShouldCreateTwilioSmsAdapter() {
        // When
        ISmsProviderPort result = beanConfiguration.smsProviderPort(twilioConfig);

        // Then
        assertNotNull(result);
        assertInstanceOf(TwilioSmsAdapter.class, result);
    }

    @Test
    void smsProviderPort_WithNullConfig_ShouldStillCreateAdapter() {
        // When
        ISmsProviderPort result = beanConfiguration.smsProviderPort(null);

        // Then
        assertNotNull(result);
        assertInstanceOf(TwilioSmsAdapter.class, result);
    }

    @Test
    void notificationServicePort_ShouldCreateNotificationUseCase() {
        // When
        INotificationServicePort result = beanConfiguration.notificationServicePort(
                notificationPersistencePort, smsProviderPort);

        // Then
        assertNotNull(result);
        assertInstanceOf(NotificationUseCase.class, result);
    }

    @Test
    void notificationServicePort_WithNullPersistencePort_ShouldStillCreateUseCase() {
        // When
        INotificationServicePort result = beanConfiguration.notificationServicePort(
                null, smsProviderPort);

        // Then
        assertNotNull(result);
        assertInstanceOf(NotificationUseCase.class, result);
    }

    @Test
    void notificationServicePort_WithNullSmsProviderPort_ShouldStillCreateUseCase() {
        // When
        INotificationServicePort result = beanConfiguration.notificationServicePort(
                notificationPersistencePort, null);

        // Then
        assertNotNull(result);
        assertInstanceOf(NotificationUseCase.class, result);
    }

    @Test
    void notificationServicePort_WithAllNullParameters_ShouldStillCreateUseCase() {
        // When
        INotificationServicePort result = beanConfiguration.notificationServicePort(null, null);

        // Then
        assertNotNull(result);
        assertInstanceOf(NotificationUseCase.class, result);
    }

    @Test
    void beanConfiguration_ShouldHaveDefaultConstructor() {
        // When
        BeanConfiguration config = new BeanConfiguration();

        // Then
        assertNotNull(config);
    }
}
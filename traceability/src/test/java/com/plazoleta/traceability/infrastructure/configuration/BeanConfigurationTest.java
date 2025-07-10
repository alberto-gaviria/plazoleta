package com.plazoleta.traceability.infrastructure.configuration;

import com.plazoleta.traceability.adapters.driven.mongodb.adapter.TraceabilityMongoAdapter;
import com.plazoleta.traceability.adapters.driven.mongodb.mapper.ITraceabilityDocumentMapper;
import com.plazoleta.traceability.adapters.driven.mongodb.repository.IOrderTraceabilityRepository;
import com.plazoleta.traceability.domain.api.ITraceabilityServicePort;
import com.plazoleta.traceability.domain.spi.ITraceabilityPersistencePort;
import com.plazoleta.traceability.domain.usecase.TraceabilityUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeanConfigurationTest {

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    void testTraceabilityPersistencePortBean() {
        // Arrange
        IOrderTraceabilityRepository repository = mock(IOrderTraceabilityRepository.class);
        ITraceabilityDocumentMapper mapper = mock(ITraceabilityDocumentMapper.class);

        // Act
        ITraceabilityPersistencePort result = beanConfiguration.traceabilityPersistencePort(repository, mapper);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof TraceabilityMongoAdapter);
    }

    @Test
    void testTraceabilityServicePortBean() {
        // Arrange
        ITraceabilityPersistencePort persistencePort = mock(ITraceabilityPersistencePort.class);

        // Act
        ITraceabilityServicePort result = beanConfiguration.traceabilityServicePort(persistencePort);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof TraceabilityUseCase);
    }
}

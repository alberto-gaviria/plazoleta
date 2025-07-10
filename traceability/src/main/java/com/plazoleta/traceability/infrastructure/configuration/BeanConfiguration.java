package com.plazoleta.traceability.infrastructure.configuration;

import com.plazoleta.traceability.adapters.driven.mongodb.adapter.TraceabilityMongoAdapter;
import com.plazoleta.traceability.adapters.driven.mongodb.mapper.ITraceabilityDocumentMapper;
import com.plazoleta.traceability.adapters.driven.mongodb.repository.IOrderTraceabilityRepository;
import com.plazoleta.traceability.domain.api.ITraceabilityServicePort;
import com.plazoleta.traceability.domain.spi.ITraceabilityPersistencePort;
import com.plazoleta.traceability.domain.usecase.TraceabilityUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort(
            IOrderTraceabilityRepository traceabilityRepository,
            ITraceabilityDocumentMapper traceabilityMapper) {
        return new TraceabilityMongoAdapter(traceabilityRepository, traceabilityMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort(
            ITraceabilityPersistencePort traceabilityPersistencePort) {
        return new TraceabilityUseCase(traceabilityPersistencePort);
    }
}
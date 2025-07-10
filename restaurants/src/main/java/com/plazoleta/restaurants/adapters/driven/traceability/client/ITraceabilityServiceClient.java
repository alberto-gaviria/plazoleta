package com.plazoleta.restaurants.adapters.driven.traceability.client;

import com.plazoleta.restaurants.adapters.driven.traceability.dto.RecordStatusChangeRequest;
import com.plazoleta.restaurants.infrastructure.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "traceability-service",
        url = "${traceability.service.url}",
        configuration = FeignClientConfiguration.TraceabilityServiceConfig.class
)
public interface ITraceabilityServiceClient {

    @PostMapping("/internal/record-status-change")
    ResponseEntity<Void> recordStatusChange(@RequestBody RecordStatusChangeRequest request);
}
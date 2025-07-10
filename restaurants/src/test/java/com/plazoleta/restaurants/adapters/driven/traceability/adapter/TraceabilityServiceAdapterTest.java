package com.plazoleta.restaurants.adapters.driven.traceability.adapter;

import com.plazoleta.restaurants.adapters.driven.traceability.client.ITraceabilityServiceClient;
import com.plazoleta.restaurants.adapters.driven.traceability.dto.RecordStatusChangeRequest;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TraceabilityServiceAdapterTest {

    private ITraceabilityServiceClient traceabilityServiceClient;
    private TraceabilityServiceAdapter adapter;

    @BeforeEach
    void setUp() {
        traceabilityServiceClient = mock(ITraceabilityServiceClient.class);
        adapter = new TraceabilityServiceAdapter(traceabilityServiceClient);
    }

    @Test
    void recordOrderStatusChange_successfulResponse_logsInfo() {
        // Arrange
        when(traceabilityServiceClient.recordStatusChange(any()))
                .thenReturn(ResponseEntity.ok().build());

        // Act
        adapter.recordOrderStatusChange(1L, 2L, "client@email.com",
                                        "PENDING", "READY", 3L, "employee@email.com");

        // Assert
        verify(traceabilityServiceClient, times(1)).recordStatusChange(any(RecordStatusChangeRequest.class));
    }

    @Test
    void recordOrderStatusChange_non2xxResponse_logsWarn() {
        // Arrange
        when(traceabilityServiceClient.recordStatusChange(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

        // Act
        adapter.recordOrderStatusChange(1L, 2L, "client@email.com",
                                        "PENDING", "READY", 3L, "employee@email.com");

        // Assert
        verify(traceabilityServiceClient, times(1)).recordStatusChange(any(RecordStatusChangeRequest.class));
    }

    @Test
    void recordOrderStatusChange_feignException_logsError() {
        // Arrange
        when(traceabilityServiceClient.recordStatusChange(any()))
                .thenThrow(mock(FeignException.class));

        // Act
        adapter.recordOrderStatusChange(1L, 2L, "client@email.com",
                                        "PENDING", "READY", 3L, "employee@email.com");

        // Assert
        verify(traceabilityServiceClient, times(1)).recordStatusChange(any(RecordStatusChangeRequest.class));
    }

    @Test
    void recordOrderStatusChange_unexpectedException_logsError() {
        // Arrange
        when(traceabilityServiceClient.recordStatusChange(any()))
                .thenThrow(new RuntimeException("Unexpected"));

        // Act
        adapter.recordOrderStatusChange(1L, 2L, "client@email.com",
                                        "PENDING", "READY", 3L, "employee@email.com");

        // Assert
        verify(traceabilityServiceClient, times(1)).recordStatusChange(any(RecordStatusChangeRequest.class));
    }
}

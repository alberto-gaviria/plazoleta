package com.plazoleta.traceability.adapters.driving.http.controller;

import com.plazoleta.traceability.adapters.driving.http.dto.request.RecordStatusChangeRequest;
import com.plazoleta.traceability.adapters.driving.http.mapper.IRecordStatusChangeRequestMapper;
import com.plazoleta.traceability.domain.api.ITraceabilityServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InternalTraceabilityControllerTest {

    @Mock
    private ITraceabilityServicePort traceabilityServicePort;

    @Mock
    private IRecordStatusChangeRequestMapper statusChangeMapper;

    @InjectMocks
    private InternalTraceabilityController controller;

    private RecordStatusChangeRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        request = new RecordStatusChangeRequest();
        request.setOrderId(1L);
        request.setClientId(2L);
        request.setClientEmail("client@example.com");
        request.setPreviousStatus("PENDING");
        request.setNewStatus("IN_PROGRESS");
        request.setEmployeeId(3L);
        request.setEmployeeEmail("employee@example.com");
    }

    @Test
    void testRecordStatusChange_ReturnsCreated() {
        doNothing().when(traceabilityServicePort).recordOrderStatusChange(
                request.getOrderId(),
                request.getClientId(),
                request.getClientEmail(),
                request.getPreviousStatus(),
                request.getNewStatus(),
                request.getEmployeeId(),
                request.getEmployeeEmail()
        );

        ResponseEntity<Void> response = controller.recordStatusChange(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(traceabilityServicePort, times(1)).recordOrderStatusChange(
                request.getOrderId(),
                request.getClientId(),
                request.getClientEmail(),
                request.getPreviousStatus(),
                request.getNewStatus(),
                request.getEmployeeId(),
                request.getEmployeeEmail()
        );
    }
}

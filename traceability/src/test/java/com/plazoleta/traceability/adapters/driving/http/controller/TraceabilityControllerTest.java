package com.plazoleta.traceability.adapters.driving.http.controller;

import com.plazoleta.traceability.adapters.driving.http.dto.response.GetOrderTraceabilityResponse;
import com.plazoleta.traceability.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.traceability.adapters.driving.http.mapper.ITraceabilityResponseMapper;
import com.plazoleta.traceability.domain.api.ITraceabilityServicePort;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TraceabilityControllerTest {

    @Mock
    private ITraceabilityServicePort traceabilityServicePort;

    @Mock
    private ITraceabilityResponseMapper traceabilityResponseMapper;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TraceabilityController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderTraceability_ReturnsPageResponse() {
        Long orderId = 1L;
        int page = 0;
        int size = 10;
        Long clientId = 123L;

        when(authentication.getName()).thenReturn(clientId.toString());

        // Usa el constructor existente de Page
        Page<OrderTraceability> traceabilityPage = new Page<>(
                Collections.emptyList(), page, size, 0L
        );

        // Crea una instancia válida de PageResponse
        PageResponse<GetOrderTraceabilityResponse> expectedResponse =
                new PageResponse<>(Collections.emptyList(), page, size, 0L, 0, false, false);

        when(traceabilityServicePort.getOrderTraceability(orderId, clientId, page, size))
                .thenReturn(traceabilityPage);

        when(traceabilityResponseMapper.toPageResponse(traceabilityPage))
                .thenReturn(expectedResponse);

        ResponseEntity<PageResponse<GetOrderTraceabilityResponse>> response =
                controller.getOrderTraceability(orderId, page, size, authentication);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());

        verify(traceabilityServicePort).getOrderTraceability(orderId, clientId, page, size);
        verify(traceabilityResponseMapper).toPageResponse(traceabilityPage);
    }
}

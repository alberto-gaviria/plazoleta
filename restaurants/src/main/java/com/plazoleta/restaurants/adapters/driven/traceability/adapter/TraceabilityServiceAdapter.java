package com.plazoleta.restaurants.adapters.driven.traceability.adapter;

import com.plazoleta.restaurants.adapters.driven.traceability.client.ITraceabilityServiceClient;
import com.plazoleta.restaurants.adapters.driven.traceability.dto.RecordStatusChangeRequest;
import com.plazoleta.restaurants.adapters.driven.traceability.util.TraceabilityAdapterConstants;
import com.plazoleta.restaurants.domain.api.ITraceabilityServicePort;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class TraceabilityServiceAdapter implements ITraceabilityServicePort {

    private static final Logger logger = LoggerFactory.getLogger(TraceabilityServiceAdapter.class);
    private final ITraceabilityServiceClient traceabilityServiceClient;

    public TraceabilityServiceAdapter(ITraceabilityServiceClient traceabilityServiceClient) {
        this.traceabilityServiceClient = traceabilityServiceClient;
    }

    @Override
    public void recordOrderStatusChange(Long orderId, Long clientId, String clientEmail,
                                        String previousStatus, String newStatus,
                                        Long employeeId, String employeeEmail) {
        try {
            RecordStatusChangeRequest request = new RecordStatusChangeRequest(
                    orderId, clientId, clientEmail, previousStatus, newStatus, employeeId, employeeEmail
            );

            ResponseEntity<Void> response = traceabilityServiceClient.recordStatusChange(request);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info(TraceabilityAdapterConstants.TRACEABILITY_SUCCESS, orderId);
            } else {
                logger.warn(TraceabilityAdapterConstants.TRACEABILITY_CLIENT_ERROR, orderId, response.getStatusCode());
            }

        } catch (FeignException e) {
            logger.error(TraceabilityAdapterConstants.TRACEABILITY_FEIGN_ERROR, orderId, e.getMessage());
        } catch (Exception e) {
            logger.error(TraceabilityAdapterConstants.TRACEABILITY_UNEXPECTED_ERROR, orderId, e.getMessage());
        }
    }
}

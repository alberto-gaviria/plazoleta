package com.plazoleta.traceability.domain.usecase;

import com.plazoleta.traceability.domain.api.ITraceabilityServicePort;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.spi.ITraceabilityPersistencePort;
import com.plazoleta.traceability.domain.util.DomainConstants;
import com.plazoleta.traceability.domain.util.exceptions.InvalidTraceabilityException;
import com.plazoleta.traceability.domain.util.paged.Page;

public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityPersistencePort traceabilityPersistencePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }

    @Override
    public void recordOrderStatusChange(Long orderId, Long clientId, String clientEmail,
                                        String previousStatus, String newStatus,
                                        Long employeeId, String employeeEmail) {
        validateRecordParameters(orderId, clientId, clientEmail, newStatus);

        OrderTraceability traceability = new OrderTraceability();
        traceability.setOrderId(orderId);
        traceability.setClientId(clientId);
        traceability.setClientEmail(clientEmail);
        traceability.setPreviousStatus(previousStatus);
        traceability.setNewStatus(newStatus);
        traceability.setEmployeeId(employeeId);
        traceability.setEmployeeEmail(employeeEmail);

        traceabilityPersistencePort.saveTraceability(traceability);
    }

    @Override
    public Page<OrderTraceability> getOrderTraceability(Long orderId, Long clientId, int pageNumber, int pageSize) {
        validateGetTraceabilityParameters(orderId, clientId, pageNumber, pageSize);

        if (!traceabilityPersistencePort.existsOrderForClient(orderId, clientId)) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_ORDER_NOT_BELONGS_TO_CLIENT);
        }

        return traceabilityPersistencePort.findByOrderIdAndClientId(orderId, clientId, pageNumber, pageSize);
    }

    private void validateRecordParameters(Long orderId, Long clientId, String clientEmail, String newStatus) {
        if (orderId == null) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_ORDER_ID_REQUIRED);
        }
        if (clientId == null) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_CLIENT_ID_REQUIRED);
        }
        if (clientEmail == null || clientEmail.trim().isEmpty()) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_CLIENT_EMAIL_REQUIRED);
        }
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_NEW_STATUS_REQUIRED);
        }
    }

    private void validateGetTraceabilityParameters(Long orderId, Long clientId, int pageNumber, int pageSize) {
        if (orderId == null) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_ORDER_ID_REQUIRED);
        }
        if (clientId == null) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_CLIENT_ID_REQUIRED);
        }
        if (pageNumber < DomainConstants.Traceability.MIN_PAGE_NUMBER) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_PAGE_NUMBER_INVALID);
        }
        if (pageSize < DomainConstants.Traceability.MIN_PAGE_SIZE) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_PAGE_SIZE_INVALID);
        }
        if (pageSize > DomainConstants.Traceability.MAX_PAGE_SIZE) {
            throw new InvalidTraceabilityException(DomainConstants.Traceability.ERROR_PAGE_SIZE_TOO_LARGE);
        }
    }
}
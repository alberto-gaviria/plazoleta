package com.plazoleta.traceability.domain.api;

import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.util.paged.Page;

public interface ITraceabilityServicePort {
    void recordOrderStatusChange(Long orderId, Long clientId, String clientEmail,
                                 String previousStatus, String newStatus,
                                 Long employeeId, String employeeEmail);
    Page<OrderTraceability> getOrderTraceability(Long orderId, Long clientId, int pageNumber, int pageSize);
}
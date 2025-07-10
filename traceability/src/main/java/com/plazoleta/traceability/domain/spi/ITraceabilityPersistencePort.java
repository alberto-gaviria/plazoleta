package com.plazoleta.traceability.domain.spi;

import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.util.paged.Page;

public interface ITraceabilityPersistencePort {
    OrderTraceability saveTraceability(OrderTraceability traceability);
    Page<OrderTraceability> findByOrderIdAndClientId(Long orderId, Long clientId, int pageNumber, int pageSize);
    boolean existsOrderForClient(Long orderId, Long clientId);
}
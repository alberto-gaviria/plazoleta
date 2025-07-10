package com.plazoleta.restaurants.domain.api;

public interface ITraceabilityServicePort {
    void recordOrderStatusChange(Long orderId, Long clientId, String clientEmail,
                                 String previousStatus, String newStatus,
                                 Long employeeId, String employeeEmail);
}

package com.plazoleta.traceability.adapters.driving.http.dto.response;

import java.time.LocalDateTime;

public class GetOrderTraceabilityResponse {
    private String id;
    private Long orderId;
    private LocalDateTime timestamp;
    private String previousStatus;
    private String newStatus;
    private String employeeEmail;

    public GetOrderTraceabilityResponse() {}

    public GetOrderTraceabilityResponse(String id, Long orderId, LocalDateTime timestamp,
                                        String previousStatus, String newStatus, String employeeEmail) {
        this.id = id;
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.employeeEmail = employeeEmail;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getPreviousStatus() { return previousStatus; }
    public void setPreviousStatus(String previousStatus) { this.previousStatus = previousStatus; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }
}
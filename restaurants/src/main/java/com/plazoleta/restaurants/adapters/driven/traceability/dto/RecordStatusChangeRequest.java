package com.plazoleta.restaurants.adapters.driven.traceability.dto;

public class RecordStatusChangeRequest {
    private Long orderId;
    private Long clientId;
    private String clientEmail;
    private String previousStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;

    public RecordStatusChangeRequest() {}

    public RecordStatusChangeRequest(Long orderId, Long clientId, String clientEmail,
                                     String previousStatus, String newStatus,
                                     Long employeeId, String employeeEmail) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.clientEmail = clientEmail;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }

    public String getPreviousStatus() { return previousStatus; }
    public void setPreviousStatus(String previousStatus) { this.previousStatus = previousStatus; }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) { this.newStatus = newStatus; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeEmail() { return employeeEmail; }
    public void setEmployeeEmail(String employeeEmail) { this.employeeEmail = employeeEmail; }

    @Override
    public String toString() {
        return "RecordStatusChangeRequest{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", clientEmail='" + clientEmail + '\'' +
                ", previousStatus='" + previousStatus + '\'' +
                ", newStatus='" + newStatus + '\'' +
                ", employeeId=" + employeeId +
                ", employeeEmail='" + employeeEmail + '\'' +
                '}';
    }
}

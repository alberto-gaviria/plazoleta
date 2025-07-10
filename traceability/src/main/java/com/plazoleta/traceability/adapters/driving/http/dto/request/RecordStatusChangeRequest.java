package com.plazoleta.traceability.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class RecordStatusChangeRequest {

    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser un número positivo")
    private Long orderId;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Long clientId;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$",
            message = "El email del cliente debe tener un formato válido")
    private String clientEmail;

    private String previousStatus;

    @NotBlank(message = "El nuevo estado es obligatorio")
    @Pattern(regexp = "^(PENDIENTE|EN_PREPARACION|LISTO|ENTREGADO|CANCELADO)$",
            message = "El estado debe ser uno de: PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO")
    private String newStatus;

    @Positive(message = "El ID del empleado debe ser un número positivo")
    private Long employeeId;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$",
            message = "El email del empleado debe tener un formato válido")
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
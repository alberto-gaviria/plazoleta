package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;

public class AssignEmployeeToOrderRequest {

    @NotNull(message = "El ID del pedido es obligatorio")
    private Long idPedido;

    public AssignEmployeeToOrderRequest() {}

    public AssignEmployeeToOrderRequest(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
}
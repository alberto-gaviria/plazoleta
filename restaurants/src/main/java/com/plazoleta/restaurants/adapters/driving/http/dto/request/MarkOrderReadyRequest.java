package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MarkOrderReadyRequest {

    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser un número positivo")
    private Long idPedido;

    public MarkOrderReadyRequest() {}

    public MarkOrderReadyRequest(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }
}
package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import com.plazoleta.restaurants.adapters.driving.http.util.HttpConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class DeliverOrderRequest {

    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser un número positivo")
    private Long idPedido;

    @NotBlank(message = "El PIN de seguridad es obligatorio")
    @Pattern(regexp = HttpConstants.ValidationPatterns.PIN_PATTERN, message = "El PIN debe tener exactamente 4 dígitos")
    private String pinSeguridad;

    public DeliverOrderRequest() {}

    public DeliverOrderRequest(Long idPedido, String pinSeguridad) {
        this.idPedido = idPedido;
        this.pinSeguridad = pinSeguridad;
    }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public String getPinSeguridad() { return pinSeguridad; }
    public void setPinSeguridad(String pinSeguridad) { this.pinSeguridad = pinSeguridad; }
}
package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CreateOrderRequest {

    @NotNull(message = "El restaurante es obligatorio")
    private Long idRestaurante;

    @NotEmpty(message = "Los platos son obligatorios")
    @Valid
    private List<OrderDishRequest> platos;

    public CreateOrderRequest() {}

    public CreateOrderRequest(Long idRestaurante, List<OrderDishRequest> platos) {
        this.idRestaurante = idRestaurante;
        this.platos = platos;
    }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public List<OrderDishRequest> getPlatos() { return platos; }
    public void setPlatos(List<OrderDishRequest> platos) { this.platos = platos; }
}
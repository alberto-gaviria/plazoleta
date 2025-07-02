package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UpdateDishRequest {

    @NotNull(message = "El precio del plato es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio del plato debe ser un número positivo y mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe ser un número válido")
    private BigDecimal precio;

    @NotBlank(message = "La descripción del plato es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    public UpdateDishRequest() {}

    public UpdateDishRequest(BigDecimal precio, String descripcion) {
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
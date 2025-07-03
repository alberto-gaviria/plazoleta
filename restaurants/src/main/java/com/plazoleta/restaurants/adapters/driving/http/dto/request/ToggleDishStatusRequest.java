package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;

public class ToggleDishStatusRequest {

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

    public ToggleDishStatusRequest() {}

    public ToggleDishStatusRequest(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

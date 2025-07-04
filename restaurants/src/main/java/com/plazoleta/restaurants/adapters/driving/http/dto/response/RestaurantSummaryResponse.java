package com.plazoleta.restaurants.adapters.driving.http.dto.response;

public class RestaurantSummaryResponse {
    private String nombre;
    private String urlLogo;

    public RestaurantSummaryResponse() {}

    public RestaurantSummaryResponse(String nombre, String urlLogo) {
        this.nombre = nombre;
        this.urlLogo = urlLogo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUrlLogo() { return urlLogo; }
    public void setUrlLogo(String urlLogo) { this.urlLogo = urlLogo; }
}

package com.plazoleta.restaurants.adapters.driving.http.dto.response;

public class OrderDishResponse {
    private Long id;
    private Long idPlato;
    private Integer cantidad;

    public OrderDishResponse() {}

    public OrderDishResponse(Long id, Long idPlato, Integer cantidad) {
        this.id = id;
        this.idPlato = idPlato;
        this.cantidad = cantidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPlato() { return idPlato; }
    public void setIdPlato(Long idPlato) { this.idPlato = idPlato; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
package com.plazoleta.restaurants.domain.model;

public class OrderDish {
    private Long id;
    private Long idPedido;
    private Long idPlato;
    private Integer cantidad;

    public OrderDish() {}

    public OrderDish(Long id, Long idPedido, Long idPlato, Integer cantidad) {
        this.id = id;
        this.idPedido = idPedido;
        this.idPlato = idPlato;
        this.cantidad = cantidad;
    }

    public OrderDish(Long idPlato, Integer cantidad) {
        this.idPlato = idPlato;
        this.cantidad = cantidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdPedido() { return idPedido; }
    public void setIdPedido(Long idPedido) { this.idPedido = idPedido; }

    public Long getIdPlato() { return idPlato; }
    public void setIdPlato(Long idPlato) { this.idPlato = idPlato; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
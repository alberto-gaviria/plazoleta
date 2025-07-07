package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long idCliente;
    private LocalDateTime fecha;
    private String estado;
    private Long idRestaurante;
    private List<OrderDishResponse> platos;

    public OrderResponse() {}

    public OrderResponse(Long id, Long idCliente, LocalDateTime fecha, String estado,
                         Long idRestaurante, List<OrderDishResponse> platos) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
        this.idRestaurante = idRestaurante;
        this.platos = platos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public List<OrderDishResponse> getPlatos() { return platos; }
    public void setPlatos(List<OrderDishResponse> platos) { this.platos = platos; }
}
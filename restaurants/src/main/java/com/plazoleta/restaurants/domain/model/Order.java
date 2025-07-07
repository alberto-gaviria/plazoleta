package com.plazoleta.restaurants.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long idCliente;
    private LocalDateTime fecha;
    private OrderStatus estado;
    private Long idEmpleado;
    private Long idRestaurante;
    private String pinSeguridad;
    private List<OrderDish> platos;

    public Order() {
        this.fecha = LocalDateTime.now();
        this.estado = OrderStatus.PENDIENTE;
    }

    public Order(Long id, Long idCliente, LocalDateTime fecha, OrderStatus estado,
                 Long idEmpleado, Long idRestaurante, String pinSeguridad, List<OrderDish> platos) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha != null ? fecha : LocalDateTime.now();
        this.estado = estado != null ? estado : OrderStatus.PENDIENTE;
        this.idEmpleado = idEmpleado;
        this.idRestaurante = idRestaurante;
        this.pinSeguridad = pinSeguridad;
        this.platos = platos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public OrderStatus getEstado() { return estado; }
    public void setEstado(OrderStatus estado) { this.estado = estado; }

    public Long getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(Long idEmpleado) { this.idEmpleado = idEmpleado; }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public String getPinSeguridad() { return pinSeguridad; }
    public void setPinSeguridad(String pinSeguridad) { this.pinSeguridad = pinSeguridad; }

    public List<OrderDish> getPlatos() { return platos; }
    public void setPlatos(List<OrderDish> platos) { this.platos = platos; }
}
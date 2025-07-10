package com.plazoleta.restaurants.domain.model;

import java.time.LocalDateTime;

public class OrderEfficiency {
    private Long orderId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long tiempoTotalMinutos;
    private Long empleadoId;
    private String empleadoEmail;
    private String estado;

    public OrderEfficiency() {}

    public OrderEfficiency(Long orderId, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                           Long tiempoTotalMinutos, Long empleadoId, String empleadoEmail, String estado) {
        this.orderId = orderId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tiempoTotalMinutos = tiempoTotalMinutos;
        this.empleadoId = empleadoId;
        this.empleadoEmail = empleadoEmail;
        this.estado = estado;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public Long getTiempoTotalMinutos() { return tiempoTotalMinutos; }
    public void setTiempoTotalMinutos(Long tiempoTotalMinutos) { this.tiempoTotalMinutos = tiempoTotalMinutos; }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public String getEmpleadoEmail() { return empleadoEmail; }
    public void setEmpleadoEmail(String empleadoEmail) { this.empleadoEmail = empleadoEmail; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
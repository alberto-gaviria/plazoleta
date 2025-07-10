package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import java.time.LocalDateTime;

public class OrderEfficiencyResponse {
    private Long orderId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String tiempoTotal;
    private Long tiempoTotalMinutos;
    private String empleadoEmail;
    private String estado;

    public OrderEfficiencyResponse() {}

    public OrderEfficiencyResponse(Long orderId, LocalDateTime fechaInicio, LocalDateTime fechaFin,
                                   String tiempoTotal, Long tiempoTotalMinutos, String empleadoEmail, String estado) {
        this.orderId = orderId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tiempoTotal = tiempoTotal;
        this.tiempoTotalMinutos = tiempoTotalMinutos;
        this.empleadoEmail = empleadoEmail;
        this.estado = estado;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public String getTiempoTotal() { return tiempoTotal; }
    public void setTiempoTotal(String tiempoTotal) { this.tiempoTotal = tiempoTotal; }

    public Long getTiempoTotalMinutos() { return tiempoTotalMinutos; }
    public void setTiempoTotalMinutos(Long tiempoTotalMinutos) { this.tiempoTotalMinutos = tiempoTotalMinutos; }

    public String getEmpleadoEmail() { return empleadoEmail; }
    public void setEmpleadoEmail(String empleadoEmail) { this.empleadoEmail = empleadoEmail; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
package com.plazoleta.restaurants.domain.model;

public class EmployeeEfficiency {
    private Long empleadoId;
    private String empleadoNombre;
    private String empleadoEmail;
    private Integer totalPedidos;
    private Double tiempoPromedioMinutos;
    private Integer ranking;

    public EmployeeEfficiency() {}

    public EmployeeEfficiency(Long empleadoId, String empleadoNombre, String empleadoEmail,
                              Integer totalPedidos, Double tiempoPromedioMinutos) {
        this.empleadoId = empleadoId;
        this.empleadoNombre = empleadoNombre;
        this.empleadoEmail = empleadoEmail;
        this.totalPedidos = totalPedidos;
        this.tiempoPromedioMinutos = tiempoPromedioMinutos;
    }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public String getEmpleadoNombre() { return empleadoNombre; }
    public void setEmpleadoNombre(String empleadoNombre) { this.empleadoNombre = empleadoNombre; }

    public String getEmpleadoEmail() { return empleadoEmail; }
    public void setEmpleadoEmail(String empleadoEmail) { this.empleadoEmail = empleadoEmail; }

    public Integer getTotalPedidos() { return totalPedidos; }
    public void setTotalPedidos(Integer totalPedidos) { this.totalPedidos = totalPedidos; }

    public Double getTiempoPromedioMinutos() { return tiempoPromedioMinutos; }
    public void setTiempoPromedioMinutos(Double tiempoPromedioMinutos) { this.tiempoPromedioMinutos = tiempoPromedioMinutos; }

    public Integer getRanking() { return ranking; }
    public void setRanking(Integer ranking) { this.ranking = ranking; }
}
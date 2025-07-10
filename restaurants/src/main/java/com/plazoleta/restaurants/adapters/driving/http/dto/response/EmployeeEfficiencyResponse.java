package com.plazoleta.restaurants.adapters.driving.http.dto.response;

public class EmployeeEfficiencyResponse {
    private Long empleadoId;
    private String empleadoNombre;
    private String empleadoEmail;
    private Integer totalPedidos;
    private String tiempoPromedioFormateado;
    private Double tiempoPromedioMinutos;
    private Integer ranking;

    public EmployeeEfficiencyResponse() {}

    public EmployeeEfficiencyResponse(Long empleadoId, String empleadoNombre, String empleadoEmail,
                                      Integer totalPedidos, String tiempoPromedioFormateado,
                                      Double tiempoPromedioMinutos, Integer ranking) {
        this.empleadoId = empleadoId;
        this.empleadoNombre = empleadoNombre;
        this.empleadoEmail = empleadoEmail;
        this.totalPedidos = totalPedidos;
        this.tiempoPromedioFormateado = tiempoPromedioFormateado;
        this.tiempoPromedioMinutos = tiempoPromedioMinutos;
        this.ranking = ranking;
    }

    public Long getEmpleadoId() { return empleadoId; }
    public void setEmpleadoId(Long empleadoId) { this.empleadoId = empleadoId; }

    public String getEmpleadoNombre() { return empleadoNombre; }
    public void setEmpleadoNombre(String empleadoNombre) { this.empleadoNombre = empleadoNombre; }

    public String getEmpleadoEmail() { return empleadoEmail; }
    public void setEmpleadoEmail(String empleadoEmail) { this.empleadoEmail = empleadoEmail; }

    public Integer getTotalPedidos() { return totalPedidos; }
    public void setTotalPedidos(Integer totalPedidos) { this.totalPedidos = totalPedidos; }

    public String getTiempoPromedioFormateado() { return tiempoPromedioFormateado; }
    public void setTiempoPromedioFormateado(String tiempoPromedioFormateado) { this.tiempoPromedioFormateado = tiempoPromedioFormateado; }

    public Double getTiempoPromedioMinutos() { return tiempoPromedioMinutos; }
    public void setTiempoPromedioMinutos(Double tiempoPromedioMinutos) { this.tiempoPromedioMinutos = tiempoPromedioMinutos; }

    public Integer getRanking() { return ranking; }
    public void setRanking(Integer ranking) { this.ranking = ranking; }
}
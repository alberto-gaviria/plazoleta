package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import java.util.List;

public class RestaurantEfficiencyResponse {
    private Long restauranteId;
    private String restauranteNombre;
    private Integer totalPedidosCompletados;
    private String tiempoPromedioGeneral;
    private Double tiempoPromedioGeneralMinutos;
    private List<OrderEfficiencyResponse> pedidos;
    private List<EmployeeEfficiencyResponse> rankingEmpleados;

    public RestaurantEfficiencyResponse() {}

    public RestaurantEfficiencyResponse(Long restauranteId, String restauranteNombre,
                                        Integer totalPedidosCompletados, String tiempoPromedioGeneral,
                                        Double tiempoPromedioGeneralMinutos, List<OrderEfficiencyResponse> pedidos,
                                        List<EmployeeEfficiencyResponse> rankingEmpleados) {
        this.restauranteId = restauranteId;
        this.restauranteNombre = restauranteNombre;
        this.totalPedidosCompletados = totalPedidosCompletados;
        this.tiempoPromedioGeneral = tiempoPromedioGeneral;
        this.tiempoPromedioGeneralMinutos = tiempoPromedioGeneralMinutos;
        this.pedidos = pedidos;
        this.rankingEmpleados = rankingEmpleados;
    }

    public Long getRestauranteId() { return restauranteId; }
    public void setRestauranteId(Long restauranteId) { this.restauranteId = restauranteId; }

    public String getRestauranteNombre() { return restauranteNombre; }
    public void setRestauranteNombre(String restauranteNombre) { this.restauranteNombre = restauranteNombre; }

    public Integer getTotalPedidosCompletados() { return totalPedidosCompletados; }
    public void setTotalPedidosCompletados(Integer totalPedidosCompletados) { this.totalPedidosCompletados = totalPedidosCompletados; }

    public String getTiempoPromedioGeneral() { return tiempoPromedioGeneral; }
    public void setTiempoPromedioGeneral(String tiempoPromedioGeneral) { this.tiempoPromedioGeneral = tiempoPromedioGeneral; }

    public Double getTiempoPromedioGeneralMinutos() { return tiempoPromedioGeneralMinutos; }
    public void setTiempoPromedioGeneralMinutos(Double tiempoPromedioGeneralMinutos) { this.tiempoPromedioGeneralMinutos = tiempoPromedioGeneralMinutos; }

    public List<OrderEfficiencyResponse> getPedidos() { return pedidos; }
    public void setPedidos(List<OrderEfficiencyResponse> pedidos) { this.pedidos = pedidos; }

    public List<EmployeeEfficiencyResponse> getRankingEmpleados() { return rankingEmpleados; }
    public void setRankingEmpleados(List<EmployeeEfficiencyResponse> rankingEmpleados) { this.rankingEmpleados = rankingEmpleados; }
}
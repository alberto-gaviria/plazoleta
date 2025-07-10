package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.EmployeeEfficiencyResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderEfficiencyResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantEfficiencyResponse;
import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Duration;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IEfficiencyResponseMapper {

    @Mapping(target = "tiempoTotal", expression = "java(formatDuration(orderEfficiency))")
    @Mapping(target = "empleadoEmail", source = "empleadoEmail")
    OrderEfficiencyResponse toOrderResponse(OrderEfficiency orderEfficiency);

    @Mapping(target = "tiempoPromedioFormateado", expression = "java(formatMinutesToDuration(employeeEfficiency.getTiempoPromedioMinutos()))")
    @Mapping(target = "ranking", ignore = true)
    EmployeeEfficiencyResponse toEmployeeResponse(EmployeeEfficiency employeeEfficiency);

    List<OrderEfficiencyResponse> toOrderResponseList(List<OrderEfficiency> orders);
    List<EmployeeEfficiencyResponse> toEmployeeResponseList(List<EmployeeEfficiency> employees);

    default RestaurantEfficiencyResponse toRestaurantResponse(Long restaurantId, String restaurantName,
                                                              List<OrderEfficiency> orders,
                                                              List<EmployeeEfficiency> employees) {

        List<OrderEfficiencyResponse> orderResponses = toOrderResponseList(orders);
        List<EmployeeEfficiencyResponse> employeeResponses = toEmployeeResponseList(employees);

        for (int i = 0; i < employeeResponses.size(); i++) {
            employeeResponses.get(i).setRanking(i + 1);
        }

        int totalPedidos = orders.size();
        double tiempoPromedioGeneral = orders.stream()
                .mapToLong(OrderEfficiency::getTiempoTotalMinutos)
                .average()
                .orElse(0.0);

        String tiempoPromedioGeneralFormatted = formatMinutesToDuration(tiempoPromedioGeneral);

        return new RestaurantEfficiencyResponse(
                restaurantId,
                restaurantName,
                totalPedidos,
                tiempoPromedioGeneralFormatted,
                tiempoPromedioGeneral,
                orderResponses,
                employeeResponses
        );
    }

    default String formatDuration(OrderEfficiency orderEfficiency) {
        if (orderEfficiency.getTiempoTotalMinutos() == null) {
            return "0 minutos";
        }
        return formatMinutesToDuration(orderEfficiency.getTiempoTotalMinutos().doubleValue());
    }

    default String formatMinutesToDuration(Double totalMinutes) {
        if (totalMinutes == null || totalMinutes <= 0) {
            return "0 minutos";
        }

        long minutes = totalMinutes.longValue();
        long seconds = Math.round((totalMinutes - minutes) * 60);

        if (minutes == 0) {
            return seconds + " segundos";
        } else if (seconds == 0) {
            return minutes + " minutos";
        } else {
            return minutes + " minutos " + seconds + " segundos";
        }
    }
}
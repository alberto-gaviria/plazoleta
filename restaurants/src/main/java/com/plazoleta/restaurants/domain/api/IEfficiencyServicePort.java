package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;

import java.util.List;

public interface IEfficiencyServicePort {
    List<OrderEfficiency> getOrdersEfficiency(Long restaurantId, Long ownerId);
    List<EmployeeEfficiency> getEmployeesEfficiencyRanking(Long restaurantId, Long ownerId);
}
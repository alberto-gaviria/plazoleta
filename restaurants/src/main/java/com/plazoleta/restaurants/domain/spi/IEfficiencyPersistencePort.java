package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.EmployeeEfficiency;
import com.plazoleta.restaurants.domain.model.OrderEfficiency;
import com.plazoleta.restaurants.domain.model.UserInfo;

import java.util.List;
import java.util.Optional;

public interface IEfficiencyPersistencePort {
    List<OrderEfficiency> findOrdersEfficiencyByRestaurant(Long restaurantId);
    List<EmployeeEfficiency> findEmployeesEfficiencyByRestaurant(Long restaurantId);
    Optional<Long> getRestaurantOwnerId(Long restaurantId);
    Optional<UserInfo> getUserInfo(Long userId);
    Optional<String> getRestaurantName(Long restaurantId);
}
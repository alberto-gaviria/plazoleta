package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.util.paged.Page;

public interface IOrderPersistencePort {
    Order saveOrder(Order order);
    boolean hasActiveOrderForClient(Long clientId);
    boolean existsDishById(Long dishId);
    Long getDishRestaurantId(Long dishId);
    boolean isDishActive(Long dishId);
    Page<Order> findOrdersByRestaurantAndStatus(Long restaurantId, OrderStatus estado, int pageNumber, int pageSize);
    Long getEmployeeRestaurantId(Long employeeId);
}
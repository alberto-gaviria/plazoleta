package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.util.paged.Page;

import java.util.Optional;

public interface IOrderPersistencePort {

    Order saveOrder(Order order);
    Order updateOrder(Order order);
    Optional<Order> findOrderById(Long orderId);
    Page<Order> findOrdersByRestaurantAndStatus(Long restaurantId, OrderStatus status, int pageNumber, int pageSize);
    boolean hasActiveOrderForClient(Long clientId);
    boolean existsDishById(Long dishId);
    boolean isDishActive(Long dishId);
    Long getDishRestaurantId(Long dishId);
    Long getEmployeeRestaurantId(Long employeeId);
    String getClientPhoneByOrderId(Long orderId);
    String getRestaurantNameByOrderId(Long orderId);
    String getClientEmailById(Long clientId);
    String getEmployeeEmailById(Long employeeId);

}
package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.util.paged.Page;

public interface IOrderServicePort {
    Order createOrder(Order order, Long clientId);
    Page<Order> getOrdersByEmployeeAndStatus(Long employeeId, OrderStatus estado, int pageNumber, int pageSize);
    Order assignEmployeeToOrder(Long orderId, Long employeeId);
    Order markOrderAsReady(Long orderId, Long employeeId);
    Order deliverOrder(Long orderId, String pin, Long employeeId);
    Order cancelOrder(Long orderId, Long clientId);
}
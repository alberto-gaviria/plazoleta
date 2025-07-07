package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Order;

public interface IOrderServicePort {
    Order createOrder(Order order, Long clientId);
}
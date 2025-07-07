package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderDishResponse;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderResponseMapper {

    @Mapping(source = "estado", target = "estado")
    OrderResponse orderToResponse(Order order);

    OrderDishResponse orderDishToResponse(OrderDish orderDish);

    default String mapOrderStatus(com.plazoleta.restaurants.domain.model.OrderStatus status) {
        return status != null ? status.name() : null;
    }
}
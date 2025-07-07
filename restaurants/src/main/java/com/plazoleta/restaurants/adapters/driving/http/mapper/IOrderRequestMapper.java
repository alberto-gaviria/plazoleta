package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.CreateOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.OrderDishRequest;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idCliente", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "idEmpleado", ignore = true)
    @Mapping(target = "pinSeguridad", ignore = true)
    Order createRequestToOrder(CreateOrderRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idPedido", ignore = true)
    OrderDish orderDishRequestToOrderDish(OrderDishRequest request);
}
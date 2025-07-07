package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderEntity;
import com.plazoleta.restaurants.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderEntityMapper {

    @Mapping(target = "platos", ignore = true)
    Order toModel(OrderEntity orderEntity);

    @Mapping(target = "id", ignore = true)
    OrderEntity toEntity(Order order);
}
package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderDishEntity;
import com.plazoleta.restaurants.domain.model.OrderDish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IOrderDishEntityMapper {

    OrderDish toModel(OrderDishEntity orderDishEntity);

    OrderDishEntity toEntity(OrderDish orderDish);

    List<OrderDish> toModelList(List<OrderDishEntity> orderDishEntities);

    List<OrderDishEntity> toEntityList(List<OrderDish> orderDishes);
}
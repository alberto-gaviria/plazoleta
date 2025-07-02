package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.domain.model.Dish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDishEntityMapper {

    Dish toModel(DishEntity dishEntity);

    DishEntity toEntity(Dish dish);

    List<Dish> toModelList(List<DishEntity> dishEntities);
}
package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRestaurantEntityMapper {

    Restaurant toModel(RestaurantEntity restaurantEntity);

    RestaurantEntity toEntity(Restaurant restaurant);

    List<Restaurant> toModelList(List<RestaurantEntity> restaurantEntities);
}
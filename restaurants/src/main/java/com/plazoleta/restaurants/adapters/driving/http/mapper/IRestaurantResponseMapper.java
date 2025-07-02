package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRestaurantResponseMapper {

    RestaurantResponse restaurantToResponse(Restaurant restaurant);

    List<RestaurantResponse> toRestaurantResponseList(List<Restaurant> restaurants);
}
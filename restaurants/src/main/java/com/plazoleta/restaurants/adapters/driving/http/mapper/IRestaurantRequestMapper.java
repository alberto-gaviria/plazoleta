package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRestaurantRequestMapper {

    @Mapping(target = "id", ignore = true)
    Restaurant addRequestToRestaurant(AddRestaurantRequest request);
}
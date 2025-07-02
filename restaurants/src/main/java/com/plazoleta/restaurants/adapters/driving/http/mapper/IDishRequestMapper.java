package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddDishRequest;
import com.plazoleta.restaurants.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDishRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", constant = "true")
    Dish addRequestToDish(AddDishRequest request);
}
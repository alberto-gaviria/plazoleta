package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.DishResponse;
import com.plazoleta.restaurants.domain.model.Dish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDishResponseMapper {

    DishResponse dishToResponse(Dish dish);

    List<DishResponse> dishListToResponseList(List<Dish> dishes);
}
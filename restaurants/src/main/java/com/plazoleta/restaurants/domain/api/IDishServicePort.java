package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Dish;

public interface IDishServicePort {
    void saveDish(Dish dish, Long currentUserId);
}
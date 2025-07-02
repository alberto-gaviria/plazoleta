package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Dish;

import java.math.BigDecimal;

public interface IDishServicePort {
    void saveDish(Dish dish, Long currentUserId);
    Dish updateDish(Long dishId, BigDecimal precio, String descripcion, Long currentUserId);
}
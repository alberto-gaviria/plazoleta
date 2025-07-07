package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import com.plazoleta.restaurants.domain.util.paged.Page;

import java.math.BigDecimal;

public interface IDishServicePort {
    Dish saveDish(Dish dish, Long currentUserId);
    Dish updateDish(Long dishId, BigDecimal precio, String descripcion, Long currentUserId);
    Dish toggleDishStatus(Long dishId, Boolean activo, Long currentUserId);
    Page<DishWithCategory> getDishesByRestaurant(Long restaurantId, Long categoryId, int pageNumber, int pageSize);
}
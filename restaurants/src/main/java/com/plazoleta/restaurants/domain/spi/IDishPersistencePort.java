package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Dish;

import java.util.Optional;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    boolean existsRestaurantById(Long restaurantId);
    Long getRestaurantOwnerId(Long restaurantId);
    Optional<Dish> findDishById(Long dishId);
    void updateDish(Dish dish);
    Long getDishRestaurantId(Long dishId);
}
package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    boolean existsRestaurantById(Long restaurantId);
    Long getRestaurantOwnerId(Long restaurantId);
}
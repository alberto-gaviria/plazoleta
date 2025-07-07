package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import com.plazoleta.restaurants.domain.util.paged.Page;

import java.util.Optional;

public interface IDishPersistencePort {
    Dish saveDish(Dish dish);
    boolean existsRestaurantById(Long restaurantId);
    Long getRestaurantOwnerId(Long restaurantId);
    Optional<Dish> findDishById(Long dishId);
    Dish updateDish(Dish dish);
    Long getDishRestaurantId(Long dishId);
    Page<DishWithCategory> findDishesByRestaurant(Long restaurantId, Long categoryId, int pageNumber, int pageSize);
}
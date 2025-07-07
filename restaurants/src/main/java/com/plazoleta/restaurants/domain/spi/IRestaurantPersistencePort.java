package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;

public interface IRestaurantPersistencePort {
    Restaurant saveRestaurant(Restaurant restaurant);
    Page<RestaurantSummary> findAllRestaurantsSorted(int pageNumber, int pageSize);
}
package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant, Long adminId);
    Page<RestaurantSummary> getAllRestaurants(int pageNumber, int pageSize);
}
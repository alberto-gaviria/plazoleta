package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant, Long adminId);
}

package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Restaurant;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
}
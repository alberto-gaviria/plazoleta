package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IRestaurantEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.RestaurantAlreadyExistsException;

import java.util.Optional;

public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    public RestaurantMysqlAdapter(IRestaurantRepository restaurantRepository,
                                  IRestaurantEntityMapper restaurantEntityMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantEntityMapper = restaurantEntityMapper;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        Optional<RestaurantEntity> existingByNit = restaurantRepository.findByNit(restaurant.getNit());
        if (existingByNit.isPresent()) {
            throw new RestaurantAlreadyExistsException(AdapterConstants.ErrorMessages.RESTAURANT_NIT_DUPLICADO);
        }

        Optional<RestaurantEntity> existingByNombre = restaurantRepository.findByNombre(restaurant.getNombre());
        if (existingByNombre.isPresent()) {
            throw new RestaurantAlreadyExistsException(AdapterConstants.ErrorMessages.RESTAURANT_NOMBRE_DUPLICADO);
        }

        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);
        restaurantRepository.save(restaurantEntity);
    }
}

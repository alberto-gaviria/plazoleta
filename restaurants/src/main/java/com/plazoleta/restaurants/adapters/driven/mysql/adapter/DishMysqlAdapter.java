package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;

import java.util.Optional;

public class DishMysqlAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IDishEntityMapper dishEntityMapper;

    public DishMysqlAdapter(IDishRepository dishRepository,
                            IRestaurantRepository restaurantRepository,
                            IDishEntityMapper dishEntityMapper) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishEntityMapper = dishEntityMapper;
    }

    @Override
    public void saveDish(Dish dish) {
        DishEntity dishEntity = dishEntityMapper.toEntity(dish);
        dishRepository.save(dishEntity);
    }

    @Override
    public boolean existsRestaurantById(Long restaurantId) {
        return restaurantRepository.existsById(restaurantId);
    }

    @Override
    public Long getRestaurantOwnerId(Long restaurantId) {
        Optional<RestaurantEntity> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            return restaurant.get().getIdPropietario();
        }
        throw new ElementNotFoundException(AdapterConstants.ErrorMessages.RESTAURANT_NO_ENCONTRADO);
    }
}
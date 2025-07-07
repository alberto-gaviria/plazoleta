package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IRestaurantEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.RestaurantAlreadyExistsException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    public RestaurantMysqlAdapter(IRestaurantRepository restaurantRepository,
                                  IRestaurantEntityMapper restaurantEntityMapper) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantEntityMapper = restaurantEntityMapper;
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        Optional<RestaurantEntity> existingByNit = restaurantRepository.findByNit(restaurant.getNit());
        if (existingByNit.isPresent()) {
            throw new RestaurantAlreadyExistsException(AdapterConstants.ErrorMessages.RESTAURANT_NIT_DUPLICADO);
        }

        Optional<RestaurantEntity> existingByNombre = restaurantRepository.findByNombre(restaurant.getNombre());
        if (existingByNombre.isPresent()) {
            throw new RestaurantAlreadyExistsException(AdapterConstants.ErrorMessages.RESTAURANT_NOMBRE_DUPLICADO);
        }

        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);
        RestaurantEntity savedEntity = restaurantRepository.save(restaurantEntity);
        return restaurantEntityMapper.toModel(savedEntity);
    }

    @Override
    public Page<RestaurantSummary> findAllRestaurantsSorted(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                                           Sort.by(AdapterConstants.DatabaseColumns.NOMBRE_COLUMN).ascending());
        org.springframework.data.domain.Page<RestaurantEntity> springPage = restaurantRepository.findAll(pageable);

        List<RestaurantSummary> content = springPage.getContent()
                .stream()
                .map(entity -> new RestaurantSummary(entity.getNombre(), entity.getUrlLogo()))
                .toList();

        return new Page<>(
                content,
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements()
        );
    }
}
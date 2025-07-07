package com.plazoleta.restaurants.infrastructure.configuration;

import com.plazoleta.restaurants.adapters.driven.mysql.adapter.DishMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.RestaurantMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.ICategoryEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IDishWithCategoryMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IRestaurantEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.ICategoryRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.usecase.DishUseCase;
import com.plazoleta.restaurants.domain.usecase.RestaurantUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(
            IRestaurantRepository restaurantRepository,
            IRestaurantEntityMapper restaurantEntityMapper) {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort(
            IRestaurantPersistencePort restaurantPersistencePort) {
        return new RestaurantUseCase(restaurantPersistencePort);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(
            IDishRepository dishRepository,
            IRestaurantRepository restaurantRepository,
            ICategoryRepository categoryRepository,
            IDishEntityMapper dishEntityMapper,
            ICategoryEntityMapper categoryEntityMapper,
            IDishWithCategoryMapper dishWithCategoryMapper) {
        return new DishMysqlAdapter(dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);
    }

    @Bean
    public IDishServicePort dishServicePort(
            IDishPersistencePort dishPersistencePort) {
        return new DishUseCase(dishPersistencePort);
    }
}
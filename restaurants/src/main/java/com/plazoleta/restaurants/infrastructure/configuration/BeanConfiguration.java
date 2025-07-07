package com.plazoleta.restaurants.infrastructure.configuration;

import com.plazoleta.restaurants.adapters.driven.mysql.adapter.DishMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.OrderMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.RestaurantMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.*;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.*;
import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.usecase.DishUseCase;
import com.plazoleta.restaurants.domain.usecase.OrderUseCase;
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

    @Bean
    public IOrderPersistencePort orderPersistencePort(
            IOrderRepository orderRepository,
            IOrderDishRepository orderDishRepository,
            IDishRepository dishRepository,
            IEmployeeRestaurantRepository employeeRestaurantRepository,
            IOrderEntityMapper orderEntityMapper,
            IOrderDishEntityMapper orderDishEntityMapper) {
        return new OrderMysqlAdapter(
                orderRepository,
                orderDishRepository,
                dishRepository,
                employeeRestaurantRepository,
                orderEntityMapper,
                orderDishEntityMapper
        );
    }

    @Bean
    public IOrderServicePort orderServicePort(IOrderPersistencePort orderPersistencePort) {
        return new OrderUseCase(orderPersistencePort);
    }
}
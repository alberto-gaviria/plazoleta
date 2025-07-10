package com.plazoleta.restaurants.infrastructure.configuration;

import com.plazoleta.restaurants.adapters.driven.messaging.adapter.MessagingServiceAdapter;
import com.plazoleta.restaurants.adapters.driven.messaging.client.IMessagingServiceClient;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.DishMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.OrderMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.RestaurantMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.*;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.*;
import com.plazoleta.restaurants.adapters.driven.traceability.adapter.TraceabilityServiceAdapter;
import com.plazoleta.restaurants.adapters.driven.traceability.client.ITraceabilityServiceClient;
import com.plazoleta.restaurants.adapters.driven.users.client.IUserServiceClient;
import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.api.IMessagingServicePort;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.api.ITraceabilityServicePort;
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
            IRestaurantRepository restaurantRepository,
            IOrderEntityMapper orderEntityMapper,
            IOrderDishEntityMapper orderDishEntityMapper,
            IUserServiceClient userServiceClient) {
        return new OrderMysqlAdapter(
                orderRepository,
                orderDishRepository,
                dishRepository,
                employeeRestaurantRepository,
                restaurantRepository,
                orderEntityMapper,
                orderDishEntityMapper,
                userServiceClient
        );
    }

    @Bean
    public IMessagingServicePort messagingServicePort(IMessagingServiceClient messagingServiceClient) {
        return new MessagingServiceAdapter(messagingServiceClient);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort(ITraceabilityServiceClient traceabilityServiceClient) {
        return new TraceabilityServiceAdapter(traceabilityServiceClient);
    }

    @Bean
    public IOrderServicePort orderServicePort(IOrderPersistencePort orderPersistencePort,
                                              IMessagingServicePort messagingServicePort,
                                              ITraceabilityServicePort traceabilityServicePort) {
        return new OrderUseCase(orderPersistencePort, messagingServicePort, traceabilityServicePort);
    }
}
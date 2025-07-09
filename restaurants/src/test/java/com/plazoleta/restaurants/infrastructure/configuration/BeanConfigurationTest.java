package com.plazoleta.restaurants.infrastructure.configuration;

import com.plazoleta.restaurants.adapters.driven.messaging.adapter.MessagingServiceAdapter;
import com.plazoleta.restaurants.adapters.driven.messaging.client.IMessagingServiceClient;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.DishMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.OrderMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.adapter.RestaurantMysqlAdapter;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.*;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.*;
import com.plazoleta.restaurants.domain.api.*;
import com.plazoleta.restaurants.domain.spi.*;
import com.plazoleta.restaurants.domain.usecase.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeanConfigurationTest {

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    void testRestaurantPersistencePortBean() {
        IRestaurantRepository restaurantRepository = mock(IRestaurantRepository.class);
        IRestaurantEntityMapper restaurantEntityMapper = mock(IRestaurantEntityMapper.class);

        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(restaurantRepository, restaurantEntityMapper);
        assertNotNull(result);
        assertTrue(result instanceof RestaurantMysqlAdapter);
    }

    @Test
    void testRestaurantServicePortBean() {
        IRestaurantPersistencePort restaurantPersistencePort = mock(IRestaurantPersistencePort.class);

        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(restaurantPersistencePort);
        assertNotNull(result);
        assertTrue(result instanceof RestaurantUseCase);
    }

    @Test
    void testDishPersistencePortBean() {
        IDishRepository dishRepository = mock(IDishRepository.class);
        IRestaurantRepository restaurantRepository = mock(IRestaurantRepository.class);
        ICategoryRepository categoryRepository = mock(ICategoryRepository.class);
        IDishEntityMapper dishEntityMapper = mock(IDishEntityMapper.class);
        ICategoryEntityMapper categoryEntityMapper = mock(ICategoryEntityMapper.class);
        IDishWithCategoryMapper dishWithCategoryMapper = mock(IDishWithCategoryMapper.class);

        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper
        );

        assertNotNull(result);
        assertTrue(result instanceof DishMysqlAdapter);
    }

    @Test
    void testDishServicePortBean() {
        IDishPersistencePort dishPersistencePort = mock(IDishPersistencePort.class);

        IDishServicePort result = beanConfiguration.dishServicePort(dishPersistencePort);
        assertNotNull(result);
        assertTrue(result instanceof DishUseCase);
    }

    @Test
    void testOrderPersistencePortBean() {
        IOrderRepository orderRepository = mock(IOrderRepository.class);
        IOrderDishRepository orderDishRepository = mock(IOrderDishRepository.class);
        IDishRepository dishRepository = mock(IDishRepository.class);
        IEmployeeRestaurantRepository employeeRestaurantRepository = mock(IEmployeeRestaurantRepository.class);
        IRestaurantRepository restaurantRepository = mock(IRestaurantRepository.class);
        IOrderEntityMapper orderEntityMapper = mock(IOrderEntityMapper.class);
        IOrderDishEntityMapper orderDishEntityMapper = mock(IOrderDishEntityMapper.class);

        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                employeeRestaurantRepository,
                restaurantRepository,
                orderEntityMapper,
                orderDishEntityMapper
        );

        assertNotNull(result);
        assertTrue(result instanceof OrderMysqlAdapter);
    }

    @Test
    void testMessagingServicePortBean() {
        IMessagingServiceClient client = mock(IMessagingServiceClient.class);

        IMessagingServicePort result = beanConfiguration.messagingServicePort(client);
        assertNotNull(result);
        assertTrue(result instanceof MessagingServiceAdapter);
    }

    @Test
    void testOrderServicePortBean() {
        IOrderPersistencePort orderPersistencePort = mock(IOrderPersistencePort.class);
        IMessagingServicePort messagingServicePort = mock(IMessagingServicePort.class);

        IOrderServicePort result = beanConfiguration.orderServicePort(orderPersistencePort, messagingServicePort);
        assertNotNull(result);
        assertTrue(result instanceof OrderUseCase);
    }
}

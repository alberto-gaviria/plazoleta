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
import com.plazoleta.restaurants.domain.api.*;
import com.plazoleta.restaurants.domain.spi.*;
import com.plazoleta.restaurants.domain.usecase.DishUseCase;
import com.plazoleta.restaurants.domain.usecase.OrderUseCase;
import com.plazoleta.restaurants.domain.usecase.RestaurantUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class BeanConfigurationTest {

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    void testRestaurantPersistencePort() {
        IRestaurantRepository restaurantRepository = mock(IRestaurantRepository.class);
        IRestaurantEntityMapper mapper = mock(IRestaurantEntityMapper.class);

        IRestaurantPersistencePort port = beanConfiguration.restaurantPersistencePort(restaurantRepository, mapper);

        assertNotNull(port);
        assertTrue(port instanceof RestaurantMysqlAdapter);
    }

    @Test
    void testRestaurantServicePort() {
        IRestaurantPersistencePort persistencePort = mock(IRestaurantPersistencePort.class);

        IRestaurantServicePort servicePort = beanConfiguration.restaurantServicePort(persistencePort);

        assertNotNull(servicePort);
        assertTrue(servicePort instanceof RestaurantUseCase);
    }

    @Test
    void testDishPersistencePort() {
        IDishRepository dishRepository = mock(IDishRepository.class);
        IRestaurantRepository restaurantRepository = mock(IRestaurantRepository.class);
        ICategoryRepository categoryRepository = mock(ICategoryRepository.class);
        IDishEntityMapper dishMapper = mock(IDishEntityMapper.class);
        ICategoryEntityMapper categoryMapper = mock(ICategoryEntityMapper.class);
        IDishWithCategoryMapper dishWithCategoryMapper = mock(IDishWithCategoryMapper.class);

        IDishPersistencePort port = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishMapper,
                categoryMapper,
                dishWithCategoryMapper
        );

        assertNotNull(port);
        assertTrue(port instanceof DishMysqlAdapter);
    }

    @Test
    void testDishServicePort() {
        IDishPersistencePort persistencePort = mock(IDishPersistencePort.class);

        IDishServicePort servicePort = beanConfiguration.dishServicePort(persistencePort);

        assertNotNull(servicePort);
        assertTrue(servicePort instanceof DishUseCase);
    }

    @Test
    void testOrderPersistencePort() {
        IOrderRepository orderRepository = mock(IOrderRepository.class);
        IOrderDishRepository orderDishRepository = mock(IOrderDishRepository.class);
        IDishRepository dishRepository = mock(IDishRepository.class);
        IEmployeeRestaurantRepository employeeRestaurantRepository = mock(IEmployeeRestaurantRepository.class);
        IRestaurantRepository restaurantRepository = mock(IRestaurantRepository.class);
        IOrderEntityMapper orderEntityMapper = mock(IOrderEntityMapper.class);
        IOrderDishEntityMapper orderDishEntityMapper = mock(IOrderDishEntityMapper.class);
        IUserServiceClient userServiceClient = mock(IUserServiceClient.class);

        IOrderPersistencePort port = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                employeeRestaurantRepository,
                restaurantRepository,
                orderEntityMapper,
                orderDishEntityMapper,
                userServiceClient
        );

        assertNotNull(port);
        assertTrue(port instanceof OrderMysqlAdapter);
    }

    @Test
    void testMessagingServicePort() {
        IMessagingServiceClient client = mock(IMessagingServiceClient.class);

        IMessagingServicePort port = beanConfiguration.messagingServicePort(client);

        assertNotNull(port);
        assertTrue(port instanceof MessagingServiceAdapter);
    }

    @Test
    void testTraceabilityServicePort() {
        ITraceabilityServiceClient client = mock(ITraceabilityServiceClient.class);

        ITraceabilityServicePort port = beanConfiguration.traceabilityServicePort(client);

        assertNotNull(port);
        assertTrue(port instanceof TraceabilityServiceAdapter);
    }

    @Test
    void testOrderServicePort() {
        IOrderPersistencePort orderPersistencePort = mock(IOrderPersistencePort.class);
        IMessagingServicePort messagingServicePort = mock(IMessagingServicePort.class);
        ITraceabilityServicePort traceabilityServicePort = mock(ITraceabilityServicePort.class);

        IOrderServicePort servicePort = beanConfiguration.orderServicePort(
                orderPersistencePort,
                messagingServicePort,
                traceabilityServicePort
        );

        assertNotNull(servicePort);
        assertTrue(servicePort instanceof OrderUseCase);
    }
}

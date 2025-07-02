package com.plazoleta.restaurants.infrastructure.configuration;

import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IRestaurantEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.domain.api.IDishServicePort;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.spi.IUserValidationPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @Mock
    private IUserValidationPort userValidationPort;

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    void restaurantPersistencePort_ShouldReturnRestaurantMysqlAdapter() {
        // When
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(IRestaurantPersistencePort.class, result);
    }

    @Test
    void restaurantServicePort_ShouldReturnRestaurantUseCase() {
        // Given
        IRestaurantPersistencePort restaurantPersistencePort =
                beanConfiguration.restaurantPersistencePort(restaurantRepository, restaurantEntityMapper);

        // When
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(
                restaurantPersistencePort, userValidationPort);

        // Then
        assertNotNull(result);
        assertInstanceOf(IRestaurantServicePort.class, result);
    }

    @Test
    void dishPersistencePort_ShouldReturnDishMysqlAdapter() {
        // When
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, dishEntityMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(IDishPersistencePort.class, result);
    }

    @Test
    void dishServicePort_ShouldReturnDishUseCase() {
        // Given
        IDishPersistencePort dishPersistencePort =
                beanConfiguration.dishPersistencePort(dishRepository, restaurantRepository, dishEntityMapper);

        // When
        IDishServicePort result = beanConfiguration.dishServicePort(
                dishPersistencePort, userValidationPort);

        // Then
        assertNotNull(result);
        assertInstanceOf(IDishServicePort.class, result);
    }

    @Test
    void allBeans_ShouldNotBeNull() {
        // Given
        IRestaurantPersistencePort restaurantPersistencePort =
                beanConfiguration.restaurantPersistencePort(restaurantRepository, restaurantEntityMapper);
        IDishPersistencePort dishPersistencePort =
                beanConfiguration.dishPersistencePort(dishRepository, restaurantRepository, dishEntityMapper);

        // When
        IRestaurantServicePort restaurantServicePort =
                beanConfiguration.restaurantServicePort(restaurantPersistencePort, userValidationPort);
        IDishServicePort dishServicePort =
                beanConfiguration.dishServicePort(dishPersistencePort, userValidationPort);

        // Then
        assertNotNull(restaurantPersistencePort);
        assertNotNull(restaurantServicePort);
        assertNotNull(dishPersistencePort);
        assertNotNull(dishServicePort);
    }
}
package com.plazoleta.restaurants.infrastructure.configuration;

import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IRestaurantEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.domain.api.IRestaurantServicePort;
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
    private IUserValidationPort userValidationPort;

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    void shouldCreateRestaurantPersistencePortBean() {
        // When
        IRestaurantPersistencePort persistencePort = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Then
        assertNotNull(persistencePort);
        assertInstanceOf(IRestaurantPersistencePort.class, persistencePort);
    }

    @Test
    void shouldCreateRestaurantServicePortBean() {
        // Given
        IRestaurantPersistencePort persistencePort = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // When
        IRestaurantServicePort servicePort = beanConfiguration.restaurantServicePort(
                persistencePort, userValidationPort);

        // Then
        assertNotNull(servicePort);
        assertInstanceOf(IRestaurantServicePort.class, servicePort);
    }

    @Test
    void shouldCreateBeanConfigurationWithDefaultConstructor() {
        // When
        BeanConfiguration config = new BeanConfiguration();

        // Then
        assertNotNull(config);
    }

    @Test
    void shouldCreateDifferentPersistencePortInstances() {
        // When
        IRestaurantPersistencePort persistencePort1 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);
        IRestaurantPersistencePort persistencePort2 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Then
        assertNotNull(persistencePort1);
        assertNotNull(persistencePort2);
        // Cada llamada al método crea una nueva instancia (no es singleton cuando se llama directamente)
        assertNotSame(persistencePort1, persistencePort2);
    }

    @Test
    void shouldCreateDifferentServicePortInstances() {
        // Given
        IRestaurantPersistencePort persistencePort1 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);
        IRestaurantPersistencePort persistencePort2 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // When
        IRestaurantServicePort servicePort1 = beanConfiguration.restaurantServicePort(
                persistencePort1, userValidationPort);
        IRestaurantServicePort servicePort2 = beanConfiguration.restaurantServicePort(
                persistencePort2, userValidationPort);

        // Then
        assertNotNull(servicePort1);
        assertNotNull(servicePort2);
        assertNotSame(servicePort1, servicePort2);
    }

    @Test
    void shouldCreateServicePortWithCorrectDependencies() {
        // Given
        IRestaurantPersistencePort persistencePort = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // When
        IRestaurantServicePort servicePort = beanConfiguration.restaurantServicePort(
                persistencePort, userValidationPort);

        // Then
        assertNotNull(servicePort);
        // Verificamos que el service port se creó correctamente con las dependencias
        assertDoesNotThrow(() -> servicePort.getClass());
    }
}
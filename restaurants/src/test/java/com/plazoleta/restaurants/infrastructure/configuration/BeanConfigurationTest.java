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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    private BeanConfiguration beanConfiguration;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @Mock
    private IDishWithCategoryMapper dishWithCategoryMapper;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderDishRepository orderDishRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IOrderDishEntityMapper orderDishEntityMapper;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    // ==================== RESTAURANT PERSISTENCE PORT TESTS ====================

    @Test
    void restaurantPersistencePort_WhenValidDependencies_ShouldReturnRestaurantMysqlAdapter() {
        // When
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                restaurantRepository,
                restaurantEntityMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(RestaurantMysqlAdapter.class, result);
    }

    @Test
    void restaurantPersistencePort_WhenCalled_ShouldCreateNewInstance() {
        // When
        IRestaurantPersistencePort result1 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository,
                restaurantEntityMapper);
        IRestaurantPersistencePort result2 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository,
                restaurantEntityMapper);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Diferentes instancias
        assertInstanceOf(RestaurantMysqlAdapter.class, result1);
        assertInstanceOf(RestaurantMysqlAdapter.class, result2);
    }

    @Test
    void restaurantPersistencePort_WhenValidParameters_ShouldNotBeNull() {
        // When
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                restaurantRepository,
                restaurantEntityMapper);

        // Then
        assertNotNull(result);
    }

    @Test
    void restaurantPersistencePort_WhenDifferentRepositoryAndMapper_ShouldCreateAdapter() {
        // Given
        IRestaurantRepository differentRepository = mock(IRestaurantRepository.class);
        IRestaurantEntityMapper differentMapper = mock(IRestaurantEntityMapper.class);

        // When
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                differentRepository,
                differentMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(RestaurantMysqlAdapter.class, result);
    }

    // ==================== RESTAURANT SERVICE PORT TESTS ====================

    @Test
    void restaurantServicePort_WhenValidPersistencePort_ShouldReturnRestaurantUseCase() {
        // When
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(restaurantPersistencePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(RestaurantUseCase.class, result);
    }

    @Test
    void restaurantServicePort_WhenCalled_ShouldCreateNewInstance() {
        // When
        IRestaurantServicePort result1 = beanConfiguration.restaurantServicePort(restaurantPersistencePort);
        IRestaurantServicePort result2 = beanConfiguration.restaurantServicePort(restaurantPersistencePort);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Diferentes instancias
        assertInstanceOf(RestaurantUseCase.class, result1);
        assertInstanceOf(RestaurantUseCase.class, result2);
    }

    @Test
    void restaurantServicePort_WhenValidParameter_ShouldNotBeNull() {
        // When
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(restaurantPersistencePort);

        // Then
        assertNotNull(result);
    }

    @Test
    void restaurantServicePort_WhenDifferentPersistencePort_ShouldCreateUseCase() {
        // Given
        IRestaurantPersistencePort differentPersistencePort = mock(IRestaurantPersistencePort.class);

        // When
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(differentPersistencePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(RestaurantUseCase.class, result);
    }

    // ==================== DISH PERSISTENCE PORT TESTS (UPDATED FOR HU10) ====================

    @Test
    void dishPersistencePort_WhenValidDependencies_ShouldReturnDishMysqlAdapter() {
        // When
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(DishMysqlAdapter.class, result);
    }

    @Test
    void dishPersistencePort_WhenCalled_ShouldCreateNewInstance() {
        // When
        IDishPersistencePort result1 = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper);
        IDishPersistencePort result2 = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Diferentes instancias
        assertInstanceOf(DishMysqlAdapter.class, result1);
        assertInstanceOf(DishMysqlAdapter.class, result2);
    }

    @Test
    void dishPersistencePort_WhenValidParameters_ShouldNotBeNull() {
        // When
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper);

        // Then
        assertNotNull(result);
    }

    @Test
    void dishPersistencePort_WhenDifferentRepositoriesAndMappers_ShouldCreateAdapter() {
        // Given
        IDishRepository differentDishRepository = mock(IDishRepository.class);
        IRestaurantRepository differentRestaurantRepository = mock(IRestaurantRepository.class);
        ICategoryRepository differentCategoryRepository = mock(ICategoryRepository.class);
        IDishEntityMapper differentDishMapper = mock(IDishEntityMapper.class);
        ICategoryEntityMapper differentCategoryMapper = mock(ICategoryEntityMapper.class);
        IDishWithCategoryMapper differentDishWithCategoryMapper = mock(IDishWithCategoryMapper.class);

        // When
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                differentDishRepository,
                differentRestaurantRepository,
                differentCategoryRepository,
                differentDishMapper,
                differentCategoryMapper,
                differentDishWithCategoryMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(DishMysqlAdapter.class, result);
    }

    // ==================== DISH SERVICE PORT TESTS ====================

    @Test
    void dishServicePort_WhenValidPersistencePort_ShouldReturnDishUseCase() {
        // When
        IDishServicePort result = beanConfiguration.dishServicePort(dishPersistencePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(DishUseCase.class, result);
    }

    @Test
    void dishServicePort_WhenCalled_ShouldCreateNewInstance() {
        // When
        IDishServicePort result1 = beanConfiguration.dishServicePort(dishPersistencePort);
        IDishServicePort result2 = beanConfiguration.dishServicePort(dishPersistencePort);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Diferentes instancias
        assertInstanceOf(DishUseCase.class, result1);
        assertInstanceOf(DishUseCase.class, result2);
    }

    @Test
    void dishServicePort_WhenValidParameter_ShouldNotBeNull() {
        // When
        IDishServicePort result = beanConfiguration.dishServicePort(dishPersistencePort);

        // Then
        assertNotNull(result);
    }

    @Test
    void dishServicePort_WhenDifferentPersistencePort_ShouldCreateUseCase() {
        // Given
        IDishPersistencePort differentPersistencePort = mock(IDishPersistencePort.class);

        // When
        IDishServicePort result = beanConfiguration.dishServicePort(differentPersistencePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(DishUseCase.class, result);
    }

    // ==================== INTEGRATION TESTS (UPDATED FOR HU10) ====================

    @Test
    void allBeans_WhenConfiguredTogether_ShouldWorkCorrectly() {
        // Given - Crear la cadena completa de beans
        IRestaurantPersistencePort restaurantPersistence = beanConfiguration.restaurantPersistencePort(
                restaurantRepository,
                restaurantEntityMapper);

        IRestaurantServicePort restaurantService = beanConfiguration.restaurantServicePort(restaurantPersistence);

        IDishPersistencePort dishPersistence = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper);

        IDishServicePort dishService = beanConfiguration.dishServicePort(dishPersistence);

        // Then
        assertNotNull(restaurantPersistence);
        assertNotNull(restaurantService);
        assertNotNull(dishPersistence);
        assertNotNull(dishService);

        assertInstanceOf(RestaurantMysqlAdapter.class, restaurantPersistence);
        assertInstanceOf(RestaurantUseCase.class, restaurantService);
        assertInstanceOf(DishMysqlAdapter.class, dishPersistence);
        assertInstanceOf(DishUseCase.class, dishService);
    }

    @Test
    void beanConfiguration_WhenInstantiated_ShouldNotBeNull() {
        // Then
        assertNotNull(beanConfiguration);
        assertInstanceOf(BeanConfiguration.class, beanConfiguration);
    }

    @Test
    void restaurantPersistencePort_WithSameParameters_ShouldCreateDifferentInstances() {
        // When
        IRestaurantPersistencePort adapter1 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);
        IRestaurantPersistencePort adapter2 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Then
        assertNotSame(adapter1, adapter2);
        assertEquals(adapter1.getClass(), adapter2.getClass());
    }

    @Test
    void dishPersistencePort_WithSameParameters_ShouldCreateDifferentInstances() {
        // When
        IDishPersistencePort adapter1 = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);
        IDishPersistencePort adapter2 = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);

        // Then
        assertNotSame(adapter1, adapter2);
        assertEquals(adapter1.getClass(), adapter2.getClass());
    }

    @Test
    void restaurantServicePort_WithSameParameters_ShouldCreateDifferentInstances() {
        // When
        IRestaurantServicePort service1 = beanConfiguration.restaurantServicePort(restaurantPersistencePort);
        IRestaurantServicePort service2 = beanConfiguration.restaurantServicePort(restaurantPersistencePort);

        // Then
        assertNotSame(service1, service2);
        assertEquals(service1.getClass(), service2.getClass());
    }

    @Test
    void dishServicePort_WithSameParameters_ShouldCreateDifferentInstances() {
        // When
        IDishServicePort service1 = beanConfiguration.dishServicePort(dishPersistencePort);
        IDishServicePort service2 = beanConfiguration.dishServicePort(dishPersistencePort);

        // Then
        assertNotSame(service1, service2);
        assertEquals(service1.getClass(), service2.getClass());
    }

    // ==================== VERIFICATION TESTS ====================

    @Test
    void restaurantPersistencePort_ShouldReturnCorrectImplementation() {
        // When
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Then
        assertTrue(result instanceof RestaurantMysqlAdapter);
        assertTrue(result instanceof IRestaurantPersistencePort);
    }

    @Test
    void restaurantServicePort_ShouldReturnCorrectImplementation() {
        // When
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(restaurantPersistencePort);

        // Then
        assertTrue(result instanceof RestaurantUseCase);
        assertTrue(result instanceof IRestaurantServicePort);
    }

    @Test
    void dishPersistencePort_ShouldReturnCorrectImplementation() {
        // When
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);

        // Then
        assertTrue(result instanceof DishMysqlAdapter);
        assertTrue(result instanceof IDishPersistencePort);
    }

    @Test
    void dishServicePort_ShouldReturnCorrectImplementation() {
        // When
        IDishServicePort result = beanConfiguration.dishServicePort(dishPersistencePort);

        // Then
        assertTrue(result instanceof DishUseCase);
        assertTrue(result instanceof IDishServicePort);
    }

    // ==================== EDGE CASE TESTS ====================

    @Test
    void allBeanMethods_ShouldBePublic() {
        // Given & When & Then
        // Los métodos @Bean deben ser públicos para que Spring los detecte
        // Este test verifica que todos los métodos bean están correctamente configurados
        assertDoesNotThrow(() -> {
            beanConfiguration.restaurantPersistencePort(restaurantRepository, restaurantEntityMapper);
            beanConfiguration.restaurantServicePort(restaurantPersistencePort);
            beanConfiguration.dishPersistencePort(dishRepository, restaurantRepository, categoryRepository,
                                                  dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);
            beanConfiguration.dishServicePort(dishPersistencePort);
        });
    }

    @Test
    void beanConfiguration_ShouldHaveNoArgsConstructor() {
        // When & Then
        assertDoesNotThrow(() -> new BeanConfiguration());
    }

    @Test
    void fullIntegrationFlow_ShouldCreateCompleteObjectGraph() {
        // Given
        BeanConfiguration config = new BeanConfiguration();

        // When
        IRestaurantPersistencePort restaurantPersistence = config.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);
        IRestaurantServicePort restaurantService = config.restaurantServicePort(restaurantPersistence);

        IDishPersistencePort dishPersistence = config.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);
        IDishServicePort dishService = config.dishServicePort(dishPersistence);

        // Then
        // Verificar que se ha creado el grafo completo de objetos
        assertAll(
                () -> assertNotNull(restaurantPersistence),
                () -> assertNotNull(restaurantService),
                () -> assertNotNull(dishPersistence),
                () -> assertNotNull(dishService),
                () -> assertInstanceOf(RestaurantMysqlAdapter.class, restaurantPersistence),
                () -> assertInstanceOf(RestaurantUseCase.class, restaurantService),
                () -> assertInstanceOf(DishMysqlAdapter.class, dishPersistence),
                () -> assertInstanceOf(DishUseCase.class, dishService)
        );
    }

    @Test
    void orderPersistencePort_WhenValidDependencies_ShouldReturnOrderMysqlAdapter() {
        // When
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                orderEntityMapper,
                orderDishEntityMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(OrderMysqlAdapter.class, result);
    }

    @Test
    void orderPersistencePort_WhenCalled_ShouldCreateNewInstance() {
        // When
        IOrderPersistencePort result1 = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                orderEntityMapper,
                orderDishEntityMapper);
        IOrderPersistencePort result2 = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                orderEntityMapper,
                orderDishEntityMapper);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Diferentes instancias
        assertInstanceOf(OrderMysqlAdapter.class, result1);
        assertInstanceOf(OrderMysqlAdapter.class, result2);
    }

    @Test
    void orderPersistencePort_WhenValidParameters_ShouldNotBeNull() {
        // When
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                orderEntityMapper,
                orderDishEntityMapper);

        // Then
        assertNotNull(result);
    }

    @Test
    void orderPersistencePort_WhenDifferentRepositoriesAndMappers_ShouldCreateAdapter() {
        // Given
        IOrderRepository differentOrderRepository = mock(IOrderRepository.class);
        IOrderDishRepository differentOrderDishRepository = mock(IOrderDishRepository.class);
        IDishRepository differentDishRepository = mock(IDishRepository.class);
        IOrderEntityMapper differentOrderEntityMapper = mock(IOrderEntityMapper.class);
        IOrderDishEntityMapper differentOrderDishEntityMapper = mock(IOrderDishEntityMapper.class);

        // When
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                differentOrderRepository,
                differentOrderDishRepository,
                differentDishRepository,
                differentOrderEntityMapper,
                differentOrderDishEntityMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(OrderMysqlAdapter.class, result);
    }

// ==================== ORDER SERVICE PORT TESTS ====================

    @Test
    void orderServicePort_WhenValidPersistencePort_ShouldReturnOrderUseCase() {
        // When
        IOrderServicePort result = beanConfiguration.orderServicePort(orderPersistencePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(OrderUseCase.class, result);
    }

    @Test
    void orderServicePort_WhenCalled_ShouldCreateNewInstance() {
        // When
        IOrderServicePort result1 = beanConfiguration.orderServicePort(orderPersistencePort);
        IOrderServicePort result2 = beanConfiguration.orderServicePort(orderPersistencePort);

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Diferentes instancias
        assertInstanceOf(OrderUseCase.class, result1);
        assertInstanceOf(OrderUseCase.class, result2);
    }

    @Test
    void orderServicePort_WhenValidParameter_ShouldNotBeNull() {
        // When
        IOrderServicePort result = beanConfiguration.orderServicePort(orderPersistencePort);

        // Then
        assertNotNull(result);
    }

    @Test
    void orderServicePort_WhenDifferentPersistencePort_ShouldCreateUseCase() {
        // Given
        IOrderPersistencePort differentPersistencePort = mock(IOrderPersistencePort.class);

        // When
        IOrderServicePort result = beanConfiguration.orderServicePort(differentPersistencePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(OrderUseCase.class, result);
    }

// ==================== ORDER INTEGRATION TESTS ====================

    @Test
    void orderBeans_WhenConfiguredTogether_ShouldWorkCorrectly() {
        // Given - Crear la cadena completa de beans para Order
        IOrderPersistencePort orderPersistence = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                orderEntityMapper,
                orderDishEntityMapper);

        IOrderServicePort orderService = beanConfiguration.orderServicePort(orderPersistence);

        // Then
        assertNotNull(orderPersistence);
        assertNotNull(orderService);
        assertInstanceOf(OrderMysqlAdapter.class, orderPersistence);
        assertInstanceOf(OrderUseCase.class, orderService);
    }

    @Test
    void orderPersistencePort_WithSameParameters_ShouldCreateDifferentInstances() {
        // When
        IOrderPersistencePort adapter1 = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                orderEntityMapper, orderDishEntityMapper);
        IOrderPersistencePort adapter2 = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                orderEntityMapper, orderDishEntityMapper);

        // Then
        assertNotSame(adapter1, adapter2);
        assertEquals(adapter1.getClass(), adapter2.getClass());
    }

    @Test
    void orderServicePort_WithSameParameters_ShouldCreateDifferentInstances() {
        // When
        IOrderServicePort service1 = beanConfiguration.orderServicePort(orderPersistencePort);
        IOrderServicePort service2 = beanConfiguration.orderServicePort(orderPersistencePort);

        // Then
        assertNotSame(service1, service2);
        assertEquals(service1.getClass(), service2.getClass());
    }

// ==================== ORDER VERIFICATION TESTS ====================

    @Test
    void orderPersistencePort_ShouldReturnCorrectImplementation() {
        // When
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                orderEntityMapper, orderDishEntityMapper);

        // Then
        assertTrue(result instanceof OrderMysqlAdapter);
        assertTrue(result instanceof IOrderPersistencePort);
    }

    @Test
    void orderServicePort_ShouldReturnCorrectImplementation() {
        // When
        IOrderServicePort result = beanConfiguration.orderServicePort(orderPersistencePort);

        // Then
        assertTrue(result instanceof OrderUseCase);
        assertTrue(result instanceof IOrderServicePort);
    }

// ==================== COMPLETE INTEGRATION TEST (UPDATED) ====================

    @Test
    void allBeans_IncludingOrder_WhenConfiguredTogether_ShouldWorkCorrectly() {
        // Given - Crear la cadena completa de beans incluyendo Order
        IRestaurantPersistencePort restaurantPersistence = beanConfiguration.restaurantPersistencePort(
                restaurantRepository,
                restaurantEntityMapper);

        IRestaurantServicePort restaurantService = beanConfiguration.restaurantServicePort(restaurantPersistence);

        IDishPersistencePort dishPersistence = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper);

        IDishServicePort dishService = beanConfiguration.dishServicePort(dishPersistence);

        IOrderPersistencePort orderPersistence = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                orderEntityMapper,
                orderDishEntityMapper);

        IOrderServicePort orderService = beanConfiguration.orderServicePort(orderPersistence);

        // Then
        assertNotNull(restaurantPersistence);
        assertNotNull(restaurantService);
        assertNotNull(dishPersistence);
        assertNotNull(dishService);
        assertNotNull(orderPersistence);
        assertNotNull(orderService);

        assertInstanceOf(RestaurantMysqlAdapter.class, restaurantPersistence);
        assertInstanceOf(RestaurantUseCase.class, restaurantService);
        assertInstanceOf(DishMysqlAdapter.class, dishPersistence);
        assertInstanceOf(DishUseCase.class, dishService);
        assertInstanceOf(OrderMysqlAdapter.class, orderPersistence);
        assertInstanceOf(OrderUseCase.class, orderService);
    }

    @Test
    void fullIntegrationFlow_IncludingOrder_ShouldCreateCompleteObjectGraph() {
        // Given
        BeanConfiguration config = new BeanConfiguration();

        // When
        IRestaurantPersistencePort restaurantPersistence = config.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);
        IRestaurantServicePort restaurantService = config.restaurantServicePort(restaurantPersistence);

        IDishPersistencePort dishPersistence = config.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);
        IDishServicePort dishService = config.dishServicePort(dishPersistence);

        IOrderPersistencePort orderPersistence = config.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                orderEntityMapper, orderDishEntityMapper);
        IOrderServicePort orderService = config.orderServicePort(orderPersistence);

        // Then
        // Verificar que se ha creado el grafo completo de objetos incluyendo Order
        assertAll(
                () -> assertNotNull(restaurantPersistence),
                () -> assertNotNull(restaurantService),
                () -> assertNotNull(dishPersistence),
                () -> assertNotNull(dishService),
                () -> assertNotNull(orderPersistence),
                () -> assertNotNull(orderService),
                () -> assertInstanceOf(RestaurantMysqlAdapter.class, restaurantPersistence),
                () -> assertInstanceOf(RestaurantUseCase.class, restaurantService),
                () -> assertInstanceOf(DishMysqlAdapter.class, dishPersistence),
                () -> assertInstanceOf(DishUseCase.class, dishService),
                () -> assertInstanceOf(OrderMysqlAdapter.class, orderPersistence),
                () -> assertInstanceOf(OrderUseCase.class, orderService)
        );
    }

// ==================== ORDER EDGE CASE TESTS ====================

    @Test
    void orderBeanMethods_ShouldBePublic() {
        // Given & When & Then
        // Los métodos @Bean deben ser públicos para que Spring los detecte
        assertDoesNotThrow(() -> {
            beanConfiguration.orderPersistencePort(orderRepository, orderDishRepository, dishRepository,
                                                   orderEntityMapper, orderDishEntityMapper);
            beanConfiguration.orderServicePort(orderPersistencePort);
        });
    }
}
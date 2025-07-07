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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

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
    private IOrderRepository orderRepository;

    @Mock
    private IOrderDishRepository orderDishRepository;

    @Mock
    private IEmployeeRestaurantRepository employeeRestaurantRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IOrderDishEntityMapper orderDishEntityMapper;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @InjectMocks
    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        // Los mocks se inicializan automáticamente con @Mock y @InjectMocks
    }

    // =================== RESTAURANT BEANS TESTS ===================

    @Test
    void restaurantPersistencePort_ShouldCreateRestaurantMysqlAdapter() {
        // Act
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RestaurantMysqlAdapter.class, result);
    }

    @Test
    void restaurantPersistencePort_WithNullRepository_ShouldStillCreateAdapter() {
        // Act
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                null, restaurantEntityMapper);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RestaurantMysqlAdapter.class, result);
    }

    @Test
    void restaurantPersistencePort_WithNullMapper_ShouldStillCreateAdapter() {
        // Act
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, null);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RestaurantMysqlAdapter.class, result);
    }

    @Test
    void restaurantPersistencePort_WithNullParameters_ShouldStillCreateAdapter() {
        // Act
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                null, null);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RestaurantMysqlAdapter.class, result);
    }

    @Test
    void restaurantServicePort_ShouldCreateRestaurantUseCase() {
        // Act
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(
                restaurantPersistencePort);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RestaurantUseCase.class, result);
    }

    @Test
    void restaurantServicePort_WithNullPersistencePort_ShouldStillCreateUseCase() {
        // Act
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(null);

        // Assert
        assertNotNull(result);
        assertInstanceOf(RestaurantUseCase.class, result);
    }

    // =================== DISH BEANS TESTS ===================

    @Test
    void dishPersistencePort_ShouldCreateDishMysqlAdapter() {
        // Act
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                dishRepository,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                categoryEntityMapper,
                dishWithCategoryMapper);

        // Assert
        assertNotNull(result);
        assertInstanceOf(DishMysqlAdapter.class, result);
    }

    @Test
    void dishPersistencePort_WithSomeNullParameters_ShouldStillCreateAdapter() {
        // Act
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                null,
                restaurantRepository,
                categoryRepository,
                dishEntityMapper,
                null,
                dishWithCategoryMapper);

        // Assert
        assertNotNull(result);
        assertInstanceOf(DishMysqlAdapter.class, result);
    }

    @Test
    void dishPersistencePort_WithAllNullParameters_ShouldStillCreateAdapter() {
        // Act
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                null, null, null, null, null, null);

        // Assert
        assertNotNull(result);
        assertInstanceOf(DishMysqlAdapter.class, result);
    }

    @Test
    void dishServicePort_ShouldCreateDishUseCase() {
        // Act
        IDishServicePort result = beanConfiguration.dishServicePort(dishPersistencePort);

        // Assert
        assertNotNull(result);
        assertInstanceOf(DishUseCase.class, result);
    }

    @Test
    void dishServicePort_WithNullPersistencePort_ShouldStillCreateUseCase() {
        // Act
        IDishServicePort result = beanConfiguration.dishServicePort(null);

        // Assert
        assertNotNull(result);
        assertInstanceOf(DishUseCase.class, result);
    }

    // =================== ORDER BEANS TESTS ===================

    @Test
    void orderPersistencePort_ShouldCreateOrderMysqlAdapter() {
        // Act
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                orderRepository,
                orderDishRepository,
                dishRepository,
                employeeRestaurantRepository,
                orderEntityMapper,
                orderDishEntityMapper);

        // Assert
        assertNotNull(result);
        assertInstanceOf(OrderMysqlAdapter.class, result);
    }

    @Test
    void orderPersistencePort_WithSomeNullParameters_ShouldStillCreateAdapter() {
        // Act
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                null,
                orderDishRepository,
                dishRepository,
                null,
                orderEntityMapper,
                orderDishEntityMapper);

        // Assert
        assertNotNull(result);
        assertInstanceOf(OrderMysqlAdapter.class, result);
    }

    @Test
    void orderPersistencePort_WithAllNullParameters_ShouldStillCreateAdapter() {
        // Act
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                null, null, null, null, null, null);

        // Assert
        assertNotNull(result);
        assertInstanceOf(OrderMysqlAdapter.class, result);
    }

    @Test
    void orderServicePort_ShouldCreateOrderUseCase() {
        // Act
        IOrderServicePort result = beanConfiguration.orderServicePort(orderPersistencePort);

        // Assert
        assertNotNull(result);
        assertInstanceOf(OrderUseCase.class, result);
    }

    @Test
    void orderServicePort_WithNullPersistencePort_ShouldStillCreateUseCase() {
        // Act
        IOrderServicePort result = beanConfiguration.orderServicePort(null);

        // Assert
        assertNotNull(result);
        assertInstanceOf(OrderUseCase.class, result);
    }

    // =================== INTEGRATION TESTS ===================

    @Test
    void allBeans_ShouldBeCreatedSuccessfully() {
        // Act - Create all beans in sequence to test integration
        IRestaurantPersistencePort restaurantPersistence = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        IRestaurantServicePort restaurantService = beanConfiguration.restaurantServicePort(
                restaurantPersistence);

        IDishPersistencePort dishPersistence = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);

        IDishServicePort dishService = beanConfiguration.dishServicePort(dishPersistence);

        IOrderPersistencePort orderPersistence = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                employeeRestaurantRepository, orderEntityMapper, orderDishEntityMapper);

        IOrderServicePort orderService = beanConfiguration.orderServicePort(orderPersistence);

        // Assert
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
    void beanConfiguration_ShouldHaveAnnotation() {
        // Assert
        assertTrue(BeanConfiguration.class.isAnnotationPresent(
                org.springframework.context.annotation.Configuration.class));
    }

    @Test
    void restaurantPersistencePortMethod_ShouldHaveBeanAnnotation() throws NoSuchMethodException {
        // Assert
        assertTrue(BeanConfiguration.class
                           .getMethod("restaurantPersistencePort", IRestaurantRepository.class, IRestaurantEntityMapper.class)
                           .isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    @Test
    void restaurantServicePortMethod_ShouldHaveBeanAnnotation() throws NoSuchMethodException {
        // Assert
        assertTrue(BeanConfiguration.class
                           .getMethod("restaurantServicePort", IRestaurantPersistencePort.class)
                           .isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    @Test
    void dishPersistencePortMethod_ShouldHaveBeanAnnotation() throws NoSuchMethodException {
        // Assert
        assertTrue(BeanConfiguration.class
                           .getMethod("dishPersistencePort", IDishRepository.class, IRestaurantRepository.class,
                                      ICategoryRepository.class, IDishEntityMapper.class, ICategoryEntityMapper.class,
                                      IDishWithCategoryMapper.class)
                           .isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    @Test
    void dishServicePortMethod_ShouldHaveBeanAnnotation() throws NoSuchMethodException {
        // Assert
        assertTrue(BeanConfiguration.class
                           .getMethod("dishServicePort", IDishPersistencePort.class)
                           .isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    @Test
    void orderPersistencePortMethod_ShouldHaveBeanAnnotation() throws NoSuchMethodException {
        // Assert
        assertTrue(BeanConfiguration.class
                           .getMethod("orderPersistencePort", IOrderRepository.class, IOrderDishRepository.class,
                                      IDishRepository.class, IEmployeeRestaurantRepository.class,
                                      IOrderEntityMapper.class, IOrderDishEntityMapper.class)
                           .isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    @Test
    void orderServicePortMethod_ShouldHaveBeanAnnotation() throws NoSuchMethodException {
        // Assert
        assertTrue(BeanConfiguration.class
                           .getMethod("orderServicePort", IOrderPersistencePort.class)
                           .isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    // =================== CONSTRUCTOR AND INSTANTIATION TESTS ===================

    @Test
    void beanConfiguration_ShouldHavePublicConstructor() {
        // Act & Assert
        assertDoesNotThrow(() -> new BeanConfiguration());
    }

    @Test
    void beanConfiguration_ShouldBeInstantiable() {
        // Act
        BeanConfiguration config = new BeanConfiguration();

        // Assert
        assertNotNull(config);
    }

    // =================== DEPENDENCY INJECTION TESTS ===================

    @Test
    void restaurantBeans_ShouldMaintainDependencyChain() {
        // Arrange
        IRestaurantPersistencePort persistence = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Act
        IRestaurantServicePort service = beanConfiguration.restaurantServicePort(persistence);

        // Assert
        assertNotNull(service);
        assertInstanceOf(RestaurantUseCase.class, service);
    }

    @Test
    void dishBeans_ShouldMaintainDependencyChain() {
        // Arrange
        IDishPersistencePort persistence = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);

        // Act
        IDishServicePort service = beanConfiguration.dishServicePort(persistence);

        // Assert
        assertNotNull(service);
        assertInstanceOf(DishUseCase.class, service);
    }

    @Test
    void orderBeans_ShouldMaintainDependencyChain() {
        // Arrange
        IOrderPersistencePort persistence = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                employeeRestaurantRepository, orderEntityMapper, orderDishEntityMapper);

        // Act
        IOrderServicePort service = beanConfiguration.orderServicePort(persistence);

        // Assert
        assertNotNull(service);
        assertInstanceOf(OrderUseCase.class, service);
    }

    // =================== RETURN TYPE VERIFICATION TESTS ===================

    @Test
    void restaurantPersistencePort_ShouldReturnCorrectInterface() {
        // Act
        IRestaurantPersistencePort result = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof IRestaurantPersistencePort);
    }

    @Test
    void dishPersistencePort_ShouldReturnCorrectInterface() {
        // Act
        IDishPersistencePort result = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof IDishPersistencePort);
    }

    @Test
    void orderPersistencePort_ShouldReturnCorrectInterface() {
        // Act
        IOrderPersistencePort result = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                employeeRestaurantRepository, orderEntityMapper, orderDishEntityMapper);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof IOrderPersistencePort);
    }

    @Test
    void restaurantServicePort_ShouldReturnCorrectInterface() {
        // Act
        IRestaurantServicePort result = beanConfiguration.restaurantServicePort(
                restaurantPersistencePort);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof IRestaurantServicePort);
    }

    @Test
    void dishServicePort_ShouldReturnCorrectInterface() {
        // Act
        IDishServicePort result = beanConfiguration.dishServicePort(dishPersistencePort);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof IDishServicePort);
    }

    @Test
    void orderServicePort_ShouldReturnCorrectInterface() {
        // Act
        IOrderServicePort result = beanConfiguration.orderServicePort(orderPersistencePort);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof IOrderServicePort);
    }

    // =================== MULTIPLE CALLS TESTS ===================

    @Test
    void restaurantPersistencePort_MultipleCalls_ShouldCreateNewInstances() {
        // Act
        IRestaurantPersistencePort result1 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);
        IRestaurantPersistencePort result2 = beanConfiguration.restaurantPersistencePort(
                restaurantRepository, restaurantEntityMapper);

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Different instances
    }

    @Test
    void dishPersistencePort_MultipleCalls_ShouldCreateNewInstances() {
        // Act
        IDishPersistencePort result1 = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);
        IDishPersistencePort result2 = beanConfiguration.dishPersistencePort(
                dishRepository, restaurantRepository, categoryRepository,
                dishEntityMapper, categoryEntityMapper, dishWithCategoryMapper);

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Different instances
    }

    @Test
    void orderPersistencePort_MultipleCalls_ShouldCreateNewInstances() {
        // Act
        IOrderPersistencePort result1 = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                employeeRestaurantRepository, orderEntityMapper, orderDishEntityMapper);
        IOrderPersistencePort result2 = beanConfiguration.orderPersistencePort(
                orderRepository, orderDishRepository, dishRepository,
                employeeRestaurantRepository, orderEntityMapper, orderDishEntityMapper);

        // Assert
        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2); // Different instances
    }
}
package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderDishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderStatus;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IEmployeeRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderDishRepository;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderMysqlAdapterTest {

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private IOrderDishRepository orderDishRepository;

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private IOrderDishEntityMapper orderDishEntityMapper;

    @Mock
    private IEmployeeRestaurantRepository employeeRestaurantRepository;

    @InjectMocks
    private OrderMysqlAdapter orderMysqlAdapter;

    private Order order;
    private OrderEntity orderEntity;
    private OrderDish orderDish;
    private OrderDishEntity orderDishEntity;

    @BeforeEach
    void setUp() {
        orderDish = new OrderDish(1L, 2);
        order = new Order();
        order.setId(1L);
        order.setPlatos(Collections.singletonList(orderDish));

        orderEntity = new OrderEntity();
        orderEntity.setId(1L);

        orderDishEntity = new OrderDishEntity();
        orderDishEntity.setId(1L);
        orderDishEntity.setIdPedido(1L);
        orderDishEntity.setIdPlato(1L);
        orderDishEntity.setCantidad(2);
    }

    @Test
    void saveOrder_WithPlatos_ShouldReturnSavedOrder() {
        // Arrange
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderDishEntityMapper.toEntityList(order.getPlatos()))
                .thenReturn(Collections.singletonList(orderDishEntity));
        when(orderDishRepository.saveAll(anyList()))
                .thenReturn(Collections.singletonList(orderDishEntity));
        when(orderEntityMapper.toModel(orderEntity)).thenReturn(order);
        when(orderDishRepository.findByIdPedido(1L))
                .thenReturn(Collections.singletonList(orderDishEntity));
        when(orderDishEntityMapper.toModelList(Collections.singletonList(orderDishEntity)))
                .thenReturn(Collections.singletonList(orderDish));

        // Act
        Order result = orderMysqlAdapter.saveOrder(order);

        // Assert
        assertNotNull(result);
        verify(orderRepository).save(orderEntity);
        verify(orderDishRepository).saveAll(anyList());
        verify(orderDishRepository).findByIdPedido(1L);
    }

    @Test
    void saveOrder_WithoutPlatos_ShouldReturnSavedOrder() {
        // Arrange
        order.setPlatos(null);
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toModel(orderEntity)).thenReturn(order);
        when(orderDishRepository.findByIdPedido(1L)).thenReturn(Collections.emptyList());
        when(orderDishEntityMapper.toModelList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        // Act
        Order result = orderMysqlAdapter.saveOrder(order);

        // Assert
        assertNotNull(result);
        verify(orderRepository).save(orderEntity);
        verify(orderDishRepository, never()).saveAll(anyList());
    }

    @Test
    void saveOrder_WithEmptyPlatos_ShouldReturnSavedOrder() {
        // Arrange
        order.setPlatos(Collections.emptyList());
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);
        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        when(orderEntityMapper.toModel(orderEntity)).thenReturn(order);
        when(orderDishRepository.findByIdPedido(1L)).thenReturn(Collections.emptyList());
        when(orderDishEntityMapper.toModelList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        // Act
        Order result = orderMysqlAdapter.saveOrder(order);

        // Assert
        assertNotNull(result);
        verify(orderRepository).save(orderEntity);
        verify(orderDishRepository, never()).saveAll(anyList());
    }

    @Test
    void hasActiveOrderForClient_ClientHasActiveOrder_ShouldReturnTrue() {
        // Arrange
        Long clientId = 1L;
        when(orderRepository.existsByIdClienteAndEstadoIn(
                clientId, OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION, OrderStatus.LISTO))
                .thenReturn(true);

        // Act
        boolean result = orderMysqlAdapter.hasActiveOrderForClient(clientId);

        // Assert
        assertTrue(result);
        verify(orderRepository).existsByIdClienteAndEstadoIn(
                clientId, OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION, OrderStatus.LISTO);
    }

    @Test
    void hasActiveOrderForClient_ClientHasNoActiveOrder_ShouldReturnFalse() {
        // Arrange
        Long clientId = 1L;
        when(orderRepository.existsByIdClienteAndEstadoIn(
                clientId, OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION, OrderStatus.LISTO))
                .thenReturn(false);

        // Act
        boolean result = orderMysqlAdapter.hasActiveOrderForClient(clientId);

        // Assert
        assertFalse(result);
    }

    @Test
    void existsDishById_DishExists_ShouldReturnTrue() {
        // Arrange
        Long dishId = 1L;
        when(dishRepository.existsById(dishId)).thenReturn(true);

        // Act
        boolean result = orderMysqlAdapter.existsDishById(dishId);

        // Assert
        assertTrue(result);
        verify(dishRepository).existsById(dishId);
    }

    @Test
    void existsDishById_DishNotExists_ShouldReturnFalse() {
        // Arrange
        Long dishId = 1L;
        when(dishRepository.existsById(dishId)).thenReturn(false);

        // Act
        boolean result = orderMysqlAdapter.existsDishById(dishId);

        // Assert
        assertFalse(result);
    }

    @Test
    void getDishRestaurantId_DishExists_ShouldReturnRestaurantId() {
        // Arrange
        Long dishId = 1L;
        Long restaurantId = 10L;
        DishEntity dishEntity = new DishEntity();
        dishEntity.setIdRestaurante(restaurantId);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));

        // Act
        Long result = orderMysqlAdapter.getDishRestaurantId(dishId);

        // Assert
        assertEquals(restaurantId, result);
        verify(dishRepository).findById(dishId);
    }

    @Test
    void getDishRestaurantId_DishNotExists_ShouldThrowException() {
        // Arrange
        Long dishId = 1L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ElementNotFoundException.class,
                     () -> orderMysqlAdapter.getDishRestaurantId(dishId));
        verify(dishRepository).findById(dishId);
    }

    @Test
    void isDishActive_DishExistsAndActive_ShouldReturnTrue() {
        // Arrange
        Long dishId = 1L;
        DishEntity dishEntity = new DishEntity();
        dishEntity.setActivo(true);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));

        // Act
        boolean result = orderMysqlAdapter.isDishActive(dishId);

        // Assert
        assertTrue(result);
    }

    @Test
    void isDishActive_DishExistsButInactive_ShouldReturnFalse() {
        // Arrange
        Long dishId = 1L;
        DishEntity dishEntity = new DishEntity();
        dishEntity.setActivo(false);
        when(dishRepository.findById(dishId)).thenReturn(Optional.of(dishEntity));

        // Act
        boolean result = orderMysqlAdapter.isDishActive(dishId);

        // Assert
        assertFalse(result);
    }

    @Test
    void isDishActive_DishNotExists_ShouldReturnFalse() {
        // Arrange
        Long dishId = 1L;
        when(dishRepository.findById(dishId)).thenReturn(Optional.empty());

        // Act
        boolean result = orderMysqlAdapter.isDishActive(dishId);

        // Assert
        assertFalse(result);
    }

    @Test
    void findOrdersByRestaurantAndStatus_WithStatus_ShouldReturnPagedOrders() {
        // Arrange
        Long restaurantId = 1L;
        com.plazoleta.restaurants.domain.model.OrderStatus domainStatus =
                com.plazoleta.restaurants.domain.model.OrderStatus.PENDIENTE;
        int pageNumber = 0;
        int pageSize = 10;

        OrderEntity orderEntity1 = new OrderEntity();
        orderEntity1.setId(1L);
        OrderEntity orderEntity2 = new OrderEntity();
        orderEntity2.setId(2L);

        List<OrderEntity> orderEntities = Arrays.asList(orderEntity1, orderEntity2);

        org.springframework.data.domain.Page<OrderEntity> springPage =
                mock(org.springframework.data.domain.Page.class);
        when(springPage.getContent()).thenReturn(orderEntities);
        when(springPage.getNumber()).thenReturn(pageNumber);
        when(springPage.getSize()).thenReturn(pageSize);
        when(springPage.getTotalElements()).thenReturn(2L);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        when(orderRepository.findByRestaurantIdAndStatus(restaurantId, OrderStatus.PENDIENTE, pageable))
                .thenReturn(springPage);

        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        when(orderEntityMapper.toModel(orderEntity1)).thenReturn(order1);
        when(orderEntityMapper.toModel(orderEntity2)).thenReturn(order2);

        List<OrderDishEntity> orderDishes1 = Arrays.asList(orderDishEntity);
        List<OrderDishEntity> orderDishes2 = Collections.emptyList();

        when(orderDishRepository.findByIdPedido(1L)).thenReturn(orderDishes1);
        when(orderDishRepository.findByIdPedido(2L)).thenReturn(orderDishes2);

        when(orderDishEntityMapper.toModelList(orderDishes1))
                .thenReturn(Arrays.asList(orderDish));
        when(orderDishEntityMapper.toModelList(orderDishes2))
                .thenReturn(Collections.emptyList());

        // Act
        Page<Order> result = orderMysqlAdapter.findOrdersByRestaurantAndStatus(
                restaurantId, domainStatus, pageNumber, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(pageNumber, result.getPageNumber());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(2L, result.getTotalElements());

        verify(orderRepository).findByRestaurantIdAndStatus(restaurantId, OrderStatus.PENDIENTE, pageable);
        verify(orderDishRepository).findByIdPedido(1L);
        verify(orderDishRepository).findByIdPedido(2L);
    }

    @Test
    void findOrdersByRestaurantAndStatus_WithoutStatus_ShouldReturnPagedOrders() {
        // Arrange
        Long restaurantId = 1L;
        int pageNumber = 0;
        int pageSize = 10;

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);

        List<OrderEntity> orderEntities = Arrays.asList(orderEntity);

        org.springframework.data.domain.Page<OrderEntity> springPage =
                mock(org.springframework.data.domain.Page.class);
        when(springPage.getContent()).thenReturn(orderEntities);
        when(springPage.getNumber()).thenReturn(pageNumber);
        when(springPage.getSize()).thenReturn(pageSize);
        when(springPage.getTotalElements()).thenReturn(1L);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        when(orderRepository.findByRestaurantId(restaurantId, pageable))
                .thenReturn(springPage);

        Order order = new Order();
        order.setId(1L);

        when(orderEntityMapper.toModel(orderEntity)).thenReturn(order);

        List<OrderDishEntity> orderDishes = Arrays.asList(orderDishEntity);
        when(orderDishRepository.findByIdPedido(1L)).thenReturn(orderDishes);
        when(orderDishEntityMapper.toModelList(orderDishes))
                .thenReturn(Arrays.asList(orderDish));

        // Act
        Page<Order> result = orderMysqlAdapter.findOrdersByRestaurantAndStatus(
                restaurantId, null, pageNumber, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(pageNumber, result.getPageNumber());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(1L, result.getTotalElements());

        verify(orderRepository).findByRestaurantId(restaurantId, pageable);
        verify(orderDishRepository).findByIdPedido(1L);
    }

    @Test
    void getEmployeeRestaurantId_EmployeeExists_ShouldReturnRestaurantId() {
        // Arrange
        Long employeeId = 1L;
        Long restaurantId = 10L;
        when(employeeRestaurantRepository.findRestaurantIdByEmployeeId(employeeId))
                .thenReturn(Optional.of(restaurantId));

        // Act
        Long result = orderMysqlAdapter.getEmployeeRestaurantId(employeeId);

        // Assert
        assertEquals(restaurantId, result);
        verify(employeeRestaurantRepository).findRestaurantIdByEmployeeId(employeeId);
    }

    @Test
    void getEmployeeRestaurantId_EmployeeNotExists_ShouldThrowException() {
        // Arrange
        Long employeeId = 1L;
        when(employeeRestaurantRepository.findRestaurantIdByEmployeeId(employeeId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ElementNotFoundException.class,
                     () -> orderMysqlAdapter.getEmployeeRestaurantId(employeeId));
        verify(employeeRestaurantRepository).findRestaurantIdByEmployeeId(employeeId);
    }
}
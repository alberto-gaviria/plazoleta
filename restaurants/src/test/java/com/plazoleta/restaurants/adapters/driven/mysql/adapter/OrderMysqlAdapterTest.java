package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderDishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderStatus;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderDishRepository;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
}
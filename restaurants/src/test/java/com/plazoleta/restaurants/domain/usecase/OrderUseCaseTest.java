package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private Order validOrder;
    private Long clientId;
    private Long restaurantId;

    @BeforeEach
    void setUp() {
        clientId = 1L;
        restaurantId = 10L;

        OrderDish orderDish1 = new OrderDish(1L, 2);
        OrderDish orderDish2 = new OrderDish(2L, 1);
        List<OrderDish> platos = Arrays.asList(orderDish1, orderDish2);

        validOrder = new Order();
        validOrder.setIdRestaurante(restaurantId);
        validOrder.setPlatos(platos);
    }

    @Test
    void createOrder_ValidOrder_ShouldReturnSavedOrder() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(restaurantId);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.existsDishById(2L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(2L)).thenReturn(true);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setIdCliente(clientId);
        savedOrder.setEstado(OrderStatus.PENDIENTE);
        when(orderPersistencePort.saveOrder(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = orderUseCase.createOrder(validOrder, clientId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(clientId, result.getIdCliente());
        assertEquals(OrderStatus.PENDIENTE, result.getEstado());

        verify(orderPersistencePort).hasActiveOrderForClient(clientId);
        verify(orderPersistencePort).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_NullOrder_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(null, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_ORDER_NULO, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void createOrder_NullClientId_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, null)
        );

        assertEquals(DomainConstants.Order.ERROR_CLIENTE_REQUERIDO, exception.getMessage());
    }

    @Test
    void createOrder_NullRestaurantId_ShouldThrowInvalidOrderException() {
        // Arrange
        validOrder.setIdRestaurante(null);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_RESTAURANTE_REQUERIDO, exception.getMessage());
    }

    @Test
    void createOrder_EmptyPlatos_ShouldThrowInvalidOrderException() {
        // Arrange
        validOrder.setPlatos(Collections.emptyList());

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATOS_REQUERIDOS, exception.getMessage());
    }

    @Test
    void createOrder_NullPlatos_ShouldThrowInvalidOrderException() {
        // Arrange
        validOrder.setPlatos(null);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATOS_REQUERIDOS, exception.getMessage());
    }

    @Test
    void createOrder_ClientHasActiveOrder_ShouldThrowInvalidOrderException() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(true);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_CLIENTE_TIENE_PEDIDO_ACTIVO, exception.getMessage());
        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void createOrder_DishWithNullId_ShouldThrowInvalidOrderException() {
        // Arrange
        OrderDish invalidDish = new OrderDish(null, 1);
        validOrder.setPlatos(Collections.singletonList(invalidDish));
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATO_ID_REQUERIDO, exception.getMessage());
    }

    @Test
    void createOrder_DishWithNullQuantity_ShouldThrowInvalidOrderException() {
        // Arrange
        OrderDish invalidDish = new OrderDish(1L, null);
        validOrder.setPlatos(Collections.singletonList(invalidDish));
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_CANTIDAD_POSITIVA, exception.getMessage());
    }

    @Test
    void createOrder_DishWithZeroQuantity_ShouldThrowInvalidOrderException() {
        // Arrange
        OrderDish invalidDish = new OrderDish(1L, 0);
        validOrder.setPlatos(Collections.singletonList(invalidDish));
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_CANTIDAD_POSITIVA, exception.getMessage());
    }

    @Test
    void createOrder_DishWithNegativeQuantity_ShouldThrowInvalidOrderException() {
        // Arrange
        OrderDish invalidDish = new OrderDish(1L, -1);
        validOrder.setPlatos(Collections.singletonList(invalidDish));
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_CANTIDAD_POSITIVA, exception.getMessage());
    }

    @Test
    void createOrder_DishesFromDifferentRestaurants_ShouldThrowInvalidOrderException() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(99L); // Different restaurant

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATOS_MISMO_RESTAURANTE, exception.getMessage());
    }

    @Test
    void createOrder_OrderRestaurantNotMatchingDishes_ShouldThrowInvalidOrderException() {
        // Arrange
        Long differentRestaurantId = 99L;
        validOrder.setIdRestaurante(differentRestaurantId);

        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(restaurantId);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_RESTAURANTE_NO_COINCIDE, exception.getMessage());
    }

    @Test
    void createOrder_DishNotExists_ShouldThrowInvalidOrderException() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(restaurantId);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(false);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATO_NO_ENCONTRADO, exception.getMessage());
    }


    @Test
    void createOrder_SecondDishNotActive_ShouldThrowInvalidOrderException() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(restaurantId);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.existsDishById(2L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(2L)).thenReturn(false);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATO_NO_ACTIVO, exception.getMessage());
    }
}
package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidOrderException;
import com.plazoleta.restaurants.domain.util.paged.Page;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private Long employeeId;

    @BeforeEach
    void setUp() {
        clientId = 1L;
        restaurantId = 10L;
        employeeId = 2L;

        OrderDish orderDish1 = new OrderDish(1L, 2);
        OrderDish orderDish2 = new OrderDish(2L, 1);
        List<OrderDish> platos = Arrays.asList(orderDish1, orderDish2);

        validOrder = new Order();
        validOrder.setIdRestaurante(restaurantId);
        validOrder.setPlatos(platos);
    }

    // =================== TESTS PARA createOrder ===================

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
    void createOrder_ValidOrderWithSingleDish_ShouldReturnSavedOrder() {
        // Arrange
        OrderDish singleDish = new OrderDish(1L, 3);
        validOrder.setPlatos(Collections.singletonList(singleDish));

        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(true);

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        when(orderPersistencePort.saveOrder(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = orderUseCase.createOrder(validOrder, clientId);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(orderPersistencePort).saveOrder(any(Order.class));
    }

    @Test
    void createOrder_ShouldSetCorrectOrderProperties() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(restaurantId);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.existsDishById(2L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(2L)).thenReturn(true);

        Order savedOrder = new Order();
        when(orderPersistencePort.saveOrder(any(Order.class))).thenReturn(savedOrder);

        LocalDateTime beforeCall = LocalDateTime.now().minusSeconds(1);

        // Act
        orderUseCase.createOrder(validOrder, clientId);

        // Assert - Verify the order passed to saveOrder has correct properties
        verify(orderPersistencePort).saveOrder(argThat(order -> {
            LocalDateTime afterCall = LocalDateTime.now().plusSeconds(1);
            return order.getIdCliente().equals(clientId) &&
                    order.getEstado() == OrderStatus.PENDIENTE &&
                    order.getIdEmpleado() == null &&
                    order.getPinSeguridad() == null &&
                    order.getFecha().isAfter(beforeCall) &&
                    order.getFecha().isBefore(afterCall);
        }));
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

    // =================== TESTS PARA getOrdersByEmployeeAndStatus ===================

    @Test
    void getOrdersByEmployeeAndStatus_ValidParameters_ShouldReturnPagedOrders() {
        // Arrange
        OrderStatus estado = OrderStatus.PENDIENTE;
        int pageNumber = 0;
        int pageSize = 10;

        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);

        List<Order> orders = Arrays.asList(order1, order2);
        Page<Order> expectedPage = new Page<>(orders, pageNumber, pageSize, 2L);

        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);
        when(orderPersistencePort.findOrdersByRestaurantAndStatus(restaurantId, estado, pageNumber, pageSize))
                .thenReturn(expectedPage);

        // Act
        Page<Order> result = orderUseCase.getOrdersByEmployeeAndStatus(employeeId, estado, pageNumber, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(pageNumber, result.getPageNumber());
        assertEquals(pageSize, result.getPageSize());
        assertEquals(2L, result.getTotalElements());

        verify(orderPersistencePort).getEmployeeRestaurantId(employeeId);
        verify(orderPersistencePort).findOrdersByRestaurantAndStatus(restaurantId, estado, pageNumber, pageSize);
    }

    @Test
    void getOrdersByEmployeeAndStatus_WithNullStatus_ShouldReturnPagedOrders() {
        // Arrange
        OrderStatus estado = null;
        int pageNumber = 0;
        int pageSize = 10;

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> expectedPage = new Page<>(orders, pageNumber, pageSize, 1L);

        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);
        when(orderPersistencePort.findOrdersByRestaurantAndStatus(restaurantId, null, pageNumber, pageSize))
                .thenReturn(expectedPage);

        // Act
        Page<Order> result = orderUseCase.getOrdersByEmployeeAndStatus(employeeId, estado, pageNumber, pageSize);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(orderPersistencePort).findOrdersByRestaurantAndStatus(restaurantId, null, pageNumber, pageSize);
    }



    @Test
    void getOrdersByEmployeeAndStatus_NegativePageNumber_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.getOrdersByEmployeeAndStatus(employeeId, OrderStatus.PENDIENTE, -1, 10)
        );

        assertEquals(DomainConstants.Order.ERROR_PAGE_NUMBER_INVALID, exception.getMessage());
        verify(orderPersistencePort, never()).getEmployeeRestaurantId(any());
    }

    @Test
    void getOrdersByEmployeeAndStatus_PageSizeTooSmall_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.getOrdersByEmployeeAndStatus(employeeId, OrderStatus.PENDIENTE, 0, 0)
        );

        assertEquals(DomainConstants.Order.ERROR_PAGE_SIZE_INVALID, exception.getMessage());
        verify(orderPersistencePort, never()).getEmployeeRestaurantId(any());
    }

    @Test
    void getOrdersByEmployeeAndStatus_PageSizeTooLarge_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.getOrdersByEmployeeAndStatus(employeeId, OrderStatus.PENDIENTE, 0, 999)
        );

        assertEquals(DomainConstants.Order.ERROR_PAGE_SIZE_TOO_LARGE, exception.getMessage());
        verify(orderPersistencePort, never()).getEmployeeRestaurantId(any());
    }

    @Test
    void getOrdersByEmployeeAndStatus_WithDifferentStatuses_ShouldWork() {
        // Test with different order statuses
        OrderStatus[] statuses = {OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION, OrderStatus.LISTO, OrderStatus.ENTREGADO, OrderStatus.CANCELADO};

        for (OrderStatus status : statuses) {
            // Arrange
            Page<Order> expectedPage = new Page<>(Collections.emptyList(), 0, 10, 0L);
            when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);
            when(orderPersistencePort.findOrdersByRestaurantAndStatus(restaurantId, status, 0, 10))
                    .thenReturn(expectedPage);

            // Act
            Page<Order> result = orderUseCase.getOrdersByEmployeeAndStatus(employeeId, status, 0, 10);

            // Assert
            assertNotNull(result);
            verify(orderPersistencePort).findOrdersByRestaurantAndStatus(restaurantId, status, 0, 10);

            // Reset for next iteration
            reset(orderPersistencePort);
        }
    }

    @Test
    void getOrdersByEmployeeAndStatus_WithBoundaryValues_ShouldWork() {
        // Arrange - Test with boundary values for pagination
        int minPageNumber = 0;
        int minPageSize = 1;
        int maxPageSize = 50; // Assuming this is the max based on constants pattern

        Page<Order> expectedPage = new Page<>(Collections.emptyList(), minPageNumber, minPageSize, 0L);
        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);
        when(orderPersistencePort.findOrdersByRestaurantAndStatus(eq(restaurantId), any(), eq(minPageNumber), eq(minPageSize)))
                .thenReturn(expectedPage);

        // Act
        Page<Order> result = orderUseCase.getOrdersByEmployeeAndStatus(employeeId, OrderStatus.PENDIENTE, minPageNumber, minPageSize);

        // Assert
        assertNotNull(result);
        verify(orderPersistencePort).findOrdersByRestaurantAndStatus(restaurantId, OrderStatus.PENDIENTE, minPageNumber, minPageSize);
    }

    @Test
    void getOrdersByEmployeeAndStatus_WithEmptyResult_ShouldReturnEmptyPage() {
        // Arrange
        Page<Order> emptyPage = new Page<>(Collections.emptyList(), 0, 10, 0L);
        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);
        when(orderPersistencePort.findOrdersByRestaurantAndStatus(restaurantId, OrderStatus.PENDIENTE, 0, 10))
                .thenReturn(emptyPage);

        // Act
        Page<Order> result = orderUseCase.getOrdersByEmployeeAndStatus(employeeId, OrderStatus.PENDIENTE, 0, 10);

        // Assert
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
        assertEquals(0L, result.getTotalElements());
    }

    @Test
    void getOrdersByEmployeeAndStatus_WithLargePageNumber_ShouldWork() {
        // Arrange
        int largePageNumber = 100;
        Page<Order> expectedPage = new Page<>(Collections.emptyList(), largePageNumber, 10, 0L);
        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);
        when(orderPersistencePort.findOrdersByRestaurantAndStatus(restaurantId, OrderStatus.PENDIENTE, largePageNumber, 10))
                .thenReturn(expectedPage);

        // Act
        Page<Order> result = orderUseCase.getOrdersByEmployeeAndStatus(employeeId, OrderStatus.PENDIENTE, largePageNumber, 10);

        // Assert
        assertNotNull(result);
        assertEquals(largePageNumber, result.getPageNumber());
    }

    @Test
    void constructor_ShouldInitializeCorrectly() {
        // Act
        OrderUseCase useCase = new OrderUseCase(orderPersistencePort);

        // Assert
        assertNotNull(useCase);
        // Constructor correctness is verified by successful mock interactions in other tests
    }

    @Test
    void getOrdersByEmployeeAndStatus_NullEmployeeId_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.getOrdersByEmployeeAndStatus(null, OrderStatus.PENDIENTE, 0, 10)
        );

        assertEquals(DomainConstants.Order.ERROR_EMPLEADO_REQUERIDO, exception.getMessage());
        // Fixed verify calls - removed any() matchers that were causing issues
        verify(orderPersistencePort, never()).getEmployeeRestaurantId(any());
        verify(orderPersistencePort, never()).findOrdersByRestaurantAndStatus(any(), any(), anyInt(), anyInt());
    }

    @Test
    void createOrder_FirstDishNotActive_ShouldThrowInvalidOrderException() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(restaurantId);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(false);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATO_NO_ACTIVO, exception.getMessage());
    }

    @Test
    void createOrder_SecondDishNotExists_ShouldThrowInvalidOrderException() {
        // Arrange
        when(orderPersistencePort.hasActiveOrderForClient(clientId)).thenReturn(false);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(restaurantId);
        when(orderPersistencePort.getDishRestaurantId(2L)).thenReturn(restaurantId);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.existsDishById(2L)).thenReturn(false);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(true); // Primer plato debe estar activo

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.createOrder(validOrder, clientId)
        );

        assertEquals(DomainConstants.Order.ERROR_PLATO_NO_ENCONTRADO, exception.getMessage());
    }

    // =================== TESTS PARA assignEmployeeToOrder (FALTANTES) ===================

    @Test
    void assignEmployeeToOrder_ValidAssignment_ShouldReturnUpdatedOrder() {
        // Arrange
        Long orderId = 1L;
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setIdRestaurante(restaurantId);
        existingOrder.setEstado(OrderStatus.PENDIENTE);

        Order updatedOrder = new Order();
        updatedOrder.setId(orderId);
        updatedOrder.setIdEmpleado(employeeId);
        updatedOrder.setEstado(OrderStatus.EN_PREPARACION);

        when(orderPersistencePort.findOrderById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);
        when(orderPersistencePort.updateOrder(any(Order.class))).thenReturn(updatedOrder);

        // Act
        Order result = orderUseCase.assignEmployeeToOrder(orderId, employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(employeeId, result.getIdEmpleado());
        assertEquals(OrderStatus.EN_PREPARACION, result.getEstado());

        verify(orderPersistencePort).findOrderById(orderId);
        verify(orderPersistencePort).getEmployeeRestaurantId(employeeId);
        verify(orderPersistencePort).updateOrder(argThat(order ->
                                                                 order.getIdEmpleado().equals(employeeId) &&
                                                                         order.getEstado() == OrderStatus.EN_PREPARACION
        ));
    }

    @Test
    void assignEmployeeToOrder_NullOrderId_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.assignEmployeeToOrder(null, employeeId)
        );

        assertEquals(DomainConstants.Order.ERROR_PEDIDO_ID_REQUERIDO, exception.getMessage());
        verify(orderPersistencePort, never()).findOrderById(any());
    }

    @Test
    void assignEmployeeToOrder_NullEmployeeId_ShouldThrowInvalidOrderException() {
        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.assignEmployeeToOrder(1L, null)
        );

        assertEquals(DomainConstants.Order.ERROR_EMPLEADO_REQUERIDO, exception.getMessage());
        verify(orderPersistencePort, never()).findOrderById(any());
    }

    @Test
    void assignEmployeeToOrder_OrderNotFound_ShouldThrowInvalidOrderException() {
        // Arrange
        Long orderId = 1L;
        when(orderPersistencePort.findOrderById(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.assignEmployeeToOrder(orderId, employeeId)
        );

        assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO, exception.getMessage());
        verify(orderPersistencePort).findOrderById(orderId);
        verify(orderPersistencePort, never()).getEmployeeRestaurantId(any());
    }

    @Test
    void assignEmployeeToOrder_EmployeeRestaurantDifferent_ShouldThrowInvalidOrderException() {
        // Arrange
        Long orderId = 1L;
        Long differentRestaurantId = 99L;

        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setIdRestaurante(restaurantId);
        existingOrder.setEstado(OrderStatus.PENDIENTE);

        when(orderPersistencePort.findOrderById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(differentRestaurantId);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.assignEmployeeToOrder(orderId, employeeId)
        );

        assertEquals(DomainConstants.Order.ERROR_EMPLEADO_RESTAURANTE_DIFERENTE, exception.getMessage());
        verify(orderPersistencePort).findOrderById(orderId);
        verify(orderPersistencePort).getEmployeeRestaurantId(employeeId);
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void assignEmployeeToOrder_OrderNotInPendienteStatus_ShouldThrowInvalidOrderException() {
        // Arrange
        Long orderId = 1L;
        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setIdRestaurante(restaurantId);
        existingOrder.setEstado(OrderStatus.EN_PREPARACION); // Not PENDIENTE

        when(orderPersistencePort.findOrderById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderUseCase.assignEmployeeToOrder(orderId, employeeId)
        );

        assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_PENDIENTE, exception.getMessage());
        verify(orderPersistencePort).findOrderById(orderId);
        verify(orderPersistencePort).getEmployeeRestaurantId(employeeId);
        verify(orderPersistencePort, never()).updateOrder(any());
    }

    @Test
    void assignEmployeeToOrder_WithDifferentOrderStatuses_ShouldFailForNonPendiente() {
        // Test with different non-PENDIENTE statuses
        OrderStatus[] invalidStatuses = {OrderStatus.EN_PREPARACION, OrderStatus.LISTO, OrderStatus.ENTREGADO, OrderStatus.CANCELADO};

        for (OrderStatus status : invalidStatuses) {
            // Arrange
            Long orderId = 1L;
            Order existingOrder = new Order();
            existingOrder.setId(orderId);
            existingOrder.setIdRestaurante(restaurantId);
            existingOrder.setEstado(status);

            when(orderPersistencePort.findOrderById(orderId)).thenReturn(Optional.of(existingOrder));
            when(orderPersistencePort.getEmployeeRestaurantId(employeeId)).thenReturn(restaurantId);

            // Act & Assert
            InvalidOrderException exception = assertThrows(
                    InvalidOrderException.class,
                    () -> orderUseCase.assignEmployeeToOrder(orderId, employeeId)
            );

            assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_PENDIENTE, exception.getMessage());

            // Reset for next iteration
            reset(orderPersistencePort);
        }
    }
}
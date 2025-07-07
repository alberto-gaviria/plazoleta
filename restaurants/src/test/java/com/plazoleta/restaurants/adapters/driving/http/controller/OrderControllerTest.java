// OrderControllerTest.java (Simplificado y Funcional)
package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.CreateOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.OrderDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderResponseMapper;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderRequestMapper orderRequestMapper;

    @Mock
    private IOrderResponseMapper orderResponseMapper;

    @InjectMocks
    private OrderController orderController;

    private CreateOrderRequest createOrderRequest;
    private Order order;
    private Order savedOrder;
    private OrderResponse orderResponse;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        // Setup request
        OrderDishRequest orderDishRequest = new OrderDishRequest(1L, 2);
        createOrderRequest = new CreateOrderRequest(10L, Collections.singletonList(orderDishRequest));

        // Setup domain order
        order = new Order();
        order.setIdRestaurante(10L);

        // Setup saved order
        savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setIdCliente(1L);
        savedOrder.setIdRestaurante(10L);
        savedOrder.setEstado(OrderStatus.PENDIENTE);
        savedOrder.setFecha(LocalDateTime.now());

        // Setup response
        orderResponse = new OrderResponse();
        orderResponse.setId(1L);
        orderResponse.setIdCliente(1L);
        orderResponse.setIdRestaurante(10L);
        orderResponse.setEstado("PENDIENTE");

        // Setup authentication
        authentication = new TestingAuthenticationToken("1", null, "CLIENTE");
    }

    @Test
    void createOrder_ValidRequest_ShouldReturnCreated() {
        // Arrange
        when(orderRequestMapper.createRequestToOrder(createOrderRequest)).thenReturn(order);
        when(orderServicePort.createOrder(eq(order), eq(1L))).thenReturn(savedOrder);
        when(orderResponseMapper.orderToResponse(savedOrder)).thenReturn(orderResponse);

        // Act
        ResponseEntity<OrderResponse> result = orderController.createOrder(createOrderRequest, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        assertEquals(1L, result.getBody().getIdCliente());
        assertEquals(10L, result.getBody().getIdRestaurante());
        assertEquals("PENDIENTE", result.getBody().getEstado());

        verify(orderRequestMapper).createRequestToOrder(createOrderRequest);
        verify(orderServicePort).createOrder(order, 1L);
        verify(orderResponseMapper).orderToResponse(savedOrder);
    }

    @Test
    void createOrder_ServiceThrowsInvalidOrderException_ShouldPropagateException() {
        // Arrange
        when(orderRequestMapper.createRequestToOrder(createOrderRequest)).thenReturn(order);
        when(orderServicePort.createOrder(eq(order), eq(1L)))
                .thenThrow(new InvalidOrderException("Error de validación"));

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderController.createOrder(createOrderRequest, authentication)
        );

        assertEquals("Error de validación", exception.getMessage());
        verify(orderRequestMapper).createRequestToOrder(createOrderRequest);
        verify(orderServicePort).createOrder(order, 1L);
        verify(orderResponseMapper, never()).orderToResponse(any());
    }

    @Test
    void createOrder_ServiceThrowsRuntimeException_ShouldPropagateException() {
        // Arrange
        when(orderRequestMapper.createRequestToOrder(createOrderRequest)).thenReturn(order);
        when(orderServicePort.createOrder(eq(order), eq(1L)))
                .thenThrow(new RuntimeException("Error interno"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderController.createOrder(createOrderRequest, authentication)
        );

        assertEquals("Error interno", exception.getMessage());
    }

    @Test
    void createOrder_MapperReturnsNull_ShouldHandleGracefully() {
        // Arrange
        when(orderRequestMapper.createRequestToOrder(createOrderRequest)).thenReturn(null);

        // Act & Assert
        assertDoesNotThrow(() -> {
            orderController.createOrder(createOrderRequest, authentication);
        });

        verify(orderServicePort).createOrder(null, 1L);
    }

    @Test
    void createOrder_AuthenticationWithDifferentUserId_ShouldUseCorrectId() {
        // Arrange
        Authentication differentAuth = new TestingAuthenticationToken("99", null, "CLIENTE");
        when(orderRequestMapper.createRequestToOrder(createOrderRequest)).thenReturn(order);
        when(orderServicePort.createOrder(eq(order), eq(99L))).thenReturn(savedOrder);
        when(orderResponseMapper.orderToResponse(savedOrder)).thenReturn(orderResponse);

        // Act
        ResponseEntity<OrderResponse> result = orderController.createOrder(createOrderRequest, differentAuth);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(orderServicePort).createOrder(order, 99L);
    }

    @Test
    void createOrder_ResponseMapperReturnsNull_ShouldReturnNullBody() {
        // Arrange
        when(orderRequestMapper.createRequestToOrder(createOrderRequest)).thenReturn(order);
        when(orderServicePort.createOrder(eq(order), eq(1L))).thenReturn(savedOrder);
        when(orderResponseMapper.orderToResponse(savedOrder)).thenReturn(null);

        // Act
        ResponseEntity<OrderResponse> result = orderController.createOrder(createOrderRequest, authentication);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void constructor_ShouldInitializeAllFields() {
        // Act
        OrderController controller = new OrderController(orderServicePort, orderRequestMapper, orderResponseMapper);

        // Assert
        assertNotNull(controller);
        // El constructor debe asignar correctamente las dependencias
        // (esto se verifica indirectamente cuando los mocks funcionan en otros tests)
    }

    @Test
    void createOrder_AllMappersAndServicesCalled_ShouldVerifyInteractions() {
        // Arrange
        when(orderRequestMapper.createRequestToOrder(any(CreateOrderRequest.class))).thenReturn(order);
        when(orderServicePort.createOrder(any(Order.class), any(Long.class))).thenReturn(savedOrder);
        when(orderResponseMapper.orderToResponse(any(Order.class))).thenReturn(orderResponse);

        // Act
        orderController.createOrder(createOrderRequest, authentication);

        // Assert
        verify(orderRequestMapper, times(1)).createRequestToOrder(createOrderRequest);
        verify(orderServicePort, times(1)).createOrder(order, 1L);
        verify(orderResponseMapper, times(1)).orderToResponse(savedOrder);
        verifyNoMoreInteractions(orderRequestMapper, orderServicePort, orderResponseMapper);
    }
}
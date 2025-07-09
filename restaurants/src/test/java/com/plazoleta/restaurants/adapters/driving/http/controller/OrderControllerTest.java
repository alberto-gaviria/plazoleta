package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.*;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderResponseMapper;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderRequestMapper orderRequestMapper;

    @Mock
    private IOrderResponseMapper orderResponseMapper;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(authentication.getName()).thenReturn("123");
    }

    @Test
    void createOrder_shouldReturnCreatedResponse() {
        CreateOrderRequest request = new CreateOrderRequest(); // usa constructor vacío
        Order order = new Order();
        Order savedOrder = new Order();
        OrderResponse response = new OrderResponse();

        when(orderRequestMapper.createRequestToOrder(request)).thenReturn(order);
        when(orderServicePort.createOrder(order, 123L)).thenReturn(savedOrder);
        when(orderResponseMapper.orderToResponse(savedOrder)).thenReturn(response);

        ResponseEntity<OrderResponse> result = orderController.createOrder(request, authentication);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getOrdersByStatus_withValidStatus_shouldReturnOkResponse() {
        String estado = "PENDIENTE";
        int page = 0;
        int size = 10;
        Page<Order> orderPage = new Page<>();
        PageResponse<OrderResponse> responsePage = new PageResponse<>();

        when(orderServicePort.getOrdersByEmployeeAndStatus(123L, OrderStatus.PENDIENTE, page, size))
                .thenReturn(orderPage);
        when(orderResponseMapper.toPageResponse(orderPage)).thenReturn(responsePage);

        ResponseEntity<PageResponse<OrderResponse>> result =
                orderController.getOrdersByStatus(estado, page, size, authentication);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responsePage, result.getBody());
    }

    @Test
    void getOrdersByStatus_withNullStatus_shouldReturnOkResponse() {
        int page = 0;
        int size = 10;
        Page<Order> orderPage = new Page<>();
        PageResponse<OrderResponse> responsePage = new PageResponse<>();

        when(orderServicePort.getOrdersByEmployeeAndStatus(123L, null, page, size)).thenReturn(orderPage);
        when(orderResponseMapper.toPageResponse(orderPage)).thenReturn(responsePage);

        ResponseEntity<PageResponse<OrderResponse>> result =
                orderController.getOrdersByStatus(null, page, size, authentication);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responsePage, result.getBody());
    }

    @Test
    void getOrdersByStatus_withInvalidStatus_shouldThrowException() {
        String estado = "NO_EXISTE";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderController.getOrdersByStatus(estado, 0, 10, authentication)
        );

        assertTrue(exception.getMessage().contains("Estado de pedido inválido"));
    }

    @Test
    void assignEmployeeToOrder_shouldReturnOkResponse() {
        AssignEmployeeToOrderRequest request = new AssignEmployeeToOrderRequest();
        request.setIdPedido(1L);

        Order updatedOrder = new Order();
        OrderResponse response = new OrderResponse();

        when(orderServicePort.assignEmployeeToOrder(1L, 123L)).thenReturn(updatedOrder);
        when(orderResponseMapper.orderToResponse(updatedOrder)).thenReturn(response);

        ResponseEntity<OrderResponse> result = orderController.assignEmployeeToOrder(request, authentication);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void markOrderAsReady_shouldReturnOkResponse() {
        MarkOrderReadyRequest request = new MarkOrderReadyRequest();
        request.setIdPedido(1L);

        Order updatedOrder = new Order();
        OrderResponse response = new OrderResponse();

        when(orderServicePort.markOrderAsReady(1L, 123L)).thenReturn(updatedOrder);
        when(orderResponseMapper.orderToResponse(updatedOrder)).thenReturn(response);

        ResponseEntity<OrderResponse> result = orderController.markOrderAsReady(request, authentication);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deliverOrder_shouldReturnOkResponse() {
        DeliverOrderRequest request = new DeliverOrderRequest(1L, "1234");

        Order deliveredOrder = new Order();
        OrderResponse response = new OrderResponse();

        when(orderServicePort.deliverOrder(1L, "1234", 123L)).thenReturn(deliveredOrder);
        when(orderResponseMapper.orderToResponse(deliveredOrder)).thenReturn(response);

        ResponseEntity<OrderResponse> result = orderController.deliverOrder(request, authentication);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }
    @Test
    void cancelOrder_shouldReturnOkResponse() {
        // Arrange
        CancelOrderRequest request = new CancelOrderRequest();
        request.setIdPedido(1L);

        Order cancelledOrder = new Order();
        OrderResponse response = new OrderResponse();

        when(orderServicePort.cancelOrder(1L, 123L)).thenReturn(cancelledOrder);
        when(orderResponseMapper.orderToResponse(cancelledOrder)).thenReturn(response);

        // Act
        ResponseEntity<OrderResponse> result = orderController.cancelOrder(request, authentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

}

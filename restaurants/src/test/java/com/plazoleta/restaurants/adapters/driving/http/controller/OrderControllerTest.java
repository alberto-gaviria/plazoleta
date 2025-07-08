package com.plazoleta.restaurants.adapters.driving.http.controller;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AssignEmployeeToOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.CreateOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.OrderDishRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderRequestMapper;
import com.plazoleta.restaurants.adapters.driving.http.mapper.IOrderResponseMapper;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidOrderException;
import com.plazoleta.restaurants.domain.util.paged.Page;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private AssignEmployeeToOrderRequest assignEmployeeRequest;
    private Order order;
    private Order savedOrder;
    private Order updatedOrder;
    private OrderResponse orderResponse;
    private Authentication authentication;
    private Authentication employeeAuthentication;

    @BeforeEach
    void setUp() {
        // Setup request
        OrderDishRequest orderDishRequest = new OrderDishRequest(1L, 2);
        createOrderRequest = new CreateOrderRequest(10L, Collections.singletonList(orderDishRequest));

        // Setup assign employee request
        assignEmployeeRequest = new AssignEmployeeToOrderRequest(1L);

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

        // Setup updated order
        updatedOrder = new Order();
        updatedOrder.setId(1L);
        updatedOrder.setIdCliente(1L);
        updatedOrder.setIdRestaurante(10L);
        updatedOrder.setIdEmpleado(2L);
        updatedOrder.setEstado(OrderStatus.EN_PREPARACION);
        updatedOrder.setFecha(LocalDateTime.now());

        // Setup response
        orderResponse = new OrderResponse();
        orderResponse.setId(1L);
        orderResponse.setIdCliente(1L);
        orderResponse.setIdRestaurante(10L);
        orderResponse.setEstado("PENDIENTE");

        // Setup authentication
        authentication = new TestingAuthenticationToken("1", null, "CLIENTE");
        employeeAuthentication = new TestingAuthenticationToken("2", null, "EMPLEADO");
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

    // ============= NUEVOS TESTS PARA getOrdersByStatus =============

    @Test
    void getOrdersByStatus_WithValidStatus_ShouldReturnPagedOrders() {
        // Arrange
        String estado = "PENDIENTE";
        int page = 0;
        int size = 10;

        Order order1 = new Order();
        order1.setId(1L);
        order1.setEstado(OrderStatus.PENDIENTE);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setEstado(OrderStatus.PENDIENTE);

        List<Order> orders = Arrays.asList(order1, order2);
        Page<Order> ordersPage = new Page<>(orders, page, size, 2L);

        OrderResponse response1 = new OrderResponse();
        response1.setId(1L);
        response1.setEstado("PENDIENTE");

        OrderResponse response2 = new OrderResponse();
        response2.setId(2L);
        response2.setEstado("PENDIENTE");

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(Arrays.asList(response1, response2));
        pageResponse.setPageNumber(page);
        pageResponse.setPageSize(size);
        pageResponse.setTotalElements(2L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(OrderStatus.PENDIENTE), eq(page), eq(size)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, page, size, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(2, result.getBody().getContent().size());
        assertEquals(page, result.getBody().getPageNumber());
        assertEquals(size, result.getBody().getPageSize());
        assertEquals(2L, result.getBody().getTotalElements());

        verify(orderServicePort).getOrdersByEmployeeAndStatus(2L, OrderStatus.PENDIENTE, page, size);
        verify(orderResponseMapper).toPageResponse(ordersPage);
    }

    @Test
    void getOrdersByStatus_WithNullStatus_ShouldReturnAllOrders() {
        // Arrange
        String estado = null;
        int page = 0;
        int size = 10;

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> ordersPage = new Page<>(orders, page, size, 1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(Collections.singletonList(orderResponse));
        pageResponse.setPageNumber(page);
        pageResponse.setPageSize(size);
        pageResponse.setTotalElements(1L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(null), eq(page), eq(size)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, page, size, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().getContent().size());

        verify(orderServicePort).getOrdersByEmployeeAndStatus(2L, null, page, size);
        verify(orderResponseMapper).toPageResponse(ordersPage);
    }

    @Test
    void getOrdersByStatus_WithEmptyStatus_ShouldReturnAllOrders() {
        // Arrange
        String estado = "";
        int page = 0;
        int size = 10;

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> ordersPage = new Page<>(orders, page, size, 1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(Collections.singletonList(orderResponse));
        pageResponse.setPageNumber(page);
        pageResponse.setPageSize(size);
        pageResponse.setTotalElements(1L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(null), eq(page), eq(size)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, page, size, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(orderServicePort).getOrdersByEmployeeAndStatus(2L, null, page, size);
    }

    @Test
    void getOrdersByStatus_WithWhitespaceStatus_ShouldReturnAllOrders() {
        // Arrange
        String estado = "   ";
        int page = 0;
        int size = 10;

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> ordersPage = new Page<>(orders, page, size, 1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(Collections.singletonList(orderResponse));
        pageResponse.setPageNumber(page);
        pageResponse.setPageSize(size);
        pageResponse.setTotalElements(1L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(null), eq(page), eq(size)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, page, size, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(orderServicePort).getOrdersByEmployeeAndStatus(2L, null, page, size);
    }

    @Test
    void getOrdersByStatus_WithLowercaseStatus_ShouldConvertToUppercase() {
        // Arrange
        String estado = "pendiente";
        int page = 0;
        int size = 10;

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> ordersPage = new Page<>(orders, page, size, 1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(Collections.singletonList(orderResponse));
        pageResponse.setPageNumber(page);
        pageResponse.setPageSize(size);
        pageResponse.setTotalElements(1L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(OrderStatus.PENDIENTE), eq(page), eq(size)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, page, size, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(orderServicePort).getOrdersByEmployeeAndStatus(2L, OrderStatus.PENDIENTE, page, size);
    }

    @Test
    void getOrdersByStatus_WithMixedCaseStatus_ShouldConvertToUppercase() {
        // Arrange
        String estado = "En_PreParAcIoN";
        int page = 0;
        int size = 10;

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> ordersPage = new Page<>(orders, page, size, 1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(Collections.singletonList(orderResponse));
        pageResponse.setPageNumber(page);
        pageResponse.setPageSize(size);
        pageResponse.setTotalElements(1L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(OrderStatus.EN_PREPARACION), eq(page), eq(size)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, page, size, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(orderServicePort).getOrdersByEmployeeAndStatus(2L, OrderStatus.EN_PREPARACION, page, size);
    }

    @Test
    void getOrdersByStatus_WithAllValidStatuses_ShouldWork() {
        // Test todos los estados válidos
        String[] validStatuses = {"PENDIENTE", "EN_PREPARACION", "LISTO", "ENTREGADO", "CANCELADO"};
        OrderStatus[] expectedStatuses = {OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION,
                OrderStatus.LISTO, OrderStatus.ENTREGADO, OrderStatus.CANCELADO};

        for (int i = 0; i < validStatuses.length; i++) {
            // Arrange
            String estado = validStatuses[i];
            OrderStatus expectedStatus = expectedStatuses[i];

            Order order = new Order();
            order.setId(1L);

            List<Order> orders = Collections.singletonList(order);
            Page<Order> ordersPage = new Page<>(orders, 0, 10, 1L);

            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(1L);

            PageResponse<OrderResponse> pageResponse = new PageResponse<>();
            pageResponse.setContent(Collections.singletonList(orderResponse));
            pageResponse.setPageNumber(0);
            pageResponse.setPageSize(10);
            pageResponse.setTotalElements(1L);

            when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(expectedStatus), eq(0), eq(10)))
                    .thenReturn(ordersPage);
            when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

            // Act
            ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                    estado, 0, 10, employeeAuthentication);

            // Assert
            assertEquals(HttpStatus.OK, result.getStatusCode());
            verify(orderServicePort).getOrdersByEmployeeAndStatus(2L, expectedStatus, 0, 10);

            // Reset mocks for next iteration
            reset(orderServicePort, orderResponseMapper);
        }
    }

    @Test
    void getOrdersByStatus_WithDifferentEmployeeId_ShouldUseCorrectEmployeeId() {
        // Arrange
        Authentication differentEmployeeAuth = new TestingAuthenticationToken("999", null, "EMPLEADO");
        String estado = "LISTO";

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> ordersPage = new Page<>(orders, 0, 10, 1L);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(1L);

        PageResponse<OrderResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(Collections.singletonList(orderResponse));
        pageResponse.setPageNumber(0);
        pageResponse.setPageSize(10);
        pageResponse.setTotalElements(1L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(999L), eq(OrderStatus.LISTO), eq(0), eq(10)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(pageResponse);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, 0, 10, differentEmployeeAuth);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(orderServicePort).getOrdersByEmployeeAndStatus(999L, OrderStatus.LISTO, 0, 10);
    }

    @Test
    void getOrdersByStatus_ServiceThrowsException_ShouldPropagateException() {
        // Arrange
        String estado = "PENDIENTE";
        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(OrderStatus.PENDIENTE), eq(0), eq(10)))
                .thenThrow(new RuntimeException("Error del servicio"));

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> orderController.getOrdersByStatus(estado, 0, 10, employeeAuthentication)
        );

        assertEquals("Error del servicio", exception.getMessage());
        verify(orderResponseMapper, never()).toPageResponse(any());
    }

    @Test
    void getOrdersByStatus_MapperReturnsNull_ShouldReturnNullBody() {
        // Arrange
        String estado = "PENDIENTE";

        Order order = new Order();
        order.setId(1L);

        List<Order> orders = Collections.singletonList(order);
        Page<Order> ordersPage = new Page<>(orders, 0, 10, 1L);

        when(orderServicePort.getOrdersByEmployeeAndStatus(eq(2L), eq(OrderStatus.PENDIENTE), eq(0), eq(10)))
                .thenReturn(ordersPage);
        when(orderResponseMapper.toPageResponse(ordersPage)).thenReturn(null);

        // Act
        ResponseEntity<PageResponse<OrderResponse>> result = orderController.getOrdersByStatus(
                estado, 0, 10, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void getOrdersByStatus_WithInvalidStatus_ShouldThrowIllegalArgumentException() {
        // Arrange
        String estado = "INVALID_STATUS";
        int page = 0;
        int size = 10;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> orderController.getOrdersByStatus(estado, page, size, employeeAuthentication)
        );

        assertTrue(exception.getMessage().contains("INVALID_STATUS"));
        // Removed problematic verify calls with any() matchers
        verify(orderServicePort, never()).getOrdersByEmployeeAndStatus(anyLong(), any(), anyInt(), anyInt());
        verify(orderResponseMapper, never()).toPageResponse(any());
    }

    // ============= TESTS ESENCIALES PARA assignEmployeeToOrder =============

    @Test
    void assignEmployeeToOrder_ValidRequest_ShouldReturnUpdatedOrder() {
        // Arrange
        OrderResponse updatedOrderResponse = new OrderResponse();
        updatedOrderResponse.setId(1L);
        updatedOrderResponse.setIdCliente(1L);
        updatedOrderResponse.setIdRestaurante(10L);
        updatedOrderResponse.setIdEmpleado(2L);
        updatedOrderResponse.setEstado("EN_PREPARACION");

        when(orderServicePort.assignEmployeeToOrder(eq(1L), eq(2L))).thenReturn(updatedOrder);
        when(orderResponseMapper.orderToResponse(updatedOrder)).thenReturn(updatedOrderResponse);

        // Act
        ResponseEntity<OrderResponse> result = orderController.assignEmployeeToOrder(
                assignEmployeeRequest, employeeAuthentication);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1L, result.getBody().getId());
        assertEquals(2L, result.getBody().getIdEmpleado());
        assertEquals("EN_PREPARACION", result.getBody().getEstado());

        verify(orderServicePort).assignEmployeeToOrder(1L, 2L);
        verify(orderResponseMapper).orderToResponse(updatedOrder);
    }

    @Test
    void assignEmployeeToOrder_ServiceThrowsInvalidOrderException_ShouldPropagateException() {
        // Arrange
        when(orderServicePort.assignEmployeeToOrder(eq(1L), eq(2L)))
                .thenThrow(new InvalidOrderException("El pedido no está en estado PENDIENTE"));

        // Act & Assert
        InvalidOrderException exception = assertThrows(
                InvalidOrderException.class,
                () -> orderController.assignEmployeeToOrder(assignEmployeeRequest, employeeAuthentication)
        );

        assertEquals("El pedido no está en estado PENDIENTE", exception.getMessage());
        verify(orderServicePort).assignEmployeeToOrder(1L, 2L);
        verify(orderResponseMapper, never()).orderToResponse(any());
    }

    @Test
    void assignEmployeeToOrder_WithDifferentEmployeeId_ShouldUseCorrectEmployeeId() {
        // Arrange
        Authentication differentEmployeeAuth = new TestingAuthenticationToken("999", null, "EMPLEADO");
        OrderResponse updatedOrderResponse = new OrderResponse();
        updatedOrderResponse.setId(1L);
        updatedOrderResponse.setIdEmpleado(999L);

        Order differentUpdatedOrder = new Order();
        differentUpdatedOrder.setId(1L);
        differentUpdatedOrder.setIdEmpleado(999L);
        differentUpdatedOrder.setEstado(OrderStatus.EN_PREPARACION);

        when(orderServicePort.assignEmployeeToOrder(eq(1L), eq(999L))).thenReturn(differentUpdatedOrder);
        when(orderResponseMapper.orderToResponse(differentUpdatedOrder)).thenReturn(updatedOrderResponse);

        // Act
        ResponseEntity<OrderResponse> result = orderController.assignEmployeeToOrder(
                assignEmployeeRequest, differentEmployeeAuth);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(999L, result.getBody().getIdEmpleado());
        verify(orderServicePort).assignEmployeeToOrder(1L, 999L);
    }
}
package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IMessagingServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidOrderException;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IMessagingServicePort messagingServicePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Order createValidOrder() {
        OrderDish dish = new OrderDish();
        dish.setIdPlato(1L);
        dish.setCantidad(2);

        Order order = new Order();
        order.setIdRestaurante(10L);
        order.setPlatos(List.of(dish));
        return order;
    }

    @Test
    void createOrder_validOrder_savesSuccessfully() {
        Order order = createValidOrder();
        when(orderPersistencePort.hasActiveOrderForClient(1L)).thenReturn(false);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(true);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(10L);
        when(orderPersistencePort.saveOrder(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

        Order result = orderUseCase.createOrder(order, 1L);

        assertNotNull(result);
        assertEquals(OrderStatus.PENDIENTE, result.getEstado());
    }

    @Test
    void createOrder_orderNull_throwsException() {
        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(null, 1L));
        assertEquals(DomainConstants.Order.ERROR_ORDER_NULO, ex.getMessage());
    }

    @Test
    void createOrder_clientIdNull_throwsException() {
        Order order = createValidOrder();
        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, null));
        assertEquals(DomainConstants.Order.ERROR_CLIENTE_REQUERIDO, ex.getMessage());
    }

    @Test
    void createOrder_restaurantIdNull_throwsException() {
        Order order = createValidOrder();
        order.setIdRestaurante(null);
        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_RESTAURANTE_REQUERIDO, ex.getMessage());
    }

    @Test
    void createOrder_nullDishList_throwsException() {
        Order order = createValidOrder();
        order.setPlatos(null);
        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_PLATOS_REQUERIDOS, ex.getMessage());
    }

    @Test
    void createOrder_emptyDishList_throwsException() {
        Order order = createValidOrder();
        order.setPlatos(new ArrayList<>());
        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_PLATOS_REQUERIDOS, ex.getMessage());
    }

    @Test
    void createOrder_activeOrderExists_throwsException() {
        Order order = createValidOrder();
        when(orderPersistencePort.hasActiveOrderForClient(1L)).thenReturn(true);

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_CLIENTE_TIENE_PEDIDO_ACTIVO, ex.getMessage());
    }

    @Test
    void createOrder_invalidDishQuantity_throwsException() {
        Order order = createValidOrder();
        order.getPlatos().get(0).setCantidad(0);
        when(orderPersistencePort.hasActiveOrderForClient(1L)).thenReturn(false);

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_DATOS_PLATOS_INVALIDOS, ex.getMessage());
    }

    @Test
    void createOrder_dishNotExists_throwsException() {
        Order order = createValidOrder();
        when(orderPersistencePort.hasActiveOrderForClient(1L)).thenReturn(false);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(false);

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_PLATO_NO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void createOrder_dishNotActive_throwsException() {
        Order order = createValidOrder();
        when(orderPersistencePort.hasActiveOrderForClient(1L)).thenReturn(false);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(false);

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_PLATO_NO_ACTIVO, ex.getMessage());
    }

    @Test
    void createOrder_dishFromDifferentRestaurant_throwsException() {
        Order order = createValidOrder();
        when(orderPersistencePort.hasActiveOrderForClient(1L)).thenReturn(false);
        when(orderPersistencePort.existsDishById(1L)).thenReturn(true);
        when(orderPersistencePort.isDishActive(1L)).thenReturn(true);
        when(orderPersistencePort.getDishRestaurantId(1L)).thenReturn(20L); // distinto

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.createOrder(order, 1L));
        assertEquals(DomainConstants.Order.ERROR_RESTAURANTE_NO_COINCIDE, ex.getMessage());
    }

    @Test
    void getOrdersByEmployeeAndStatus_valid() {
        Page<Order> page = new Page<>(List.of(new Order()), 0, 1, 1);
        when(orderPersistencePort.getEmployeeRestaurantId(1L)).thenReturn(10L);
        when(orderPersistencePort.findOrdersByRestaurantAndStatus(10L, OrderStatus.PENDIENTE, 0, 10)).thenReturn(page);

        Page<Order> result = orderUseCase.getOrdersByEmployeeAndStatus(1L, OrderStatus.PENDIENTE, 0, 10);

        assertEquals(page, result);
    }

    @Test
    void getOrdersByEmployeeAndStatus_nullEmployee_throwsException() {
        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.getOrdersByEmployeeAndStatus(null, OrderStatus.PENDIENTE, 0, 10));
        assertEquals(DomainConstants.Order.ERROR_EMPLEADO_REQUERIDO, ex.getMessage());
    }

    @Test
    void assignEmployeeToOrder_valid_assignsSuccessfully() {
        Order order = new Order();
        order.setIdRestaurante(10L);
        order.setEstado(OrderStatus.PENDIENTE);

        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.of(order));
        when(orderPersistencePort.getEmployeeRestaurantId(2L)).thenReturn(10L);
        when(orderPersistencePort.updateOrder(any())).thenAnswer(inv -> inv.getArgument(0));

        Order result = orderUseCase.assignEmployeeToOrder(1L, 2L);

        assertEquals(OrderStatus.EN_PREPARACION, result.getEstado());
        assertEquals(2L, result.getIdEmpleado());
    }


    @Test
    void assignEmployeeToOrder_invalidStatus_throwsException() {
        Order order = new Order();
        order.setIdRestaurante(10L);
        order.setEstado(OrderStatus.LISTO);
        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.of(order));
        when(orderPersistencePort.getEmployeeRestaurantId(2L)).thenReturn(10L);

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.assignEmployeeToOrder(1L, 2L));
        assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_PENDIENTE, ex.getMessage());
    }

    @Test
    void markOrderAsReady_valid_sendsNotification() {
        Order order = new Order();
        order.setIdRestaurante(10L);
        order.setEstado(OrderStatus.EN_PREPARACION);

        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.of(order));
        when(orderPersistencePort.getEmployeeRestaurantId(2L)).thenReturn(10L);
        when(orderPersistencePort.updateOrder(any())).thenReturn(order);
        when(orderPersistencePort.getClientPhoneByOrderId(1L)).thenReturn("+573001112233");
        when(orderPersistencePort.getRestaurantNameByOrderId(1L)).thenReturn("Restaurante");

        Order result = orderUseCase.markOrderAsReady(1L, 2L);

        assertEquals(OrderStatus.LISTO, result.getEstado());
        verify(messagingServicePort).sendOrderReadyNotification(anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void markOrderAsReady_notificationFails_doesNotThrow() {
        Order order = new Order();
        order.setIdRestaurante(10L);
        order.setEstado(OrderStatus.EN_PREPARACION);

        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.of(order));
        when(orderPersistencePort.getEmployeeRestaurantId(2L)).thenReturn(10L);
        when(orderPersistencePort.updateOrder(any())).thenReturn(order);
        when(orderPersistencePort.getClientPhoneByOrderId(1L)).thenThrow(new RuntimeException());

        Order result = orderUseCase.markOrderAsReady(1L, 2L);

        assertEquals(OrderStatus.LISTO, result.getEstado());
    }

    @Test
    void markOrderAsReady_orderNotFound_throwsException() {
        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.empty());

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.markOrderAsReady(1L, 2L));
        assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void assignEmployeeToOrder_wrongRestaurant_throwsException() {
        Order order = new Order();
        order.setEstado(OrderStatus.PENDIENTE);
        order.setIdRestaurante(10L);

        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.of(order));
        when(orderPersistencePort.getEmployeeRestaurantId(2L)).thenReturn(99L); // Restaurante distinto

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.assignEmployeeToOrder(1L, 2L));
        assertEquals(DomainConstants.Order.ERROR_EMPLEADO_NO_PERTENECE_RESTAURANTE, ex.getMessage());
    }

    @Test
    void assignEmployeeToOrder_notFound_throwsException() {
        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.empty());

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.assignEmployeeToOrder(1L, 2L));
        assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void markOrderAsReady_wrongRestaurant_throwsException() {
        Order order = new Order();
        order.setEstado(OrderStatus.EN_PREPARACION);
        order.setIdRestaurante(10L);

        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.of(order));
        when(orderPersistencePort.getEmployeeRestaurantId(2L)).thenReturn(99L); // Restaurante distinto

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.markOrderAsReady(1L, 2L));
        assertEquals(DomainConstants.Order.ERROR_EMPLEADO_NO_PERTENECE_RESTAURANTE, ex.getMessage());
    }

    @Test
    void markOrderAsReady_invalidStatus_throwsException() {
        Order order = new Order();
        order.setEstado(OrderStatus.LISTO);
        order.setIdRestaurante(10L);

        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.of(order));
        when(orderPersistencePort.getEmployeeRestaurantId(2L)).thenReturn(10L);

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.markOrderAsReady(1L, 2L));
        assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_EN_PREPARACION, ex.getMessage());
    }

    @Test
    void markOrderAsReady_notFound_throwsException() {
        when(orderPersistencePort.findOrderById(1L)).thenReturn(Optional.empty());

        InvalidOrderException ex = assertThrows(InvalidOrderException.class,
                                                () -> orderUseCase.markOrderAsReady(1L, 2L));
        assertEquals(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO, ex.getMessage());
    }

}

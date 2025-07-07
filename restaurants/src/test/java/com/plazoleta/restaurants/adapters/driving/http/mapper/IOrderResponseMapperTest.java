package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderResponse;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.OrderDishResponse;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IOrderResponseMapper Tests")
class IOrderResponseMapperTest {

    private IOrderResponseMapper mapper;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IOrderResponseMapper.class);
        testDate = LocalDateTime.of(2024, 1, 1, 12, 0, 0);
    }

    @Test
    @DisplayName("Should map Order to OrderResponse correctly")
    void shouldMapOrderToOrderResponseCorrectly() {
        // Given
        List<OrderDish> platos = Arrays.asList(
                new OrderDish(1L, 100L, 10L, 2),
                new OrderDish(2L, 100L, 20L, 3)
        );

        Order order = new Order(1L, 2L, testDate, OrderStatus.PENDIENTE, 3L, 4L, "1234", platos);

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getIdCliente(), result.getIdCliente());
        assertEquals(order.getFecha(), result.getFecha());
        assertEquals(OrderStatus.PENDIENTE.name(), result.getEstado());
        assertEquals(order.getIdEmpleado(), result.getIdEmpleado());
        assertEquals(order.getIdRestaurante(), result.getIdRestaurante());
        assertEquals(order.getPinSeguridad(), result.getPinSeguridad());
        assertNotNull(result.getPlatos());
        assertEquals(2, result.getPlatos().size());
    }

    @Test
    @DisplayName("Should map Order with null values")
    void shouldMapOrderWithNullValues() {
        // Given
        Order order = new Order();
        order.setId(null);
        order.setIdCliente(null);
        order.setFecha(null);
        order.setEstado(null);
        order.setIdEmpleado(null);
        order.setIdRestaurante(null);
        order.setPinSeguridad(null);
        order.setPlatos(null);

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getIdCliente());
        assertNull(result.getFecha());
        assertNull(result.getEstado());
        assertNull(result.getIdEmpleado());
        assertNull(result.getIdRestaurante());
        assertNull(result.getPinSeguridad());
        assertNull(result.getPlatos());
    }

    @Test
    @DisplayName("Should handle null Order")
    void shouldHandleNullOrder() {
        // Given
        Order order = null;

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map Order with empty platos list")
    void shouldMapOrderWithEmptyPlatosList() {
        // Given
        Order order = new Order(1L, 2L, testDate, OrderStatus.LISTO, 3L, 4L, "5678", Collections.emptyList());

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getIdCliente(), result.getIdCliente());
        assertEquals(order.getFecha(), result.getFecha());
        assertEquals(OrderStatus.LISTO.name(), result.getEstado());
        assertEquals(order.getIdEmpleado(), result.getIdEmpleado());
        assertEquals(order.getIdRestaurante(), result.getIdRestaurante());
        assertEquals(order.getPinSeguridad(), result.getPinSeguridad());
        assertNotNull(result.getPlatos());
        assertTrue(result.getPlatos().isEmpty());
    }

    @Test
    @DisplayName("Should map all OrderStatus values correctly")
    void shouldMapAllOrderStatusValuesCorrectly() {
        // Test all enum values
        for (OrderStatus status : OrderStatus.values()) {
            // Given
            Order order = new Order(1L, 2L, testDate, status, 3L, 4L, "1234", Collections.emptyList());

            // When
            OrderResponse result = mapper.orderToResponse(order);

            // Then
            assertNotNull(result);
            assertEquals(status.name(), result.getEstado());
        }
    }

    @Test
    @DisplayName("Should map OrderDish to OrderDishResponse correctly")
    void shouldMapOrderDishToOrderDishResponseCorrectly() {
        // Given
        OrderDish orderDish = new OrderDish(1L, 100L, 10L, 5);

        // When
        OrderDishResponse result = mapper.orderDishToResponse(orderDish);

        // Then
        assertNotNull(result);
        assertEquals(orderDish.getId(), result.getId());
        assertEquals(orderDish.getIdPlato(), result.getIdPlato());
        assertEquals(orderDish.getCantidad(), result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDish with null values")
    void shouldMapOrderDishWithNullValues() {
        // Given
        OrderDish orderDish = new OrderDish(null, null, null, null);

        // When
        OrderDishResponse result = mapper.orderDishToResponse(orderDish);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getIdPlato());
        assertNull(result.getCantidad());
    }

    @Test
    @DisplayName("Should handle null OrderDish")
    void shouldHandleNullOrderDish() {
        // Given
        OrderDish orderDish = null;

        // When
        OrderDishResponse result = mapper.orderDishToResponse(orderDish);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map OrderStatus to String correctly with mapOrderStatus method")
    void shouldMapOrderStatusToStringCorrectlyWithMapOrderStatusMethod() {
        // Test all enum values
        for (OrderStatus status : OrderStatus.values()) {
            // When
            String result = mapper.mapOrderStatus(status);

            // Then
            assertEquals(status.name(), result);
        }
    }

    @Test
    @DisplayName("Should return null when mapping null OrderStatus")
    void shouldReturnNullWhenMappingNullOrderStatus() {
        // Given
        OrderStatus status = null;

        // When
        String result = mapper.mapOrderStatus(status);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map Order with large numbers correctly")
    void shouldMapOrderWithLargeNumbersCorrectly() {
        // Given
        Long largeNumber = Long.MAX_VALUE;
        Integer largeCantidad = Integer.MAX_VALUE;
        List<OrderDish> platos = Arrays.asList(
                new OrderDish(largeNumber, largeNumber, largeNumber, largeCantidad)
        );

        Order order = new Order(largeNumber, largeNumber, testDate, OrderStatus.ENTREGADO,
                                largeNumber, largeNumber, "9999", platos);

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals(largeNumber, result.getId());
        assertEquals(largeNumber, result.getIdCliente());
        assertEquals(testDate, result.getFecha());
        assertEquals(OrderStatus.ENTREGADO.name(), result.getEstado());
        assertEquals(largeNumber, result.getIdEmpleado());
        assertEquals(largeNumber, result.getIdRestaurante());
        assertEquals("9999", result.getPinSeguridad());
        assertNotNull(result.getPlatos());
        assertEquals(1, result.getPlatos().size());

        OrderDishResponse dishResponse = result.getPlatos().get(0);
        assertEquals(largeNumber, dishResponse.getId());
        assertEquals(largeNumber, dishResponse.getIdPlato());
        assertEquals(largeCantidad, dishResponse.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDish with zero and negative values")
    void shouldMapOrderDishWithZeroAndNegativeValues() {
        // Given
        OrderDish orderDishZero = new OrderDish(0L, 0L, 0L, 0);
        OrderDish orderDishNegative = new OrderDish(-1L, -2L, -3L, -4);

        // When
        OrderDishResponse resultZero = mapper.orderDishToResponse(orderDishZero);
        OrderDishResponse resultNegative = mapper.orderDishToResponse(orderDishNegative);

        // Then
        assertNotNull(resultZero);
        assertEquals(0L, resultZero.getId());
        assertEquals(0L, resultZero.getIdPlato());
        assertEquals(0, resultZero.getCantidad());

        assertNotNull(resultNegative);
        assertEquals(-1L, resultNegative.getId());
        assertEquals(-3L, resultNegative.getIdPlato());
        assertEquals(-4, resultNegative.getCantidad());
    }

    @Test
    @DisplayName("Should map Order with single dish")
    void shouldMapOrderWithSingleDish() {
        // Given
        OrderDish singleDish = new OrderDish(1L, 100L, 10L, 2);
        List<OrderDish> platos = Arrays.asList(singleDish);

        Order order = new Order(1L, 2L, testDate, OrderStatus.EN_PREPARACION, 3L, 4L, "1111", platos);

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getIdCliente(), result.getIdCliente());
        assertEquals(order.getFecha(), result.getFecha());
        assertEquals(OrderStatus.EN_PREPARACION.name(), result.getEstado());
        assertEquals(order.getIdEmpleado(), result.getIdEmpleado());
        assertEquals(order.getIdRestaurante(), result.getIdRestaurante());
        assertEquals(order.getPinSeguridad(), result.getPinSeguridad());
        assertNotNull(result.getPlatos());
        assertEquals(1, result.getPlatos().size());

        OrderDishResponse dishResponse = result.getPlatos().get(0);
        assertEquals(singleDish.getId(), dishResponse.getId());
        assertEquals(singleDish.getIdPlato(), dishResponse.getIdPlato());
        assertEquals(singleDish.getCantidad(), dishResponse.getCantidad());
    }

    @Test
    @DisplayName("Should map Order with multiple dishes maintaining order")
    void shouldMapOrderWithMultipleDishesB() {
        // Given
        List<OrderDish> platos = Arrays.asList(
                new OrderDish(1L, 100L, 10L, 2),
                new OrderDish(2L, 100L, 20L, 3),
                new OrderDish(3L, 100L, 30L, 1),
                new OrderDish(4L, 100L, 40L, 5)
        );

        Order order = new Order(1L, 2L, testDate, OrderStatus.CANCELADO, 3L, 4L, "2222", platos);

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getIdCliente(), result.getIdCliente());
        assertEquals(order.getFecha(), result.getFecha());
        assertEquals(OrderStatus.CANCELADO.name(), result.getEstado());
        assertEquals(order.getIdEmpleado(), result.getIdEmpleado());
        assertEquals(order.getIdRestaurante(), result.getIdRestaurante());
        assertEquals(order.getPinSeguridad(), result.getPinSeguridad());
        assertNotNull(result.getPlatos());
        assertEquals(4, result.getPlatos().size());

        // Verify that the order is maintained
        for (int i = 0; i < platos.size(); i++) {
            OrderDish originalDish = platos.get(i);
            OrderDishResponse responseDish = result.getPlatos().get(i);

            assertEquals(originalDish.getId(), responseDish.getId());
            assertEquals(originalDish.getIdPlato(), responseDish.getIdPlato());
            assertEquals(originalDish.getCantidad(), responseDish.getCantidad());
        }
    }

    @Test
    @DisplayName("Should handle empty pin seguridad")
    void shouldHandleEmptyPinSeguridad() {
        // Given
        Order order = new Order(1L, 2L, testDate, OrderStatus.PENDIENTE, 3L, 4L, "", Collections.emptyList());

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals("", result.getPinSeguridad());
    }

    @Test
    @DisplayName("Should handle special characters in pin seguridad")
    void shouldHandleSpecialCharactersInPinSeguridad() {
        // Given
        String specialPin = "!@#$%^&*()_+-={}[]|\\:;\"'<>?,./~`";
        Order order = new Order(1L, 2L, testDate, OrderStatus.PENDIENTE, 3L, 4L, specialPin, Collections.emptyList());

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals(specialPin, result.getPinSeguridad());
    }

    @Test
    @DisplayName("Should map Order with current timestamp")
    void shouldMapOrderWithCurrentTimestamp() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order(1L, 2L, now, OrderStatus.PENDIENTE, 3L, 4L, "1234", Collections.emptyList());

        // When
        OrderResponse result = mapper.orderToResponse(order);

        // Then
        assertNotNull(result);
        assertEquals(now, result.getFecha());
    }

    @Test
    @DisplayName("Should map OrderDish with minimum valid values")
    void shouldMapOrderDishWithMinimumValidValues() {
        // Given
        OrderDish orderDish = new OrderDish(1L, 1L, 1L, 1);

        // When
        OrderDishResponse result = mapper.orderDishToResponse(orderDish);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getIdPlato());
        assertEquals(1, result.getCantidad());
    }
}
package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.CreateOrderRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.request.OrderDishRequest;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IOrderRequestMapper Tests")
class IOrderRequestMapperTest {

    private IOrderRequestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IOrderRequestMapper.class);
    }

    @Test
    @DisplayName("Should map CreateOrderRequest with null restaurant id")
    void shouldMapCreateOrderRequestWithNullRestaurantId() {
        // Given
        CreateOrderRequest request = new CreateOrderRequest(null, Collections.emptyList());

        // When
        Order result = mapper.createRequestToOrder(request);

        // Then
        assertNotNull(result);
        assertNull(result.getIdRestaurante());
        assertNotNull(result.getPlatos());
        assertTrue(result.getPlatos().isEmpty());
    }

    @Test
    @DisplayName("Should map CreateOrderRequest with empty platos list")
    void shouldMapCreateOrderRequestWithEmptyPlatosList() {
        // Given
        Long idRestaurante = 1L;
        CreateOrderRequest request = new CreateOrderRequest(idRestaurante, Collections.emptyList());

        // When
        Order result = mapper.createRequestToOrder(request);

        // Then
        assertNotNull(result);
        assertEquals(idRestaurante, result.getIdRestaurante());
        assertNotNull(result.getPlatos());
        assertTrue(result.getPlatos().isEmpty());
    }

    @Test
    @DisplayName("Should map CreateOrderRequest with null platos list")
    void shouldMapCreateOrderRequestWithNullPlatosList() {
        // Given
        Long idRestaurante = 1L;
        CreateOrderRequest request = new CreateOrderRequest(idRestaurante, null);

        // When
        Order result = mapper.createRequestToOrder(request);

        // Then
        assertNotNull(result);
        assertEquals(idRestaurante, result.getIdRestaurante());
        assertNull(result.getPlatos());
    }

    @Test
    @DisplayName("Should handle null CreateOrderRequest")
    void shouldHandleNullCreateOrderRequest() {
        // Given
        CreateOrderRequest request = null;

        // When
        Order result = mapper.createRequestToOrder(request);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map OrderDishRequest to OrderDish correctly")
    void shouldMapOrderDishRequestToOrderDishCorrectly() {
        // Given
        Long idPlato = 5L;
        Integer cantidad = 10;
        OrderDishRequest request = new OrderDishRequest(idPlato, cantidad);

        // When
        OrderDish result = mapper.orderDishRequestToOrderDish(request);

        // Then
        assertNotNull(result);
        assertNull(result.getId()); // Should be ignored
        assertNull(result.getIdPedido()); // Should be ignored
        assertEquals(idPlato, result.getIdPlato());
        assertEquals(cantidad, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDishRequest with null values")
    void shouldMapOrderDishRequestWithNullValues() {
        // Given
        OrderDishRequest request = new OrderDishRequest(null, null);

        // When
        OrderDish result = mapper.orderDishRequestToOrderDish(request);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getIdPedido());
        assertNull(result.getIdPlato());
        assertNull(result.getCantidad());
    }

    @Test
    @DisplayName("Should handle null OrderDishRequest")
    void shouldHandleNullOrderDishRequest() {
        // Given
        OrderDishRequest request = null;

        // When
        OrderDish result = mapper.orderDishRequestToOrderDish(request);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map OrderDishRequest with zero cantidad")
    void shouldMapOrderDishRequestWithZeroCantidad() {
        // Given
        Long idPlato = 1L;
        Integer cantidad = 0;
        OrderDishRequest request = new OrderDishRequest(idPlato, cantidad);

        // When
        OrderDish result = mapper.orderDishRequestToOrderDish(request);

        // Then
        assertNotNull(result);
        assertEquals(idPlato, result.getIdPlato());
        assertEquals(cantidad, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDishRequest with negative cantidad")
    void shouldMapOrderDishRequestWithNegativeCantidad() {
        // Given
        Long idPlato = 1L;
        Integer cantidad = -5;
        OrderDishRequest request = new OrderDishRequest(idPlato, cantidad);

        // When
        OrderDish result = mapper.orderDishRequestToOrderDish(request);

        // Then
        assertNotNull(result);
        assertEquals(idPlato, result.getIdPlato());
        assertEquals(cantidad, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDishRequest with large numbers")
    void shouldMapOrderDishRequestWithLargeNumbers() {
        // Given
        Long idPlato = Long.MAX_VALUE;
        Integer cantidad = Integer.MAX_VALUE;
        OrderDishRequest request = new OrderDishRequest(idPlato, cantidad);

        // When
        OrderDish result = mapper.orderDishRequestToOrderDish(request);

        // Then
        assertNotNull(result);
        assertEquals(idPlato, result.getIdPlato());
        assertEquals(cantidad, result.getCantidad());
    }

    @Test
    @DisplayName("Should map CreateOrderRequest with single dish")
    void shouldMapCreateOrderRequestWithSingleDish() {
        // Given
        Long idRestaurante = 1L;
        OrderDishRequest dishRequest = new OrderDishRequest(10L, 5);
        CreateOrderRequest request = new CreateOrderRequest(idRestaurante, Arrays.asList(dishRequest));

        // When
        Order result = mapper.createRequestToOrder(request);

        // Then
        assertNotNull(result);
        assertEquals(idRestaurante, result.getIdRestaurante());
        assertNotNull(result.getPlatos());
        assertEquals(1, result.getPlatos().size());

        OrderDish mappedDish = result.getPlatos().get(0);
        assertEquals(dishRequest.getIdPlato(), mappedDish.getIdPlato());
        assertEquals(dishRequest.getCantidad(), mappedDish.getCantidad());
        assertNull(mappedDish.getId());
        assertNull(mappedDish.getIdPedido());
    }

    @Test
    @DisplayName("Should map CreateOrderRequest with multiple dishes")
    void shouldMapCreateOrderRequestWithMultipleDishes() {
        // Given
        Long idRestaurante = 1L;
        List<OrderDishRequest> platosRequest = Arrays.asList(
                new OrderDishRequest(1L, 2),
                new OrderDishRequest(2L, 3),
                new OrderDishRequest(3L, 1)
        );
        CreateOrderRequest request = new CreateOrderRequest(idRestaurante, platosRequest);

        // When
        Order result = mapper.createRequestToOrder(request);

        // Then
        assertNotNull(result);
        assertEquals(idRestaurante, result.getIdRestaurante());
        assertNotNull(result.getPlatos());
        assertEquals(3, result.getPlatos().size());

        for (int i = 0; i < platosRequest.size(); i++) {
            OrderDishRequest originalRequest = platosRequest.get(i);
            OrderDish mappedDish = result.getPlatos().get(i);

            assertEquals(originalRequest.getIdPlato(), mappedDish.getIdPlato());
            assertEquals(originalRequest.getCantidad(), mappedDish.getCantidad());
            assertNull(mappedDish.getId());
            assertNull(mappedDish.getIdPedido());
        }
    }

    @Test
    @DisplayName("Should map CreateOrderRequest to Order correctly")
    void shouldMapCreateOrderRequestToOrderCorrectly() {
        // Given
        Long idRestaurante = 1L;
        List<OrderDishRequest> platosRequest = Arrays.asList(
                new OrderDishRequest(1L, 2),
                new OrderDishRequest(2L, 3)
        );
        CreateOrderRequest request = new CreateOrderRequest(idRestaurante, platosRequest);

        // When
        Order result = mapper.createRequestToOrder(request);

        // Then
        assertNotNull(result);
        assertNull(result.getId()); // Should be ignored
        assertNull(result.getIdCliente()); // Should be ignored
        // Note: MapStruct creates Order using default constructor, so fecha and estado are set
        assertNotNull(result.getFecha()); // Constructor sets current time
        assertNotNull(result.getEstado()); // Constructor sets PENDIENTE
        assertNull(result.getIdEmpleado()); // Should be ignored
        assertNull(result.getPinSeguridad()); // Should be ignored
        assertEquals(idRestaurante, result.getIdRestaurante());
        assertNotNull(result.getPlatos());
        assertEquals(2, result.getPlatos().size());
    }
}

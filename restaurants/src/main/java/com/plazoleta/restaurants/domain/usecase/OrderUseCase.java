package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidOrderException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort) {
        this.orderPersistencePort = orderPersistencePort;
    }

    @Override
    public Order createOrder(Order order, Long clientId) {
        validateOrder(order);
        validateClient(clientId);
        validateClientHasNoActiveOrder(clientId);
        validateOrderDishes(order);
        validateSameRestaurant(order);
        validateDishesExistAndActive(order);

        order.setIdCliente(clientId);
        order.setFecha(LocalDateTime.now());
        order.setEstado(OrderStatus.PENDIENTE);

        return orderPersistencePort.saveOrder(order);
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_ORDER_NULO);
        }

        if (order.getIdRestaurante() == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_RESTAURANTE_REQUERIDO);
        }

        if (order.getPlatos() == null || order.getPlatos().isEmpty()) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATOS_REQUERIDOS);
        }
    }

    private void validateClient(Long clientId) {
        if (clientId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_CLIENTE_REQUERIDO);
        }
    }

    private void validateClientHasNoActiveOrder(Long clientId) {
        if (orderPersistencePort.hasActiveOrderForClient(clientId)) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_CLIENTE_TIENE_PEDIDO_ACTIVO);
        }
    }

    private void validateOrderDishes(Order order) {
        for (OrderDish orderDish : order.getPlatos()) {
            if (orderDish.getIdPlato() == null) {
                throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATO_ID_REQUERIDO);
            }

            if (orderDish.getCantidad() == null || orderDish.getCantidad() <= 0) {
                throw new InvalidOrderException(DomainConstants.Order.ERROR_CANTIDAD_POSITIVA);
            }
        }
    }

    private void validateSameRestaurant(Order order) {
        Set<Long> restaurantIds = new HashSet<>();

        for (OrderDish orderDish : order.getPlatos()) {
            Long dishRestaurantId = orderPersistencePort.getDishRestaurantId(orderDish.getIdPlato());
            restaurantIds.add(dishRestaurantId);
        }

        if (restaurantIds.size() > 1) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATOS_MISMO_RESTAURANTE);
        }

        Long dishRestaurantId = restaurantIds.iterator().next();
        if (!order.getIdRestaurante().equals(dishRestaurantId)) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_RESTAURANTE_NO_COINCIDE);
        }
    }

    private void validateDishesExistAndActive(Order order) {
        for (OrderDish orderDish : order.getPlatos()) {
            if (!orderPersistencePort.existsDishById(orderDish.getIdPlato())) {
                throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATO_NO_ENCONTRADO);
            }

            if (!orderPersistencePort.isDishActive(orderDish.getIdPlato())) {
                throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATO_NO_ACTIVO);
            }
        }
    }
}
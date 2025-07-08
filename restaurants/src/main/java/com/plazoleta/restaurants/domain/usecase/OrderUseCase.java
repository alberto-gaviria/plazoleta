package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidOrderException;
import com.plazoleta.restaurants.domain.util.paged.Page;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
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
        order.setIdEmpleado(null);
        order.setPinSeguridad(null);

        return orderPersistencePort.saveOrder(order);
    }

    @Override
    public Page<Order> getOrdersByEmployeeAndStatus(Long employeeId, OrderStatus estado, int pageNumber, int pageSize) {
        validateEmployeeOrderListParameters(employeeId, pageNumber, pageSize);

        Long restaurantId = orderPersistencePort.getEmployeeRestaurantId(employeeId);

        return orderPersistencePort.findOrdersByRestaurantAndStatus(restaurantId, estado, pageNumber, pageSize);
    }

    @Override
    public Order assignEmployeeToOrder(Long orderId, Long employeeId) {
        validateAssignEmployeeParameters(orderId, employeeId);

        Optional<Order> orderOptional = orderPersistencePort.findOrderById(orderId);
        if (orderOptional.isEmpty()) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO);
        }

        Order order = orderOptional.get();

        Long employeeRestaurantId = orderPersistencePort.getEmployeeRestaurantId(employeeId);
        if (!order.getIdRestaurante().equals(employeeRestaurantId)) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_EMPLEADO_RESTAURANTE_DIFERENTE);
        }

        if (!OrderStatus.PENDIENTE.equals(order.getEstado())) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_PENDIENTE);
        }
        order.setIdEmpleado(employeeId);
        order.setEstado(OrderStatus.EN_PREPARACION);

        return orderPersistencePort.updateOrder(order);
    }

    private void validateEmployeeOrderListParameters(Long employeeId, int pageNumber, int pageSize) {
        if (employeeId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_EMPLEADO_REQUERIDO);
        }

        if (pageNumber < DomainConstants.Order.MIN_PAGE_NUMBER) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PAGE_NUMBER_INVALID);
        }

        if (pageSize < DomainConstants.Order.MIN_PAGE_SIZE) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PAGE_SIZE_INVALID);
        }

        if (pageSize > DomainConstants.Order.MAX_PAGE_SIZE) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PAGE_SIZE_TOO_LARGE);
        }
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

        if (restaurantIds.size() > DomainConstants.Order.MAX_RESTAURANTS_PER_ORDER) {
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

    private void validateAssignEmployeeParameters(Long orderId, Long employeeId) {
        if (orderId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_ID_REQUERIDO);
        }

        if (employeeId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_EMPLEADO_REQUERIDO);
        }
    }
}
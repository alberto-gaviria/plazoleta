package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.api.IMessagingServicePort;
import com.plazoleta.restaurants.domain.api.IOrderServicePort;
import com.plazoleta.restaurants.domain.api.ITraceabilityServicePort;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.model.OrderStatus;
import com.plazoleta.restaurants.domain.service.SecurityPinGenerator;
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
    private final IMessagingServicePort messagingServicePort;
    private final ITraceabilityServicePort traceabilityServicePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort,
                        IMessagingServicePort messagingServicePort,
                        ITraceabilityServicePort traceabilityServicePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.messagingServicePort = messagingServicePort;
        this.traceabilityServicePort = traceabilityServicePort;
    }

    @Override
    public Order createOrder(Order order, Long clientId) {
        validateOrderCreation(order, clientId);

        order.setIdCliente(clientId);
        order.setFecha(LocalDateTime.now());
        order.setEstado(OrderStatus.PENDIENTE);
        order.setIdEmpleado(null);
        order.setPinSeguridad(null);

        Order savedOrder = orderPersistencePort.saveOrder(order);

        recordTraceability(savedOrder.getId(), clientId, null,
                           DomainConstants.Order.ESTADO_PENDIENTE, null, null);

        return savedOrder;
    }

    @Override
    public Page<Order> getOrdersByEmployeeAndStatus(Long employeeId, OrderStatus estado, int pageNumber, int pageSize) {
        if (employeeId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_EMPLEADO_REQUERIDO);
        }

        Long restaurantId = orderPersistencePort.getEmployeeRestaurantId(employeeId);
        return orderPersistencePort.findOrdersByRestaurantAndStatus(restaurantId, estado, pageNumber, pageSize);
    }

    @Override
    public Order assignEmployeeToOrder(Long orderId, Long employeeId) {
        if (orderId == null || employeeId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_EMPLEADO_REQUERIDOS);
        }

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

        Order savedOrder = orderPersistencePort.updateOrder(order);

        recordTraceability(orderId, order.getIdCliente(), DomainConstants.Order.ESTADO_PENDIENTE,
                           DomainConstants.Order.ESTADO_EN_PREPARACION, employeeId, null);

        return savedOrder;
    }

    @Override
    public Order markOrderAsReady(Long orderId, Long employeeId) {
        if (orderId == null || employeeId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_EMPLEADO_REQUERIDOS);
        }

        Optional<Order> orderOptional = orderPersistencePort.findOrderById(orderId);
        if (orderOptional.isEmpty()) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO);
        }

        Order order = orderOptional.get();
        Long employeeRestaurantId = orderPersistencePort.getEmployeeRestaurantId(employeeId);

        if (!order.getIdRestaurante().equals(employeeRestaurantId)) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_EMPLEADO_RESTAURANTE_DIFERENTE);
        }

        if (!OrderStatus.EN_PREPARACION.equals(order.getEstado())) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_EN_PREPARACION);
        }

        String pin = SecurityPinGenerator.generate();
        order.setPinSeguridad(pin);
        order.setEstado(OrderStatus.LISTO);

        Order updatedOrder = orderPersistencePort.updateOrder(order);

        recordTraceability(orderId, order.getIdCliente(), DomainConstants.Order.ESTADO_EN_PREPARACION,
                           DomainConstants.Order.ESTADO_LISTO, employeeId, null);

        try {
            String clientPhone = orderPersistencePort.getClientPhoneByOrderId(orderId);
            String restaurantName = orderPersistencePort.getRestaurantNameByOrderId(orderId);

            messagingServicePort.sendOrderReadyNotification(
                    orderId.toString(),
                    clientPhone,
                    pin,
                    restaurantName
            );
        } catch (Exception e) {

        }

        return updatedOrder;
    }

    @Override
    public Order deliverOrder(Long orderId, String pin, Long employeeId) {
        if (orderId == null || employeeId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_EMPLEADO_REQUERIDOS);
        }

        if (pin == null || pin.trim().isEmpty()) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PIN_REQUERIDO);
        }

        Optional<Order> orderOptional = orderPersistencePort.findOrderById(orderId);
        if (orderOptional.isEmpty()) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO);
        }

        Order order = orderOptional.get();
        Long employeeRestaurantId = orderPersistencePort.getEmployeeRestaurantId(employeeId);

        if (!order.getIdRestaurante().equals(employeeRestaurantId)) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_EMPLEADO_RESTAURANTE_DIFERENTE);
        }

        if (!OrderStatus.LISTO.equals(order.getEstado())) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_LISTO);
        }

        if (!pin.equals(order.getPinSeguridad())) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PIN_INVALIDO);
        }

        order.setEstado(OrderStatus.ENTREGADO);

        Order savedOrder = orderPersistencePort.updateOrder(order);

        recordTraceability(orderId, order.getIdCliente(), DomainConstants.Order.ESTADO_LISTO,
                           DomainConstants.Order.ESTADO_ENTREGADO, employeeId, null);

        return savedOrder;
    }

    @Override
    public Order cancelOrder(Long orderId, Long clientId) {
        if (orderId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_ID_REQUERIDO);
        }

        if (clientId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_CLIENTE_REQUERIDO);
        }

        Optional<Order> orderOptional = orderPersistencePort.findOrderById(orderId);
        if (orderOptional.isEmpty()) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO);
        }

        Order order = orderOptional.get();

        if (!order.getIdCliente().equals(clientId)) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_PERTENECE_CLIENTE);
        }

        if (!OrderStatus.PENDIENTE.equals(order.getEstado())) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PEDIDO_NO_PUEDE_CANCELARSE);
        }

        order.setEstado(OrderStatus.CANCELADO);

        Order savedOrder = orderPersistencePort.updateOrder(order);

        recordTraceability(orderId, clientId, DomainConstants.Order.ESTADO_PENDIENTE,
                           DomainConstants.Order.ESTADO_CANCELADO, null, null);

        return savedOrder;
    }

    private void recordTraceability(Long orderId, Long clientId, String previousStatus,
                                    String newStatus, Long employeeId, String specificMessage) {
        try {
            String clientEmail = getClientEmailSafely(clientId);
            String employeeEmail = getEmployeeEmailSafely(employeeId);

            traceabilityServicePort.recordOrderStatusChange(
                    orderId,
                    clientId,
                    clientEmail,
                    previousStatus,
                    newStatus,
                    employeeId,
                    employeeEmail
            );
        } catch (Exception e) {

        }
    }

    private String getClientEmailSafely(Long clientId) {
        try {
            if (clientId == null) return null;
            return orderPersistencePort.getClientEmailById(clientId);
        } catch (Exception e) {
            return DomainConstants.Order.FALLBACK_CLIENT_EMAIL;
        }
    }

    private String getEmployeeEmailSafely(Long employeeId) {
        try {
            if (employeeId == null) return null;
            return orderPersistencePort.getEmployeeEmailById(employeeId);
        } catch (Exception e) {
            return DomainConstants.Order.FALLBACK_EMPLOYEE_EMAIL;
        }
    }

    private void validateOrderCreation(Order order, Long clientId) {
        if (order == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_ORDER_NULO);
        }
        if (clientId == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_CLIENTE_REQUERIDO);
        }
        if (order.getIdRestaurante() == null) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_RESTAURANTE_REQUERIDO);
        }
        if (order.getPlatos() == null || order.getPlatos().isEmpty()) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATOS_REQUERIDOS);
        }

        if (orderPersistencePort.hasActiveOrderForClient(clientId)) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_CLIENTE_TIENE_PEDIDO_ACTIVO);
        }

        Set<Long> restaurantIds = new HashSet<>();
        for (OrderDish orderDish : order.getPlatos()) {
            if (orderDish.getIdPlato() == null || orderDish.getCantidad() == null || orderDish.getCantidad() <= 0) {
                throw new InvalidOrderException(DomainConstants.Order.ERROR_DATOS_PLATOS_INVALIDOS);
            }

            if (!orderPersistencePort.existsDishById(orderDish.getIdPlato())) {
                throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATO_NO_ENCONTRADO);
            }

            if (!orderPersistencePort.isDishActive(orderDish.getIdPlato())) {
                throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATO_NO_ACTIVO);
            }

            Long dishRestaurantId = orderPersistencePort.getDishRestaurantId(orderDish.getIdPlato());
            restaurantIds.add(dishRestaurantId);
        }

        if (restaurantIds.size() > DomainConstants.Order.MAX_RESTAURANTS_PER_ORDER) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_PLATOS_MISMO_RESTAURANTE);
        }

        if (!restaurantIds.iterator().next().equals(order.getIdRestaurante())) {
            throw new InvalidOrderException(DomainConstants.Order.ERROR_RESTAURANTE_NO_COINCIDE);
        }
    }
}
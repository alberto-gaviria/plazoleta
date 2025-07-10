package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.*;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.*;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.adapters.driven.users.client.IUserServiceClient;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.util.paged.Page;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    private final IDishRepository dishRepository;
    private final IEmployeeRestaurantRepository employeeRestaurantRepository;
    private final IRestaurantRepository restaurantRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IUserServiceClient userServiceClient;

    public OrderMysqlAdapter(IOrderRepository orderRepository,
                             IOrderDishRepository orderDishRepository,
                             IDishRepository dishRepository,
                             IEmployeeRestaurantRepository employeeRestaurantRepository,
                             IRestaurantRepository restaurantRepository,
                             IOrderEntityMapper orderEntityMapper,
                             IOrderDishEntityMapper orderDishEntityMapper,
                             IUserServiceClient userServiceClient) {
        this.orderRepository = orderRepository;
        this.orderDishRepository = orderDishRepository;
        this.dishRepository = dishRepository;
        this.employeeRestaurantRepository = employeeRestaurantRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderEntityMapper = orderEntityMapper;
        this.orderDishEntityMapper = orderDishEntityMapper;
        this.userServiceClient = userServiceClient;
    }

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);

        if (order.getPlatos() != null && !order.getPlatos().isEmpty()) {
            List<OrderDishEntity> orderDishEntities = orderDishEntityMapper.toEntityList(order.getPlatos());
            for (OrderDishEntity orderDishEntity : orderDishEntities) {
                orderDishEntity.setIdPedido(savedOrderEntity.getId());
            }
            orderDishRepository.saveAll(orderDishEntities);
        }

        Order savedOrder = orderEntityMapper.toModel(savedOrderEntity);
        List<OrderDishEntity> savedOrderDishes = orderDishRepository.findByIdPedido(savedOrderEntity.getId());
        savedOrder.setPlatos(orderDishEntityMapper.toModelList(savedOrderDishes));

        return savedOrder;
    }

    @Override
    public boolean hasActiveOrderForClient(Long clientId) {
        return orderRepository.existsByIdClienteAndEstadoIn(
                clientId,
                OrderStatus.PENDIENTE,
                OrderStatus.EN_PREPARACION,
                OrderStatus.LISTO
        );
    }

    @Override
    public boolean existsDishById(Long dishId) {
        return dishRepository.existsById(dishId);
    }

    @Override
    public Long getDishRestaurantId(Long dishId) {
        Optional<DishEntity> dishEntity = dishRepository.findById(dishId);
        if (dishEntity.isPresent()) {
            return dishEntity.get().getIdRestaurante();
        }
        throw new ElementNotFoundException(AdapterConstants.ErrorMessages.DISH_NO_ENCONTRADO);
    }

    @Override
    public boolean isDishActive(Long dishId) {
        Optional<DishEntity> dishEntity = dishRepository.findById(dishId);
        return dishEntity.map(DishEntity::getActivo).orElse(false);
    }

    @Override
    public Page<Order> findOrdersByRestaurantAndStatus(Long restaurantId, com.plazoleta.restaurants.domain.model.OrderStatus estado, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        org.springframework.data.domain.Page<OrderEntity> springPage;

        if (estado != null) {
            OrderStatus entityStatus = OrderStatus.valueOf(estado.name());
            springPage = orderRepository.findByRestaurantIdAndStatus(restaurantId, entityStatus, pageable);
        } else {
            springPage = orderRepository.findByRestaurantId(restaurantId, pageable);
        }

        List<Order> content = springPage.getContent()
                .stream()
                .map(this::convertToOrderWithDishes)
                .toList();

        return new Page<>(
                content,
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements()
        );
    }

    @Override
    public Long getEmployeeRestaurantId(Long employeeId) {
        Optional<Long> restaurantId = employeeRestaurantRepository.findRestaurantIdByEmployeeId(employeeId);

        if (restaurantId.isEmpty()) {
            throw new ElementNotFoundException(AdapterConstants.ErrorMessages.EMPLEADO_SIN_RESTAURANTE);
        }

        return restaurantId.get();
    }

    @Override
    public Optional<Order> findOrderById(Long orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if (orderEntity.isPresent()) {
            Order order = convertToOrderWithDishes(orderEntity.get());
            return Optional.of(order);
        }
        return Optional.empty();
    }

    @Override
    public Order updateOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        orderEntity.setId(order.getId());

        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return convertToOrderWithDishes(savedOrderEntity);
    }

    @Override
    public String getRestaurantNameByOrderId(Long orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if (orderEntity.isEmpty()) {
            throw new ElementNotFoundException(AdapterConstants.ErrorMessages.ORDER_NO_ENCONTRADO);
        }

        Long restaurantId = orderEntity.get().getIdRestaurante();
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(restaurantId);
        if (restaurantEntity.isEmpty()) {
            throw new ElementNotFoundException(AdapterConstants.ErrorMessages.RESTAURANT_NO_ENCONTRADO);
        }

        return restaurantEntity.get().getNombre();
    }

    @Override
    public String getClientEmailById(Long clientId) {
        try {
            return userServiceClient.getUserEmail(clientId);
        } catch (Exception e) {
            return AdapterConstants.TemporaryData.DEFAULT_CLIENT_EMAIL_PREFIX + clientId + AdapterConstants.TemporaryData.DEFAULT_EMAIL_DOMAIN;
        }
    }

    @Override
    public String getEmployeeEmailById(Long employeeId) {
        try {
            return userServiceClient.getUserEmail(employeeId);
        } catch (Exception e) {
            return AdapterConstants.TemporaryData.DEFAULT_EMPLOYEE_EMAIL_PREFIX + employeeId + AdapterConstants.TemporaryData.DEFAULT_EMAIL_DOMAIN;
        }
    }

    private Order convertToOrderWithDishes(OrderEntity orderEntity) {
        Order order = orderEntityMapper.toModel(orderEntity);

        List<OrderDishEntity> orderDishEntities = orderDishRepository.findByIdPedido(orderEntity.getId());
        List<OrderDish> orderDishes = orderDishEntityMapper.toModelList(orderDishEntities);
        order.setPlatos(orderDishes);

        return order;
    }

    @Override
    public String getClientPhoneByOrderId(Long orderId) {
        try {
            Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
            if (orderEntity.isEmpty()) {
                throw new ElementNotFoundException(AdapterConstants.ErrorMessages.ORDER_NO_ENCONTRADO);
            }

            Long clientId = orderEntity.get().getIdCliente();

            try {
                String realPhone = userServiceClient.getUserPhone(clientId);

                if (isValidPhoneFormat(realPhone)) {
                    return realPhone;
                } else {
                    return AdapterConstants.TemporaryData.DUMMY_CLIENT_PHONE;
                }
            } catch (Exception e) {
                return AdapterConstants.TemporaryData.DUMMY_CLIENT_PHONE;
            }

        } catch (Exception e) {
            return AdapterConstants.TemporaryData.DUMMY_CLIENT_PHONE;
        }
    }

    private boolean isValidPhoneFormat(String phone) {
        return phone != null &&
                !phone.trim().isEmpty() &&
                phone.matches(AdapterConstants.PhoneValidationConstants.PHONE_REGEX);
    }

    private String maskPhone(String phone) {
        if (phone.length() > AdapterConstants.PhoneValidationConstants.MIN_PHONE_LENGTH_FOR_MASKING) {
            return phone.substring(0, AdapterConstants.PhoneValidationConstants.PHONE_MASK_PREFIX_LENGTH) +
                    AdapterConstants.PhoneValidationConstants.PHONE_MASK_PATTERN +
                    phone.substring(phone.length() - AdapterConstants.PhoneValidationConstants.PHONE_MASK_SUFFIX_LENGTH);
        }
        return AdapterConstants.PhoneValidationConstants.PHONE_MASK_PATTERN;
    }
}
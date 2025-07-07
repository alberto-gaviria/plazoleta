package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderDishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderStatus;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IOrderDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IOrderDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.domain.model.Order;
import com.plazoleta.restaurants.domain.model.OrderDish;
import com.plazoleta.restaurants.domain.spi.IOrderPersistencePort;

import java.util.List;
import java.util.Optional;

public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;
    private final IDishRepository dishRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    public OrderMysqlAdapter(IOrderRepository orderRepository,
                             IOrderDishRepository orderDishRepository,
                             IDishRepository dishRepository,
                             IOrderEntityMapper orderEntityMapper,
                             IOrderDishEntityMapper orderDishEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderDishRepository = orderDishRepository;
        this.dishRepository = dishRepository;
        this.orderEntityMapper = orderEntityMapper;
        this.orderDishEntityMapper = orderDishEntityMapper;
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
}
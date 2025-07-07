package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {

    List<OrderDishEntity> findByIdPedido(Long idPedido);
}
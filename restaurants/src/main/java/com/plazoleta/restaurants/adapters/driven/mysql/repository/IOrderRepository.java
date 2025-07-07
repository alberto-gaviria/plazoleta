package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.idCliente = :clientId AND o.estado IN ('PENDIENTE', 'EN_PREPARACION', 'LISTO')")
    Optional<OrderEntity> findActiveOrderByClientId(@Param("clientId") Long clientId);

    boolean existsByIdClienteAndEstadoIn(Long idCliente, OrderStatus... estados);

    @Query("SELECT o FROM OrderEntity o WHERE o.idRestaurante = :restaurantId AND o.estado = :estado ORDER BY o.fecha ASC")
    Page<OrderEntity> findByRestaurantIdAndStatus(@Param("restaurantId") Long restaurantId,
                                                  @Param("estado") OrderStatus estado,
                                                  Pageable pageable);

    @Query("SELECT o FROM OrderEntity o WHERE o.idRestaurante = :restaurantId ORDER BY o.fecha ASC")
    Page<OrderEntity> findByRestaurantId(@Param("restaurantId") Long restaurantId, Pageable pageable);
}

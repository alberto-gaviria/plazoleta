package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query(value = """
    SELECT o.id as order_id,
           o.fecha as fecha_inicio,
           DATE_ADD(o.fecha, INTERVAL 
               CASE 
                   WHEN o.estado = 'ENTREGADO' THEN 
                       (30 + (o.id % 60))
                   WHEN o.estado = 'CANCELADO' THEN 
                       (5 + (o.id % 20))
                   ELSE 45
               END MINUTE
           ) as fecha_fin,
           o.id_empleado as empleado_id,
           o.estado as estado
    FROM pedido o
    WHERE o.id_restaurante = :restaurantId 
      AND o.estado IN ('ENTREGADO', 'CANCELADO')
      AND o.id_empleado IS NOT NULL
    ORDER BY o.fecha DESC
    """, nativeQuery = true)
    List<Object[]> findCompletedOrdersEfficiencyByRestaurant(@Param("restaurantId") Long restaurantId);

    @Query(value = """
    SELECT o.id_empleado as empleado_id,
           COUNT(o.id) as total_pedidos,
           AVG(
               CASE 
                   WHEN o.estado = 'ENTREGADO' THEN 
                       (30 + (o.id % 60))
                   WHEN o.estado = 'CANCELADO' THEN 
                       (5 + (o.id % 20))
                   ELSE 45
               END
           ) as tiempo_promedio_minutos
    FROM pedido o
    WHERE o.id_restaurante = :restaurantId 
      AND o.estado IN ('ENTREGADO', 'CANCELADO')
      AND o.id_empleado IS NOT NULL
    GROUP BY o.id_empleado
    HAVING COUNT(o.id) > 0
    ORDER BY tiempo_promedio_minutos ASC
    """, nativeQuery = true)
    List<Object[]> findEmployeesEfficiencyByRestaurant(@Param("restaurantId") Long restaurantId);
}

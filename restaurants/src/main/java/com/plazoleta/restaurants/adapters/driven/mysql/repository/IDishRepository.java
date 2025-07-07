package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    @Query("SELECT d FROM DishEntity d WHERE d.idRestaurante = :restaurantId AND d.activo = true " +
            "AND (:categoryId IS NULL OR d.idCategoria = :categoryId)")
    Page<DishEntity> findActiveDishesByRestaurantAndCategory(
            @Param("restaurantId") Long restaurantId,
            @Param("categoryId") Long categoryId,
            Pageable pageable);
}
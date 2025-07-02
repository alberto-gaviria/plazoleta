package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
}
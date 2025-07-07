package com.plazoleta.restaurants.adapters.driven.mysql.repository;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.EmployeeRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IEmployeeRestaurantRepository extends JpaRepository<EmployeeRestaurantEntity, Long> {

    @Query("SELECT er.idRestaurante FROM EmployeeRestaurantEntity er WHERE er.idEmpleado = :employeeId")
    Optional<Long> findRestaurantIdByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT er FROM EmployeeRestaurantEntity er WHERE er.idEmpleado = :employeeId")
    Optional<EmployeeRestaurantEntity> findByEmployeeId(@Param("employeeId") Long employeeId);

    boolean existsByIdEmpleadoAndIdRestaurante(Long idEmpleado, Long idRestaurante);
}
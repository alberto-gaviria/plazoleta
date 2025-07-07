// 1. EmployeeRestaurantEntity.java - CREAR NUEVA ENTIDAD
package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "empleado_restaurante",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_empleado", "id_restaurante"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_empleado", nullable = false)
    private Long idEmpleado;

    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion;

    @PrePersist
    public void prePersist() {
        if (fechaAsignacion == null) {
            fechaAsignacion = LocalDateTime.now();
        }
    }
}

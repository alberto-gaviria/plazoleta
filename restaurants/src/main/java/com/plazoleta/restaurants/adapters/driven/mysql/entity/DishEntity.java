package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "plato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;

    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    @Column(name = "id_restaurante", nullable = false)
    private Long idRestaurante;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @PrePersist
    public void prePersist() {
        if (activo == null) {
            activo = true;
        }
    }
}
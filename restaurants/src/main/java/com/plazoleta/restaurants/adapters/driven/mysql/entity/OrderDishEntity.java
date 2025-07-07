package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedido_plato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Column(name = "id_plato", nullable = false)
    private Long idPlato;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
}
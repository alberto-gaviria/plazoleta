package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishResponse {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String urlImagen;
    private Long idCategoria;
    private Long idRestaurante;
    private Boolean activo;
}
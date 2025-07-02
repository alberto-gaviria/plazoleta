package com.plazoleta.restaurants.domain.model;

import java.math.BigDecimal;

public class Dish {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String urlImagen;
    private Long idCategoria;
    private Long idRestaurante;
    private Boolean activo;

    public Dish() {
        this.activo = true;
    }

    public Dish(Long id, String nombre, BigDecimal precio, String descripcion,
                String urlImagen, Long idCategoria, Long idRestaurante, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.idCategoria = idCategoria;
        this.idRestaurante = idRestaurante;
        this.activo = activo != null ? activo : true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public Long getIdRestaurante() { return idRestaurante; }
    public void setIdRestaurante(Long idRestaurante) { this.idRestaurante = idRestaurante; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import java.math.BigDecimal;

public class DishWithCategoryResponse {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    private String urlImagen;
    private CategoryResponse categoria;
    private Boolean activo;

    public DishWithCategoryResponse() {}

    public DishWithCategoryResponse(Long id, String nombre, BigDecimal precio, String descripcion,
                                    String urlImagen, CategoryResponse categoria, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
        this.activo = activo;
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

    public CategoryResponse getCategoria() { return categoria; }
    public void setCategoria(CategoryResponse categoria) { this.categoria = categoria; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
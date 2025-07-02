package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AddDishRequest {

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotNull(message = "El precio del plato es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio del plato debe ser un número positivo y mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "El precio debe ser un número válido")
    private BigDecimal precio;

    @NotBlank(message = "La descripción del plato es obligatoria")
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String urlImagen;

    @NotNull(message = "La categoría es obligatoria")
    private Long idCategoria;

    @NotNull(message = "El restaurante es obligatorio")
    private Long idRestaurante;

    public AddDishRequest() {}

    public AddDishRequest(String nombre, BigDecimal precio, String descripcion,
                          String urlImagen, Long idCategoria, Long idRestaurante) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
        this.idCategoria = idCategoria;
        this.idRestaurante = idRestaurante;
    }

    // Getters y Setters
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
}
package com.plazoleta.restaurants.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddRestaurantRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "El NIT es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El NIT debe contener únicamente números")
    private String nit;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[0-9]{1,13}$", message = "El teléfono debe contener máximo 13 caracteres numéricos y puede incluir el símbolo +")
    private String telefono;

    @NotBlank(message = "La URL del logo es obligatoria")
    private String urlLogo;

    @NotNull(message = "El ID del propietario es obligatorio")
    private Long idPropietario;

    public AddRestaurantRequest() {}

    public AddRestaurantRequest(String nombre, String nit, String direccion,
                                String telefono, String urlLogo, Long idPropietario) {
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.urlLogo = urlLogo;
        this.idPropietario = idPropietario;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getUrlLogo() { return urlLogo; }
    public void setUrlLogo(String urlLogo) { this.urlLogo = urlLogo; }

    public Long getIdPropietario() { return idPropietario; }
    public void setIdPropietario(Long idPropietario) { this.idPropietario = idPropietario; }
}
package com.plazoleta.users.domain.model;

import com.plazoleta.users.domain.model.RoleType;
import java.time.LocalDate;

public class User {
    private Long id;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
    private Long idRol;

    public User() {}

    public User(Long id, String nombre, String apellido, String numeroDocumento,
                String celular, LocalDate fechaNacimiento, String correo,
                String clave, Long idRol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.clave = clave;
        this.idRol = idRol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public Long getIdRol() { return idRol; }
    public void setIdRol(Long idRol) { this.idRol = idRol; }

    public RoleType getRoleType() {
        return this.idRol != null ? RoleType.fromId(this.idRol) : null;
    }

    public void setRoleType(RoleType roleType) {
        this.idRol = roleType != null ? roleType.getId() : null;
    }

    public boolean hasRole(RoleType roleType) {
        return this.idRol != null && roleType != null && this.idRol.equals(roleType.getId());
    }
}
package com.plazoleta.users.domain.model;

import com.plazoleta.users.domain.util.DomainConstants;

public enum RoleType {
    ADMINISTRADOR(DomainConstants.Role.ADMINISTRADOR_ID, DomainConstants.Role.ADMINISTRADOR_AUTHORITY),
    PROPIETARIO(DomainConstants.Role.PROPIETARIO_ID, DomainConstants.Role.PROPIETARIO_AUTHORITY),
    EMPLEADO(DomainConstants.Role.EMPLEADO_ID, DomainConstants.Role.EMPLEADO_AUTHORITY),
    CLIENTE(DomainConstants.Role.CLIENTE_ID, DomainConstants.Role.CLIENTE_AUTHORITY);

    private final Long id;
    private final String authority;

    RoleType(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }

    public static RoleType fromId(Long id) {
        for (RoleType role : values()) {
            if (role.id.equals(id)) {
                return role;
            }
        }
        throw new IllegalArgumentException(DomainConstants.Role.ERROR_INVALID_ROLE_ID + id);
    }
}
package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.model.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserResponseMapper {

    @Mapping(source = "roleType", target = "rolNombre", qualifiedByName = "roleTypeToName")
    @Mapping(source = "roleType", target = "idRol", qualifiedByName = "roleTypeToId")
    UserResponse userToDto(User user);

    @org.mapstruct.Named("roleTypeToId")
    default Long roleTypeToId(RoleType roleType) {
        if (roleType == null) return null;
        return roleType.getId();
    }

    @org.mapstruct.Named("roleTypeToName")
    default String roleTypeToName(RoleType roleType) {
        if (roleType == null) return null;
        return roleType.name();
    }
}
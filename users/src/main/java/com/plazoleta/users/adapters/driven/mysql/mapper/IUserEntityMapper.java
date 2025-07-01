package com.plazoleta.users.adapters.driven.mysql.mapper;

import com.plazoleta.users.adapters.driven.mysql.entity.UserEntity;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.model.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {

    @Mapping(source = "idRol", target = "roleType", qualifiedByName = "idToRoleType")
    User toModel(UserEntity userEntity);

    @Mapping(source = "roleType", target = "idRol", qualifiedByName = "roleTypeToId")
    UserEntity toEntity(User user);

    List<User> toModelList(List<UserEntity> usuarioEntities);

    @org.mapstruct.Named("idToRoleType")
    default RoleType idToRoleType(Long idRol) {
        if (idRol == null) return null;
        return RoleType.fromId(idRol);
    }

    @org.mapstruct.Named("roleTypeToId")
    default Long roleTypeToId(RoleType roleType) {
        if (roleType == null) return null;
        return roleType.getId();
    }
}
package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.request.AddUserRequest;
import com.plazoleta.users.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idRol", ignore = true)
    User addRequestToUsuario(AddUserRequest addUserRequest);
}
package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.response.UserResponse;
import com.plazoleta.users.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserResponseMapper {

    UserResponse toUsuarioResponse(User user);

    List<UserResponse> toUsuarioResponseList(List<User> users);
}
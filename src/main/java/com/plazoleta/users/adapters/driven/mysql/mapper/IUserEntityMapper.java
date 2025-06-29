package com.plazoleta.users.adapters.driven.mysql.mapper;

import com.plazoleta.users.adapters.driven.mysql.entity.UserEntity;
import com.plazoleta.users.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IUserEntityMapper {

    User toModel(UserEntity userEntity);

    UserEntity toEntity(User user);

    List<User> toModelList(List<UserEntity> usuarioEntities);
}
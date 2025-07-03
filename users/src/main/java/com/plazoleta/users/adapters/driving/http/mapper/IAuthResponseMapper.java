package com.plazoleta.users.adapters.driving.http.mapper;

import com.plazoleta.users.adapters.driving.http.dto.response.AuthResponse;
import com.plazoleta.users.domain.model.Authentication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuthResponseMapper {

    AuthResponse authToResponse(Authentication authentication);
}
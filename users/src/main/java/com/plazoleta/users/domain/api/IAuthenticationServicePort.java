package com.plazoleta.users.domain.api;

import com.plazoleta.users.domain.model.Authentication;

public interface IAuthenticationServicePort {
    Authentication authenticate(String email, String password);
}
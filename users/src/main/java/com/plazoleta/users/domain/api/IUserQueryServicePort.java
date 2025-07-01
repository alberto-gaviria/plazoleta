package com.plazoleta.users.domain.api;

import com.plazoleta.users.domain.model.User;

public interface IUserQueryServicePort {
    User getUserById(Long id);
    boolean validateUserRole(Long userId, String expectedRole);
}
package com.plazoleta.users.domain.api;

import com.plazoleta.users.domain.model.User;

public interface IAdminUserManagementServicePort {
    void savePropietario(User user);
}
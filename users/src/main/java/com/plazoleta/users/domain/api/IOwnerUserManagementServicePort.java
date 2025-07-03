package com.plazoleta.users.domain.api;

import com.plazoleta.users.domain.model.User;

public interface IOwnerUserManagementServicePort {
    User saveEmpleado(User user, Long propietarioId);
}
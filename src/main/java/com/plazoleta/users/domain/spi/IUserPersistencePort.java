package com.plazoleta.users.domain.spi;

import com.plazoleta.users.domain.model.User;

public interface IUserPersistencePort {
    void saveUsuario(User user);
}

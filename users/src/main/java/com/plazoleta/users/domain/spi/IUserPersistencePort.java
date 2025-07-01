package com.plazoleta.users.domain.spi;

import com.plazoleta.users.domain.model.User;

import java.util.Optional;

public interface IUserPersistencePort {
    void saveUsuario(User user);
    Optional<User> findById(Long id);
    Optional<User> findByCorreo(String correo);
}
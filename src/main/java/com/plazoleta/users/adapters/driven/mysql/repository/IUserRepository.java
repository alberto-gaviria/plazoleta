package com.plazoleta.users.adapters.driven.mysql.repository;

import com.plazoleta.users.adapters.driven.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface
IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByCorreo(String correo);
    Optional<UserEntity> findByNumeroDocumento(String numeroDocumento);
}
package com.plazoleta.restaurants.domain.spi;

public interface IUserValidationPort {
    boolean existsUserById(Long userId);
    boolean hasRequiredRole(Long userId, String requiredRole);
}
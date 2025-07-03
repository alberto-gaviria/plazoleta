package com.plazoleta.users.domain.spi;

import com.plazoleta.users.domain.model.User;

public interface ITokenServicePort {
    String generateToken(User user);
    boolean validateToken(String token);
    String getEmailFromToken(String token);
    Long getUserIdFromToken(String token);
    String getRoleFromToken(String token);
}
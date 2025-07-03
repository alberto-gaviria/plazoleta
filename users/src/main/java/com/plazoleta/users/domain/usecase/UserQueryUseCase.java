package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.util.DomainConstants;
import com.plazoleta.users.domain.util.exceptions.UserNotFoundException;

public class UserQueryUseCase implements IUserQueryServicePort {

    private final IUserPersistencePort userPersistencePort;

    public UserQueryUseCase(IUserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public User getUserById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        DomainConstants.Usuario.ERROR_USUARIO_NO_ENCONTRADO + id));
    }

    @Override
    public boolean validateUserRole(Long userId, String expectedRole) {
        User user = getUserById(userId);

        if (user.getRoleType() == null) {
            return false;
        }

        return expectedRole.equals(user.getRoleType().name());
    }
}
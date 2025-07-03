package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.api.IAuthenticationServicePort;
import com.plazoleta.users.domain.model.Authentication;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.ITokenServicePort;
import com.plazoleta.users.domain.util.DomainConstants;
import com.plazoleta.users.domain.util.exceptions.InvalidCredentialsException;

import java.util.Optional;

public class AuthenticationUseCase implements IAuthenticationServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;
    private final ITokenServicePort tokenServicePort;

    public AuthenticationUseCase(IUserPersistencePort userPersistencePort,
                                 IPasswordEncoderPort passwordEncoderPort,
                                 ITokenServicePort tokenServicePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenServicePort = tokenServicePort;
    }

    @Override
    public Authentication authenticate(String email, String password) {
        validateAuthenticationData(email, password);

        Optional<User> userOptional = userPersistencePort.findByCorreo(email);
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException(DomainConstants.Authentication.ERROR_INVALID_CREDENTIALS);
        }

        User user = userOptional.get();

        if (!passwordEncoderPort.matches(password, user.getClave())) {
            throw new InvalidCredentialsException(DomainConstants.Authentication.ERROR_INVALID_CREDENTIALS);
        }

        String token = tokenServicePort.generateToken(user);

        return new Authentication(
                user.getId(),
                user.getCorreo(),
                user.getRoleType().name(),
                token
        );
    }

    private void validateAuthenticationData(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidCredentialsException(DomainConstants.Authentication.ERROR_EMAIL_REQUIRED);
        }

        if (password == null || password.trim().isEmpty()) {
            throw new InvalidCredentialsException(DomainConstants.Authentication.ERROR_PASSWORD_REQUIRED);
        }
    }
}
package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.Authentication;
import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.ITokenServicePort;
import com.plazoleta.users.domain.util.DomainConstants;
import com.plazoleta.users.domain.util.exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationUseCaseTest {

    private IUserPersistencePort userPersistencePort;
    private IPasswordEncoderPort passwordEncoderPort;
    private ITokenServicePort tokenServicePort;
    private AuthenticationUseCase authenticationUseCase;

    private final String validEmail = "test@example.com";
    private final String validPassword = "securePassword";
    private final String encodedPassword = "hashedPassword";
    private final String token = "jwt-token";

    private User mockUser;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(IUserPersistencePort.class);
        passwordEncoderPort = mock(IPasswordEncoderPort.class);
        tokenServicePort = mock(ITokenServicePort.class);
        authenticationUseCase = new AuthenticationUseCase(userPersistencePort, passwordEncoderPort, tokenServicePort);

        mockUser = new User(
                1L,
                "Juan",
                "Pérez",
                "123456789",
                "3001234567",
                LocalDate.of(1990, 1, 1),
                validEmail,
                encodedPassword,
                RoleType.CLIENTE
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailIsNull() {
        assertThatThrownBy(() -> authenticationUseCase.authenticate(null, validPassword))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining(DomainConstants.Authentication.ERROR_EMAIL_REQUIRED);
    }

    @Test
    void shouldThrowExceptionWhenEmailIsEmpty() {
        assertThatThrownBy(() -> authenticationUseCase.authenticate("   ", validPassword))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining(DomainConstants.Authentication.ERROR_EMAIL_REQUIRED);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsNull() {
        assertThatThrownBy(() -> authenticationUseCase.authenticate(validEmail, null))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining(DomainConstants.Authentication.ERROR_PASSWORD_REQUIRED);
    }

    @Test
    void shouldThrowExceptionWhenPasswordIsEmpty() {
        assertThatThrownBy(() -> authenticationUseCase.authenticate(validEmail, ""))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining(DomainConstants.Authentication.ERROR_PASSWORD_REQUIRED);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        when(userPersistencePort.findByCorreo(validEmail)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authenticationUseCase.authenticate(validEmail, validPassword))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining(DomainConstants.Authentication.ERROR_INVALID_CREDENTIALS);
    }

    @Test
    void shouldThrowExceptionWhenPasswordDoesNotMatch() {
        when(userPersistencePort.findByCorreo(validEmail)).thenReturn(Optional.of(mockUser));
        when(passwordEncoderPort.matches(validPassword, encodedPassword)).thenReturn(false);

        assertThatThrownBy(() -> authenticationUseCase.authenticate(validEmail, validPassword))
                .isInstanceOf(InvalidCredentialsException.class)
                .hasMessageContaining(DomainConstants.Authentication.ERROR_INVALID_CREDENTIALS);
    }

    @Test
    void shouldAuthenticateSuccessfullyWithValidCredentials() {
        when(userPersistencePort.findByCorreo(validEmail)).thenReturn(Optional.of(mockUser));
        when(passwordEncoderPort.matches(validPassword, encodedPassword)).thenReturn(true);
        when(tokenServicePort.generateToken(mockUser)).thenReturn(token);

        Authentication result = authenticationUseCase.authenticate(validEmail, validPassword);

        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo(validEmail);
        assertThat(result.getRole()).isEqualTo("CLIENTE");
        assertThat(result.getToken()).isEqualTo(token);
    }
}

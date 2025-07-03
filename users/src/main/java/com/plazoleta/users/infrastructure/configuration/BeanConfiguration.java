package com.plazoleta.users.infrastructure.configuration;

import com.plazoleta.users.adapters.driven.mysql.adapter.AdminUserManagement;
import com.plazoleta.users.adapters.driven.mysql.mapper.IUserEntityMapper;
import com.plazoleta.users.adapters.driven.mysql.repository.IUserRepository;
import com.plazoleta.users.infrastructure.configuration.security.adapter.PasswordEncoderAdapter;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.api.IOwnerUserManagementServicePort;
import com.plazoleta.users.domain.api.IClientUserManagementServicePort;
import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.api.IAuthenticationServicePort;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.ITokenServicePort;
import com.plazoleta.users.domain.usecase.AdminUserManagementUseCase;
import com.plazoleta.users.domain.usecase.OwnerUserManagementUseCase;
import com.plazoleta.users.domain.usecase.ClientUserManagementUseCase;
import com.plazoleta.users.domain.usecase.UserQueryUseCase;
import com.plazoleta.users.domain.usecase.AuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    @Bean
    public IUserPersistencePort userPersistencePort(
            IUserRepository userRepository,
            IUserEntityMapper userEntityMapper) {
        return new AdminUserManagement(userRepository, userEntityMapper);
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderAdapter();
    }

    @Bean
    public IAdminUserManagementServicePort adminUserManagementServicePort(
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort) {
        return new AdminUserManagementUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Bean
    public IOwnerUserManagementServicePort ownerUserManagementServicePort(
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort) {
        return new OwnerUserManagementUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Bean
    public IClientUserManagementServicePort clientUserManagementServicePort(
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort) {
        return new ClientUserManagementUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Bean
    public IUserQueryServicePort userQueryServicePort(
            IUserPersistencePort userPersistencePort) {
        return new UserQueryUseCase(userPersistencePort);
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort(
            IUserPersistencePort userPersistencePort,
            IPasswordEncoderPort passwordEncoderPort,
            ITokenServicePort tokenServicePort) {
        return new AuthenticationUseCase(userPersistencePort, passwordEncoderPort, tokenServicePort);
    }
}
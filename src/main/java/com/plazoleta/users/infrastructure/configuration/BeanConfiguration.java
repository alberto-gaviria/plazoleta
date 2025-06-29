package com.plazoleta.users.infrastructure.configuration;

import com.plazoleta.users.adapters.driven.mysql.adapter.AdminUserManagement;
import com.plazoleta.users.adapters.driven.mysql.mapper.IUserEntityMapper;
import com.plazoleta.users.adapters.driven.mysql.repository.IUserRepository;
import com.plazoleta.users.infrastructure.configuration.security.adapter.PasswordEncoderAdapter;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.usecase.AdminUserManagementUseCase;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    private final IUserRepository usuarioRepository;
    private final IUserEntityMapper usuarioEntityMapper;

    public BeanConfiguration(IUserRepository usuarioRepository,
                             IUserEntityMapper usuarioEntityMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioEntityMapper = usuarioEntityMapper;
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort() {
        return new PasswordEncoderAdapter();
    }

    @Bean
    public IUserPersistencePort usuarioPersistencePort() {
        return new AdminUserManagement(usuarioRepository, usuarioEntityMapper);
    }

    @Bean
    public IAdminUserManagementServicePort usuarioServicePort() {
        return new AdminUserManagementUseCase(usuarioPersistencePort(), passwordEncoderPort());
    }
}
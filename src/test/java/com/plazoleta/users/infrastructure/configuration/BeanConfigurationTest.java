package com.plazoleta.users.infrastructure.configuration;

import com.plazoleta.users.adapters.driven.mysql.mapper.IUserEntityMapper;
import com.plazoleta.users.adapters.driven.mysql.repository.IUserRepository;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private IUserRepository usuarioRepository;

    @Mock
    private IUserEntityMapper usuarioEntityMapper;

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration(usuarioRepository, usuarioEntityMapper);
    }

    @Test
    void passwordEncoderPort_ShouldReturnPasswordEncoderAdapter() {
        // When
        IPasswordEncoderPort passwordEncoderPort = beanConfiguration.passwordEncoderPort();

        // Then
        assertNotNull(passwordEncoderPort);
    }

    @Test
    void usuarioPersistencePort_ShouldReturnUsuarioAdapter() {
        // When
        IUserPersistencePort usuarioPersistencePort = beanConfiguration.usuarioPersistencePort();

        // Then
        assertNotNull(usuarioPersistencePort);
    }

    @Test
    void usuarioServicePort_ShouldReturnUsuarioUseCase() {
        // When
        IAdminUserManagementServicePort usuarioServicePort = beanConfiguration.usuarioServicePort();

        // Then
        assertNotNull(usuarioServicePort);
    }

    @Test
    void beanConfiguration_ShouldCreateWithDependencies() {
        // When
        BeanConfiguration config = new BeanConfiguration(usuarioRepository, usuarioEntityMapper);

        // Then
        assertNotNull(config);
    }
}
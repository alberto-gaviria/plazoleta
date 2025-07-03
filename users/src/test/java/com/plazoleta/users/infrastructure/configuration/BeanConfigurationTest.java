package com.plazoleta.users.infrastructure.configuration;

import com.plazoleta.users.adapters.driven.mysql.mapper.IUserEntityMapper;
import com.plazoleta.users.adapters.driven.mysql.repository.IUserRepository;
import com.plazoleta.users.domain.api.IAdminUserManagementServicePort;
import com.plazoleta.users.domain.api.IOwnerUserManagementServicePort;
import com.plazoleta.users.domain.api.IClientUserManagementServicePort;
import com.plazoleta.users.domain.api.IUserQueryServicePort;
import com.plazoleta.users.domain.api.IAuthenticationServicePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.ITokenServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BeanConfigurationTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserEntityMapper userEntityMapper;

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @Mock
    private ITokenServicePort tokenServicePort;

    private BeanConfiguration beanConfiguration;

    @BeforeEach
    void setUp() {
        beanConfiguration = new BeanConfiguration();
    }

    @Test
    void passwordEncoderPort_ShouldReturnPasswordEncoderAdapter() {
        // When
        IPasswordEncoderPort result = beanConfiguration.passwordEncoderPort();

        // Then
        assertNotNull(result);
        assertInstanceOf(IPasswordEncoderPort.class, result);
    }

    @Test
    void userPersistencePort_ShouldReturnAdminUserManagement() {
        // When
        IUserPersistencePort result = beanConfiguration.userPersistencePort(userRepository, userEntityMapper);

        // Then
        assertNotNull(result);
        assertInstanceOf(IUserPersistencePort.class, result);
    }

    @Test
    void adminUserManagementServicePort_ShouldReturnAdminUserManagementUseCase() {
        // When
        IAdminUserManagementServicePort result = beanConfiguration.adminUserManagementServicePort(
                userPersistencePort, passwordEncoderPort);

        // Then
        assertNotNull(result);
        assertInstanceOf(IAdminUserManagementServicePort.class, result);
    }

    @Test
    void ownerUserManagementServicePort_ShouldReturnOwnerUserManagementUseCase() {
        // When
        IOwnerUserManagementServicePort result = beanConfiguration.ownerUserManagementServicePort(
                userPersistencePort, passwordEncoderPort);

        // Then
        assertNotNull(result);
        assertInstanceOf(IOwnerUserManagementServicePort.class, result);
    }

    @Test
    void clientUserManagementServicePort_ShouldReturnClientUserManagementUseCase() {
        // When
        IClientUserManagementServicePort result = beanConfiguration.clientUserManagementServicePort(
                userPersistencePort, passwordEncoderPort);

        // Then
        assertNotNull(result);
        assertInstanceOf(IClientUserManagementServicePort.class, result);
    }

    @Test
    void userQueryServicePort_ShouldReturnUserQueryUseCase() {
        // When
        IUserQueryServicePort result = beanConfiguration.userQueryServicePort(userPersistencePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(IUserQueryServicePort.class, result);
    }

    @Test
    void authenticationServicePort_ShouldReturnAuthenticationUseCase() {
        // When
        IAuthenticationServicePort result = beanConfiguration.authenticationServicePort(
                userPersistencePort, passwordEncoderPort, tokenServicePort);

        // Then
        assertNotNull(result);
        assertInstanceOf(IAuthenticationServicePort.class, result);
    }

    @Test
    void beanConfiguration_ShouldCreateWithDefaultConstructor() {
        // When
        BeanConfiguration config = new BeanConfiguration();

        // Then
        assertNotNull(config);
    }

    @Test
    void userPersistencePort_WithNullRepository_ShouldHandleGracefully() {
        // When & Then - Esto podría lanzar excepción dependiendo de la implementación
        assertDoesNotThrow(() -> {
            IUserPersistencePort result = beanConfiguration.userPersistencePort(null, userEntityMapper);
            // Nota: Esto podría fallar en runtime dependiendo de la implementación de AdminUserManagement
        });
    }

    @Test
    void userPersistencePort_WithNullMapper_ShouldHandleGracefully() {
        // When & Then
        assertDoesNotThrow(() -> {
            IUserPersistencePort result = beanConfiguration.userPersistencePort(userRepository, null);
            // Nota: Esto podría fallar en runtime dependiendo de la implementación de AdminUserManagement
        });
    }

    @Test
    void adminUserManagementServicePort_WithNullDependencies_ShouldHandleGracefully() {
        // When & Then
        assertDoesNotThrow(() -> {
            IAdminUserManagementServicePort result = beanConfiguration.adminUserManagementServicePort(null, null);
            // Nota: Esto podría fallar en runtime dependiendo de la implementación
        });
    }

    @Test
    void ownerUserManagementServicePort_WithNullDependencies_ShouldHandleGracefully() {
        // When & Then
        assertDoesNotThrow(() -> {
            IOwnerUserManagementServicePort result = beanConfiguration.ownerUserManagementServicePort(null, null);
            // Nota: Esto podría fallar en runtime dependiendo de la implementación
        });
    }

    @Test
    void clientUserManagementServicePort_WithNullDependencies_ShouldHandleGracefully() {
        // When & Then
        assertDoesNotThrow(() -> {
            IClientUserManagementServicePort result = beanConfiguration.clientUserManagementServicePort(null, null);
            // Nota: Esto podría fallar en runtime dependiendo de la implementación
        });
    }

    @Test
    void userQueryServicePort_WithNullPersistencePort_ShouldHandleGracefully() {
        // When & Then
        assertDoesNotThrow(() -> {
            IUserQueryServicePort result = beanConfiguration.userQueryServicePort(null);
            // Nota: Esto podría fallar en runtime dependiendo de la implementación
        });
    }

    @Test
    void authenticationServicePort_WithNullDependencies_ShouldHandleGracefully() {
        // When & Then
        assertDoesNotThrow(() -> {
            IAuthenticationServicePort result = beanConfiguration.authenticationServicePort(null, null, null);
            // Nota: Esto podría fallar en runtime dependiendo de la implementación
        });
    }

    @Test
    void passwordEncoderPort_ShouldReturnSameTypeOnMultipleCalls() {
        // When
        IPasswordEncoderPort result1 = beanConfiguration.passwordEncoderPort();
        IPasswordEncoderPort result2 = beanConfiguration.passwordEncoderPort();

        // Then
        assertNotNull(result1);
        assertNotNull(result2);
        assertEquals(result1.getClass(), result2.getClass());
        // Nota: En Spring serían singleton, pero aquí llamamos directamente al método
    }

    @Test
    void allBeanMethods_ShouldReturnNonNullInstances() {
        // When
        IPasswordEncoderPort passwordEncoder = beanConfiguration.passwordEncoderPort();
        IUserPersistencePort userPersistence = beanConfiguration.userPersistencePort(userRepository, userEntityMapper);
        IAdminUserManagementServicePort adminService = beanConfiguration.adminUserManagementServicePort(userPersistencePort, passwordEncoderPort);
        IOwnerUserManagementServicePort ownerService = beanConfiguration.ownerUserManagementServicePort(userPersistencePort, passwordEncoderPort);
        IClientUserManagementServicePort clientService = beanConfiguration.clientUserManagementServicePort(userPersistencePort, passwordEncoderPort);
        IUserQueryServicePort queryService = beanConfiguration.userQueryServicePort(userPersistencePort);
        IAuthenticationServicePort authService = beanConfiguration.authenticationServicePort(userPersistencePort, passwordEncoderPort, tokenServicePort);

        // Then
        assertNotNull(passwordEncoder);
        assertNotNull(userPersistence);
        assertNotNull(adminService);
        assertNotNull(ownerService);
        assertNotNull(clientService);
        assertNotNull(queryService);
        assertNotNull(authService);
    }
}
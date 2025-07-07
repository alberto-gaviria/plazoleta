package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminUserManagementUseCaseTest {

    private IUserPersistencePort persistencePort;
    private IPasswordEncoderPort encoderPort;
    private AdminUserManagementUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IUserPersistencePort.class);
        encoderPort = mock(IPasswordEncoderPort.class);
        useCase = new AdminUserManagementUseCase(persistencePort, encoderPort);
    }

    @Test
    void savePropietario_ValidData_ShouldSave() {
        User user = buildValidUser();
        when(encoderPort.encode("clave123")).thenReturn("encoded");
        when(persistencePort.saveUsuario(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = useCase.savePropietario(user);

        assertEquals(RoleType.PROPIETARIO, saved.getRoleType());
        assertEquals("encoded", saved.getClave());
        verify(encoderPort).encode("clave123");
        verify(persistencePort).saveUsuario(user);
    }

    @Test
    void savePropietario_NullUser_ShouldThrow() {
        assertThrows(InvalidUsuarioException.class, () -> useCase.savePropietario(null));
    }

    @Test
    void savePropietario_Underage_ShouldThrow() {
        User user = buildValidUser();
        user.setFechaNacimiento(LocalDate.now().minusYears(17));
        assertThrows(InvalidUsuarioException.class, () -> useCase.savePropietario(user));
    }

    private User buildValidUser() {
        User user = new User();
        user.setNombre("Juan");
        user.setApellido("Perez");
        user.setNumeroDocumento("123456");
        user.setCelular("3001234567");
        user.setCorreo("juan@correo.com");
        user.setClave("clave123");
        user.setFechaNacimiento(LocalDate.now().minusYears(20));
        return user;
    }
}
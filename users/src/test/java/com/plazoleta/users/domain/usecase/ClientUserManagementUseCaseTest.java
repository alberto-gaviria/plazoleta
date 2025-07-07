package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientUserManagementUseCaseTest {

    private IUserPersistencePort userPersistencePort;
    private IPasswordEncoderPort passwordEncoderPort;
    private ClientUserManagementUseCase useCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(IUserPersistencePort.class);
        passwordEncoderPort = mock(IPasswordEncoderPort.class);
        useCase = new ClientUserManagementUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Test
    void saveCliente_ValidData_ShouldSave() {
        User user = buildValidUser();
        when(passwordEncoderPort.encode("clave456")).thenReturn("encoded456");
        when(userPersistencePort.saveUsuario(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = useCase.saveCliente(user);

        assertEquals(RoleType.CLIENTE, result.getRoleType());
        assertEquals("encoded456", result.getClave());
        verify(passwordEncoderPort).encode("clave456");
        verify(userPersistencePort).saveUsuario(user);
    }

    @Test
    void saveCliente_NullUser_ShouldThrow() {
        assertThrows(InvalidUsuarioException.class, () -> useCase.saveCliente(null));
    }

    @Test
    void saveCliente_UnderageUser_ShouldThrow() {
        User user = buildValidUser();
        user.setFechaNacimiento(LocalDate.now().minusYears(17)); // menor de edad
        assertThrows(InvalidUsuarioException.class, () -> useCase.saveCliente(user));
    }

    private User buildValidUser() {
        User user = new User();
        user.setNombre("Ana");
        user.setApellido("Gomez");
        user.setNumeroDocumento("987654321");
        user.setCelular("3101234567");
        user.setCorreo("ana@correo.com");
        user.setClave("clave456");
        user.setFechaNacimiento(LocalDate.now().minusYears(25));
        return user;
    }
}

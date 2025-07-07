package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import com.plazoleta.users.domain.util.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerUserManagementUseCaseTest {

    private IUserPersistencePort persistencePort;
    private IPasswordEncoderPort encoderPort;
    private OwnerUserManagementUseCase useCase;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IUserPersistencePort.class);
        encoderPort = mock(IPasswordEncoderPort.class);
        useCase = new OwnerUserManagementUseCase(persistencePort, encoderPort);
    }

    @Test
    void saveEmpleado_ValidData_ShouldSave() {
        User empleado = buildValidUser();
        User propietario = new User();
        propietario.setRoleType(RoleType.PROPIETARIO);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(propietario));
        when(encoderPort.encode(anyString())).thenReturn("encoded");
        when(persistencePort.saveUsuario(any(User.class))).thenReturn(empleado);

        User saved = useCase.saveEmpleado(empleado, 1L);

        assertEquals(RoleType.EMPLEADO, saved.getRoleType());
        verify(persistencePort).saveUsuario(empleado);
    }

    @Test
    void saveEmpleado_InvalidOwner_ShouldThrow() {
        User propietario = new User();
        propietario.setRoleType(RoleType.CLIENTE); // no es propietario

        when(persistencePort.findById(1L)).thenReturn(Optional.of(propietario));

        User empleado = buildValidUser();

        assertThrows(InvalidUsuarioException.class, () -> useCase.saveEmpleado(empleado, 1L));
    }

    @Test
    void saveEmpleado_OwnerNotFound_ShouldThrow() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());

        User empleado = buildValidUser();

        assertThrows(UserNotFoundException.class, () -> useCase.saveEmpleado(empleado, 1L));
    }

    private User buildValidUser() {
        User user = new User();
        user.setNombre("Pedro");
        user.setApellido("Ramirez");
        user.setNumeroDocumento("987654");
        user.setCelular("3109876543");
        user.setCorreo("pedro@correo.com");
        user.setClave("clave789");
        user.setFechaNacimiento(LocalDate.now().minusYears(30));
        return user;
    }
}

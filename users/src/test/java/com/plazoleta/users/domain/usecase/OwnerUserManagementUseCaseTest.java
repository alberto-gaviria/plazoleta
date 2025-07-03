package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import com.plazoleta.users.domain.util.exceptions.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerUserManagementUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    private OwnerUserManagementUseCase ownerUserManagementUseCase;

    private User validEmployeeUser;
    private User validOwnerUser;
    private Long ownerId;

    @BeforeEach
    void setUp() {
        ownerUserManagementUseCase = new OwnerUserManagementUseCase(userPersistencePort, passwordEncoderPort);

        ownerId = 1L;

        validOwnerUser = new User();
        validOwnerUser.setId(ownerId);
        validOwnerUser.setNombre("Propietario");
        validOwnerUser.setApellido("Test");
        validOwnerUser.setRoleType(RoleType.PROPIETARIO);

        validEmployeeUser = new User();
        validEmployeeUser.setNombre("Juan");
        validEmployeeUser.setApellido("Perez");
        validEmployeeUser.setNumeroDocumento("12345678");
        validEmployeeUser.setCelular("+573001234567");
        validEmployeeUser.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        validEmployeeUser.setCorreo("juan.perez@test.com");
        validEmployeeUser.setClave("password123");
    }

    @Test
    void saveEmpleado_WhenValidData_ShouldCreateEmployee() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        when(passwordEncoderPort.encode("password123")).thenReturn("encodedPassword");

        // Act
        User result = ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId);

        // Assert
        assertNotNull(result);
        assertEquals(RoleType.EMPLEADO, result.getRoleType());
        assertEquals("encodedPassword", result.getClave());
        assertEquals(validEmployeeUser, result);
        verify(userPersistencePort).findById(ownerId);
        verify(passwordEncoderPort).encode("password123");
        verify(userPersistencePort).saveUsuario(validEmployeeUser);
    }

    @Test
    void saveEmpleado_WhenOwnerNotFound_ShouldThrowUserNotFoundException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertTrue(exception.getMessage().contains("Usuario no encontrado con ID: " + ownerId));
        verify(userPersistencePort).findById(ownerId);
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveEmpleado_WhenUserIsNotOwner_ShouldThrowInvalidUsuarioException() {
        // Arrange
        User adminUser = new User();
        adminUser.setId(ownerId);
        adminUser.setRoleType(RoleType.ADMINISTRADOR);

        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(adminUser));

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("Solo el propietario puede crear cuentas de empleados", exception.getMessage());
        verify(userPersistencePort).findById(ownerId);
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveEmpleado_WhenUserIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(null, ownerId));

        assertEquals("El usuario no puede ser nulo", exception.getMessage());
        verify(userPersistencePort).findById(ownerId);
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveEmpleado_WhenNombreIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setNombre(null);

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El nombre es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenNombreIsEmpty_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setNombre("   ");

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El nombre es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenApellidoIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setApellido(null);

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El apellido es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenApellidoIsEmpty_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setApellido("   ");

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El apellido es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenDocumentoIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setNumeroDocumento(null);

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El número de documento es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenDocumentoIsEmpty_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setNumeroDocumento("   ");

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El número de documento es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenCelularIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setCelular(null);

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El celular es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenCelularIsEmpty_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setCelular("   ");

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El celular es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenCorreoIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setCorreo(null);

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El correo es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenCorreoIsEmpty_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setCorreo("   ");

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El correo es obligatorio", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenClaveIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setClave(null);

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("La clave es obligatoria", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenClaveIsEmpty_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setClave("   ");

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("La clave es obligatoria", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenFechaNacimientoIsNull_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setFechaNacimiento(null);

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("La fecha de nacimiento es obligatoria", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenUserIsMinor_ShouldThrowInvalidUsuarioException() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        validEmployeeUser.setFechaNacimiento(LocalDate.now().minusYears(17)); // 17 años

        // Act & Assert
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId));

        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
    }

    @Test
    void saveEmpleado_WhenUserIsExactly18_ShouldCreateEmployee() {
        // Arrange
        when(userPersistencePort.findById(ownerId)).thenReturn(Optional.of(validOwnerUser));
        when(passwordEncoderPort.encode("password123")).thenReturn("encodedPassword");
        validEmployeeUser.setFechaNacimiento(LocalDate.now().minusYears(18));

        // Act
        User result = ownerUserManagementUseCase.saveEmpleado(validEmployeeUser, ownerId);

        // Assert
        assertNotNull(result);
        assertEquals(RoleType.EMPLEADO, result.getRoleType());
        verify(userPersistencePort).saveUsuario(validEmployeeUser);
    }
}
package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminUserManagementUseCaseTest {

    @Mock
    private IUserPersistencePort usuarioPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private AdminUserManagementUseCase usuarioUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan@email.com");
        user.setClave("password123");
    }

    @Test
    void savePropietario_WhenValidUser_ShouldSaveSuccessfully() {
        // Given
        String encodedPassword = "encodedPassword123";
        when(passwordEncoderPort.encode(user.getClave())).thenReturn(encodedPassword);
        doNothing().when(usuarioPersistencePort).saveUsuario(any(User.class));

        // When
        usuarioUseCase.savePropietario(user);

        // Then
        assertEquals(RoleType.PROPIETARIO, user.getRoleType()); // Usando getRoleType() en lugar de getIdRol()
        assertEquals(encodedPassword, user.getClave());
        verify(passwordEncoderPort).encode("password123");
        verify(usuarioPersistencePort).saveUsuario(user);
    }

    @Test
    void savePropietario_WhenUsuarioIsNull_ShouldThrowException() {
        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(null));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenDocumentoIsNull_ShouldThrowException() {
        // Given
        user.setNumeroDocumento(null);

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenCelularIsNull_ShouldThrowException() {
        // Given
        user.setCelular(null);

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenCorreoIsNull_ShouldThrowException() {
        // Given
        user.setCorreo(null);

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenClaveIsNull_ShouldThrowException() {
        // Given
        user.setClave(null);

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenFechaNacimientoIsNull_ShouldThrowException() {
        // Given
        user.setFechaNacimiento(null);

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenUserIsMinor_ShouldThrowException() {
        // Given
        user.setFechaNacimiento(LocalDate.now().minusYears(17));

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenUserIs18YearsOld_ShouldSaveSuccessfully() {
        // Given
        user.setFechaNacimiento(LocalDate.now().minusYears(18));
        String encodedPassword = "encodedPassword123";
        when(passwordEncoderPort.encode(user.getClave())).thenReturn(encodedPassword);
        doNothing().when(usuarioPersistencePort).saveUsuario(any(User.class));

        // When
        usuarioUseCase.savePropietario(user);

        // Then
        assertEquals(RoleType.PROPIETARIO, user.getRoleType()); // Usando getRoleType()
        assertEquals(encodedPassword, user.getClave());
        verify(passwordEncoderPort).encode("password123");
        verify(usuarioPersistencePort).saveUsuario(user);
    }

    @Test
    void savePropietario_WhenNombreIsEmpty_ShouldThrowException() {
        // Given
        user.setNombre("   ");

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenApellidoIsEmpty_ShouldThrowException() {
        // Given
        user.setApellido("   ");

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenDocumentoIsEmpty_ShouldThrowException() {
        // Given
        user.setNumeroDocumento("   ");

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenCelularIsEmpty_ShouldThrowException() {
        // Given
        user.setCelular("   ");

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenCorreoIsEmpty_ShouldThrowException() {
        // Given
        user.setCorreo("   ");

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenClaveIsEmpty_ShouldThrowException() {
        // Given
        user.setClave("   ");

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_ShouldSetRoleTypeToPropietario() {
        // Given
        String encodedPassword = "encodedPassword123";
        when(passwordEncoderPort.encode(user.getClave())).thenReturn(encodedPassword);
        doNothing().when(usuarioPersistencePort).saveUsuario(any(User.class));

        // When
        usuarioUseCase.savePropietario(user);

        // Then
        assertEquals(RoleType.PROPIETARIO, user.getRoleType()); // Solo verificar que se asignó el rol correcto
        assertNotNull(user.getRoleType());
        verify(passwordEncoderPort).encode("password123");
        verify(usuarioPersistencePort).saveUsuario(user);
    }

    @Test
    void savePropietario_WhenNombreIsNull_ShouldThrowException() {
        // Given
        user.setNombre(null);

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenApellidoIsNull_ShouldThrowException() {
        // Given
        user.setApellido(null);

        // When & Then
        assertThrows(InvalidUsuarioException.class, () -> usuarioUseCase.savePropietario(user));
        verify(passwordEncoderPort, never()).encode(any());
        verify(usuarioPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void savePropietario_WhenValidUserWithMinimumAge_ShouldEncodePasswordAndSave() {
        // Given
        user.setFechaNacimiento(LocalDate.now().minusYears(18).minusDays(1)); // Justo mayor de edad
        String originalPassword = "password123";
        String encodedPassword = "encoded_password_hash";
        when(passwordEncoderPort.encode(originalPassword)).thenReturn(encodedPassword);
        doNothing().when(usuarioPersistencePort).saveUsuario(any(User.class));

        // When
        usuarioUseCase.savePropietario(user);

        // Then
        assertEquals(RoleType.PROPIETARIO, user.getRoleType());
        assertEquals(encodedPassword, user.getClave());
        assertNotEquals(originalPassword, user.getClave()); // La clave debe estar codificada
        verify(passwordEncoderPort).encode(originalPassword);
        verify(usuarioPersistencePort).saveUsuario(user);
    }

    @Test
    void savePropietario_WhenUserIsExactly18_ShouldSucceed() {
        // Given
        user.setFechaNacimiento(LocalDate.now().minusYears(18)); // Exactamente 18 años
        String encodedPassword = "encodedPassword123";
        when(passwordEncoderPort.encode(user.getClave())).thenReturn(encodedPassword);
        doNothing().when(usuarioPersistencePort).saveUsuario(any(User.class));

        // When
        usuarioUseCase.savePropietario(user);

        // Then
        assertEquals(RoleType.PROPIETARIO, user.getRoleType());
        assertEquals(encodedPassword, user.getClave());
        verify(passwordEncoderPort).encode("password123");
        verify(usuarioPersistencePort).saveUsuario(user);
    }
}
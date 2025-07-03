package com.plazoleta.users.domain.usecase;

import com.plazoleta.users.domain.model.RoleType;
import com.plazoleta.users.domain.model.User;
import com.plazoleta.users.domain.spi.IUserPersistencePort;
import com.plazoleta.users.domain.spi.IPasswordEncoderPort;
import com.plazoleta.users.domain.util.DomainConstants;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientUserManagementUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    private ClientUserManagementUseCase clientUserManagementUseCase;

    @BeforeEach
    void setUp() {
        clientUserManagementUseCase = new ClientUserManagementUseCase(userPersistencePort, passwordEncoderPort);
    }

    @Test
    void saveCliente_ValidUser_ShouldSaveClienteSuccessfully() {
        // Given
        User validUser = createValidUser();
        String encodedPassword = "$2a$10$encodedPassword";

        when(passwordEncoderPort.encode(anyString())).thenReturn(encodedPassword);
        doNothing().when(userPersistencePort).saveUsuario(any(User.class));

        // When
        User result = clientUserManagementUseCase.saveCliente(validUser);

        // Then
        assertNotNull(result);
        assertEquals(RoleType.CLIENTE, result.getRoleType());
        assertEquals(encodedPassword, result.getClave());
        assertEquals("Juan", result.getNombre());
        assertEquals("Pérez", result.getApellido());
        assertEquals("juan.perez@email.com", result.getCorreo());

        verify(passwordEncoderPort, times(1)).encode("password123");
        verify(userPersistencePort, times(1)).saveUsuario(result);
    }

    @Test
    void saveCliente_NullUser_ShouldThrowInvalidUsuarioException() {
        // Given
        User nullUser = null;

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(nullUser));

        assertEquals(DomainConstants.Usuario.ERROR_USUARIO_NULO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_NullNombre_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithNullNombre = createValidUser();
        userWithNullNombre.setNombre(null);

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithNullNombre));

        assertEquals(DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_EmptyNombre_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithEmptyNombre = createValidUser();
        userWithEmptyNombre.setNombre("");

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithEmptyNombre));

        assertEquals(DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_WhitespaceNombre_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithWhitespaceNombre = createValidUser();
        userWithWhitespaceNombre.setNombre("   ");

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithWhitespaceNombre));

        assertEquals(DomainConstants.Usuario.ERROR_NOMBRE_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_NullApellido_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithNullApellido = createValidUser();
        userWithNullApellido.setApellido(null);

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithNullApellido));

        assertEquals(DomainConstants.Usuario.ERROR_APELLIDO_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_EmptyApellido_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithEmptyApellido = createValidUser();
        userWithEmptyApellido.setApellido("");

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithEmptyApellido));

        assertEquals(DomainConstants.Usuario.ERROR_APELLIDO_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_NullNumeroDocumento_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithNullDocumento = createValidUser();
        userWithNullDocumento.setNumeroDocumento(null);

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithNullDocumento));

        assertEquals(DomainConstants.Usuario.ERROR_DOCUMENTO_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_EmptyNumeroDocumento_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithEmptyDocumento = createValidUser();
        userWithEmptyDocumento.setNumeroDocumento("");

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithEmptyDocumento));

        assertEquals(DomainConstants.Usuario.ERROR_DOCUMENTO_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_NullCelular_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithNullCelular = createValidUser();
        userWithNullCelular.setCelular(null);

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithNullCelular));

        assertEquals(DomainConstants.Usuario.ERROR_CELULAR_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_EmptyCelular_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithEmptyCelular = createValidUser();
        userWithEmptyCelular.setCelular("");

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithEmptyCelular));

        assertEquals(DomainConstants.Usuario.ERROR_CELULAR_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_NullCorreo_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithNullCorreo = createValidUser();
        userWithNullCorreo.setCorreo(null);

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithNullCorreo));

        assertEquals(DomainConstants.Usuario.ERROR_CORREO_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_EmptyCorreo_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithEmptyCorreo = createValidUser();
        userWithEmptyCorreo.setCorreo("");

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithEmptyCorreo));

        assertEquals(DomainConstants.Usuario.ERROR_CORREO_REQUERIDO, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_NullClave_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithNullClave = createValidUser();
        userWithNullClave.setClave(null);

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithNullClave));

        assertEquals(DomainConstants.Usuario.ERROR_CLAVE_REQUERIDA, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_EmptyClave_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithEmptyClave = createValidUser();
        userWithEmptyClave.setClave("");

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithEmptyClave));

        assertEquals(DomainConstants.Usuario.ERROR_CLAVE_REQUERIDA, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_NullFechaNacimiento_ShouldThrowInvalidUsuarioException() {
        // Given
        User userWithNullFecha = createValidUser();
        userWithNullFecha.setFechaNacimiento(null);

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(userWithNullFecha));

        assertEquals(DomainConstants.Usuario.ERROR_FECHA_NACIMIENTO_REQUERIDA, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_MinorAge_ShouldThrowInvalidUsuarioException() {
        // Given
        User minorUser = createValidUser();
        minorUser.setFechaNacimiento(LocalDate.now().minusYears(17)); // 17 years old

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(minorUser));

        assertEquals(DomainConstants.Usuario.ERROR_MENOR_EDAD, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_ExactlyEighteenYearsOld_ShouldSaveSuccessfully() {
        // Given
        User eighteenYearOldUser = createValidUser();
        eighteenYearOldUser.setFechaNacimiento(LocalDate.now().minusYears(18));
        String encodedPassword = "$2a$10$encodedPassword";

        when(passwordEncoderPort.encode(anyString())).thenReturn(encodedPassword);
        doNothing().when(userPersistencePort).saveUsuario(any(User.class));

        // When
        User result = clientUserManagementUseCase.saveCliente(eighteenYearOldUser);

        // Then
        assertNotNull(result);
        assertEquals(RoleType.CLIENTE, result.getRoleType());
        assertEquals(encodedPassword, result.getClave());

        verify(passwordEncoderPort, times(1)).encode("password123");
        verify(userPersistencePort, times(1)).saveUsuario(result);
    }

    @Test
    void saveCliente_TwentyFiveYearsOld_ShouldSaveSuccessfully() {
        // Given
        User adultUser = createValidUser();
        adultUser.setFechaNacimiento(LocalDate.now().minusYears(25));
        String encodedPassword = "$2a$10$encodedPassword";

        when(passwordEncoderPort.encode(anyString())).thenReturn(encodedPassword);
        doNothing().when(userPersistencePort).saveUsuario(any(User.class));

        // When
        User result = clientUserManagementUseCase.saveCliente(adultUser);

        // Then
        assertNotNull(result);
        assertEquals(RoleType.CLIENTE, result.getRoleType());
        assertEquals(encodedPassword, result.getClave());

        verify(passwordEncoderPort, times(1)).encode("password123");
        verify(userPersistencePort, times(1)).saveUsuario(result);
    }

    @Test
    void saveCliente_SeventeenYearsAndElevenMonths_ShouldThrowInvalidUsuarioException() {
        // Given
        User almostEighteenUser = createValidUser();
        almostEighteenUser.setFechaNacimiento(LocalDate.now().minusYears(18).plusMonths(1));

        // When & Then
        InvalidUsuarioException exception = assertThrows(InvalidUsuarioException.class,
                () -> clientUserManagementUseCase.saveCliente(almostEighteenUser));

        assertEquals(DomainConstants.Usuario.ERROR_MENOR_EDAD, exception.getMessage());

        verify(passwordEncoderPort, never()).encode(anyString());
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_UserWithAllFieldsTrimmed_ShouldValidateCorrectly() {
        // Given
        User userWithSpaces = createValidUser();
        userWithSpaces.setNombre("  Juan  ");
        userWithSpaces.setApellido("  Pérez  ");
        userWithSpaces.setNumeroDocumento("  12345678  ");
        userWithSpaces.setCelular("  +573001234567  ");
        userWithSpaces.setCorreo("  juan.perez@email.com  ");
        userWithSpaces.setClave("  password123  ");

        String encodedPassword = "$2a$10$encodedPassword";

        when(passwordEncoderPort.encode(anyString())).thenReturn(encodedPassword);
        doNothing().when(userPersistencePort).saveUsuario(any(User.class));

        // When
        User result = clientUserManagementUseCase.saveCliente(userWithSpaces);

        // Then
        assertNotNull(result);
        assertEquals(RoleType.CLIENTE, result.getRoleType());
        assertEquals(encodedPassword, result.getClave());

        verify(passwordEncoderPort, times(1)).encode("  password123  ");
        verify(userPersistencePort, times(1)).saveUsuario(result);
    }

    @Test
    void saveCliente_PasswordEncoderThrowsException_ShouldPropagateException() {
        // Given
        User validUser = createValidUser();

        when(passwordEncoderPort.encode(anyString())).thenThrow(new RuntimeException("Encoding failed"));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clientUserManagementUseCase.saveCliente(validUser));

        assertEquals("Encoding failed", exception.getMessage());

        verify(passwordEncoderPort, times(1)).encode("password123");
        verify(userPersistencePort, never()).saveUsuario(any());
    }

    @Test
    void saveCliente_PersistencePortThrowsException_ShouldPropagateException() {
        // Given
        User validUser = createValidUser();
        String encodedPassword = "$2a$10$encodedPassword";

        when(passwordEncoderPort.encode(anyString())).thenReturn(encodedPassword);
        doThrow(new RuntimeException("Database error")).when(userPersistencePort).saveUsuario(any(User.class));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clientUserManagementUseCase.saveCliente(validUser));

        assertEquals("Database error", exception.getMessage());

        verify(passwordEncoderPort, times(1)).encode("password123");
        verify(userPersistencePort, times(1)).saveUsuario(any(User.class));
    }

    @Test
    void saveCliente_ShouldNotModifyOriginalPasswordBeforeEncoding() {
        // Given
        User validUser = createValidUser();
        String originalPassword = validUser.getClave();
        String encodedPassword = "$2a$10$encodedPassword";

        when(passwordEncoderPort.encode(anyString())).thenReturn(encodedPassword);
        doNothing().when(userPersistencePort).saveUsuario(any(User.class));

        // When
        User result = clientUserManagementUseCase.saveCliente(validUser);

        // Then
        assertNotNull(result);
        assertEquals(encodedPassword, result.getClave());

        verify(passwordEncoderPort, times(1)).encode(originalPassword);
        verify(userPersistencePort, times(1)).saveUsuario(result);
    }

    @Test
    void constructor_ShouldInitializeCorrectly() {
        // When
        ClientUserManagementUseCase useCase = new ClientUserManagementUseCase(userPersistencePort, passwordEncoderPort);

        // Then
        assertNotNull(useCase);
    }

    // Helper methods
    private User createValidUser() {
        User user = new User();
        user.setNombre("Juan");
        user.setApellido("Pérez");
        user.setNumeroDocumento("12345678");
        user.setCelular("+573001234567");
        user.setFechaNacimiento(LocalDate.of(1990, 1, 1));
        user.setCorreo("juan.perez@email.com");
        user.setClave("password123");
        return user;
    }
}
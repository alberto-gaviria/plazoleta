package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.spi.IUserValidationPort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserValidationPort userValidationPort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant validRestaurant;
    private Long validAdminId;
    private Long validPropietarioId;

    @BeforeEach
    void setUp() {
        validAdminId = 1L;
        validPropietarioId = 2L;

        validRestaurant = new Restaurant();
        validRestaurant.setNombre("Restaurante Test");
        validRestaurant.setNit("123456789");
        validRestaurant.setDireccion("Calle 123");
        validRestaurant.setTelefono("+573001234567");
        validRestaurant.setUrlLogo("http://logo.com");
        validRestaurant.setIdPropietario(validPropietarioId);
    }

    @Test
    void shouldSaveRestaurantSuccessfully() {
        // Given
        when(userValidationPort.existsUserById(validAdminId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(eq(validAdminId), eq(DomainConstants.Restaurant.ROL_ADMINISTRADOR))).thenReturn(true);
        when(userValidationPort.existsUserById(validPropietarioId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(eq(validPropietarioId), eq(DomainConstants.Restaurant.ROL_PROPIETARIO))).thenReturn(true);
        doNothing().when(restaurantPersistencePort).saveRestaurant(any(Restaurant.class));

        // When & Then
        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId));

        // Verify
        verify(userValidationPort).existsUserById(validAdminId);
        verify(userValidationPort).hasRequiredRole(eq(validAdminId), eq(DomainConstants.Restaurant.ROL_ADMINISTRADOR));
        verify(userValidationPort).existsUserById(validPropietarioId);
        verify(userValidationPort).hasRequiredRole(eq(validPropietarioId), eq(DomainConstants.Restaurant.ROL_PROPIETARIO));
        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }

    // ========== TESTS DE VALIDACIÓN DE ADMINISTRADOR ==========

    @Test
    void shouldThrowExceptionWhenAdminIdIsNull() {
        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, null)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_ADMIN_ID_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenAdminDoesNotExist() {
        // Given
        when(userValidationPort.existsUserById(validAdminId)).thenReturn(false);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_ENCONTRADO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort).existsUserById(validAdminId);
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenAdminDoesNotHaveValidRole() {
        // Given
        when(userValidationPort.existsUserById(validAdminId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(validAdminId, DomainConstants.Restaurant.ROL_ADMINISTRADOR)).thenReturn(false);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_VALIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort).existsUserById(validAdminId);
        verify(userValidationPort).hasRequiredRole(validAdminId, DomainConstants.Restaurant.ROL_ADMINISTRADOR);
    }

    // ========== TESTS DE VALIDACIÓN DE RESTAURANTE ==========

    @Test
    void shouldThrowExceptionWhenRestaurantIsNull() {
        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(null, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_RESTAURANT_NULO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNombreIsNull() {
        // Given
        validRestaurant.setNombre(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNombreIsEmpty() {
        // Given
        validRestaurant.setNombre("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNombreIsBlank() {
        // Given
        validRestaurant.setNombre("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNitIsNull() {
        // Given
        validRestaurant.setNit(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNitIsEmpty() {
        // Given
        validRestaurant.setNit("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenNitIsBlank() {
        // Given
        validRestaurant.setNit("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenDireccionIsNull() {
        // Given
        validRestaurant.setDireccion(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenDireccionIsEmpty() {
        // Given
        validRestaurant.setDireccion("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenDireccionIsBlank() {
        // Given
        validRestaurant.setDireccion("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoIsNull() {
        // Given
        validRestaurant.setTelefono(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoIsEmpty() {
        // Given
        validRestaurant.setTelefono("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoIsBlank() {
        // Given
        validRestaurant.setTelefono("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenUrlLogoIsNull() {
        // Given
        validRestaurant.setUrlLogo(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenUrlLogoIsEmpty() {
        // Given
        validRestaurant.setUrlLogo("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenUrlLogoIsBlank() {
        // Given
        validRestaurant.setUrlLogo("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenIdPropietarioIsNull() {
        // Given
        validRestaurant.setIdPropietario(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    // ========== TESTS DE REGLAS DE NEGOCIO ==========

    @Test
    void shouldThrowExceptionWhenNombreIsOnlyNumbers() {
        // Given
        validRestaurant.setNombre("123456");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldAcceptNombreWithNumbers() {
        // Given
        validRestaurant.setNombre("Restaurante123");
        when(userValidationPort.existsUserById(validAdminId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(eq(validAdminId), eq(DomainConstants.Restaurant.ROL_ADMINISTRADOR))).thenReturn(true);
        when(userValidationPort.existsUserById(validPropietarioId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(eq(validPropietarioId), eq(DomainConstants.Restaurant.ROL_PROPIETARIO))).thenReturn(true);
        doNothing().when(restaurantPersistencePort).saveRestaurant(any(Restaurant.class));

        // When & Then
        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId));

        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }

    @Test
    void shouldThrowExceptionWhenNitHasInvalidFormat() {
        // Given
        validRestaurant.setNit("abc123");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoHasInvalidFormat() {
        // Given
        validRestaurant.setTelefono("abc123def");

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    // ========== TESTS DE VALIDACIÓN DE PROPIETARIO ==========

    @Test
    void shouldThrowExceptionWhenPropietarioDoesNotExist() {
        // Given
        when(userValidationPort.existsUserById(validAdminId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(eq(validAdminId), eq(DomainConstants.Restaurant.ROL_ADMINISTRADOR))).thenReturn(true);
        when(userValidationPort.existsUserById(validPropietarioId)).thenReturn(false);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort).existsUserById(validPropietarioId);
        verify(userValidationPort, never()).hasRequiredRole(eq(validPropietarioId), anyString());
    }

    @Test
    void shouldThrowExceptionWhenPropietarioDoesNotHaveValidRole() {
        // Given
        when(userValidationPort.existsUserById(validAdminId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(eq(validAdminId), eq(DomainConstants.Restaurant.ROL_ADMINISTRADOR))).thenReturn(true);
        when(userValidationPort.existsUserById(validPropietarioId)).thenReturn(true);
        when(userValidationPort.hasRequiredRole(eq(validPropietarioId), eq(DomainConstants.Restaurant.ROL_PROPIETARIO))).thenReturn(false);

        // When & Then
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, validAdminId)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort).existsUserById(validPropietarioId);
        verify(userValidationPort).hasRequiredRole(eq(validPropietarioId), eq(DomainConstants.Restaurant.ROL_PROPIETARIO));
    }
}
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserValidationPort userValidationPort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private Restaurant validRestaurant;

    @BeforeEach
    void setUp() {
        validRestaurant = new Restaurant();
        validRestaurant.setNombre("Restaurante Test");
        validRestaurant.setNit("123456789");
        validRestaurant.setDireccion("Calle 123");
        validRestaurant.setTelefono("+573001234567");
        validRestaurant.setUrlLogo("http://logo.com");
        validRestaurant.setIdPropietario(1L);
    }

    @Test
    void shouldSaveRestaurantSuccessfully() {
        when(userValidationPort.existsUserById(anyLong())).thenReturn(true);
        when(userValidationPort.hasRequiredRole(anyLong(), anyString())).thenReturn(true);
        doNothing().when(restaurantPersistencePort).saveRestaurant(any(Restaurant.class));

        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(validRestaurant));

        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
        verify(userValidationPort).existsUserById(1L);
        verify(userValidationPort).hasRequiredRole(1L, DomainConstants.Restaurant.ROL_PROPIETARIO);
    }

    @Test
    void shouldThrowExceptionWhenRestaurantIsNull() {
        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(null)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_RESTAURANT_NULO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenNombreIsNull() {
        validRestaurant.setNombre(null);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenNombreIsEmpty() {
        validRestaurant.setNombre("");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenNombreIsBlank() {
        validRestaurant.setNombre("   ");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenNitIsNull() {
        validRestaurant.setNit(null);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenNitIsEmpty() {
        validRestaurant.setNit("");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenNitIsBlank() {
        validRestaurant.setNit("   ");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenDireccionIsNull() {
        validRestaurant.setDireccion(null);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenDireccionIsEmpty() {
        validRestaurant.setDireccion("");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenDireccionIsBlank() {
        validRestaurant.setDireccion("   ");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoIsNull() {
        validRestaurant.setTelefono(null);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoIsEmpty() {
        validRestaurant.setTelefono("");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoIsBlank() {
        validRestaurant.setTelefono("   ");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenUrlLogoIsNull() {
        validRestaurant.setUrlLogo(null);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenUrlLogoIsEmpty() {
        validRestaurant.setUrlLogo("");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenUrlLogoIsBlank() {
        validRestaurant.setUrlLogo("   ");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenIdPropietarioIsNull() {
        validRestaurant.setIdPropietario(null);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenNombreIsOnlyNumbers() {
        validRestaurant.setNombre("123456");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldAcceptNombreWithNumbers() {
        validRestaurant.setNombre("Restaurante123");
        when(userValidationPort.existsUserById(anyLong())).thenReturn(true);
        when(userValidationPort.hasRequiredRole(anyLong(), anyString())).thenReturn(true);
        doNothing().when(restaurantPersistencePort).saveRestaurant(any(Restaurant.class));

        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(validRestaurant));

        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
        verify(userValidationPort).existsUserById(1L);
        verify(userValidationPort).hasRequiredRole(1L, DomainConstants.Restaurant.ROL_PROPIETARIO);
    }

    @Test
    void shouldAcceptNombreWithMixedContent() {
        validRestaurant.setNombre("Restaurante ABC 123");
        when(userValidationPort.existsUserById(anyLong())).thenReturn(true);
        when(userValidationPort.hasRequiredRole(anyLong(), anyString())).thenReturn(true);
        doNothing().when(restaurantPersistencePort).saveRestaurant(any(Restaurant.class));

        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(validRestaurant));

        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
        verify(userValidationPort).existsUserById(1L);
        verify(userValidationPort).hasRequiredRole(1L, DomainConstants.Restaurant.ROL_PROPIETARIO);
    }

    @Test
    void shouldThrowExceptionWhenPropietarioDoesNotExist() {
        when(userValidationPort.existsUserById(anyLong())).thenReturn(false);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort).existsUserById(1L);
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenPropietarioDoesNotHaveValidRole() {
        when(userValidationPort.existsUserById(anyLong())).thenReturn(true);
        when(userValidationPort.hasRequiredRole(anyLong(), anyString())).thenReturn(false);

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort).existsUserById(1L);
        verify(userValidationPort).hasRequiredRole(1L, DomainConstants.Restaurant.ROL_PROPIETARIO);
    }

    @Test
    void shouldThrowExceptionWhenNitHasInvalidFormat() {
        validRestaurant.setNit("abc123");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }

    @Test
    void shouldThrowExceptionWhenTelefonoHasInvalidFormat() {
        validRestaurant.setTelefono("abc123def");

        InvalidRestaurantException exception = assertThrows(
                InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant)
        );

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO, exception.getMessage());
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).existsUserById(anyLong());
        verify(userValidationPort, never()).hasRequiredRole(anyLong(), anyString());
    }
}
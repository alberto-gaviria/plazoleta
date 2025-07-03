package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    private RestaurantUseCase restaurantUseCase;

    private Restaurant validRestaurant;
    private Long adminId;

    @BeforeEach
    void setUp() {
        restaurantUseCase = new RestaurantUseCase(restaurantPersistencePort);

        adminId = 1L;

        validRestaurant = new Restaurant();
        validRestaurant.setNombre("Restaurante El Buen Sabor");
        validRestaurant.setNit("900123456");
        validRestaurant.setDireccion("Calle 123 #45-67");
        validRestaurant.setTelefono("+573001234567");
        validRestaurant.setUrlLogo("https://ejemplo.com/logo.png");
        validRestaurant.setIdPropietario(2L);
    }

    // ========== TESTS DE GUARDADO EXITOSO ==========

    @Test
    void saveRestaurant_WhenValidRestaurantAndAdmin_ShouldSaveRestaurant() {
        // Given
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When
        restaurantUseCase.saveRestaurant(validRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }

    @Test
    void saveRestaurant_WhenValidDataWithDifferentFormats_ShouldSaveSuccessfully() {
        // Given
        Restaurant customRestaurant = new Restaurant();
        customRestaurant.setNombre("Pizza Palace");
        customRestaurant.setNit("800555444");
        customRestaurant.setDireccion("Avenida 456 #78-90");
        customRestaurant.setTelefono("+57300987654");
        customRestaurant.setUrlLogo("http://pizza-logo.com/image.jpg");
        customRestaurant.setIdPropietario(3L);

        doNothing().when(restaurantPersistencePort).saveRestaurant(customRestaurant);

        // When
        restaurantUseCase.saveRestaurant(customRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(customRestaurant);
    }

    // ========== TESTS DE VALIDACIÓN DE RESTAURANTE NULO ==========

    @Test
    void saveRestaurant_WhenRestaurantIsNull_ShouldThrowInvalidRestaurantException() {
        // Given
        Restaurant nullRestaurant = null;

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(nullRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_RESTAURANT_NULO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    // ========== TESTS DE VALIDACIÓN DE NOMBRE ==========

    @Test
    void saveRestaurant_WhenNombreIsNull_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNombre(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNombreIsEmpty_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNombre("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNombreIsBlank_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNombre("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNombreIsOnlyNumbers_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNombre("123456789");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNombreHasLettersAndNumbers_ShouldSaveSuccessfully() {
        // Given
        validRestaurant.setNombre("Restaurante123");
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When
        restaurantUseCase.saveRestaurant(validRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }

    // ========== TESTS DE VALIDACIÓN DE NIT ==========

    @Test
    void saveRestaurant_WhenNitIsNull_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNit(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNitIsEmpty_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNit("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNitIsBlank_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNit("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNitHasInvalidFormat_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setNit("ABC123DEF");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenNitHasValidNumericFormat_ShouldSaveSuccessfully() {
        // Given
        validRestaurant.setNit("900123456789");
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When
        restaurantUseCase.saveRestaurant(validRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }

    // ========== TESTS DE VALIDACIÓN DE DIRECCIÓN ==========

    @Test
    void saveRestaurant_WhenDireccionIsNull_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setDireccion(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenDireccionIsEmpty_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setDireccion("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenDireccionIsBlank_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setDireccion("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    // ========== TESTS DE VALIDACIÓN DE TELÉFONO ==========

    @Test
    void saveRestaurant_WhenTelefonoIsNull_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setTelefono(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenTelefonoIsEmpty_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setTelefono("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenTelefonoIsBlank_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setTelefono("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenTelefonoHasInvalidFormat_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setTelefono("abc123def");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenTelefonoHasValidFormat_ShouldSaveSuccessfully() {
        // Given
        validRestaurant.setTelefono("+573009876543");
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When
        restaurantUseCase.saveRestaurant(validRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }

    // ========== TESTS DE VALIDACIÓN DE URL LOGO ==========

    @Test
    void saveRestaurant_WhenUrlLogoIsNull_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setUrlLogo(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenUrlLogoIsEmpty_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setUrlLogo("");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenUrlLogoIsBlank_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setUrlLogo("   ");

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    // ========== TESTS DE VALIDACIÓN DE ID PROPIETARIO ==========

    @Test
    void saveRestaurant_WhenIdPropietarioIsNull_ShouldThrowInvalidRestaurantException() {
        // Given
        validRestaurant.setIdPropietario(null);

        // When & Then
        InvalidRestaurantException exception = assertThrows(InvalidRestaurantException.class,
                () -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));

        assertEquals(DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(restaurantPersistencePort);
    }

    // ========== TESTS ADICIONALES DE COBERTURA ==========

    @Test
    void saveRestaurant_WhenAllFieldsValid_ShouldCallPersistencePortExactlyOnce() {
        // Given
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When
        restaurantUseCase.saveRestaurant(validRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort, times(1)).saveRestaurant(validRestaurant);
        verifyNoMoreInteractions(restaurantPersistencePort);
    }

    @Test
    void saveRestaurant_WhenMultipleValidRestaurants_ShouldSaveAll() {
        // Given
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setNombre("Restaurante Uno");
        restaurant1.setNit("111111111");
        restaurant1.setDireccion("Dirección 1");
        restaurant1.setTelefono("+571111111111");
        restaurant1.setUrlLogo("http://logo1.com");
        restaurant1.setIdPropietario(1L);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setNombre("Restaurante Dos");
        restaurant2.setNit("222222222");
        restaurant2.setDireccion("Dirección 2");
        restaurant2.setTelefono("+572222222222");
        restaurant2.setUrlLogo("http://logo2.com");
        restaurant2.setIdPropietario(2L);

        doNothing().when(restaurantPersistencePort).saveRestaurant(restaurant1);
        doNothing().when(restaurantPersistencePort).saveRestaurant(restaurant2);

        // When
        restaurantUseCase.saveRestaurant(restaurant1, adminId);
        restaurantUseCase.saveRestaurant(restaurant2, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(restaurant1);
        verify(restaurantPersistencePort).saveRestaurant(restaurant2);
    }

    @Test
    void saveRestaurant_WhenValidRestaurantWithMinimalData_ShouldSaveSuccessfully() {
        // Given
        Restaurant minimalRestaurant = new Restaurant();
        minimalRestaurant.setNombre("R");
        minimalRestaurant.setNit("1");
        minimalRestaurant.setDireccion("D");
        minimalRestaurant.setTelefono("+571");
        minimalRestaurant.setUrlLogo("h");
        minimalRestaurant.setIdPropietario(1L);

        doNothing().when(restaurantPersistencePort).saveRestaurant(minimalRestaurant);

        // When
        restaurantUseCase.saveRestaurant(minimalRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(minimalRestaurant);
    }

    @Test
    void saveRestaurant_WhenValidationPasses_ShouldNotThrowException() {
        // Given
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When & Then
        assertDoesNotThrow(() -> restaurantUseCase.saveRestaurant(validRestaurant, adminId));
    }

    @Test
    void saveRestaurant_WhenNombreContainsSpecialCharacters_ShouldSaveSuccessfully() {
        // Given
        validRestaurant.setNombre("Restaurante El Buen Sabor & Más");
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When
        restaurantUseCase.saveRestaurant(validRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }

    @Test
    void saveRestaurant_WhenDireccionHasComplexFormat_ShouldSaveSuccessfully() {
        // Given
        validRestaurant.setDireccion("Calle 123 #45-67, Barrio Centro, Edificio Torre Plaza, Piso 5");
        doNothing().when(restaurantPersistencePort).saveRestaurant(validRestaurant);

        // When
        restaurantUseCase.saveRestaurant(validRestaurant, adminId);

        // Then
        verify(restaurantPersistencePort).saveRestaurant(validRestaurant);
    }
}
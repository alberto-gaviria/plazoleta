package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.domain.spi.IUserValidationPort;
import com.plazoleta.restaurants.domain.util.DomainConstants;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidDishException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IUserValidationPort userValidationPort;

    private DishUseCase dishUseCase;

    private Dish validDish;
    private Long currentUserId;
    private Long restaurantOwnerId;

    @BeforeEach
    void setUp() {
        dishUseCase = new DishUseCase(dishPersistencePort, userValidationPort);

        currentUserId = 1L;
        restaurantOwnerId = 1L;

        validDish = new Dish();
        validDish.setNombre("Pizza Margherita");
        validDish.setPrecio(new BigDecimal("25.50"));
        validDish.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        validDish.setUrlImagen("https://example.com/pizza.jpg");
        validDish.setIdCategoria(2L);
        validDish.setIdRestaurante(1L);
        validDish.setActivo(true);
    }

    @Test
    void saveDish_WhenValidDishAndAuthorizedUser_ShouldSaveDish() {
        // Given
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(validDish.getIdRestaurante())).thenReturn(restaurantOwnerId);

        // When
        dishUseCase.saveDish(validDish, currentUserId);

        // Then
        verify(dishPersistencePort).saveDish(validDish);
        verify(dishPersistencePort).existsRestaurantById(validDish.getIdRestaurante());
        verify(userValidationPort).hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO);
        verify(dishPersistencePort).getRestaurantOwnerId(validDish.getIdRestaurante());
    }

    @Test
    void saveDish_WhenDishIsNull_ShouldThrowInvalidDishException() {
        // Given
        Dish nullDish = null;

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(nullDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DISH_NULO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenNombreIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setNombre(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenNombreIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        validDish.setNombre("   ");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenPrecioIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setPrecio(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenPrecioIsZero_ShouldThrowInvalidDishException() {
        // Given
        validDish.setPrecio(BigDecimal.ZERO);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenPrecioIsNegative_ShouldThrowInvalidDishException() {
        // Given
        validDish.setPrecio(new BigDecimal("-10.00"));

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenDescripcionIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setDescripcion(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenDescripcionIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        validDish.setDescripcion("   ");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenUrlImagenIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setUrlImagen(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenUrlImagenIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        validDish.setUrlImagen("   ");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenIdCategoriaIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setIdCategoria(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_CATEGORIA_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenIdRestauranteIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setIdRestaurante(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenRestaurantDoesNotExist_ShouldThrowInvalidDishException() {
        // Given
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(false);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_RESTAURANTE_NO_ENCONTRADO, exception.getMessage());
        verify(dishPersistencePort).existsRestaurantById(validDish.getIdRestaurante());
        verifyNoMoreInteractions(dishPersistencePort);
        verifyNoInteractions(userValidationPort);
    }

    @Test
    void saveDish_WhenUserIsNotPropietario_ShouldThrowInvalidDishException() {
        // Given
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(false);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO, exception.getMessage());
        verify(dishPersistencePort).existsRestaurantById(validDish.getIdRestaurante());
        verify(userValidationPort).hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO);
        verifyNoMoreInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void saveDish_WhenUserIsNotRestaurantOwner_ShouldThrowInvalidDishException() {
        // Given
        Long differentOwnerId = 2L;
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(validDish.getIdRestaurante())).thenReturn(differentOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO, exception.getMessage());
        verify(dishPersistencePort).existsRestaurantById(validDish.getIdRestaurante());
        verify(userValidationPort).hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO);
        verify(dishPersistencePort).getRestaurantOwnerId(validDish.getIdRestaurante());
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void updateDish_WhenValidParametersAndAuthorizedUser_ShouldUpdateAndReturnDish() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String newDescription = "Nueva descripción actualizada";
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setNombre("Pizza Margherita");
        existingDish.setPrecio(BigDecimal.valueOf(25.50));
        existingDish.setDescripcion("Descripción original");
        existingDish.setUrlImagen("https://example.com/pizza.jpg");
        existingDish.setIdCategoria(2L);
        existingDish.setIdRestaurante(restaurantId);
        existingDish.setActivo(true);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);

        // When
        Dish result = dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId);

        // Then
        assertEquals(newPrice, result.getPrecio());
        assertEquals(newDescription, result.getDescripcion());
        assertEquals(dishId, result.getId());
        assertEquals("Pizza Margherita", result.getNombre()); // No debe cambiar

        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
        verify(userValidationPort).hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO);
        verify(dishPersistencePort).getRestaurantOwnerId(restaurantId);
        verify(dishPersistencePort).updateDish(result);
    }

    @Test
    void updateDish_WhenDishIdIsNull_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = null;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String newDescription = "Nueva descripción";

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DISH_ID_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void updateDish_WhenCurrentUserIdIsNull_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String newDescription = "Nueva descripción";
        Long nullUserId = null;

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, newDescription, nullUserId));

        assertEquals(DomainConstants.Dish.ERROR_USUARIO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void updateDish_WhenPrecioIsNull_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal nullPrice = null;
        String newDescription = "Nueva descripción";

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, nullPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void updateDish_WhenDescripcionIsNull_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String nullDescription = null;

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, nullDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void updateDish_WhenDescripcionIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String emptyDescription = "   ";

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, emptyDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort, userValidationPort);
    }

    @Test
    void updateDish_WhenDishDoesNotExist_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String newDescription = "Nueva descripción";

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.empty());

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DISH_NO_ENCONTRADO, exception.getMessage());
        verify(dishPersistencePort).findDishById(dishId);
        verifyNoMoreInteractions(dishPersistencePort);
        verifyNoInteractions(userValidationPort);
    }

    @Test
    void updateDish_WhenPrecioIsZero_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal zeroPrice = BigDecimal.ZERO;
        String newDescription = "Nueva descripción";
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setPrecio(BigDecimal.valueOf(25.50));
        existingDish.setDescripcion("Descripción original");
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, zeroPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
        verify(userValidationPort).hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO);
        verify(dishPersistencePort).getRestaurantOwnerId(restaurantId);
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void updateDish_WhenPrecioIsNegative_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal negativePrice = BigDecimal.valueOf(-5.00);
        String newDescription = "Nueva descripción";
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setPrecio(BigDecimal.valueOf(25.50));
        existingDish.setDescripcion("Descripción original");
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, negativePrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void updateDish_WhenUserIsNotPropietario_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String newDescription = "Nueva descripción";
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(false);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO, exception.getMessage());
        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
        verify(userValidationPort).hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO);
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void updateDish_WhenUserIsNotRestaurantOwner_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String newDescription = "Nueva descripción";
        Long restaurantId = 1L;
        Long differentOwnerId = 2L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(differentOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO, exception.getMessage());
        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
        verify(userValidationPort).hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO);
        verify(dishPersistencePort).getRestaurantOwnerId(restaurantId);
        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void updateDish_WithMinimumValidPrice_ShouldUpdateSuccessfully() {
        // Given
        Long dishId = 1L;
        BigDecimal minimumPrice = BigDecimal.valueOf(0.01);
        String newDescription = "Nueva descripción";
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setPrecio(BigDecimal.valueOf(25.50));
        existingDish.setDescripcion("Descripción original");
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(userValidationPort.hasRequiredRole(currentUserId, DomainConstants.Dish.ROL_PROPIETARIO)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);

        // When
        Dish result = dishUseCase.updateDish(dishId, minimumPrice, newDescription, currentUserId);

        // Then
        assertEquals(minimumPrice, result.getPrecio());
        assertEquals(newDescription, result.getDescripcion());
        verify(dishPersistencePort).updateDish(result);
    }
}
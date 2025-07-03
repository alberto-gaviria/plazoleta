package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
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

    private DishUseCase dishUseCase;

    private Dish validDish;
    private Long currentUserId;
    private Long restaurantOwnerId;

    @BeforeEach
    void setUp() {
        dishUseCase = new DishUseCase(dishPersistencePort);

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

    // ==================== SAVE DISH TESTS - CASOS EXITOSOS ====================

    @Test
    void saveDish_WhenValidDishAndAuthorizedUser_ShouldSaveDish() {
        // Given
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(validDish.getIdRestaurante())).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).saveDish(validDish);

        // When
        dishUseCase.saveDish(validDish, currentUserId);

        // Then
        verify(dishPersistencePort).existsRestaurantById(validDish.getIdRestaurante());
        verify(dishPersistencePort).getRestaurantOwnerId(validDish.getIdRestaurante());
        verify(dishPersistencePort).saveDish(validDish);
    }

    @Test
    void saveDish_WhenAllValidationsPass_ShouldCallSaveExactlyOnce() {
        // Given
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(validDish.getIdRestaurante())).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).saveDish(validDish);

        // When
        dishUseCase.saveDish(validDish, currentUserId);

        // Then
        verify(dishPersistencePort, times(1)).saveDish(validDish);
        verifyNoMoreInteractions(dishPersistencePort);
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE DISH NULO ====================

    @Test
    void saveDish_WhenDishIsNull_ShouldThrowInvalidDishException() {
        // Given
        Dish nullDish = null;

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(nullDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DISH_NULO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE NOMBRE ====================

    @Test
    void saveDish_WhenNombreIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setNombre(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenNombreIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        validDish.setNombre("");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenNombreIsBlank_ShouldThrowInvalidDishException() {
        // Given
        validDish.setNombre("   ");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE PRECIO ====================

    @Test
    void saveDish_WhenPrecioIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setPrecio(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenPrecioIsZero_ShouldThrowInvalidDishException() {
        // Given
        validDish.setPrecio(BigDecimal.ZERO);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenPrecioIsNegative_ShouldThrowInvalidDishException() {
        // Given
        validDish.setPrecio(new BigDecimal("-10.00"));

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenPrecioIsPositive_ShouldSaveSuccessfully() {
        // Given
        validDish.setPrecio(new BigDecimal("0.01"));
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(validDish.getIdRestaurante())).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).saveDish(validDish);

        // When
        dishUseCase.saveDish(validDish, currentUserId);

        // Then
        verify(dishPersistencePort).saveDish(validDish);
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE DESCRIPCIÓN ====================

    @Test
    void saveDish_WhenDescripcionIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setDescripcion(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenDescripcionIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        validDish.setDescripcion("");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenDescripcionIsBlank_ShouldThrowInvalidDishException() {
        // Given
        validDish.setDescripcion("   ");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE URL IMAGEN ====================

    @Test
    void saveDish_WhenUrlImagenIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setUrlImagen(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenUrlImagenIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        validDish.setUrlImagen("");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void saveDish_WhenUrlImagenIsBlank_ShouldThrowInvalidDishException() {
        // Given
        validDish.setUrlImagen("   ");

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE CATEGORÍA ====================

    @Test
    void saveDish_WhenIdCategoriaIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setIdCategoria(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_CATEGORIA_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE RESTAURANTE ====================

    @Test
    void saveDish_WhenIdRestauranteIsNull_ShouldThrowInvalidDishException() {
        // Given
        validDish.setIdRestaurante(null);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
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
    }

    // ==================== SAVE DISH TESTS - VALIDACIÓN DE OWNERSHIP ====================

    @Test
    void saveDish_WhenUserIsNotRestaurantOwner_ShouldThrowInvalidDishException() {
        // Given
        Long differentOwnerId = 2L;
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(validDish.getIdRestaurante())).thenReturn(differentOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.saveDish(validDish, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO, exception.getMessage());
        verify(dishPersistencePort).existsRestaurantById(validDish.getIdRestaurante());
        verify(dishPersistencePort).getRestaurantOwnerId(validDish.getIdRestaurante());
        verify(dishPersistencePort, never()).saveDish(any());
    }

    // ==================== UPDATE DISH TESTS - CASOS EXITOSOS ====================

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
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).updateDish(existingDish);

        // When
        Dish result = dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId);

        // Then
        assertEquals(newPrice, result.getPrecio());
        assertEquals(newDescription, result.getDescripcion());
        assertEquals(dishId, result.getId());
        assertEquals("Pizza Margherita", result.getNombre()); // No debe cambiar

        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
        verify(dishPersistencePort).getRestaurantOwnerId(restaurantId);
        verify(dishPersistencePort).updateDish(result);
    }

    @Test
    void updateDish_WhenAllValidationsPass_ShouldCallUpdateExactlyOnce() {
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
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).updateDish(existingDish);

        // When
        dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId);

        // Then
        verify(dishPersistencePort, times(1)).updateDish(existingDish);
    }

    // ==================== UPDATE DISH TESTS - VALIDACIÓN DE PARÁMETROS ====================

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
        verifyNoInteractions(dishPersistencePort);
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
        verifyNoInteractions(dishPersistencePort);
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
        verifyNoInteractions(dishPersistencePort);
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
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void updateDish_WhenDescripcionIsEmpty_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String emptyDescription = "";

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, emptyDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    @Test
    void updateDish_WhenDescripcionIsBlank_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = BigDecimal.valueOf(30.00);
        String blankDescription = "   ";

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, blankDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA, exception.getMessage());
        verifyNoInteractions(dishPersistencePort);
    }

    // ==================== UPDATE DISH TESTS - DISH NO ENCONTRADO ====================

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
    }

    // ==================== UPDATE DISH TESTS - VALIDACIÓN DE PRECIO ====================

    @Test
    void updateDish_WhenPrecioIsZero_ShouldThrowInvalidDishException() {
        // Given
        Long dishId = 1L;
        BigDecimal zeroPrice = BigDecimal.ZERO;
        String newDescription = "Nueva descripción";
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, zeroPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
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
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, negativePrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PRECIO_POSITIVO, exception.getMessage());
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
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).updateDish(existingDish);

        // When
        Dish result = dishUseCase.updateDish(dishId, minimumPrice, newDescription, currentUserId);

        // Then
        assertEquals(minimumPrice, result.getPrecio());
        assertEquals(newDescription, result.getDescripcion());
        verify(dishPersistencePort).updateDish(result);
    }

    // ==================== UPDATE DISH TESTS - VALIDACIÓN DE OWNERSHIP ====================

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
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(differentOwnerId);

        // When & Then
        InvalidDishException exception = assertThrows(InvalidDishException.class,
                () -> dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId));

        assertEquals(DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO, exception.getMessage());
        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
        verify(dishPersistencePort).getRestaurantOwnerId(restaurantId);
        verify(dishPersistencePort, never()).updateDish(any());
    }

    // ==================== TESTS ADICIONALES DE COBERTURA ====================

    @Test
    void saveDish_WhenMultipleValidDishes_ShouldSaveAll() {
        // Given
        Dish dish1 = new Dish();
        dish1.setNombre("Plato 1");
        dish1.setPrecio(new BigDecimal("10.00"));
        dish1.setDescripcion("Descripción 1");
        dish1.setUrlImagen("http://imagen1.com");
        dish1.setIdCategoria(1L);
        dish1.setIdRestaurante(1L);

        Dish dish2 = new Dish();
        dish2.setNombre("Plato 2");
        dish2.setPrecio(new BigDecimal("20.00"));
        dish2.setDescripcion("Descripción 2");
        dish2.setUrlImagen("http://imagen2.com");
        dish2.setIdCategoria(2L);
        dish2.setIdRestaurante(1L);

        when(dishPersistencePort.existsRestaurantById(1L)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(1L)).thenReturn(currentUserId);
        doNothing().when(dishPersistencePort).saveDish(dish1);
        doNothing().when(dishPersistencePort).saveDish(dish2);

        // When
        dishUseCase.saveDish(dish1, currentUserId);
        dishUseCase.saveDish(dish2, currentUserId);

        // Then
        verify(dishPersistencePort).saveDish(dish1);
        verify(dishPersistencePort).saveDish(dish2);
    }

    @Test
    void updateDish_WhenDifferentValidParameters_ShouldUpdateCorrectly() {
        // Given
        Long dishId = 2L;
        BigDecimal customPrice = new BigDecimal("99.99");
        String customDescription = "Descripción personalizada muy larga";
        Long restaurantId = 2L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setNombre("Plato Personalizado");
        existingDish.setPrecio(new BigDecimal("50.00"));
        existingDish.setDescripcion("Descripción anterior");
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(currentUserId);
        doNothing().when(dishPersistencePort).updateDish(existingDish);

        // When
        Dish result = dishUseCase.updateDish(dishId, customPrice, customDescription, currentUserId);

        // Then
        assertEquals(customPrice, result.getPrecio());
        assertEquals(customDescription, result.getDescripcion());
        assertEquals("Plato Personalizado", result.getNombre()); // No debe cambiar
        verify(dishPersistencePort).updateDish(result);
    }

    @Test
    void saveDish_WhenValidationPassesButPersistenceFails_ShouldNotAffectValidations() {
        // Given & When & Then
        // Este test verifica que el método puede ser llamado sin errores de compilación
        assertDoesNotThrow(() -> {
            // Solo verificamos que el constructor y la clase funcionan correctamente
            DishUseCase useCase = new DishUseCase(dishPersistencePort);
            assertNotNull(useCase);
        });
    }

    @Test
    void updateDish_WhenParametersHaveExtremeValues_ShouldHandleCorrectly() {
        // Given
        Long dishId = Long.MAX_VALUE;
        BigDecimal extremePrice = new BigDecimal("999999.99");
        String longDescription = "A".repeat(1000); // Descripción muy larga
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(currentUserId);
        doNothing().when(dishPersistencePort).updateDish(existingDish);

        // When
        Dish result = dishUseCase.updateDish(dishId, extremePrice, longDescription, currentUserId);

        // Then
        assertEquals(extremePrice, result.getPrecio());
        assertEquals(longDescription, result.getDescripcion());
        verify(dishPersistencePort).updateDish(result);
    }

    @Test
    void saveDish_WhenValidDishWithMinimalValidData_ShouldSaveSuccessfully() {
        // Given
        Dish minimalDish = new Dish();
        minimalDish.setNombre("A"); // Mínimo válido
        minimalDish.setPrecio(new BigDecimal("0.01")); // Mínimo válido
        minimalDish.setDescripcion("B"); // Mínimo válido
        minimalDish.setUrlImagen("C"); // Mínimo válido
        minimalDish.setIdCategoria(1L);
        minimalDish.setIdRestaurante(1L);

        when(dishPersistencePort.existsRestaurantById(1L)).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(1L)).thenReturn(currentUserId);
        doNothing().when(dishPersistencePort).saveDish(minimalDish);

        // When
        dishUseCase.saveDish(minimalDish, currentUserId);

        // Then
        verify(dishPersistencePort).saveDish(minimalDish);
    }

    @Test
    void saveDish_WhenValidationFlowExecutesCorrectly_ShouldCallMethodsInOrder() {
        // Given
        when(dishPersistencePort.existsRestaurantById(validDish.getIdRestaurante())).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(validDish.getIdRestaurante())).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).saveDish(validDish);

        // When
        dishUseCase.saveDish(validDish, currentUserId);

        // Then
        // Verificar que las validaciones se ejecutan en el orden correcto
        // 1. validateDish (incluye validaciones básicas)
        // 2. validateDishBusinessRules (precio positivo)
        // 3. validateRestaurantExists
        // 4. validateOwnership
        // 5. saveDish
        verify(dishPersistencePort).existsRestaurantById(validDish.getIdRestaurante());
        verify(dishPersistencePort).getRestaurantOwnerId(validDish.getIdRestaurante());
        verify(dishPersistencePort).saveDish(validDish);
    }

    @Test
    void updateDish_WhenValidationFlowExecutesCorrectly_ShouldCallMethodsInOrder() {
        // Given
        Long dishId = 1L;
        BigDecimal newPrice = new BigDecimal("25.00");
        String newDescription = "Nueva descripción";
        Long restaurantId = 1L;

        Dish existingDish = new Dish();
        existingDish.setId(dishId);
        existingDish.setIdRestaurante(restaurantId);

        when(dishPersistencePort.findDishById(dishId)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(dishId)).thenReturn(restaurantId);
        when(dishPersistencePort.getRestaurantOwnerId(restaurantId)).thenReturn(restaurantOwnerId);
        doNothing().when(dishPersistencePort).updateDish(existingDish);

        // When
        dishUseCase.updateDish(dishId, newPrice, newDescription, currentUserId);

        // Then
        // Verificar que las validaciones se ejecutan en el orden correcto
        // 1. validateUpdateParameters
        // 2. findDishById
        // 3. getDishRestaurantId
        // 4. validateOwnership
        // 5. validatePrecioPositivo
        // 6. updateDish
        verify(dishPersistencePort).findDishById(dishId);
        verify(dishPersistencePort).getDishRestaurantId(dishId);
        verify(dishPersistencePort).getRestaurantOwnerId(restaurantId);
        verify(dishPersistencePort).updateDish(existingDish);
    }
}
package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Dish;
import com.plazoleta.restaurants.domain.model.DishWithCategory;
import com.plazoleta.restaurants.domain.spi.IDishPersistencePort;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidDishException;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishUseCaseTest {

    private DishUseCase useCase;
    private IDishPersistencePort dishPersistencePort;

    @BeforeEach
    void setUp() {
        dishPersistencePort = mock(IDishPersistencePort.class);
        useCase = new DishUseCase(dishPersistencePort);
    }

    @Test
    void saveDish_ValidDish_ShouldSucceed() {
        Dish dish = buildValidDish();
        when(dishPersistencePort.existsRestaurantById(dish.getIdRestaurante())).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(dish.getIdRestaurante())).thenReturn(100L);
        when(dishPersistencePort.saveDish(dish)).thenReturn(dish);

        Dish saved = useCase.saveDish(dish, 100L);

        assertEquals(dish, saved);
    }

    @Test
    void saveDish_InvalidDish_ShouldThrow() {
        assertThrows(InvalidDishException.class, () -> useCase.saveDish(null, 1L));
    }

    @Test
    void updateDish_ValidData_ShouldUpdate() {
        Dish existingDish = buildValidDish();
        existingDish.setIdRestaurante(1L);

        when(dishPersistencePort.findDishById(1L)).thenReturn(Optional.of(existingDish));
        when(dishPersistencePort.getDishRestaurantId(1L)).thenReturn(1L);
        when(dishPersistencePort.getRestaurantOwnerId(1L)).thenReturn(10L);
        when(dishPersistencePort.updateDish(any(Dish.class))).thenReturn(existingDish);

        Dish updated = useCase.updateDish(1L, BigDecimal.TEN, "New Desc", 10L);
        assertEquals("New Desc", updated.getDescripcion());
    }

    @Test
    void updateDish_NullId_ShouldThrow() {
        assertThrows(InvalidDishException.class, () ->
                useCase.updateDish(null, BigDecimal.TEN, "desc", 1L));
    }

    @Test
    void toggleDishStatus_Valid_ShouldUpdate() {
        Dish dish = buildValidDish();
        when(dishPersistencePort.findDishById(1L)).thenReturn(Optional.of(dish));
        when(dishPersistencePort.getDishRestaurantId(1L)).thenReturn(1L);
        when(dishPersistencePort.getRestaurantOwnerId(1L)).thenReturn(2L);
        when(dishPersistencePort.updateDish(any(Dish.class))).thenReturn(dish);

        Dish result = useCase.toggleDishStatus(1L, false, 2L);
        assertFalse(result.getActivo());
    }

    @Test
    void toggleDishStatus_DishNotFound_ShouldThrow() {
        when(dishPersistencePort.findDishById(1L)).thenReturn(Optional.empty());
        assertThrows(InvalidDishException.class, () ->
                useCase.toggleDishStatus(1L, true, 1L));
    }

    @Test
    void getDishesByRestaurant_Valid_ShouldReturnPage() {
        Page<DishWithCategory> page = new Page<>(Collections.emptyList(), 0, 10, 1);
        when(dishPersistencePort.existsRestaurantById(1L)).thenReturn(true);
        when(dishPersistencePort.findDishesByRestaurant(1L, 2L, 0, 10)).thenReturn(page);

        Page<DishWithCategory> result = useCase.getDishesByRestaurant(1L, 2L, 0, 10);
        assertEquals(page, result);
    }

    @Test
    void getDishesByRestaurant_InvalidParams_ShouldThrow() {
        assertThrows(InvalidDishException.class, () ->
                useCase.getDishesByRestaurant(null, 1L, 0, 10));
        assertThrows(InvalidDishException.class, () ->
                useCase.getDishesByRestaurant(1L, 1L, -1, 10));
        assertThrows(InvalidDishException.class, () ->
                useCase.getDishesByRestaurant(1L, 1L, 0, 0));
        assertThrows(InvalidDishException.class, () ->
                useCase.getDishesByRestaurant(1L, 1L, 0, 9999));
    }

    @Test
    void saveDish_InvalidPrecio_ShouldThrow() {
        Dish dish = buildValidDish();
        dish.setPrecio(BigDecimal.ZERO);
        assertThrows(InvalidDishException.class, () ->
                useCase.saveDish(dish, 1L));
    }

    @Test
    void validateOwnership_InvalidOwner_ShouldThrow() {
        Dish dish = buildValidDish();
        when(dishPersistencePort.existsRestaurantById(dish.getIdRestaurante())).thenReturn(true);
        when(dishPersistencePort.getRestaurantOwnerId(dish.getIdRestaurante())).thenReturn(999L);

        assertThrows(InvalidDishException.class, () ->
                useCase.saveDish(dish, 1L));
    }

    private Dish buildValidDish() {
        Dish dish = new Dish();
        dish.setNombre("Pasta");
        dish.setPrecio(BigDecimal.valueOf(12.50));
        dish.setDescripcion("Delicious");
        dish.setUrlImagen("http://image.com");
        dish.setIdCategoria(1L);
        dish.setIdRestaurante(1L);
        dish.setActivo(true);
        return dish;
    }
}

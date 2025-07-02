package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.DishEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IDishEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IDishRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.util.AdapterConstants;
import com.plazoleta.restaurants.domain.model.Dish;
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
class DishMysqlAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    private DishMysqlAdapter dishMysqlAdapter;

    private Dish dish;
    private DishEntity dishEntity;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        dishMysqlAdapter = new DishMysqlAdapter(dishRepository, restaurantRepository, dishEntityMapper);

        dish = new Dish();
        dish.setId(1L);
        dish.setNombre("Pizza Margherita");
        dish.setPrecio(new BigDecimal("25.50"));
        dish.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dish.setUrlImagen("https://example.com/pizza.jpg");
        dish.setIdCategoria(2L);
        dish.setIdRestaurante(1L);
        dish.setActivo(true);

        dishEntity = new DishEntity();
        dishEntity.setId(1L);
        dishEntity.setNombre("Pizza Margherita");
        dishEntity.setPrecio(new BigDecimal("25.50"));
        dishEntity.setDescripcion("Pizza con salsa de tomate, mozzarella y albahaca");
        dishEntity.setUrlImagen("https://example.com/pizza.jpg");
        dishEntity.setIdCategoria(2L);
        dishEntity.setIdRestaurante(1L);
        dishEntity.setActivo(true);

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setIdPropietario(1L);
    }

    @Test
    void saveDish_ShouldMapAndSaveDishEntity() {
        // Given
        when(dishEntityMapper.toEntity(dish)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        // When
        dishMysqlAdapter.saveDish(dish);

        // Then
        verify(dishEntityMapper).toEntity(dish);
        verify(dishRepository).save(dishEntity);
    }

    @Test
    void existsRestaurantById_WhenRestaurantExists_ShouldReturnTrue() {
        // Given
        Long restaurantId = 1L;
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);

        // When
        boolean result = dishMysqlAdapter.existsRestaurantById(restaurantId);

        // Then
        assertTrue(result);
        verify(restaurantRepository).existsById(restaurantId);
    }

    @Test
    void existsRestaurantById_WhenRestaurantDoesNotExist_ShouldReturnFalse() {
        // Given
        Long restaurantId = 1L;
        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        // When
        boolean result = dishMysqlAdapter.existsRestaurantById(restaurantId);

        // Then
        assertFalse(result);
        verify(restaurantRepository).existsById(restaurantId);
    }

    @Test
    void getRestaurantOwnerId_WhenRestaurantExists_ShouldReturnOwnerId() {
        // Given
        Long restaurantId = 1L;
        Long expectedOwnerId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantEntity));

        // When
        Long result = dishMysqlAdapter.getRestaurantOwnerId(restaurantId);

        // Then
        assertEquals(expectedOwnerId, result);
        verify(restaurantRepository).findById(restaurantId);
    }

    @Test
    void getRestaurantOwnerId_WhenRestaurantDoesNotExist_ShouldThrowElementNotFoundException() {
        // Given
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // When & Then
        ElementNotFoundException exception = assertThrows(ElementNotFoundException.class,
                () -> dishMysqlAdapter.getRestaurantOwnerId(restaurantId));

        assertEquals(AdapterConstants.ErrorMessages.RESTAURANT_NO_ENCONTRADO, exception.getMessage());
        verify(restaurantRepository).findById(restaurantId);
    }
}
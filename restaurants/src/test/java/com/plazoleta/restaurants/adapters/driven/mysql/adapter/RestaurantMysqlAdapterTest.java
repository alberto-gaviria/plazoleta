package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IRestaurantEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.RestaurantAlreadyExistsException;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantMysqlAdapterTest {

    @Mock
    private IRestaurantRepository restaurantRepository;

    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;

    @InjectMocks
    private RestaurantMysqlAdapter restaurantMysqlAdapter;

    private Restaurant restaurant;
    private RestaurantEntity restaurantEntity;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNombre("Restaurante Test");
        restaurant.setNit("123456789");
        restaurant.setDireccion("Calle 123");
        restaurant.setTelefono("3001234567");
        restaurant.setUrlLogo("http://logo.com");
        restaurant.setIdPropietario(1L);

        restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setNombre("Restaurante Test");
        restaurantEntity.setNit("123456789");
        restaurantEntity.setDireccion("Calle 123");
        restaurantEntity.setTelefono("3001234567");
        restaurantEntity.setUrlLogo("http://logo.com");
        restaurantEntity.setIdPropietario(1L);
    }

    @Test
    void shouldSaveRestaurantSuccessfully() {
        // Given
        when(restaurantRepository.findByNit(restaurant.getNit())).thenReturn(Optional.empty());
        when(restaurantRepository.findByNombre(restaurant.getNombre())).thenReturn(Optional.empty());
        when(restaurantEntityMapper.toEntity(restaurant)).thenReturn(restaurantEntity);
        when(restaurantRepository.save(restaurantEntity)).thenReturn(restaurantEntity);

        // When
        assertDoesNotThrow(() -> restaurantMysqlAdapter.saveRestaurant(restaurant));

        // Then
        verify(restaurantRepository).findByNit(restaurant.getNit());
        verify(restaurantRepository).findByNombre(restaurant.getNombre());
        verify(restaurantEntityMapper).toEntity(restaurant);
        verify(restaurantRepository).save(restaurantEntity);
    }

    @Test
    void shouldThrowExceptionWhenNitAlreadyExists() {
        // Given
        when(restaurantRepository.findByNit(restaurant.getNit())).thenReturn(Optional.of(restaurantEntity));

        // When & Then
        RestaurantAlreadyExistsException exception = assertThrows(
                RestaurantAlreadyExistsException.class,
                () -> restaurantMysqlAdapter.saveRestaurant(restaurant)
        );

        assertEquals("Ya existe un restaurante con ese NIT", exception.getMessage());
        verify(restaurantRepository).findByNit(restaurant.getNit());
        verify(restaurantRepository, never()).findByNombre(any());
        verify(restaurantEntityMapper, never()).toEntity(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenNombreAlreadyExists() {
        // Given
        when(restaurantRepository.findByNit(restaurant.getNit())).thenReturn(Optional.empty());
        when(restaurantRepository.findByNombre(restaurant.getNombre())).thenReturn(Optional.of(restaurantEntity));

        // When & Then
        RestaurantAlreadyExistsException exception = assertThrows(
                RestaurantAlreadyExistsException.class,
                () -> restaurantMysqlAdapter.saveRestaurant(restaurant)
        );

        assertEquals("Ya existe un restaurante con ese nombre", exception.getMessage());
        verify(restaurantRepository).findByNit(restaurant.getNit());
        verify(restaurantRepository).findByNombre(restaurant.getNombre());
        verify(restaurantEntityMapper, never()).toEntity(any());
        verify(restaurantRepository, never()).save(any());
    }
}
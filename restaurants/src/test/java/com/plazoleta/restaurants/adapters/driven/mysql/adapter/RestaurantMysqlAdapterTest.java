package com.plazoleta.restaurants.adapters.driven.mysql.adapter;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.adapters.driven.mysql.mapper.IRestaurantEntityMapper;
import com.plazoleta.restaurants.adapters.driven.mysql.repository.IRestaurantRepository;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.RestaurantAlreadyExistsException;
import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
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

    @Test
    void shouldReturnSortedRestaurantSummaries() {
        // Given
        RestaurantEntity entity1 = new RestaurantEntity();
        entity1.setNombre("A Restaurante");
        entity1.setUrlLogo("http://logo1.com");

        RestaurantEntity entity2 = new RestaurantEntity();
        entity2.setNombre("B Restaurante");
        entity2.setUrlLogo("http://logo2.com");

        List<RestaurantEntity> entities = List.of(entity1, entity2);
        Pageable pageable = PageRequest.of(0, 2, Sort.by("nombre").ascending());
        org.springframework.data.domain.Page<RestaurantEntity> springPage =
                new PageImpl<>(entities, pageable, entities.size());

        when(restaurantRepository.findAll(any(Pageable.class))).thenReturn(springPage);

        // When
        Page<RestaurantSummary> result = restaurantMysqlAdapter.findAllRestaurantsSorted(0, 2);

        // Then
        assertEquals(2, result.getContent().size());
        assertEquals("A Restaurante", result.getContent().get(0).getNombre());
        assertEquals("http://logo1.com", result.getContent().get(0).getUrlLogo());
        assertEquals(0, result.getPageNumber());
        assertEquals(2, result.getPageSize());
        assertEquals(2, result.getTotalElements());

        verify(restaurantRepository).findAll(any(Pageable.class));
    }
}
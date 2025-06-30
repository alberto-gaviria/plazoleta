package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.RestaurantEntity;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IRestaurantEntityMapperTest {

    private IRestaurantEntityMapper restaurantEntityMapper;

    @BeforeEach
    void setUp() {
        restaurantEntityMapper = Mappers.getMapper(IRestaurantEntityMapper.class);
    }

    @Test
    void shouldMapRestaurantEntityToModel() {
        // Given
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1L);
        restaurantEntity.setNombre("Restaurante Test");
        restaurantEntity.setNit("123456789");
        restaurantEntity.setDireccion("Calle 123");
        restaurantEntity.setTelefono("3001234567");
        restaurantEntity.setUrlLogo("http://logo.com");
        restaurantEntity.setIdPropietario(1L);

        // When
        Restaurant restaurant = restaurantEntityMapper.toModel(restaurantEntity);

        // Then
        assertNotNull(restaurant);
        assertEquals(restaurantEntity.getId(), restaurant.getId());
        assertEquals(restaurantEntity.getNombre(), restaurant.getNombre());
        assertEquals(restaurantEntity.getNit(), restaurant.getNit());
        assertEquals(restaurantEntity.getDireccion(), restaurant.getDireccion());
        assertEquals(restaurantEntity.getTelefono(), restaurant.getTelefono());
        assertEquals(restaurantEntity.getUrlLogo(), restaurant.getUrlLogo());
        assertEquals(restaurantEntity.getIdPropietario(), restaurant.getIdPropietario());
    }

    @Test
    void shouldMapRestaurantModelToEntity() {
        // Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNombre("Restaurante Test");
        restaurant.setNit("123456789");
        restaurant.setDireccion("Calle 123");
        restaurant.setTelefono("3001234567");
        restaurant.setUrlLogo("http://logo.com");
        restaurant.setIdPropietario(1L);

        // When
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);

        // Then
        assertNotNull(restaurantEntity);
        assertEquals(restaurant.getId(), restaurantEntity.getId());
        assertEquals(restaurant.getNombre(), restaurantEntity.getNombre());
        assertEquals(restaurant.getNit(), restaurantEntity.getNit());
        assertEquals(restaurant.getDireccion(), restaurantEntity.getDireccion());
        assertEquals(restaurant.getTelefono(), restaurantEntity.getTelefono());
        assertEquals(restaurant.getUrlLogo(), restaurantEntity.getUrlLogo());
        assertEquals(restaurant.getIdPropietario(), restaurantEntity.getIdPropietario());
    }

    @Test
    void shouldMapRestaurantEntityListToModelList() {
        // Given
        RestaurantEntity entity1 = new RestaurantEntity(1L, "Restaurant 1", "111", "Dir 1", "111", "logo1.com", 1L);
        RestaurantEntity entity2 = new RestaurantEntity(2L, "Restaurant 2", "222", "Dir 2", "222", "logo2.com", 2L);
        List<RestaurantEntity> entityList = Arrays.asList(entity1, entity2);

        // When
        List<Restaurant> restaurantList = restaurantEntityMapper.toModelList(entityList);

        // Then
        assertNotNull(restaurantList);
        assertEquals(2, restaurantList.size());

        Restaurant restaurant1 = restaurantList.get(0);
        assertEquals(entity1.getId(), restaurant1.getId());
        assertEquals(entity1.getNombre(), restaurant1.getNombre());
        assertEquals(entity1.getNit(), restaurant1.getNit());

        Restaurant restaurant2 = restaurantList.get(1);
        assertEquals(entity2.getId(), restaurant2.getId());
        assertEquals(entity2.getNombre(), restaurant2.getNombre());
        assertEquals(entity2.getNit(), restaurant2.getNit());
    }

    @Test
    void shouldHandleNullEntity() {
        // When
        Restaurant restaurant = restaurantEntityMapper.toModel(null);

        // Then
        assertNull(restaurant);
    }

    @Test
    void shouldHandleNullModel() {
        // When
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(null);

        // Then
        assertNull(restaurantEntity);
    }

    @Test
    void shouldHandleNullList() {
        // When
        List<Restaurant> restaurantList = restaurantEntityMapper.toModelList(null);

        // Then
        assertNull(restaurantList);
    }
}
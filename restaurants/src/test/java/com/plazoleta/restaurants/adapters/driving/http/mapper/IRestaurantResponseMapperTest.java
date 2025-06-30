package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IRestaurantResponseMapperTest {

    private IRestaurantResponseMapper restaurantResponseMapper;

    @BeforeEach
    void setUp() {
        restaurantResponseMapper = Mappers.getMapper(IRestaurantResponseMapper.class);
    }

    @Test
    void shouldMapRestaurantToRestaurantResponse() {
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
        RestaurantResponse response = restaurantResponseMapper.toRestaurantResponse(restaurant);

        // Then
        assertNotNull(response);
        assertEquals(restaurant.getId(), response.getId());
        assertEquals(restaurant.getNombre(), response.getNombre());
        assertEquals(restaurant.getNit(), response.getNit());
        assertEquals(restaurant.getDireccion(), response.getDireccion());
        assertEquals(restaurant.getTelefono(), response.getTelefono());
        assertEquals(restaurant.getUrlLogo(), response.getUrlLogo());
        assertEquals(restaurant.getIdPropietario(), response.getIdPropietario());
    }

    @Test
    void shouldMapRestaurantListToRestaurantResponseList() {
        // Given
        Restaurant restaurant1 = new Restaurant(1L, "Restaurant 1", "111", "Dir 1", "111", "logo1.com", 1L);
        Restaurant restaurant2 = new Restaurant(2L, "Restaurant 2", "222", "Dir 2", "222", "logo2.com", 2L);
        List<Restaurant> restaurantList = Arrays.asList(restaurant1, restaurant2);

        // When
        List<RestaurantResponse> responseList = restaurantResponseMapper.toRestaurantResponseList(restaurantList);

        // Then
        assertNotNull(responseList);
        assertEquals(2, responseList.size());

        RestaurantResponse response1 = responseList.get(0);
        assertEquals(restaurant1.getId(), response1.getId());
        assertEquals(restaurant1.getNombre(), response1.getNombre());
        assertEquals(restaurant1.getNit(), response1.getNit());

        RestaurantResponse response2 = responseList.get(1);
        assertEquals(restaurant2.getId(), response2.getId());
        assertEquals(restaurant2.getNombre(), response2.getNombre());
        assertEquals(restaurant2.getNit(), response2.getNit());
    }

    @Test
    void shouldHandleNullRestaurant() {
        // When
        RestaurantResponse response = restaurantResponseMapper.toRestaurantResponse(null);

        // Then
        assertNull(response);
    }

    @Test
    void shouldHandleNullList() {
        // When
        List<RestaurantResponse> responseList = restaurantResponseMapper.toRestaurantResponseList(null);

        // Then
        assertNull(responseList);
    }

    @Test
    void shouldMapPartialRestaurant() {
        // Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNombre("Restaurante Parcial");
        // Otros campos quedan null

        // When
        RestaurantResponse response = restaurantResponseMapper.toRestaurantResponse(restaurant);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Restaurante Parcial", response.getNombre());
        assertNull(response.getNit());
        assertNull(response.getDireccion());
        assertNull(response.getTelefono());
        assertNull(response.getUrlLogo());
        assertNull(response.getIdPropietario());
    }
}
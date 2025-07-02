package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IRestaurantRequestMapperTest {

    @Autowired
    private IRestaurantRequestMapper restaurantRequestMapper;

    @Test
    void shouldMapAddRequestToRestaurant() {
        // Given
        AddRestaurantRequest request = new AddRestaurantRequest(
                "Restaurante Test",
                "123456789",
                "Calle 123 #45-67",
                "+573001234567",
                "https://restaurante.com/logo.png",
                1L
        );

        // When
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(request);

        // Then
        assertNotNull(restaurant);
        assertNull(restaurant.getId()); // Should be ignored due to @Mapping
        assertEquals("Restaurante Test", restaurant.getNombre());
        assertEquals("123456789", restaurant.getNit());
        assertEquals("Calle 123 #45-67", restaurant.getDireccion());
        assertEquals("+573001234567", restaurant.getTelefono());
        assertEquals("https://restaurante.com/logo.png", restaurant.getUrlLogo());
        assertEquals(1L, restaurant.getIdPropietario());
    }

    @Test
    void shouldHandleNullValuesInAddRequest() {
        // Given
        AddRestaurantRequest request = new AddRestaurantRequest(
                null, null, null, null, null, null
        );

        // When
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(request);

        // Then
        assertNotNull(restaurant);
        assertNull(restaurant.getId());
        assertNull(restaurant.getNombre());
        assertNull(restaurant.getNit());
        assertNull(restaurant.getDireccion());
        assertNull(restaurant.getTelefono());
        assertNull(restaurant.getUrlLogo());
        assertNull(restaurant.getIdPropietario());
    }

    @Test
    void shouldHandleNullRequest() {
        // When
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(null);

        // Then
        assertNull(restaurant);
    }

    @Test
    void shouldMapCompleteAddRequestToRestaurant() {
        // Given
        AddRestaurantRequest request = new AddRestaurantRequest(
                "Pizza Palace",
                "555666777",
                "Avenida Principal 123",
                "+573009876543",
                "https://pizzapalace.com/logo.svg",
                50L
        );

        // When
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(request);

        // Then
        assertNotNull(restaurant);
        assertNull(restaurant.getId()); // Should be ignored
        assertEquals("Pizza Palace", restaurant.getNombre());
        assertEquals("555666777", restaurant.getNit());
        assertEquals("Avenida Principal 123", restaurant.getDireccion());
        assertEquals("+573009876543", restaurant.getTelefono());
        assertEquals("https://pizzapalace.com/logo.svg", restaurant.getUrlLogo());
        assertEquals(50L, restaurant.getIdPropietario());
    }

    @Test
    void shouldMapRequestWithSpecialCharacters() {
        // Given
        AddRestaurantRequest request = new AddRestaurantRequest(
                "Café & Té",
                "987654321",
                "Carrera 15 #23-45",
                "+571234567890",
                "https://example.com/logo.jpg",
                99L
        );

        // When
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(request);

        // Then
        assertNotNull(restaurant);
        assertNull(restaurant.getId());
        assertEquals("Café & Té", restaurant.getNombre());
        assertEquals("987654321", restaurant.getNit());
        assertEquals("Carrera 15 #23-45", restaurant.getDireccion());
        assertEquals("+571234567890", restaurant.getTelefono());
        assertEquals("https://example.com/logo.jpg", restaurant.getUrlLogo());
        assertEquals(99L, restaurant.getIdPropietario());
    }
}
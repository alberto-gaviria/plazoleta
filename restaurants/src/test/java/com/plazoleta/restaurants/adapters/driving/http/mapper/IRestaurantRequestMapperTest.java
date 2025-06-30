package com.plazoleta.restaurants.adapters.driving.http.mapper;

import com.plazoleta.restaurants.adapters.driving.http.dto.request.AddRestaurantRequest;
import com.plazoleta.restaurants.adapters.driving.http.dto.response.RestaurantResponse;
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
    void shouldMapRestaurantToResponse() {
        // Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setNombre("Restaurante Test");
        restaurant.setNit("123456789");
        restaurant.setDireccion("Calle 123 #45-67");
        restaurant.setTelefono("+573001234567");
        restaurant.setUrlLogo("https://restaurante.com/logo.png");
        restaurant.setIdPropietario(1L);

        // When
        RestaurantResponse response = restaurantRequestMapper.restaurantToResponse(restaurant);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Restaurante Test", response.getNombre());
        assertEquals("123456789", response.getNit());
        assertEquals("Calle 123 #45-67", response.getDireccion());
        assertEquals("+573001234567", response.getTelefono());
        assertEquals("https://restaurante.com/logo.png", response.getUrlLogo());
        assertEquals(1L, response.getIdPropietario());
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
    void shouldHandleNullValuesInRestaurant() {
        // Given
        Restaurant restaurant = new Restaurant();
        // All fields are null by default

        // When
        RestaurantResponse response = restaurantRequestMapper.restaurantToResponse(restaurant);

        // Then
        assertNotNull(response);
        assertNull(response.getId());
        assertNull(response.getNombre());
        assertNull(response.getNit());
        assertNull(response.getDireccion());
        assertNull(response.getTelefono());
        assertNull(response.getUrlLogo());
        assertNull(response.getIdPropietario());
    }

    @Test
    void shouldHandleNullRequest() {
        // When
        Restaurant restaurant = restaurantRequestMapper.addRequestToRestaurant(null);

        // Then
        assertNull(restaurant);
    }

    @Test
    void shouldHandleNullRestaurant() {
        // When
        RestaurantResponse response = restaurantRequestMapper.restaurantToResponse(null);

        // Then
        assertNull(response);
    }

    @Test
    void shouldMapCompleteRestaurantDataToResponse() {
        // Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(999L);
        restaurant.setNombre("El Mejor Restaurante");
        restaurant.setNit("987654321");
        restaurant.setDireccion("Carrera 10 #20-30");
        restaurant.setTelefono("+5712345678");
        restaurant.setUrlLogo("https://ejemplo.com/logo.jpg");
        restaurant.setIdPropietario(100L);

        // When
        RestaurantResponse response = restaurantRequestMapper.restaurantToResponse(restaurant);

        // Then
        assertNotNull(response);
        assertEquals(999L, response.getId());
        assertEquals("El Mejor Restaurante", response.getNombre());
        assertEquals("987654321", response.getNit());
        assertEquals("Carrera 10 #20-30", response.getDireccion());
        assertEquals("+5712345678", response.getTelefono());
        assertEquals("https://ejemplo.com/logo.jpg", response.getUrlLogo());
        assertEquals(100L, response.getIdPropietario());
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
}
package com.plazoleta.restaurants.domain.usecase;

import com.plazoleta.restaurants.domain.model.Restaurant;
import com.plazoleta.restaurants.domain.model.RestaurantSummary;
import com.plazoleta.restaurants.domain.spi.IRestaurantPersistencePort;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;
import com.plazoleta.restaurants.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    private RestaurantUseCase useCase;
    private IRestaurantPersistencePort persistencePort;

    @BeforeEach
    void setUp() {
        persistencePort = mock(IRestaurantPersistencePort.class);
        useCase = new RestaurantUseCase(persistencePort);
    }

    @Test
    void saveRestaurant_Valid_ShouldSucceed() {
        Restaurant restaurant = buildValidRestaurant();
        when(persistencePort.saveRestaurant(restaurant)).thenReturn(restaurant);

        Restaurant result = useCase.saveRestaurant(restaurant, 1L);
        assertEquals(restaurant, result);
    }

    @Test
    void saveRestaurant_Invalid_ShouldThrow() {
        assertThrows(InvalidRestaurantException.class, () ->
                useCase.saveRestaurant(null, 1L));
    }

    @Test
    void getAllRestaurants_Valid_ShouldReturnPage() {
        Page<RestaurantSummary> page = new Page<>(Collections.emptyList(), 0, 10, 0);
        when(persistencePort.findAllRestaurantsSorted(0, 10)).thenReturn(page);

        Page<RestaurantSummary> result = useCase.getAllRestaurants(0, 10);
        assertEquals(page, result);
    }

    @Test
    void getAllRestaurants_InvalidPageSize_ShouldThrow() {
        assertThrows(InvalidRestaurantException.class, () ->
                useCase.getAllRestaurants(-1, 10));
        assertThrows(InvalidRestaurantException.class, () ->
                useCase.getAllRestaurants(0, 0));
        assertThrows(InvalidRestaurantException.class, () ->
                useCase.getAllRestaurants(0, 1000));
    }

    @Test
    void saveRestaurant_InvalidFormat_ShouldThrow() {
        Restaurant r = buildValidRestaurant();
        r.setNombre("1234");
        assertThrows(InvalidRestaurantException.class, () ->
                useCase.saveRestaurant(r, 1L));

        r.setNombre("Valid Name");
        r.setNit("ABC");
        assertThrows(InvalidRestaurantException.class, () ->
                useCase.saveRestaurant(r, 1L));

        r.setNit("12345");
        r.setTelefono("abc");
        assertThrows(InvalidRestaurantException.class, () ->
                useCase.saveRestaurant(r, 1L));
    }

    private Restaurant buildValidRestaurant() {
        Restaurant r = new Restaurant();
        r.setNombre("Pizza Place");
        r.setNit("123456");
        r.setDireccion("Calle 123");
        r.setTelefono("1234567");
        r.setUrlLogo("http://logo.com");
        r.setIdPropietario(1L);
        return r;
    }
}

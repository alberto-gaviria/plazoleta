package com.plazoleta.restaurants.domain.api;

import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IRestaurantServicePortTest {

    @Test
    void shouldHaveCorrectMethodSignature() {
        // Given
        IRestaurantServicePort servicePort = new IRestaurantServicePort() {
            @Override
            public void saveRestaurant(Restaurant restaurant) {
                // Implementation for test
            }
        };

        // When & Then
        assertNotNull(servicePort);
        assertDoesNotThrow(() -> servicePort.saveRestaurant(new Restaurant()));
    }

    @Test
    void shouldBeAnInterface() {
        // Then
        assertTrue(IRestaurantServicePort.class.isInterface());
    }
}
package com.plazoleta.restaurants.domain.spi;

import com.plazoleta.restaurants.domain.model.Restaurant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IRestaurantPersistencePortTest {

    @Test
    void shouldHaveCorrectMethodSignature() {
        // Given
        IRestaurantPersistencePort persistencePort = new IRestaurantPersistencePort() {
            @Override
            public void saveRestaurant(Restaurant restaurant) {
                // Implementation for test
            }
        };

        // When & Then
        assertNotNull(persistencePort);
        assertDoesNotThrow(() -> persistencePort.saveRestaurant(new Restaurant()));
    }

    @Test
    void shouldBeAnInterface() {
        // Then
        assertTrue(IRestaurantPersistencePort.class.isInterface());
    }
}
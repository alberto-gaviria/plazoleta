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
            public void saveRestaurant(Restaurant restaurant, Long adminId) {
                // Implementation for test
            }
        };

        // When & Then
        assertNotNull(servicePort);
        assertDoesNotThrow(() -> servicePort.saveRestaurant(new Restaurant(), 1L));
    }

    @Test
    void shouldBeAnInterface() {
        // Then
        assertTrue(IRestaurantServicePort.class.isInterface());
    }

    @Test
    void shouldHaveMethodWithCorrectParameters() throws NoSuchMethodException {
        // Given - Verificar que el método existe con los parámetros correctos

        // When & Then
        assertDoesNotThrow(() -> {
            IRestaurantServicePort.class.getMethod("saveRestaurant", Restaurant.class, Long.class);
        });
    }

    @Test
    void shouldNotHaveOldMethodSignature() {
        // Given - Verificar que el método anterior (sin adminId) ya no existe

        // When & Then
        assertThrows(NoSuchMethodException.class, () -> {
            IRestaurantServicePort.class.getMethod("saveRestaurant", Restaurant.class);
        });
    }
}
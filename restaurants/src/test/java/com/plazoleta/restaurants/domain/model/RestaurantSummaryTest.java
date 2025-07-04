package com.plazoleta.restaurants.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RestaurantSummary Tests")
class RestaurantSummaryTest {

    @Test
    @DisplayName("Should create RestaurantSummary with default constructor")
    void shouldCreateWithDefaultConstructor() {
        // Given & When
        RestaurantSummary restaurantSummary = new RestaurantSummary();

        // Then
        assertNull(restaurantSummary.getNombre());
        assertNull(restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should create RestaurantSummary with parameterized constructor")
    void shouldCreateWithParameterizedConstructor() {
        // Given
        String nombre = "Restaurante Test";
        String urlLogo = "https://example.com/logo.jpg";

        // When
        RestaurantSummary restaurantSummary = new RestaurantSummary(nombre, urlLogo);

        // Then
        assertEquals(nombre, restaurantSummary.getNombre());
        assertEquals(urlLogo, restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should set and get nombre correctly")
    void shouldSetAndGetNombre() {
        // Given
        RestaurantSummary restaurantSummary = new RestaurantSummary();
        String nombre = "Nuevo Restaurante";

        // When
        restaurantSummary.setNombre(nombre);

        // Then
        assertEquals(nombre, restaurantSummary.getNombre());
    }

    @Test
    @DisplayName("Should set and get urlLogo correctly")
    void shouldSetAndGetUrlLogo() {
        // Given
        RestaurantSummary restaurantSummary = new RestaurantSummary();
        String urlLogo = "https://test.com/new-logo.png";

        // When
        restaurantSummary.setUrlLogo(urlLogo);

        // Then
        assertEquals(urlLogo, restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle null nombre")
    void shouldHandleNullNombre() {
        // Given
        RestaurantSummary restaurantSummary = new RestaurantSummary();

        // When
        restaurantSummary.setNombre(null);

        // Then
        assertNull(restaurantSummary.getNombre());
    }

    @Test
    @DisplayName("Should handle null urlLogo")
    void shouldHandleNullUrlLogo() {
        // Given
        RestaurantSummary restaurantSummary = new RestaurantSummary();

        // When
        restaurantSummary.setUrlLogo(null);

        // Then
        assertNull(restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Given
        RestaurantSummary restaurantSummary = new RestaurantSummary();

        // When
        restaurantSummary.setNombre("");
        restaurantSummary.setUrlLogo("");

        // Then
        assertEquals("", restaurantSummary.getNombre());
        assertEquals("", restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should create with null parameters in constructor")
    void shouldCreateWithNullParametersInConstructor() {
        // Given & When
        RestaurantSummary restaurantSummary = new RestaurantSummary(null, null);

        // Then
        assertNull(restaurantSummary.getNombre());
        assertNull(restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle unicode characters")
    void shouldHandleUnicodeCharacters() {
        // Given
        String nombreWithUnicode = "Restaurante 中文 🍕 Café";
        String urlWithUnicode = "https://example.com/中文-logo.png";

        // When
        RestaurantSummary restaurantSummary = new RestaurantSummary(nombreWithUnicode, urlWithUnicode);

        // Then
        assertEquals(nombreWithUnicode, restaurantSummary.getNombre());
        assertEquals(urlWithUnicode, restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should maintain data integrity when modifying properties")
    void shouldMaintainDataIntegrityWhenModifying() {
        // Given
        String initialNombre = "Initial Name";
        String initialUrl = "https://initial.com/logo.png";
        RestaurantSummary restaurantSummary = new RestaurantSummary(initialNombre, initialUrl);

        // When
        String newNombre = "Modified Name";
        String newUrl = "https://modified.com/logo.png";
        restaurantSummary.setNombre(newNombre);
        restaurantSummary.setUrlLogo(newUrl);

        // Then
        assertEquals(newNombre, restaurantSummary.getNombre());
        assertEquals(newUrl, restaurantSummary.getUrlLogo());
        assertNotEquals(initialNombre, restaurantSummary.getNombre());
        assertNotEquals(initialUrl, restaurantSummary.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle whitespace strings")
    void shouldHandleWhitespaceStrings() {
        // Given
        String whitespaceNombre = "   ";
        String whitespaceUrl = "\t\n ";

        // When
        RestaurantSummary restaurantSummary = new RestaurantSummary();
        restaurantSummary.setNombre(whitespaceNombre);
        restaurantSummary.setUrlLogo(whitespaceUrl);

        // Then
        assertEquals(whitespaceNombre, restaurantSummary.getNombre());
        assertEquals(whitespaceUrl, restaurantSummary.getUrlLogo());
    }
}
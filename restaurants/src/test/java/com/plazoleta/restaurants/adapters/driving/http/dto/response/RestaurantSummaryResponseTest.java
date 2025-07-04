package com.plazoleta.restaurants.adapters.driving.http.dto.response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RestaurantSummaryResponse Tests")
class RestaurantSummaryResponseTest {

    @Test
    @DisplayName("Should create RestaurantSummaryResponse with default constructor")
    void shouldCreateWithDefaultConstructor() {
        // Given & When
        RestaurantSummaryResponse response = new RestaurantSummaryResponse();

        // Then
        assertNull(response.getNombre());
        assertNull(response.getUrlLogo());
    }

    @Test
    @DisplayName("Should create RestaurantSummaryResponse with parameterized constructor")
    void shouldCreateWithParameterizedConstructor() {
        // Given
        String nombre = "Restaurante Prueba";
        String urlLogo = "https://example.com/logo.png";

        // When
        RestaurantSummaryResponse response = new RestaurantSummaryResponse(nombre, urlLogo);

        // Then
        assertEquals(nombre, response.getNombre());
        assertEquals(urlLogo, response.getUrlLogo());
    }

    @Test
    @DisplayName("Should set and get nombre correctly")
    void shouldSetAndGetNombre() {
        // Given
        RestaurantSummaryResponse response = new RestaurantSummaryResponse();
        String nombre = "Mi Restaurante";

        // When
        response.setNombre(nombre);

        // Then
        assertEquals(nombre, response.getNombre());
    }

    @Test
    @DisplayName("Should set and get urlLogo correctly")
    void shouldSetAndGetUrlLogo() {
        // Given
        RestaurantSummaryResponse response = new RestaurantSummaryResponse();
        String urlLogo = "https://test.com/image.jpg";

        // When
        response.setUrlLogo(urlLogo);

        // Then
        assertEquals(urlLogo, response.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle null nombre")
    void shouldHandleNullNombre() {
        // Given
        RestaurantSummaryResponse response = new RestaurantSummaryResponse();

        // When
        response.setNombre(null);

        // Then
        assertNull(response.getNombre());
    }

    @Test
    @DisplayName("Should handle null urlLogo")
    void shouldHandleNullUrlLogo() {
        // Given
        RestaurantSummaryResponse response = new RestaurantSummaryResponse();

        // When
        response.setUrlLogo(null);

        // Then
        assertNull(response.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle empty strings")
    void shouldHandleEmptyStrings() {
        // Given
        RestaurantSummaryResponse response = new RestaurantSummaryResponse();

        // When
        response.setNombre("");
        response.setUrlLogo("");

        // Then
        assertEquals("", response.getNombre());
        assertEquals("", response.getUrlLogo());
    }

    @Test
    @DisplayName("Should create with null parameters in constructor")
    void shouldCreateWithNullParametersInConstructor() {
        // Given & When
        RestaurantSummaryResponse response = new RestaurantSummaryResponse(null, null);

        // Then
        assertNull(response.getNombre());
        assertNull(response.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle special characters")
    void shouldHandleSpecialCharacters() {
        // Given
        String nombreWithSpecialChars = "Café & Más ñáéíóú";
        String urlWithSpecialChars = "https://example.com/café-logo.png?param=value&other=1";

        // When
        RestaurantSummaryResponse response = new RestaurantSummaryResponse(nombreWithSpecialChars, urlWithSpecialChars);

        // Then
        assertEquals(nombreWithSpecialChars, response.getNombre());
        assertEquals(urlWithSpecialChars, response.getUrlLogo());
    }

    @Test
    @DisplayName("Should handle very long strings")
    void shouldHandleVeryLongStrings() {
        // Given
        String longNombre = "A".repeat(1000);
        String longUrl = "https://example.com/" + "b".repeat(500) + ".png";

        // When
        RestaurantSummaryResponse response = new RestaurantSummaryResponse();
        response.setNombre(longNombre);
        response.setUrlLogo(longUrl);

        // Then
        assertEquals(longNombre, response.getNombre());
        assertEquals(longUrl, response.getUrlLogo());
    }
}
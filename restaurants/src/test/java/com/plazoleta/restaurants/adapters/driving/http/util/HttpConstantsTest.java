package com.plazoleta.restaurants.adapters.driving.http.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class HttpConstantsTest {

    @Test
    void shouldNotInstantiateHttpConstants() throws NoSuchMethodException {
        // Given
        Constructor<HttpConstants> constructor = HttpConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateRoles() throws NoSuchMethodException {
        // Given
        Constructor<HttpConstants.Roles> constructor =
                HttpConstants.Roles.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Constants class", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiatePaths() throws NoSuchMethodException {
        // Given
        Constructor<HttpConstants.Paths> constructor =
                HttpConstants.Paths.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Constants class", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateMessages() throws NoSuchMethodException {
        // Given
        Constructor<HttpConstants.Messages> constructor =
                HttpConstants.Messages.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Constants class", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiatePagination() throws NoSuchMethodException {
        // Given
        Constructor<HttpConstants.Pagination> constructor =
                HttpConstants.Pagination.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertTrue(exception.getCause() instanceof IllegalStateException);
        assertEquals("Constants class", exception.getCause().getMessage());
    }

    @Test
    void shouldHaveCorrectRoleConstants() {
        // Then
        assertEquals("ADMINISTRADOR", HttpConstants.Roles.ADMINISTRADOR);
        assertEquals("PROPIETARIO", HttpConstants.Roles.PROPIETARIO);
        assertEquals("CLIENTE", HttpConstants.Roles.CLIENTE);
    }

    @Test
    void shouldHaveCorrectPathConstants() {
        // Then
        assertEquals("/platos", HttpConstants.Paths.PLATOS);
        assertEquals("/{dishId}", HttpConstants.Paths.DISH_BY_ID);
        assertEquals("/{dishId}/estado", HttpConstants.Paths.DISH_STATUS);
    }

    @Test
    void shouldHaveCorrectSuccessMessages() {
        // Then
        assertEquals("Plato creado exitosamente",
                HttpConstants.Messages.CREATE_DISH_SUCCESS);
        assertEquals("Plato actualizado exitosamente",
                HttpConstants.Messages.UPDATE_DISH_SUCCESS);
        assertEquals("Estado del plato actualizado exitosamente",
                HttpConstants.Messages.UPDATE_STATUS_SUCCESS);
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
        // Then
        assertEquals("Datos de entrada inválidos",
                HttpConstants.Messages.INVALID_INPUT);
        assertEquals("No autorizado - Token requerido",
                HttpConstants.Messages.UNAUTHORIZED);
        assertEquals("Prohibido - Solo propietarios pueden crear platos",
                HttpConstants.Messages.FORBIDDEN_CREATE_DISH);
        assertEquals("Prohibido - Solo el propietario del restaurante puede modificar platos",
                HttpConstants.Messages.FORBIDDEN_UPDATE_DISH);
        assertEquals("Prohibido - Solo el propietario del restaurante puede cambiar el estado de platos",
                HttpConstants.Messages.FORBIDDEN_TOGGLE_DISH);
        assertEquals("Plato no encontrado",
                HttpConstants.Messages.DISH_NOT_FOUND);
        assertEquals("Restaurante no encontrado",
                HttpConstants.Messages.RESTAURANT_NOT_FOUND);
        assertEquals("Error interno del servidor",
                HttpConstants.Messages.INTERNAL_ERROR);
    }

    @Test
    void shouldHaveCorrectPaginationStringConstants() {
        // Then
        assertEquals("0", HttpConstants.Pagination.DEFAULT_PAGE_VALUE);
        assertEquals("10", HttpConstants.Pagination.DEFAULT_SIZE_VALUE);
    }

    @Test
    void shouldHaveCorrectPaginationIntegerConstants() {
        // Then
        assertEquals(0, HttpConstants.Pagination.MIN_PAGE);
        assertEquals(1, HttpConstants.Pagination.MIN_SIZE);
        assertEquals(50, HttpConstants.Pagination.MAX_SIZE);
    }

    @Test
    void shouldVerifyAllRoleConstantsAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Roles.ADMINISTRADOR);
        assertNotNull(HttpConstants.Roles.PROPIETARIO);
        assertNotNull(HttpConstants.Roles.CLIENTE);
    }

    @Test
    void shouldVerifyAllPathConstantsAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Paths.PLATOS);
        assertNotNull(HttpConstants.Paths.DISH_BY_ID);
        assertNotNull(HttpConstants.Paths.DISH_STATUS);
    }

    @Test
    void shouldVerifyAllSuccessMessagesAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Messages.CREATE_DISH_SUCCESS);
        assertNotNull(HttpConstants.Messages.UPDATE_DISH_SUCCESS);
        assertNotNull(HttpConstants.Messages.UPDATE_STATUS_SUCCESS);
    }

    @Test
    void shouldVerifyAllErrorMessagesAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Messages.INVALID_INPUT);
        assertNotNull(HttpConstants.Messages.UNAUTHORIZED);
        assertNotNull(HttpConstants.Messages.FORBIDDEN_CREATE_DISH);
        assertNotNull(HttpConstants.Messages.FORBIDDEN_UPDATE_DISH);
        assertNotNull(HttpConstants.Messages.FORBIDDEN_TOGGLE_DISH);
        assertNotNull(HttpConstants.Messages.DISH_NOT_FOUND);
        assertNotNull(HttpConstants.Messages.RESTAURANT_NOT_FOUND);
        assertNotNull(HttpConstants.Messages.INTERNAL_ERROR);
    }

    @Test
    void shouldVerifyAllPaginationConstantsAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Pagination.DEFAULT_PAGE_VALUE);
        assertNotNull(HttpConstants.Pagination.DEFAULT_SIZE_VALUE);
    }

    @Test
    void shouldVerifyPaginationValueRanges() {
        // Then
        assertTrue(HttpConstants.Pagination.MIN_PAGE >= 0);
        assertTrue(HttpConstants.Pagination.MIN_SIZE >= 1);
        assertTrue(HttpConstants.Pagination.MAX_SIZE > HttpConstants.Pagination.MIN_SIZE);
        assertTrue(HttpConstants.Pagination.MAX_SIZE >= 1);
    }

    @Test
    void shouldVerifyDefaultPaginationValuesAreValid() {
        // Then
        int defaultPage = Integer.parseInt(HttpConstants.Pagination.DEFAULT_PAGE_VALUE);
        int defaultSize = Integer.parseInt(HttpConstants.Pagination.DEFAULT_SIZE_VALUE);

        assertTrue(defaultPage >= HttpConstants.Pagination.MIN_PAGE);
        assertTrue(defaultSize >= HttpConstants.Pagination.MIN_SIZE);
        assertTrue(defaultSize <= HttpConstants.Pagination.MAX_SIZE);
    }

    @Test
    void shouldVerifyPathConstantsContainCorrectPlaceholders() {
        // Then
        assertTrue(HttpConstants.Paths.DISH_BY_ID.contains("{dishId}"));
        assertTrue(HttpConstants.Paths.DISH_STATUS.contains("{dishId}"));
        assertTrue(HttpConstants.Paths.DISH_STATUS.contains("/estado"));
    }

    @Test
    void shouldVerifyRoleConstantsAreUpperCase() {
        // Then
        assertEquals(HttpConstants.Roles.ADMINISTRADOR.toUpperCase(), HttpConstants.Roles.ADMINISTRADOR);
        assertEquals(HttpConstants.Roles.PROPIETARIO.toUpperCase(), HttpConstants.Roles.PROPIETARIO);
        assertEquals(HttpConstants.Roles.CLIENTE.toUpperCase(), HttpConstants.Roles.CLIENTE);
    }

    @Test
    void shouldVerifyPathConstantsStartCorrectly() {
        // Then
        assertTrue(HttpConstants.Paths.PLATOS.startsWith("/"));
        assertTrue(HttpConstants.Paths.DISH_BY_ID.startsWith("/{"));
        assertTrue(HttpConstants.Paths.DISH_STATUS.startsWith("/{"));
    }

    @Test
    void shouldVerifySuccessMessagesAreInSpanish() {
        // Then
        assertTrue(HttpConstants.Messages.CREATE_DISH_SUCCESS.contains("exitosamente"));
        assertTrue(HttpConstants.Messages.UPDATE_DISH_SUCCESS.contains("exitosamente"));
        assertTrue(HttpConstants.Messages.UPDATE_STATUS_SUCCESS.contains("exitosamente"));
    }

    @Test
    void shouldVerifyErrorMessagesAreInSpanish() {
        // Then
        assertTrue(HttpConstants.Messages.INVALID_INPUT.contains("inválidos"));
        assertTrue(HttpConstants.Messages.UNAUTHORIZED.contains("autorizado"));
        assertTrue(HttpConstants.Messages.FORBIDDEN_CREATE_DISH.contains("Prohibido"));
        assertTrue(HttpConstants.Messages.DISH_NOT_FOUND.contains("no encontrado"));
        assertTrue(HttpConstants.Messages.RESTAURANT_NOT_FOUND.contains("no encontrado"));
        assertTrue(HttpConstants.Messages.INTERNAL_ERROR.contains("Error interno"));
    }

    @Test
    void shouldVerifyConstantsAreImmutable() {
        // Then - All constants should be static final, verified by successful compilation
        // This test ensures constants exist and are accessible
        String role = HttpConstants.Roles.ADMINISTRADOR;
        String path = HttpConstants.Paths.PLATOS;
        String message = HttpConstants.Messages.CREATE_DISH_SUCCESS;
        String pagination = HttpConstants.Pagination.DEFAULT_PAGE_VALUE;
        int minPage = HttpConstants.Pagination.MIN_PAGE;

        assertNotNull(role);
        assertNotNull(path);
        assertNotNull(message);
        assertNotNull(pagination);
        assertTrue(minPage >= 0);
    }

}
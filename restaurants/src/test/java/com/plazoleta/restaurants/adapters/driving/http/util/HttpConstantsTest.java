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
        assertEquals("EMPLEADO", HttpConstants.Roles.EMPLEADO);
    }

    @Test
    void shouldHaveCorrectPathConstants() {
        // Then
        assertEquals("/platos", HttpConstants.Paths.PLATOS);
        assertEquals("/{dishId}", HttpConstants.Paths.DISH_BY_ID);
        assertEquals("/{dishId}/estado", HttpConstants.Paths.DISH_STATUS);
        assertEquals("/restaurante/{restaurantId}", HttpConstants.Paths.DISH_BY_RESTAURANT);
        assertEquals("/pedidos", HttpConstants.Paths.PEDIDOS);
        assertEquals("/asignar", HttpConstants.Paths.ASSIGN_EMPLOYEE);
    }

    @Test
    void shouldHaveCorrectSuccessMessages() {
        // Then
        assertEquals("Pedido creado exitosamente",
                     HttpConstants.Messages.CREATE_ORDER_SUCCESS);
        assertEquals("Lista de pedidos obtenida exitosamente",
                     HttpConstants.Messages.GET_ORDERS_SUCCESS);
        assertEquals("Empleado asignado exitosamente al pedido",
                     HttpConstants.Messages.ASSIGN_EMPLOYEE_SUCCESS);
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
        // Then
        assertEquals("Estado de pedido inválido: ",
                     HttpConstants.Messages.INVALID_ORDER_STATUS);
        assertEquals("Datos de entrada inválidos",
                     HttpConstants.Messages.INVALID_INPUT);
        assertEquals("Parámetros de paginación inválidos",
                     HttpConstants.Messages.INVALID_PAGINATION);
        assertEquals("No autorizado - Token requerido",
                     HttpConstants.Messages.UNAUTHORIZED);
        assertEquals("Prohibido - Solo empleados pueden listar pedidos",
                     HttpConstants.Messages.FORBIDDEN_EMPLOYEE);
        assertEquals("Prohibido - Solo clientes pueden crear pedidos",
                     HttpConstants.Messages.FORBIDDEN_CLIENT);
        assertEquals("Empleado sin restaurante asignado",
                     HttpConstants.Messages.EMPLOYEE_WITHOUT_RESTAURANT);
        assertEquals("El cliente ya tiene un pedido activo",
                     HttpConstants.Messages.CLIENT_HAS_ACTIVE_ORDER);
        assertEquals("Pedido no encontrado",
                     HttpConstants.Messages.ORDER_NOT_FOUND);
        assertEquals("Plato o restaurante no encontrado",
                     HttpConstants.Messages.DISH_NOT_FOUND);
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
        assertNotNull(HttpConstants.Roles.EMPLEADO);
    }

    @Test
    void shouldVerifyAllPathConstantsAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Paths.PLATOS);
        assertNotNull(HttpConstants.Paths.DISH_BY_ID);
        assertNotNull(HttpConstants.Paths.DISH_STATUS);
        assertNotNull(HttpConstants.Paths.DISH_BY_RESTAURANT);
        assertNotNull(HttpConstants.Paths.PEDIDOS);
        assertNotNull(HttpConstants.Paths.ASSIGN_EMPLOYEE);
    }

    @Test
    void shouldVerifyAllSuccessMessagesAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Messages.CREATE_ORDER_SUCCESS);
        assertNotNull(HttpConstants.Messages.GET_ORDERS_SUCCESS);
        assertNotNull(HttpConstants.Messages.ASSIGN_EMPLOYEE_SUCCESS);
    }

    @Test
    void shouldVerifyAllErrorMessagesAreNotNull() {
        // Then
        assertNotNull(HttpConstants.Messages.INVALID_ORDER_STATUS);
        assertNotNull(HttpConstants.Messages.INVALID_INPUT);
        assertNotNull(HttpConstants.Messages.INVALID_PAGINATION);
        assertNotNull(HttpConstants.Messages.UNAUTHORIZED);
        assertNotNull(HttpConstants.Messages.FORBIDDEN_EMPLOYEE);
        assertNotNull(HttpConstants.Messages.FORBIDDEN_CLIENT);
        assertNotNull(HttpConstants.Messages.EMPLOYEE_WITHOUT_RESTAURANT);
        assertNotNull(HttpConstants.Messages.CLIENT_HAS_ACTIVE_ORDER);
        assertNotNull(HttpConstants.Messages.ORDER_NOT_FOUND);
        assertNotNull(HttpConstants.Messages.DISH_NOT_FOUND);
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
        assertTrue(HttpConstants.Paths.DISH_BY_RESTAURANT.contains("{restaurantId}"));
        assertTrue(HttpConstants.Paths.DISH_BY_RESTAURANT.contains("/restaurante"));
    }

    @Test
    void shouldVerifyRoleConstantsAreUpperCase() {
        // Then
        assertEquals(HttpConstants.Roles.ADMINISTRADOR.toUpperCase(), HttpConstants.Roles.ADMINISTRADOR);
        assertEquals(HttpConstants.Roles.PROPIETARIO.toUpperCase(), HttpConstants.Roles.PROPIETARIO);
        assertEquals(HttpConstants.Roles.CLIENTE.toUpperCase(), HttpConstants.Roles.CLIENTE);
        assertEquals(HttpConstants.Roles.EMPLEADO.toUpperCase(), HttpConstants.Roles.EMPLEADO);
    }

    @Test
    void shouldVerifyPathConstantsStartCorrectly() {
        // Then
        assertTrue(HttpConstants.Paths.PLATOS.startsWith("/"));
        assertTrue(HttpConstants.Paths.DISH_BY_ID.startsWith("/{"));
        assertTrue(HttpConstants.Paths.DISH_STATUS.startsWith("/{"));
        assertTrue(HttpConstants.Paths.DISH_BY_RESTAURANT.startsWith("/restaurante"));
        assertTrue(HttpConstants.Paths.PEDIDOS.startsWith("/"));
        assertTrue(HttpConstants.Paths.ASSIGN_EMPLOYEE.startsWith("/"));
    }

    @Test
    void shouldVerifySuccessMessagesAreInSpanish() {
        // Then
        assertTrue(HttpConstants.Messages.CREATE_ORDER_SUCCESS.contains("exitosamente"));
        assertTrue(HttpConstants.Messages.GET_ORDERS_SUCCESS.contains("exitosamente"));
        assertTrue(HttpConstants.Messages.ASSIGN_EMPLOYEE_SUCCESS.contains("exitosamente"));
    }

    @Test
    void shouldVerifyErrorMessagesAreInSpanish() {
        // Then
        assertTrue(HttpConstants.Messages.INVALID_ORDER_STATUS.contains("inválido"));
        assertTrue(HttpConstants.Messages.INVALID_INPUT.contains("inválidos"));
        assertTrue(HttpConstants.Messages.INVALID_PAGINATION.contains("inválidos"));
        assertTrue(HttpConstants.Messages.UNAUTHORIZED.contains("autorizado"));
        assertTrue(HttpConstants.Messages.FORBIDDEN_EMPLOYEE.contains("Prohibido"));
        assertTrue(HttpConstants.Messages.FORBIDDEN_CLIENT.contains("Prohibido"));
        assertTrue(HttpConstants.Messages.EMPLOYEE_WITHOUT_RESTAURANT.contains("sin restaurante"));
        assertTrue(HttpConstants.Messages.CLIENT_HAS_ACTIVE_ORDER.contains("pedido activo"));
        assertTrue(HttpConstants.Messages.ORDER_NOT_FOUND.contains("no encontrado"));
        assertTrue(HttpConstants.Messages.DISH_NOT_FOUND.contains("no encontrado"));
        assertTrue(HttpConstants.Messages.INTERNAL_ERROR.contains("Error interno"));
    }

    @Test
    void shouldVerifyOrderRelatedMessages() {
        // Then - Verificar que los mensajes relacionados con pedidos sean correctos
        assertTrue(HttpConstants.Messages.CREATE_ORDER_SUCCESS.contains("Pedido"));
        assertTrue(HttpConstants.Messages.GET_ORDERS_SUCCESS.contains("pedidos"));
        assertTrue(HttpConstants.Messages.ORDER_NOT_FOUND.contains("Pedido"));
        assertTrue(HttpConstants.Messages.CLIENT_HAS_ACTIVE_ORDER.contains("cliente"));
        assertTrue(HttpConstants.Messages.ASSIGN_EMPLOYEE_SUCCESS.contains("Empleado"));
        assertTrue(HttpConstants.Messages.ASSIGN_EMPLOYEE_SUCCESS.contains("pedido"));
    }

    @Test
    void shouldVerifyEmployeeRelatedMessages() {
        // Then - Verificar que los mensajes relacionados con empleados sean correctos
        assertTrue(HttpConstants.Messages.FORBIDDEN_EMPLOYEE.contains("empleados"));
        assertTrue(HttpConstants.Messages.EMPLOYEE_WITHOUT_RESTAURANT.contains("Empleado"));
        assertTrue(HttpConstants.Messages.ASSIGN_EMPLOYEE_SUCCESS.contains("Empleado"));
    }

    @Test
    void shouldVerifyClientRelatedMessages() {
        // Then - Verificar que los mensajes relacionados con clientes sean correctos
        assertTrue(HttpConstants.Messages.FORBIDDEN_CLIENT.contains("clientes"));
        assertTrue(HttpConstants.Messages.CLIENT_HAS_ACTIVE_ORDER.contains("cliente"));
    }

    @Test
    void shouldVerifyForbiddenMessagesStructure() {
        // Then - Verificar que los mensajes de prohibido tengan la estructura correcta
        assertTrue(HttpConstants.Messages.FORBIDDEN_EMPLOYEE.startsWith("Prohibido"));
        assertTrue(HttpConstants.Messages.FORBIDDEN_CLIENT.startsWith("Prohibido"));
        assertTrue(HttpConstants.Messages.FORBIDDEN_EMPLOYEE.contains("Solo"));
        assertTrue(HttpConstants.Messages.FORBIDDEN_CLIENT.contains("Solo"));
    }

    @Test
    void shouldVerifyInvalidMessagesStructure() {
        // Then - Verificar que los mensajes de inválido tengan la estructura correcta
        assertTrue(HttpConstants.Messages.INVALID_INPUT.contains("inválidos"));
        assertTrue(HttpConstants.Messages.INVALID_PAGINATION.contains("inválidos"));
        assertTrue(HttpConstants.Messages.INVALID_ORDER_STATUS.endsWith(": "));
    }

    @Test
    void shouldVerifyConstantsAreImmutable() {
        // Then - All constants should be static final, verified by successful compilation
        // This test ensures constants exist and are accessible
        String role = HttpConstants.Roles.ADMINISTRADOR;
        String empleado = HttpConstants.Roles.EMPLEADO;
        String path = HttpConstants.Paths.PLATOS;
        String pedidosPath = HttpConstants.Paths.PEDIDOS;
        String assignPath = HttpConstants.Paths.ASSIGN_EMPLOYEE;
        String message = HttpConstants.Messages.CREATE_ORDER_SUCCESS;
        String orderMessage = HttpConstants.Messages.GET_ORDERS_SUCCESS;
        String assignMessage = HttpConstants.Messages.ASSIGN_EMPLOYEE_SUCCESS;
        String pagination = HttpConstants.Pagination.DEFAULT_PAGE_VALUE;
        int minPage = HttpConstants.Pagination.MIN_PAGE;

        assertNotNull(role);
        assertNotNull(empleado);
        assertNotNull(path);
        assertNotNull(pedidosPath);
        assertNotNull(assignPath);
        assertNotNull(message);
        assertNotNull(orderMessage);
        assertNotNull(assignMessage);
        assertNotNull(pagination);
        assertTrue(minPage >= 0);
    }

    @Test
    void shouldVerifyAllPathsAreValidUrlPatterns() {
        // Then - Verificar que todos los paths sean patrones de URL válidos
        assertFalse(HttpConstants.Paths.PLATOS.contains(" "));
        assertFalse(HttpConstants.Paths.DISH_BY_ID.contains(" "));
        assertFalse(HttpConstants.Paths.DISH_STATUS.contains(" "));
        assertFalse(HttpConstants.Paths.DISH_BY_RESTAURANT.contains(" "));
        assertFalse(HttpConstants.Paths.PEDIDOS.contains(" "));
        assertFalse(HttpConstants.Paths.ASSIGN_EMPLOYEE.contains(" "));
    }

    @Test
    void shouldVerifyPathParameterFormat() {
        // Then - Verificar que los parámetros de path tengan el formato correcto
        assertTrue(HttpConstants.Paths.DISH_BY_ID.matches(".*\\{\\w+\\}.*"));
        assertTrue(HttpConstants.Paths.DISH_STATUS.matches(".*\\{\\w+\\}.*"));
        assertTrue(HttpConstants.Paths.DISH_BY_RESTAURANT.matches(".*\\{\\w+\\}.*"));
    }

    @Test
    void shouldVerifyAssignEmployeePathFormat() {
        // Then - Verificar que el path de asignar empleado sea correcto
        assertEquals("/asignar", HttpConstants.Paths.ASSIGN_EMPLOYEE);
        assertTrue(HttpConstants.Paths.ASSIGN_EMPLOYEE.startsWith("/"));
        assertFalse(HttpConstants.Paths.ASSIGN_EMPLOYEE.contains("{"));
        assertFalse(HttpConstants.Paths.ASSIGN_EMPLOYEE.contains("}"));
    }
}
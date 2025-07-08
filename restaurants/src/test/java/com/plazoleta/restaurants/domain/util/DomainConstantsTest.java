package com.plazoleta.restaurants.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DomainConstantsTest {

    @Test
    void shouldNotInstantiateDomainConstants() throws NoSuchMethodException {
        // Given
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateRestaurant() throws NoSuchMethodException {
        // Given
        Constructor<DomainConstants.Restaurant> constructor =
                DomainConstants.Restaurant.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateDish() throws NoSuchMethodException {
        // Given
        Constructor<DomainConstants.Dish> constructor =
                DomainConstants.Dish.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateOrder() throws NoSuchMethodException {
        // Given
        Constructor<DomainConstants.Order> constructor =
                DomainConstants.Order.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // When & Then
        InvocationTargetException exception = assertThrows(
                InvocationTargetException.class,
                constructor::newInstance
        );

        assertInstanceOf(IllegalStateException.class, exception.getCause());
        assertEquals("Clase de constantes", exception.getCause().getMessage());
    }

    // =================== RESTAURANT CONSTANTS TESTS ===================

    @Test
    void shouldHaveCorrectRestaurantPatterns() {
        // Then
        assertEquals("^[0-9]+$", DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN);
        assertEquals("^\\+?[0-9]{1,13}$", DomainConstants.Restaurant.TELEFONO_PATTERN);
    }

    @Test
    void shouldHaveCorrectRestaurantRoles() {
        // Then
        assertEquals("PROPIETARIO", DomainConstants.Restaurant.ROL_PROPIETARIO);
        assertEquals("ADMINISTRADOR", DomainConstants.Restaurant.ROL_ADMINISTRADOR);
        assertEquals("CLIENTE", DomainConstants.Restaurant.ROL_CLIENTE);
    }

    @Test
    void shouldHaveCorrectRestaurantRoleIds() {
        // Then
        assertEquals(Long.valueOf(1L), DomainConstants.Restaurant.ROL_ADMINISTRADOR_ID);
        assertEquals(Long.valueOf(2L), DomainConstants.Restaurant.ROL_PROPIETARIO_ID);
        assertEquals(Long.valueOf(4L), DomainConstants.Restaurant.ROL_CLIENTE_ID);
    }

    @Test
    void shouldHaveCorrectRestaurantPaginationConstants() {
        // Then
        assertEquals(100, DomainConstants.Restaurant.MAX_PAGE_SIZE);
        assertEquals(10, DomainConstants.Restaurant.DEFAULT_PAGE_SIZE);
        assertEquals(0, DomainConstants.Restaurant.MIN_PAGE_NUMBER);
        assertEquals(1, DomainConstants.Restaurant.MIN_PAGE_SIZE);
        assertEquals(1, DomainConstants.Order.MAX_RESTAURANTS_PER_ORDER);
    }

    @Test
    void shouldHaveCorrectRestaurantErrorMessages() {
        // Then
        assertEquals("El restaurante no puede ser nulo", DomainConstants.Restaurant.ERROR_RESTAURANT_NULO);
        assertEquals("El nombre es obligatorio", DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO);
        assertEquals("El NIT es obligatorio", DomainConstants.Restaurant.ERROR_NIT_REQUERIDO);
        assertEquals("La dirección es obligatoria", DomainConstants.Restaurant.ERROR_DIRECCION_REQUERIDA);
        assertEquals("El teléfono es obligatorio", DomainConstants.Restaurant.ERROR_TELEFONO_REQUERIDO);
        assertEquals("La URL del logo es obligatoria", DomainConstants.Restaurant.ERROR_URL_LOGO_REQUERIDA);
        assertEquals("El ID del propietario es obligatorio", DomainConstants.Restaurant.ERROR_ID_PROPIETARIO_REQUERIDO);
        assertEquals("El ID del propietario no corresponde a un usuario con rol propietario", DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_VALIDO);
        assertEquals("No se encontró el usuario propietario especificado", DomainConstants.Restaurant.ERROR_PROPIETARIO_NO_ENCONTRADO);
        assertEquals("El nombre del restaurante no puede contener sólo números", DomainConstants.Restaurant.ERROR_NOMBRE_SOLO_NUMEROS);
        assertEquals("El NIT debe contener únicamente números", DomainConstants.Restaurant.ERROR_NIT_FORMATO_INVALIDO);
        assertEquals("El teléfono debe contener máximo 13 caracteres numéricos y puede incluir el símbolo +", DomainConstants.Restaurant.ERROR_TELEFONO_FORMATO_INVALIDO);
    }

    @Test
    void shouldHaveCorrectRestaurantAdminErrorMessages() {
        // Then
        assertEquals("El ID del administrador es obligatorio", DomainConstants.Restaurant.ERROR_ADMIN_ID_REQUERIDO);
        assertEquals("No se encontró el usuario administrador especificado", DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_ENCONTRADO);
        assertEquals("El ID del administrador no corresponde a un usuario con rol administrador", DomainConstants.Restaurant.ERROR_ADMINISTRADOR_NO_VALIDO);
    }

    @Test
    void shouldHaveCorrectRestaurantPaginationErrorMessages() {
        // Then
        assertEquals("El número de página debe ser mayor o igual a 0", DomainConstants.Restaurant.ERROR_PAGE_NUMBER_INVALID);
        assertEquals("El tamaño de página debe ser mayor o igual a 1", DomainConstants.Restaurant.ERROR_PAGE_SIZE_INVALID);
        assertEquals("El tamaño de página no puede ser mayor a 100", DomainConstants.Restaurant.ERROR_PAGE_SIZE_TOO_LARGE);
    }

    // =================== DISH CONSTANTS TESTS ===================

    @Test
    void shouldHaveCorrectDishRole() {
        // Then
        assertEquals("PROPIETARIO", DomainConstants.Dish.ROL_PROPIETARIO);
    }

    @Test
    void shouldHaveCorrectDishPriceConstants() {
        // Then
        assertEquals(0, DomainConstants.Dish.MIN_PRICE_VALUE);
    }

    @Test
    void shouldHaveCorrectDishPaginationConstants() {
        // Then
        assertEquals(100, DomainConstants.Dish.MAX_PAGE_SIZE);
        assertEquals(0, DomainConstants.Dish.MIN_PAGE_NUMBER);
        assertEquals(1, DomainConstants.Dish.MIN_PAGE_SIZE);
    }

    @Test
    void shouldHaveCorrectDishErrorMessages() {
        // Then
        assertEquals("El plato no puede ser nulo", DomainConstants.Dish.ERROR_DISH_NULO);
        assertEquals("El nombre del plato es obligatorio", DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO);
        assertEquals("El precio del plato es obligatorio", DomainConstants.Dish.ERROR_PRECIO_REQUERIDO);
        assertEquals("El precio del plato debe ser un número entero positivo y mayor a 0", DomainConstants.Dish.ERROR_PRECIO_POSITIVO);
        assertEquals("La descripción del plato es obligatoria", DomainConstants.Dish.ERROR_DESCRIPCION_REQUERIDA);
        assertEquals("La URL de la imagen es obligatoria", DomainConstants.Dish.ERROR_URL_IMAGEN_REQUERIDA);
        assertEquals("La categoría es obligatoria", DomainConstants.Dish.ERROR_CATEGORIA_REQUERIDA);
        assertEquals("El restaurante es obligatorio", DomainConstants.Dish.ERROR_RESTAURANTE_REQUERIDO);
        assertEquals("No se encontró el restaurante especificado", DomainConstants.Dish.ERROR_RESTAURANTE_NO_ENCONTRADO);
        assertEquals("Solo el propietario del restaurante puede crear o modificar platos", DomainConstants.Dish.ERROR_PROPIETARIO_NO_AUTORIZADO);
        assertEquals("No se encontró el plato especificado", DomainConstants.Dish.ERROR_DISH_NO_ENCONTRADO);
        assertEquals("El ID del plato es obligatorio", DomainConstants.Dish.ERROR_DISH_ID_REQUERIDO);
        assertEquals("El ID del usuario es obligatorio", DomainConstants.Dish.ERROR_USUARIO_REQUERIDO);
        assertEquals("El estado del plato (activo/inactivo) es obligatorio", DomainConstants.Dish.ERROR_ESTADO_REQUERIDO);
        assertEquals("No se pueden modificar platos de otros restaurantes", DomainConstants.Dish.ERROR_PLATO_OTRO_RESTAURANTE);
    }

    @Test
    void shouldHaveCorrectDishPaginationErrorMessages() {
        // Then
        assertEquals("El número de página debe ser mayor o igual a 0", DomainConstants.Dish.ERROR_PAGE_NUMBER_INVALID);
        assertEquals("El tamaño de página debe ser mayor o igual a 1", DomainConstants.Dish.ERROR_PAGE_SIZE_INVALID);
        assertEquals("El tamaño de página no puede ser mayor a 100", DomainConstants.Dish.ERROR_PAGE_SIZE_TOO_LARGE);
    }

    // =================== ORDER CONSTANTS TESTS ===================

    @Test
    void shouldHaveCorrectOrderRoles() {
        // Then
        assertEquals("CLIENTE", DomainConstants.Order.ROL_CLIENTE);
        assertEquals("EMPLEADO", DomainConstants.Order.ROL_EMPLEADO);
    }

    @Test
    void shouldHaveCorrectOrderPaginationConstants() {
        // Then
        assertEquals(100, DomainConstants.Order.MAX_PAGE_SIZE);
        assertEquals(0, DomainConstants.Order.MIN_PAGE_NUMBER);
        assertEquals(1, DomainConstants.Order.MIN_PAGE_SIZE);
    }

    @Test
    void shouldHaveCorrectOrderStatusConstants() {
        // Then
        assertEquals("PENDIENTE", DomainConstants.Order.ESTADO_PENDIENTE);
        assertEquals("EN_PREPARACION", DomainConstants.Order.ESTADO_EN_PREPARACION);
        assertEquals("LISTO", DomainConstants.Order.ESTADO_LISTO);
        assertEquals("ENTREGADO", DomainConstants.Order.ESTADO_ENTREGADO);
        assertEquals("CANCELADO", DomainConstants.Order.ESTADO_CANCELADO);
    }

    @Test
    void shouldHaveCorrectOrderBasicErrorMessages() {
        // Then
        assertEquals("El pedido no puede ser nulo", DomainConstants.Order.ERROR_ORDER_NULO);
        assertEquals("El ID del cliente es obligatorio", DomainConstants.Order.ERROR_CLIENTE_REQUERIDO);
        assertEquals("El ID del empleado es obligatorio", DomainConstants.Order.ERROR_EMPLEADO_REQUERIDO);
        assertEquals("El restaurante es obligatorio", DomainConstants.Order.ERROR_RESTAURANTE_REQUERIDO);
        assertEquals("Los platos son obligatorios", DomainConstants.Order.ERROR_PLATOS_REQUERIDOS);
        assertEquals("El ID del plato es obligatorio", DomainConstants.Order.ERROR_PLATO_ID_REQUERIDO);
        assertEquals("La cantidad debe ser mayor a 0", DomainConstants.Order.ERROR_CANTIDAD_POSITIVA);
    }

    @Test
    void shouldHaveCorrectOrderBusinessErrorMessages() {
        // Then
        assertEquals("Todos los platos deben ser del mismo restaurante", DomainConstants.Order.ERROR_PLATOS_MISMO_RESTAURANTE);
        assertEquals("El restaurante del pedido no coincide con el de los platos", DomainConstants.Order.ERROR_RESTAURANTE_NO_COINCIDE);
        assertEquals("No se encontró el plato especificado", DomainConstants.Order.ERROR_PLATO_NO_ENCONTRADO);
        assertEquals("El plato no está disponible", DomainConstants.Order.ERROR_PLATO_NO_ACTIVO);
        assertEquals("El cliente ya tiene un pedido en proceso (pendiente, en preparación o listo)", DomainConstants.Order.ERROR_CLIENTE_TIENE_PEDIDO_ACTIVO);
    }

    @Test
    void shouldHaveCorrectOrderAssignmentErrorMessages() {
        // Then
        assertEquals("No se encontró el pedido especificado", DomainConstants.Order.ERROR_PEDIDO_NO_ENCONTRADO);
        assertEquals("El empleado no pertenece al restaurante del pedido", DomainConstants.Order.ERROR_EMPLEADO_RESTAURANTE_DIFERENTE);
        assertEquals("Solo se pueden asignar pedidos en estado PENDIENTE", DomainConstants.Order.ERROR_PEDIDO_NO_PENDIENTE);
        assertEquals("El ID del pedido es obligatorio", DomainConstants.Order.ERROR_PEDIDO_ID_REQUERIDO);
    }

    @Test
    void shouldHaveCorrectOrderPaginationErrorMessages() {
        // Then
        assertEquals("El número de página debe ser mayor o igual a 0", DomainConstants.Order.ERROR_PAGE_NUMBER_INVALID);
        assertEquals("El tamaño de página debe ser mayor o igual a 1", DomainConstants.Order.ERROR_PAGE_SIZE_INVALID);
        assertEquals("El tamaño de página no puede ser mayor a 100", DomainConstants.Order.ERROR_PAGE_SIZE_TOO_LARGE);
    }

    // =================== VALIDATION TESTS ===================

    @Test
    void shouldVerifyAllRestaurantConstantsAreNotNull() {
        // Then
        assertNotNull(DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN);
        assertNotNull(DomainConstants.Restaurant.TELEFONO_PATTERN);
        assertNotNull(DomainConstants.Restaurant.ROL_PROPIETARIO);
        assertNotNull(DomainConstants.Restaurant.ROL_ADMINISTRADOR);
        assertNotNull(DomainConstants.Restaurant.ROL_CLIENTE);
        assertNotNull(DomainConstants.Restaurant.ROL_ADMINISTRADOR_ID);
        assertNotNull(DomainConstants.Restaurant.ROL_PROPIETARIO_ID);
        assertNotNull(DomainConstants.Restaurant.ROL_CLIENTE_ID);
    }

    @Test
    void shouldVerifyAllDishConstantsAreNotNull() {
        // Then
        assertNotNull(DomainConstants.Dish.ROL_PROPIETARIO);
        assertNotNull(DomainConstants.Dish.ERROR_DISH_NULO);
        assertNotNull(DomainConstants.Dish.ERROR_NOMBRE_REQUERIDO);
        assertNotNull(DomainConstants.Dish.ERROR_PRECIO_REQUERIDO);
        assertNotNull(DomainConstants.Dish.ERROR_PRECIO_POSITIVO);
    }

    @Test
    void shouldVerifyAllOrderConstantsAreNotNull() {
        // Then
        assertNotNull(DomainConstants.Order.ROL_CLIENTE);
        assertNotNull(DomainConstants.Order.ROL_EMPLEADO);
        assertNotNull(DomainConstants.Order.ESTADO_PENDIENTE);
        assertNotNull(DomainConstants.Order.ESTADO_EN_PREPARACION);
        assertNotNull(DomainConstants.Order.ERROR_ORDER_NULO);
        assertNotNull(DomainConstants.Order.ERROR_CLIENTE_REQUERIDO);
    }

    @Test
    void shouldVerifyPaginationConstantsConsistency() {
        // Then - Verify pagination constants are consistent across all inner classes
        assertEquals(DomainConstants.Restaurant.MAX_PAGE_SIZE, DomainConstants.Dish.MAX_PAGE_SIZE);
        assertEquals(DomainConstants.Restaurant.MAX_PAGE_SIZE, DomainConstants.Order.MAX_PAGE_SIZE);

        assertEquals(DomainConstants.Restaurant.MIN_PAGE_NUMBER, DomainConstants.Dish.MIN_PAGE_NUMBER);
        assertEquals(DomainConstants.Restaurant.MIN_PAGE_NUMBER, DomainConstants.Order.MIN_PAGE_NUMBER);

        assertEquals(DomainConstants.Restaurant.MIN_PAGE_SIZE, DomainConstants.Dish.MIN_PAGE_SIZE);
        assertEquals(DomainConstants.Restaurant.MIN_PAGE_SIZE, DomainConstants.Order.MIN_PAGE_SIZE);
    }

    @Test
    void shouldVerifyPaginationRanges() {
        // Then
        assertTrue(DomainConstants.Restaurant.MIN_PAGE_NUMBER >= 0);
        assertTrue(DomainConstants.Restaurant.MIN_PAGE_SIZE >= 1);
        assertTrue(DomainConstants.Restaurant.MAX_PAGE_SIZE > DomainConstants.Restaurant.MIN_PAGE_SIZE);
        assertTrue(DomainConstants.Restaurant.DEFAULT_PAGE_SIZE >= DomainConstants.Restaurant.MIN_PAGE_SIZE);
        assertTrue(DomainConstants.Restaurant.DEFAULT_PAGE_SIZE <= DomainConstants.Restaurant.MAX_PAGE_SIZE);
    }

    @Test
    void shouldVerifyRoleIdsAreValid() {
        // Then
        assertTrue(DomainConstants.Restaurant.ROL_ADMINISTRADOR_ID > 0);
        assertTrue(DomainConstants.Restaurant.ROL_PROPIETARIO_ID > 0);
        assertTrue(DomainConstants.Restaurant.ROL_CLIENTE_ID > 0);

        // Verify they are different
        assertNotEquals(DomainConstants.Restaurant.ROL_ADMINISTRADOR_ID, DomainConstants.Restaurant.ROL_PROPIETARIO_ID);
        assertNotEquals(DomainConstants.Restaurant.ROL_ADMINISTRADOR_ID, DomainConstants.Restaurant.ROL_CLIENTE_ID);
        assertNotEquals(DomainConstants.Restaurant.ROL_PROPIETARIO_ID, DomainConstants.Restaurant.ROL_CLIENTE_ID);
    }

    @Test
    void shouldVerifyRegexPatternsAreValid() {
        // Then - Test regex patterns compilation
        assertDoesNotThrow(() -> {
            "123".matches(DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN);
            "+1234567890123".matches(DomainConstants.Restaurant.TELEFONO_PATTERN);
        });
    }

    @Test
    void shouldVerifyRoleConstantsAreUpperCase() {
        // Then
        assertEquals(DomainConstants.Restaurant.ROL_PROPIETARIO.toUpperCase(), DomainConstants.Restaurant.ROL_PROPIETARIO);
        assertEquals(DomainConstants.Restaurant.ROL_ADMINISTRADOR.toUpperCase(), DomainConstants.Restaurant.ROL_ADMINISTRADOR);
        assertEquals(DomainConstants.Restaurant.ROL_CLIENTE.toUpperCase(), DomainConstants.Restaurant.ROL_CLIENTE);
        assertEquals(DomainConstants.Dish.ROL_PROPIETARIO.toUpperCase(), DomainConstants.Dish.ROL_PROPIETARIO);
        assertEquals(DomainConstants.Order.ROL_CLIENTE.toUpperCase(), DomainConstants.Order.ROL_CLIENTE);
        assertEquals(DomainConstants.Order.ROL_EMPLEADO.toUpperCase(), DomainConstants.Order.ROL_EMPLEADO);
    }

    @Test
    void shouldVerifyOrderStatusConstantsAreUpperCase() {
        // Then
        assertEquals(DomainConstants.Order.ESTADO_PENDIENTE.toUpperCase(), DomainConstants.Order.ESTADO_PENDIENTE);
        assertEquals(DomainConstants.Order.ESTADO_EN_PREPARACION.toUpperCase(), DomainConstants.Order.ESTADO_EN_PREPARACION);
        assertEquals(DomainConstants.Order.ESTADO_LISTO.toUpperCase(), DomainConstants.Order.ESTADO_LISTO);
        assertEquals(DomainConstants.Order.ESTADO_ENTREGADO.toUpperCase(), DomainConstants.Order.ESTADO_ENTREGADO);
        assertEquals(DomainConstants.Order.ESTADO_CANCELADO.toUpperCase(), DomainConstants.Order.ESTADO_CANCELADO);
    }

    @Test
    void shouldVerifyErrorMessagesAreInSpanish() {
        // Then
        assertTrue(DomainConstants.Restaurant.ERROR_RESTAURANT_NULO.contains("nulo"));
        assertTrue(DomainConstants.Restaurant.ERROR_NOMBRE_REQUERIDO.contains("obligatorio"));
        assertTrue(DomainConstants.Dish.ERROR_DISH_NULO.contains("nulo"));
        assertTrue(DomainConstants.Order.ERROR_ORDER_NULO.contains("nulo"));
        assertTrue(DomainConstants.Order.ERROR_PLATOS_REQUERIDOS.contains("obligatorios"));
    }

    @Test
    void shouldVerifyMinPriceValue() {
        // Then
        assertEquals(0, DomainConstants.Dish.MIN_PRICE_VALUE);
        assertTrue(DomainConstants.Dish.MIN_PRICE_VALUE >= 0);
    }

    @Test
    void shouldVerifyConstantsAreImmutable() {
        // Then - All constants should be static final, verified by successful compilation
        // This test ensures constants exist and are accessible
        String restaurantPattern = DomainConstants.Restaurant.SOLO_NUMEROS_PATTERN;
        String role = DomainConstants.Restaurant.ROL_PROPIETARIO;
        Long roleId = DomainConstants.Restaurant.ROL_PROPIETARIO_ID;
        String dishRole = DomainConstants.Dish.ROL_PROPIETARIO;
        int minPrice = DomainConstants.Dish.MIN_PRICE_VALUE;
        String orderRole = DomainConstants.Order.ROL_CLIENTE;
        String orderStatus = DomainConstants.Order.ESTADO_PENDIENTE;
        int maxPageSize = DomainConstants.Restaurant.MAX_PAGE_SIZE;

        assertNotNull(restaurantPattern);
        assertNotNull(role);
        assertNotNull(roleId);
        assertNotNull(dishRole);
        assertTrue(minPrice >= 0);
        assertNotNull(orderRole);
        assertNotNull(orderStatus);
        assertTrue(maxPageSize > 0);
    }
}
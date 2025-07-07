package com.plazoleta.restaurants.adapters.driven.mysql.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class AdapterConstantsTest {

    @Test
    void shouldNotInstantiateAdapterConstants() throws NoSuchMethodException {
        Constructor<AdapterConstants> ctor = AdapterConstants.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateErrorMessages() throws NoSuchMethodException {
        Constructor<AdapterConstants.ErrorMessages> ctor =
                AdapterConstants.ErrorMessages.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateDatabaseColumns() throws NoSuchMethodException {
        Constructor<AdapterConstants.DatabaseColumns> ctor =
                AdapterConstants.DatabaseColumns.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateQueryConstants() throws NoSuchMethodException {
        Constructor<AdapterConstants.QueryConstants> ctor =
                AdapterConstants.QueryConstants.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateValidationMessages() throws NoSuchMethodException {
        Constructor<AdapterConstants.ValidationMessages> ctor =
                AdapterConstants.ValidationMessages.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldNotInstantiateOrderConstants() throws NoSuchMethodException {
        Constructor<AdapterConstants.OrderConstants> ctor =
                AdapterConstants.OrderConstants.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        InvocationTargetException ex = assertThrows(
                InvocationTargetException.class,
                ctor::newInstance
        );
        assertTrue(ex.getCause() instanceof IllegalStateException);
        assertEquals("Clase de constantes", ex.getCause().getMessage());
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
        assertEquals("Ya existe un restaurante con ese NIT",
                     AdapterConstants.ErrorMessages.RESTAURANT_NIT_DUPLICADO);
        assertEquals("Ya existe un restaurante con ese nombre",
                     AdapterConstants.ErrorMessages.RESTAURANT_NOMBRE_DUPLICADO);
        assertEquals("No se encontró el restaurante solicitado",
                     AdapterConstants.ErrorMessages.RESTAURANT_NO_ENCONTRADO);
        assertEquals("No se encontró el propietario solicitado",
                     AdapterConstants.ErrorMessages.PROPIETARIO_NO_ENCONTRADO);
        assertEquals("No se encontró el plato solicitado",
                     AdapterConstants.ErrorMessages.DISH_NO_ENCONTRADO);
        assertEquals("No se encontró el pedido solicitado",
                     AdapterConstants.ErrorMessages.ORDER_NO_ENCONTRADO);
        assertEquals("No se encontró el cliente solicitado",
                     AdapterConstants.ErrorMessages.CLIENTE_NO_ENCONTRADO);
        assertEquals("No se encontró la categoría solicitada",
                     AdapterConstants.ErrorMessages.CATEGORIA_NO_ENCONTRADA);
        assertEquals("El plato no está disponible",
                     AdapterConstants.ErrorMessages.PLATO_NO_ACTIVO);
        assertEquals("Los platos deben ser del mismo restaurante",
                     AdapterConstants.ErrorMessages.PLATOS_RESTAURANTE_DIFERENTE);
        assertEquals("El cliente ya tiene un pedido activo",
                     AdapterConstants.ErrorMessages.CLIENTE_CON_PEDIDO_ACTIVO);
        assertEquals("El empleado no tiene un restaurante asignado",
                     AdapterConstants.ErrorMessages.EMPLEADO_SIN_RESTAURANTE);
    }

    @Test
    void shouldHaveCorrectDatabaseColumns() {
        assertEquals("nombre", AdapterConstants.DatabaseColumns.NOMBRE_COLUMN);
        assertEquals("nit", AdapterConstants.DatabaseColumns.NIT_COLUMN);
        assertEquals("id", AdapterConstants.DatabaseColumns.ID_COLUMN);
        assertEquals("fecha", AdapterConstants.DatabaseColumns.FECHA_COLUMN);
        assertEquals("estado", AdapterConstants.DatabaseColumns.ESTADO_COLUMN);
        assertEquals("id_cliente", AdapterConstants.DatabaseColumns.ID_CLIENTE_COLUMN);
        assertEquals("id_restaurante", AdapterConstants.DatabaseColumns.ID_RESTAURANTE_COLUMN);
        assertEquals("id_plato", AdapterConstants.DatabaseColumns.ID_PLATO_COLUMN);
        assertEquals("id_pedido", AdapterConstants.DatabaseColumns.ID_PEDIDO_COLUMN);
        assertEquals("cantidad", AdapterConstants.DatabaseColumns.CANTIDAD_COLUMN);
        assertEquals("activo", AdapterConstants.DatabaseColumns.ACTIVO_COLUMN);
    }

    @Test
    void shouldHaveCorrectQueryConstants() {
        assertEquals("SELECT o FROM OrderEntity o WHERE o.idCliente = :clientId AND o.estado IN ('PENDIENTE', 'EN_PREPARACION', 'LISTO')",
                     AdapterConstants.QueryConstants.FIND_ACTIVE_ORDER_BY_CLIENT);
        assertEquals("SELECT d FROM DishEntity d WHERE d.idRestaurante = :restaurantId AND d.activo = true AND (:categoryId IS NULL OR d.idCategoria = :categoryId)",
                     AdapterConstants.QueryConstants.FIND_ACTIVE_DISHES_BY_RESTAURANT_AND_CATEGORY);
        assertEquals("SELECT od FROM OrderDishEntity od WHERE od.idPedido = :pedidoId",
                     AdapterConstants.QueryConstants.FIND_ORDERS_BY_PEDIDO_ID);
    }

    @Test
    void shouldHaveCorrectValidationMessages() {
        assertEquals("Número de página inválido",
                     AdapterConstants.ValidationMessages.INVALID_PAGE_NUMBER);
        assertEquals("Tamaño de página inválido",
                     AdapterConstants.ValidationMessages.INVALID_PAGE_SIZE);
        assertEquals("Estado de pedido inválido",
                     AdapterConstants.ValidationMessages.INVALID_ORDER_STATUS);
        assertEquals("Campo obligatorio faltante",
                     AdapterConstants.ValidationMessages.REQUIRED_FIELD_MISSING);
        assertEquals("La cantidad del plato debe ser mayor a 0",
                     AdapterConstants.ValidationMessages.INVALID_DISH_QUANTITY);
        assertEquals("El pedido debe contener al menos un plato",
                     AdapterConstants.ValidationMessages.EMPTY_ORDER);
    }

    @Test
    void shouldHaveCorrectOrderConstants() {
        assertEquals("PENDIENTE", AdapterConstants.OrderConstants.ESTADO_PENDIENTE);
        assertEquals("EN_PREPARACION", AdapterConstants.OrderConstants.ESTADO_EN_PREPARACION);
        assertEquals("LISTO", AdapterConstants.OrderConstants.ESTADO_LISTO);
        assertEquals("ENTREGADO", AdapterConstants.OrderConstants.ESTADO_ENTREGADO);
        assertEquals("CANCELADO", AdapterConstants.OrderConstants.ESTADO_CANCELADO);
    }
}
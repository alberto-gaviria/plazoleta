package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    @Test
    void constructor_DefaultConstructor_ShouldCreateEmptyObject() {
        // Act
        OrderEntity entity = new OrderEntity();

        // Assert
        assertNull(entity.getId());
        assertNull(entity.getIdCliente());
        assertNull(entity.getFecha());
        assertNull(entity.getEstado());
        assertNull(entity.getIdEmpleado());
        assertNull(entity.getIdRestaurante());
        assertNull(entity.getPinSeguridad());
    }

    @Test
    void constructor_FullConstructor_ShouldSetAllValues() {
        // Arrange
        Long id = 1L;
        Long idCliente = 2L;
        LocalDateTime fecha = LocalDateTime.now();
        OrderStatus estado = OrderStatus.PENDIENTE;
        Long idChef = 3L;
        Long idRestaurante = 4L;
        String pinSeguridad = "1234";

        // Act
        OrderEntity entity = new OrderEntity(id, idCliente, fecha, estado, idChef, idRestaurante, pinSeguridad);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals(idCliente, entity.getIdCliente());
        assertEquals(fecha, entity.getFecha());
        assertEquals(estado, entity.getEstado());
        assertEquals(idChef, entity.getIdEmpleado());
        assertEquals(idRestaurante, entity.getIdRestaurante());
        assertEquals(pinSeguridad, entity.getPinSeguridad());
    }

    @Test
    void prePersist_WithNullFecha_ShouldSetCurrentTime() {
        // Arrange
        OrderEntity entity = new OrderEntity();

        // Act
        entity.prePersist();

        // Assert
        assertNotNull(entity.getFecha());
    }

    @Test
    void prePersist_WithNullEstado_ShouldSetPendiente() {
        // Arrange
        OrderEntity entity = new OrderEntity();

        // Act
        entity.prePersist();

        // Assert
        assertEquals(OrderStatus.PENDIENTE, entity.getEstado());
    }

    @Test
    void prePersist_WithExistingValues_ShouldNotOverride() {
        // Arrange
        LocalDateTime existingFecha = LocalDateTime.of(2023, 1, 1, 10, 0);
        OrderStatus existingEstado = OrderStatus.LISTO;
        OrderEntity entity = new OrderEntity();
        entity.setFecha(existingFecha);
        entity.setEstado(existingEstado);

        // Act
        entity.prePersist();

        // Assert
        assertEquals(existingFecha, entity.getFecha());
        assertEquals(existingEstado, entity.getEstado());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Arrange
        OrderEntity entity = new OrderEntity();
        Long id = 1L;
        Long idCliente = 2L;
        LocalDateTime fecha = LocalDateTime.now();
        OrderStatus estado = OrderStatus.EN_PREPARACION;
        Long idChef = 3L;
        Long idRestaurante = 4L;
        String pinSeguridad = "5678";

        // Act
        entity.setId(id);
        entity.setIdCliente(idCliente);
        entity.setFecha(fecha);
        entity.setEstado(estado);
        entity.setIdEmpleado(idChef);
        entity.setIdRestaurante(idRestaurante);
        entity.setPinSeguridad(pinSeguridad);

        // Assert
        assertEquals(id, entity.getId());
        assertEquals(idCliente, entity.getIdCliente());
        assertEquals(fecha, entity.getFecha());
        assertEquals(estado, entity.getEstado());
        assertEquals(idChef, entity.getIdEmpleado());
        assertEquals(idRestaurante, entity.getIdRestaurante());
        assertEquals(pinSeguridad, entity.getPinSeguridad());
    }
}
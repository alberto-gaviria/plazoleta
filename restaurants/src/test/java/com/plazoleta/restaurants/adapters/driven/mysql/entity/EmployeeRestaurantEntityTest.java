package com.plazoleta.restaurants.adapters.driven.mysql.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRestaurantEntityTest {

    private EmployeeRestaurantEntity employeeRestaurantEntity;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        employeeRestaurantEntity = new EmployeeRestaurantEntity();
        testDateTime = LocalDateTime.of(2024, 1, 15, 10, 30, 45);
    }

    @Test
    @DisplayName("Debe crear una instancia con constructor sin argumentos")
    void testNoArgsConstructor() {
        // Given & When
        EmployeeRestaurantEntity entity = new EmployeeRestaurantEntity();

        // Then
        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getIdEmpleado());
        assertNull(entity.getIdRestaurante());
        assertNull(entity.getFechaAsignacion());
    }

    @Test
    @DisplayName("Debe crear una instancia con constructor con todos los argumentos")
    void testAllArgsConstructor() {
        // Given
        Long id = 1L;
        Long idEmpleado = 100L;
        Long idRestaurante = 200L;
        LocalDateTime fechaAsignacion = testDateTime;

        // When
        EmployeeRestaurantEntity entity = new EmployeeRestaurantEntity(
                id, idEmpleado, idRestaurante, fechaAsignacion
        );

        // Then
        assertNotNull(entity);
        assertEquals(id, entity.getId());
        assertEquals(idEmpleado, entity.getIdEmpleado());
        assertEquals(idRestaurante, entity.getIdRestaurante());
        assertEquals(fechaAsignacion, entity.getFechaAsignacion());
    }

    @Test
    @DisplayName("Debe establecer y obtener el ID correctamente")
    void testSetAndGetId() {
        // Given
        Long expectedId = 1L;

        // When
        employeeRestaurantEntity.setId(expectedId);

        // Then
        assertEquals(expectedId, employeeRestaurantEntity.getId());
    }

    @Test
    @DisplayName("Debe establecer y obtener el ID del empleado correctamente")
    void testSetAndGetIdEmpleado() {
        // Given
        Long expectedIdEmpleado = 100L;

        // When
        employeeRestaurantEntity.setIdEmpleado(expectedIdEmpleado);

        // Then
        assertEquals(expectedIdEmpleado, employeeRestaurantEntity.getIdEmpleado());
    }

    @Test
    @DisplayName("Debe establecer y obtener el ID del restaurante correctamente")
    void testSetAndGetIdRestaurante() {
        // Given
        Long expectedIdRestaurante = 200L;

        // When
        employeeRestaurantEntity.setIdRestaurante(expectedIdRestaurante);

        // Then
        assertEquals(expectedIdRestaurante, employeeRestaurantEntity.getIdRestaurante());
    }

    @Test
    @DisplayName("Debe establecer y obtener la fecha de asignación correctamente")
    void testSetAndGetFechaAsignacion() {
        // Given
        LocalDateTime expectedFecha = testDateTime;

        // When
        employeeRestaurantEntity.setFechaAsignacion(expectedFecha);

        // Then
        assertEquals(expectedFecha, employeeRestaurantEntity.getFechaAsignacion());
    }

    @Test
    @DisplayName("Debe establecer la fecha de asignación automáticamente en prePersist cuando es null")
    void testPrePersistSetsDateWhenNull() {
        // Given
        employeeRestaurantEntity.setFechaAsignacion(null);
        LocalDateTime beforePrePersist = LocalDateTime.now();

        // When
        employeeRestaurantEntity.prePersist();

        // Then
        LocalDateTime afterPrePersist = LocalDateTime.now();
        assertNotNull(employeeRestaurantEntity.getFechaAsignacion());
        assertTrue(employeeRestaurantEntity.getFechaAsignacion().isAfter(beforePrePersist) ||
                           employeeRestaurantEntity.getFechaAsignacion().isEqual(beforePrePersist));
        assertTrue(employeeRestaurantEntity.getFechaAsignacion().isBefore(afterPrePersist) ||
                           employeeRestaurantEntity.getFechaAsignacion().isEqual(afterPrePersist));
    }

    @Test
    @DisplayName("No debe modificar la fecha de asignación en prePersist cuando ya tiene valor")
    void testPrePersistDoesNotModifyExistingDate() {
        // Given
        LocalDateTime existingDate = testDateTime;
        employeeRestaurantEntity.setFechaAsignacion(existingDate);

        // When
        employeeRestaurantEntity.prePersist();

        // Then
        assertEquals(existingDate, employeeRestaurantEntity.getFechaAsignacion());
    }

    @Test
    @DisplayName("Debe permitir valores null en todos los campos")
    void testNullValues() {
        // Given & When
        employeeRestaurantEntity.setId(null);
        employeeRestaurantEntity.setIdEmpleado(null);
        employeeRestaurantEntity.setIdRestaurante(null);
        employeeRestaurantEntity.setFechaAsignacion(null);

        // Then
        assertNull(employeeRestaurantEntity.getId());
        assertNull(employeeRestaurantEntity.getIdEmpleado());
        assertNull(employeeRestaurantEntity.getIdRestaurante());
        assertNull(employeeRestaurantEntity.getFechaAsignacion());
    }

    @Test
    @DisplayName("Debe manejar correctamente diferentes tipos de fechas")
    void testDifferentDateTypes() {
        // Given
        LocalDateTime minDate = LocalDateTime.MIN;
        LocalDateTime maxDate = LocalDateTime.MAX;
        LocalDateTime currentDate = LocalDateTime.now();

        // When & Then
        employeeRestaurantEntity.setFechaAsignacion(minDate);
        assertEquals(minDate, employeeRestaurantEntity.getFechaAsignacion());

        employeeRestaurantEntity.setFechaAsignacion(maxDate);
        assertEquals(maxDate, employeeRestaurantEntity.getFechaAsignacion());

        employeeRestaurantEntity.setFechaAsignacion(currentDate);
        assertEquals(currentDate, employeeRestaurantEntity.getFechaAsignacion());
    }

    @Test
    @DisplayName("Debe manejar correctamente números Long grandes")
    void testLargeLongValues() {
        // Given
        Long largeId = Long.MAX_VALUE;
        Long largeIdEmpleado = Long.MAX_VALUE - 1;
        Long largeIdRestaurante = Long.MAX_VALUE - 2;

        // When
        employeeRestaurantEntity.setId(largeId);
        employeeRestaurantEntity.setIdEmpleado(largeIdEmpleado);
        employeeRestaurantEntity.setIdRestaurante(largeIdRestaurante);

        // Then
        assertEquals(largeId, employeeRestaurantEntity.getId());
        assertEquals(largeIdEmpleado, employeeRestaurantEntity.getIdEmpleado());
        assertEquals(largeIdRestaurante, employeeRestaurantEntity.getIdRestaurante());
    }

    @Test
    @DisplayName("Debe verificar que la entidad sea mutable")
    void testEntityMutability() {
        // Given
        Long initialId = 1L;
        Long newId = 2L;

        // When
        employeeRestaurantEntity.setId(initialId);
        assertEquals(initialId, employeeRestaurantEntity.getId());

        employeeRestaurantEntity.setId(newId);

        // Then
        assertEquals(newId, employeeRestaurantEntity.getId());
        assertNotEquals(initialId, employeeRestaurantEntity.getId());
    }

    @Test
    @DisplayName("Debe verificar el comportamiento de prePersist múltiples veces")
    void testMultiplePrePersistCalls() {
        // Given
        employeeRestaurantEntity.setFechaAsignacion(null);

        // When
        employeeRestaurantEntity.prePersist();
        LocalDateTime firstDate = employeeRestaurantEntity.getFechaAsignacion();

        // Simular un pequeño delay para asegurar que el tiempo sea diferente
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        employeeRestaurantEntity.prePersist();
        LocalDateTime secondDate = employeeRestaurantEntity.getFechaAsignacion();

        // Then
        assertNotNull(firstDate);
        assertNotNull(secondDate);
        assertEquals(firstDate, secondDate); // No debe cambiar en la segunda llamada
    }
}
package com.plazoleta.restaurants.adapters.driven.mysql.mapper;

import com.plazoleta.restaurants.adapters.driven.mysql.entity.OrderDishEntity;
import com.plazoleta.restaurants.domain.model.OrderDish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IOrderDishEntityMapper Tests")
class IOrderDishEntityMapperTest {

    private IOrderDishEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IOrderDishEntityMapper.class);
    }

    @Test
    @DisplayName("Should map OrderDishEntity to OrderDish correctly")
    void shouldMapOrderDishEntityToOrderDishCorrectly() {
        // Given
        OrderDishEntity entity = new OrderDishEntity(1L, 100L, 10L, 5);

        // When
        OrderDish result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getIdPedido(), result.getIdPedido());
        assertEquals(entity.getIdPlato(), result.getIdPlato());
        assertEquals(entity.getCantidad(), result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDishEntity with null values")
    void shouldMapOrderDishEntityWithNullValues() {
        // Given
        OrderDishEntity entity = new OrderDishEntity(null, null, null, null);

        // When
        OrderDish result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getIdPedido());
        assertNull(result.getIdPlato());
        assertNull(result.getCantidad());
    }

    @Test
    @DisplayName("Should handle null OrderDishEntity")
    void shouldHandleNullOrderDishEntity() {
        // Given
        OrderDishEntity entity = null;

        // When
        OrderDish result = mapper.toModel(entity);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map OrderDishEntity with zero values")
    void shouldMapOrderDishEntityWithZeroValues() {
        // Given
        OrderDishEntity entity = new OrderDishEntity(0L, 0L, 0L, 0);

        // When
        OrderDish result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertEquals(0L, result.getId());
        assertEquals(0L, result.getIdPedido());
        assertEquals(0L, result.getIdPlato());
        assertEquals(0, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDishEntity with negative values")
    void shouldMapOrderDishEntityWithNegativeValues() {
        // Given
        OrderDishEntity entity = new OrderDishEntity(-1L, -2L, -3L, -4);

        // When
        OrderDish result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertEquals(-1L, result.getId());
        assertEquals(-2L, result.getIdPedido());
        assertEquals(-3L, result.getIdPlato());
        assertEquals(-4, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDishEntity with large values")
    void shouldMapOrderDishEntityWithLargeValues() {
        // Given
        Long largeNumber = Long.MAX_VALUE;
        Integer largeCantidad = Integer.MAX_VALUE;
        OrderDishEntity entity = new OrderDishEntity(largeNumber, largeNumber, largeNumber, largeCantidad);

        // When
        OrderDish result = mapper.toModel(entity);

        // Then
        assertNotNull(result);
        assertEquals(largeNumber, result.getId());
        assertEquals(largeNumber, result.getIdPedido());
        assertEquals(largeNumber, result.getIdPlato());
        assertEquals(largeCantidad, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDish to OrderDishEntity correctly")
    void shouldMapOrderDishToOrderDishEntityCorrectly() {
        // Given
        OrderDish model = new OrderDish(1L, 100L, 10L, 5);

        // When
        OrderDishEntity result = mapper.toEntity(model);

        // Then
        assertNotNull(result);
        assertEquals(model.getId(), result.getId());
        assertEquals(model.getIdPedido(), result.getIdPedido());
        assertEquals(model.getIdPlato(), result.getIdPlato());
        assertEquals(model.getCantidad(), result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDish with null values")
    void shouldMapOrderDishWithNullValues() {
        // Given
        OrderDish model = new OrderDish(null, null, null, null);

        // When
        OrderDishEntity result = mapper.toEntity(model);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getIdPedido());
        assertNull(result.getIdPlato());
        assertNull(result.getCantidad());
    }

    @Test
    @DisplayName("Should handle null OrderDish")
    void shouldHandleNullOrderDish() {
        // Given
        OrderDish model = null;

        // When
        OrderDishEntity result = mapper.toEntity(model);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map OrderDish with zero values")
    void shouldMapOrderDishWithZeroValues() {
        // Given
        OrderDish model = new OrderDish(0L, 0L, 0L, 0);

        // When
        OrderDishEntity result = mapper.toEntity(model);

        // Then
        assertNotNull(result);
        assertEquals(0L, result.getId());
        assertEquals(0L, result.getIdPedido());
        assertEquals(0L, result.getIdPlato());
        assertEquals(0, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDish with negative values")
    void shouldMapOrderDishWithNegativeValues() {
        // Given
        OrderDish model = new OrderDish(-1L, -2L, -3L, -4);

        // When
        OrderDishEntity result = mapper.toEntity(model);

        // Then
        assertNotNull(result);
        assertEquals(-1L, result.getId());
        assertEquals(-2L, result.getIdPedido());
        assertEquals(-3L, result.getIdPlato());
        assertEquals(-4, result.getCantidad());
    }

    @Test
    @DisplayName("Should map OrderDish with large values")
    void shouldMapOrderDishWithLargeValues() {
        // Given
        Long largeNumber = Long.MAX_VALUE;
        Integer largeCantidad = Integer.MAX_VALUE;
        OrderDish model = new OrderDish(largeNumber, largeNumber, largeNumber, largeCantidad);

        // When
        OrderDishEntity result = mapper.toEntity(model);

        // Then
        assertNotNull(result);
        assertEquals(largeNumber, result.getId());
        assertEquals(largeNumber, result.getIdPedido());
        assertEquals(largeNumber, result.getIdPlato());
        assertEquals(largeCantidad, result.getCantidad());
    }

    @Test
    @DisplayName("Should map list of OrderDishEntity to list of OrderDish correctly")
    void shouldMapListOfOrderDishEntityToListOfOrderDishCorrectly() {
        // Given
        List<OrderDishEntity> entities = Arrays.asList(
                new OrderDishEntity(1L, 100L, 10L, 2),
                new OrderDishEntity(2L, 100L, 20L, 3),
                new OrderDishEntity(3L, 100L, 30L, 1)
        );

        // When
        List<OrderDish> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        for (int i = 0; i < entities.size(); i++) {
            OrderDishEntity originalEntity = entities.get(i);
            OrderDish mappedModel = result.get(i);

            assertEquals(originalEntity.getId(), mappedModel.getId());
            assertEquals(originalEntity.getIdPedido(), mappedModel.getIdPedido());
            assertEquals(originalEntity.getIdPlato(), mappedModel.getIdPlato());
            assertEquals(originalEntity.getCantidad(), mappedModel.getCantidad());
        }
    }

    @Test
    @DisplayName("Should map empty list of OrderDishEntity")
    void shouldMapEmptyListOfOrderDishEntity() {
        // Given
        List<OrderDishEntity> entities = Collections.emptyList();

        // When
        List<OrderDish> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should handle null list of OrderDishEntity")
    void shouldHandleNullListOfOrderDishEntity() {
        // Given
        List<OrderDishEntity> entities = null;

        // When
        List<OrderDish> result = mapper.toModelList(entities);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map list of OrderDishEntity with null elements")
    void shouldMapListOfOrderDishEntityWithNullElements() {
        // Given
        List<OrderDishEntity> entities = Arrays.asList(
                new OrderDishEntity(1L, 100L, 10L, 2),
                null,
                new OrderDishEntity(3L, 100L, 30L, 1)
        );

        // When
        List<OrderDish> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertNotNull(result.get(0));
        assertNull(result.get(1));
        assertNotNull(result.get(2));
    }

    @Test
    @DisplayName("Should map list of OrderDish to list of OrderDishEntity correctly")
    void shouldMapListOfOrderDishToListOfOrderDishEntityCorrectly() {
        // Given
        List<OrderDish> models = Arrays.asList(
                new OrderDish(1L, 100L, 10L, 2),
                new OrderDish(2L, 100L, 20L, 3),
                new OrderDish(3L, 100L, 30L, 1)
        );

        // When
        List<OrderDishEntity> result = mapper.toEntityList(models);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        for (int i = 0; i < models.size(); i++) {
            OrderDish originalModel = models.get(i);
            OrderDishEntity mappedEntity = result.get(i);

            assertEquals(originalModel.getId(), mappedEntity.getId());
            assertEquals(originalModel.getIdPedido(), mappedEntity.getIdPedido());
            assertEquals(originalModel.getIdPlato(), mappedEntity.getIdPlato());
            assertEquals(originalModel.getCantidad(), mappedEntity.getCantidad());
        }
    }

    @Test
    @DisplayName("Should map empty list of OrderDish")
    void shouldMapEmptyListOfOrderDish() {
        // Given
        List<OrderDish> models = Collections.emptyList();

        // When
        List<OrderDishEntity> result = mapper.toEntityList(models);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Should handle null list of OrderDish")
    void shouldHandleNullListOfOrderDish() {
        // Given
        List<OrderDish> models = null;

        // When
        List<OrderDishEntity> result = mapper.toEntityList(models);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map list of OrderDish with null elements")
    void shouldMapListOfOrderDishWithNullElements() {
        // Given
        List<OrderDish> models = Arrays.asList(
                new OrderDish(1L, 100L, 10L, 2),
                null,
                new OrderDish(3L, 100L, 30L, 1)
        );

        // When
        List<OrderDishEntity> result = mapper.toEntityList(models);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertNotNull(result.get(0));
        assertNull(result.get(1));
        assertNotNull(result.get(2));
    }

    @Test
    @DisplayName("Should map single element list correctly")
    void shouldMapSingleElementListCorrectly() {
        // Given
        List<OrderDishEntity> entities = Arrays.asList(
                new OrderDishEntity(1L, 100L, 10L, 5)
        );

        // When
        List<OrderDish> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());

        OrderDish mappedModel = result.get(0);
        OrderDishEntity originalEntity = entities.get(0);

        assertEquals(originalEntity.getId(), mappedModel.getId());
        assertEquals(originalEntity.getIdPedido(), mappedModel.getIdPedido());
        assertEquals(originalEntity.getIdPlato(), mappedModel.getIdPlato());
        assertEquals(originalEntity.getCantidad(), mappedModel.getCantidad());
    }

    @Test
    @DisplayName("Should maintain order when mapping lists")
    void shouldMaintainOrderWhenMappingLists() {
        // Given
        List<OrderDishEntity> entities = Arrays.asList(
                new OrderDishEntity(3L, 100L, 30L, 1),
                new OrderDishEntity(1L, 100L, 10L, 2),
                new OrderDishEntity(2L, 100L, 20L, 3)
        );

        // When
        List<OrderDish> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());

        // Verify order is maintained
        assertEquals(3L, result.get(0).getId());
        assertEquals(1L, result.get(1).getId());
        assertEquals(2L, result.get(2).getId());
    }

    @Test
    @DisplayName("Should handle list with mixed valid and edge case values")
    void shouldHandleListWithMixedValidAndEdgeCaseValues() {
        // Given
        List<OrderDishEntity> entities = Arrays.asList(
                new OrderDishEntity(1L, 100L, 10L, 2),
                new OrderDishEntity(0L, 0L, 0L, 0),
                new OrderDishEntity(-1L, -2L, -3L, -4),
                new OrderDishEntity(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, Integer.MAX_VALUE),
                new OrderDishEntity(null, null, null, null)
        );

        // When
        List<OrderDish> result = mapper.toModelList(entities);

        // Then
        assertNotNull(result);
        assertEquals(5, result.size());

        // Verify each mapping
        assertEquals(1L, result.get(0).getId());
        assertEquals(0L, result.get(1).getId());
        assertEquals(-1L, result.get(2).getId());
        assertEquals(Long.MAX_VALUE, result.get(3).getId());
        assertNull(result.get(4).getId());
    }

    @Test
    @DisplayName("Should create constructor with specific values")
    void shouldCreateConstructorWithSpecificValues() {
        // Given
        OrderDish model = new OrderDish(10L, 5);

        // When
        OrderDishEntity result = mapper.toEntity(model);

        // Then
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getIdPedido());
        assertEquals(10L, result.getIdPlato());
        assertEquals(5, result.getCantidad());
    }
}
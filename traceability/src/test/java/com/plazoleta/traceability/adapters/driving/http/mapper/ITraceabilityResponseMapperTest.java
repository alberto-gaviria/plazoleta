package com.plazoleta.traceability.adapters.driving.http.mapper;

import com.plazoleta.traceability.adapters.driving.http.dto.response.GetOrderTraceabilityResponse;
import com.plazoleta.traceability.adapters.driving.http.dto.response.PageResponse;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.util.paged.Page;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ITraceabilityResponseMapperTest {

    private final ITraceabilityResponseMapper mapper = Mappers.getMapper(ITraceabilityResponseMapper.class);

    @Test
    void toResponse_shouldMapFieldsCorrectly() {
        OrderTraceability model = new OrderTraceability();
        model.setId("abc123");
        model.setOrderId(1L);
        model.setTimestamp(LocalDateTime.now());
        model.setPreviousStatus("PENDIENTE");
        model.setNewStatus("EN_PREPARACION");
        model.setEmployeeEmail("empleado@example.com");

        GetOrderTraceabilityResponse response = mapper.toResponse(model);

        assertNotNull(response);
        assertEquals(model.getId(), response.getId());
        assertEquals(model.getOrderId(), response.getOrderId());
        assertEquals(model.getTimestamp(), response.getTimestamp());
        assertEquals(model.getPreviousStatus(), response.getPreviousStatus());
        assertEquals(model.getNewStatus(), response.getNewStatus());
        assertEquals(model.getEmployeeEmail(), response.getEmployeeEmail());
    }

    @Test
    void toResponseList_shouldMapListCorrectly() {
        OrderTraceability model = new OrderTraceability();
        model.setId("id1");
        model.setOrderId(1L);
        model.setTimestamp(LocalDateTime.now());
        model.setPreviousStatus("PENDIENTE");
        model.setNewStatus("LISTO");
        model.setEmployeeEmail("empleado@correo.com");

        List<GetOrderTraceabilityResponse> responses = mapper.toResponseList(List.of(model));

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("id1", responses.get(0).getId());
    }

    @Test
    void toPageResponse_shouldMapPageCorrectly() {
        OrderTraceability model = new OrderTraceability();
        model.setId("id2");
        model.setOrderId(5L);
        model.setTimestamp(LocalDateTime.now());
        model.setPreviousStatus("EN_PREPARACION");
        model.setNewStatus("ENTREGADO");
        model.setEmployeeEmail("correo@ejemplo.com");

        List<OrderTraceability> content = List.of(model);
        int pageNumber = 1;
        int pageSize = 10;
        long totalElements = 50L;

        Page<OrderTraceability> domainPage = new Page<>(content, pageNumber, pageSize, totalElements);

        PageResponse<GetOrderTraceabilityResponse> responsePage = mapper.toPageResponse(domainPage);

        assertNotNull(responsePage);
        assertEquals(pageNumber, responsePage.getPageNumber());
        assertEquals(pageSize, responsePage.getPageSize());
        assertEquals(totalElements, responsePage.getTotalElements());
        assertEquals(5, responsePage.getTotalPages()); // 50 / 10 = 5
        assertTrue(responsePage.isHasNext());
        assertTrue(responsePage.isHasPrevious());
        assertEquals(1, responsePage.getContent().size());
        assertEquals("id2", responsePage.getContent().get(0).getId());
    }

    @Test
    void toPageResponse_shouldHandleEmptyList() {
        Page<OrderTraceability> emptyPage = new Page<>(Collections.emptyList(), 0, 10, 0L);

        PageResponse<GetOrderTraceabilityResponse> response = mapper.toPageResponse(emptyPage);

        assertNotNull(response);
        assertEquals(0, response.getPageNumber());
        assertEquals(10, response.getPageSize());
        assertEquals(0L, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
        assertFalse(response.isHasNext());
        assertFalse(response.isHasPrevious());
        assertTrue(response.getContent().isEmpty());
    }
}

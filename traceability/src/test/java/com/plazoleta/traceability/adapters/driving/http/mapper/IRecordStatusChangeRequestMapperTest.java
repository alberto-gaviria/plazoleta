package com.plazoleta.traceability.adapters.driving.http.mapper;

import com.plazoleta.traceability.adapters.driving.http.dto.request.RecordStatusChangeRequest;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class IRecordStatusChangeRequestMapperTest {

    private final IRecordStatusChangeRequestMapper mapper = Mappers.getMapper(IRecordStatusChangeRequestMapper.class);

    @Test
    void toModel_shouldMapFieldsCorrectly() {
        RecordStatusChangeRequest request = new RecordStatusChangeRequest();
        request.setOrderId(1L);
        request.setClientId(2L);
        request.setClientEmail("cliente@example.com");
        request.setPreviousStatus("PENDIENTE");
        request.setNewStatus("EN_PREPARACION");
        request.setEmployeeId(3L);
        request.setEmployeeEmail("empleado@example.com");

        OrderTraceability model = mapper.toModel(request);

        assertNotNull(model);
        assertEquals(1L, model.getOrderId());
        assertEquals(2L, model.getClientId());
        assertEquals("cliente@example.com", model.getClientEmail());
        assertEquals("PENDIENTE", model.getPreviousStatus());
        assertEquals("EN_PREPARACION", model.getNewStatus());
        assertEquals(3L, model.getEmployeeId());
        assertEquals("empleado@example.com", model.getEmployeeEmail());

        assertNull(model.getId()); // solo si el mapper lo ignora
        assertNotNull(model.getTimestamp()); // si no se ignora o se inicializa automáticamente
    }
}

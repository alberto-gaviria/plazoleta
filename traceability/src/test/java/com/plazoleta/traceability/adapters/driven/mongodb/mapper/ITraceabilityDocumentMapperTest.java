package com.plazoleta.traceability.adapters.driven.mongodb.mapper;

import com.plazoleta.traceability.adapters.driven.mongodb.document.OrderTraceabilityDocument;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ITraceabilityDocumentMapperTest {

    private final ITraceabilityDocumentMapper mapper = Mappers.getMapper(ITraceabilityDocumentMapper.class);

    @Test
    void toModel_shouldMapCorrectly() {
        OrderTraceabilityDocument document = new OrderTraceabilityDocument();
        document.setId("abc123");
        document.setOrderId(1L);
        document.setClientId(2L);
        document.setClientEmail("cliente@example.com");
        document.setEmployeeId(3L);
        document.setEmployeeEmail("empleado@example.com");
        document.setPreviousStatus("PENDIENTE");
        document.setNewStatus("EN_PREPARACION");
        document.setTimestamp(LocalDateTime.now());

        OrderTraceability model = mapper.toModel(document);

        assertNotNull(model);
        assertEquals(document.getId(), model.getId());
        assertEquals(document.getOrderId(), model.getOrderId());
        assertEquals(document.getClientId(), model.getClientId());
        assertEquals(document.getClientEmail(), model.getClientEmail());
        assertEquals(document.getEmployeeId(), model.getEmployeeId());
        assertEquals(document.getEmployeeEmail(), model.getEmployeeEmail());
        assertEquals(document.getPreviousStatus(), model.getPreviousStatus());
        assertEquals(document.getNewStatus(), model.getNewStatus());
        assertEquals(document.getTimestamp(), model.getTimestamp());
    }

    @Test
    void toDocument_shouldMapCorrectly() {
        OrderTraceability model = new OrderTraceability();
        model.setId("xyz789");
        model.setOrderId(10L);
        model.setClientId(20L);
        model.setClientEmail("otrocliente@example.com");
        model.setEmployeeId(30L);
        model.setEmployeeEmail("otroempleado@example.com");
        model.setPreviousStatus("EN_PREPARACION");
        model.setNewStatus("LISTO");
        model.setTimestamp(LocalDateTime.now());

        OrderTraceabilityDocument document = mapper.toDocument(model);

        assertNotNull(document);
        assertEquals(model.getId(), document.getId());
        assertEquals(model.getOrderId(), document.getOrderId());
        assertEquals(model.getClientId(), document.getClientId());
        assertEquals(model.getClientEmail(), document.getClientEmail());
        assertEquals(model.getEmployeeId(), document.getEmployeeId());
        assertEquals(model.getEmployeeEmail(), document.getEmployeeEmail());
        assertEquals(model.getPreviousStatus(), document.getPreviousStatus());
        assertEquals(model.getNewStatus(), document.getNewStatus());
        assertEquals(model.getTimestamp(), document.getTimestamp());
    }

    @Test
    void toModelList_shouldMapListCorrectly() {
        OrderTraceabilityDocument doc1 = new OrderTraceabilityDocument();
        doc1.setOrderId(1L);
        doc1.setClientEmail("a@example.com");

        OrderTraceabilityDocument doc2 = new OrderTraceabilityDocument();
        doc2.setOrderId(2L);
        doc2.setClientEmail("b@example.com");

        List<OrderTraceability> models = mapper.toModelList(List.of(doc1, doc2));

        assertNotNull(models);
        assertEquals(2, models.size());
        assertEquals("a@example.com", models.get(0).getClientEmail());
        assertEquals("b@example.com", models.get(1).getClientEmail());
    }
}

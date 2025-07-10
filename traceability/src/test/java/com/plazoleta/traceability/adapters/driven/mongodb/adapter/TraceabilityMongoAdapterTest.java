package com.plazoleta.traceability.adapters.driven.mongodb.adapter;

import com.plazoleta.traceability.adapters.driven.mongodb.document.OrderTraceabilityDocument;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityDatabaseException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityRetrieveException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilitySaveException;
import com.plazoleta.traceability.adapters.driven.mongodb.mapper.ITraceabilityDocumentMapper;
import com.plazoleta.traceability.adapters.driven.mongodb.repository.IOrderTraceabilityRepository;
import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraceabilityMongoAdapterTest {

    private IOrderTraceabilityRepository repository;
    private ITraceabilityDocumentMapper mapper;
    private TraceabilityMongoAdapter adapter;

    private final OrderTraceability domainObj = new OrderTraceability(); // Agrega datos si tu clase los requiere
    private final OrderTraceabilityDocument docObj = new OrderTraceabilityDocument();

    @BeforeEach
    void setUp() {
        repository = mock(IOrderTraceabilityRepository.class);
        mapper = mock(ITraceabilityDocumentMapper.class);
        adapter = new TraceabilityMongoAdapter(repository, mapper);
    }

    // ---------- saveTraceability ----------

    @Test
    void saveTraceability_success() {
        when(mapper.toDocument(domainObj)).thenReturn(docObj);
        when(repository.save(docObj)).thenReturn(docObj);
        when(mapper.toModel(docObj)).thenReturn(domainObj);

        OrderTraceability result = adapter.saveTraceability(domainObj);

        assertNotNull(result);
        verify(repository).save(docObj);
    }

    @Test
    void saveTraceability_dataAccessException() {
        when(mapper.toDocument(domainObj)).thenThrow(mock(DataAccessException.class));

        assertThrows(TraceabilitySaveException.class, () -> adapter.saveTraceability(domainObj));
    }

    @Test
    void saveTraceability_unexpectedException() {
        when(mapper.toDocument(domainObj)).thenThrow(new RuntimeException("unexpected"));

        assertThrows(TraceabilityDatabaseException.class, () -> adapter.saveTraceability(domainObj));
    }

    // ---------- findByOrderIdAndClientId ----------

    @Test
    void findByOrderIdAndClientId_success() {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").ascending());
        List<OrderTraceabilityDocument> docList = List.of(docObj);
        List<OrderTraceability> modelList = List.of(domainObj);

        PageImpl<OrderTraceabilityDocument> mongoPage = new PageImpl<>(docList, pageable, 1);

        when(repository.findByOrderIdAndClientIdOrderByTimestampAsc(1L, 2L, pageable))
                .thenReturn(mongoPage);
        when(mapper.toModelList(docList)).thenReturn(modelList);

        Page<OrderTraceability> result = adapter.findByOrderIdAndClientId(1L, 2L, page, size);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findByOrderIdAndClientId_dataAccessException() {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").ascending());

        when(repository.findByOrderIdAndClientIdOrderByTimestampAsc(1L, 2L, pageable))
                .thenThrow(mock(DataAccessException.class));

        assertThrows(TraceabilityRetrieveException.class,
                     () -> adapter.findByOrderIdAndClientId(1L, 2L, page, size));
    }

    @Test
    void findByOrderIdAndClientId_unexpectedException() {
        int page = 0, size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").ascending());

        when(repository.findByOrderIdAndClientIdOrderByTimestampAsc(1L, 2L, pageable))
                .thenThrow(new RuntimeException("unexpected"));

        assertThrows(TraceabilityDatabaseException.class,
                     () -> adapter.findByOrderIdAndClientId(1L, 2L, page, size));
    }

    // ---------- existsOrderForClient ----------

    @Test
    void existsOrderForClient_success() {
        when(repository.existsByOrderIdAndClientId(1L, 2L)).thenReturn(true);
        assertTrue(adapter.existsOrderForClient(1L, 2L));
    }

    @Test
    void existsOrderForClient_dataAccessException() {
        when(repository.existsByOrderIdAndClientId(1L, 2L))
                .thenThrow(mock(DataAccessException.class));
        assertThrows(TraceabilityRetrieveException.class,
                     () -> adapter.existsOrderForClient(1L, 2L));
    }

    @Test
    void existsOrderForClient_unexpectedException() {
        when(repository.existsByOrderIdAndClientId(1L, 2L))
                .thenThrow(new RuntimeException("unexpected"));
        assertThrows(TraceabilityDatabaseException.class,
                     () -> adapter.existsOrderForClient(1L, 2L));
    }
}

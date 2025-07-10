package com.plazoleta.traceability.domain.usecase;

import com.plazoleta.traceability.domain.model.OrderTraceability;
import com.plazoleta.traceability.domain.spi.ITraceabilityPersistencePort;
import com.plazoleta.traceability.domain.util.DomainConstants;
import com.plazoleta.traceability.domain.util.exceptions.InvalidTraceabilityException;
import com.plazoleta.traceability.domain.util.paged.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraceabilityUseCaseTest {

    @Mock
    private ITraceabilityPersistencePort persistencePort;

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --------- Tests para recordOrderStatusChange ---------

    @Test
    void recordOrderStatusChange_successful() {
        traceabilityUseCase.recordOrderStatusChange(
                1L, 2L, "client@example.com",
                "PENDING", "IN_PROGRESS",
                3L, "employee@example.com"
        );

        ArgumentCaptor<OrderTraceability> captor = ArgumentCaptor.forClass(OrderTraceability.class);
        verify(persistencePort).saveTraceability(captor.capture());
        OrderTraceability traceability = captor.getValue();

        assertEquals(1L, traceability.getOrderId());
        assertEquals(2L, traceability.getClientId());
        assertEquals("client@example.com", traceability.getClientEmail());
        assertEquals("PENDING", traceability.getPreviousStatus());
        assertEquals("IN_PROGRESS", traceability.getNewStatus());
        assertEquals(3L, traceability.getEmployeeId());
        assertEquals("employee@example.com", traceability.getEmployeeEmail());
        assertNotNull(traceability.getTimestamp());
    }

    @Test
    void recordOrderStatusChange_nullOrderId_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.recordOrderStatusChange(null, 2L, "client@example.com", "OLD", "NEW", 3L, "emp@example.com"));

        assertEquals(DomainConstants.Traceability.ERROR_ORDER_ID_REQUIRED, ex.getMessage());
    }

    @Test
    void recordOrderStatusChange_nullClientId_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.recordOrderStatusChange(1L, null, "client@example.com", "OLD", "NEW", 3L, "emp@example.com"));

        assertEquals(DomainConstants.Traceability.ERROR_CLIENT_ID_REQUIRED, ex.getMessage());
    }

    @Test
    void recordOrderStatusChange_nullClientEmail_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.recordOrderStatusChange(1L, 2L, null, "OLD", "NEW", 3L, "emp@example.com"));

        assertEquals(DomainConstants.Traceability.ERROR_CLIENT_EMAIL_REQUIRED, ex.getMessage());
    }

    @Test
    void recordOrderStatusChange_emptyClientEmail_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.recordOrderStatusChange(1L, 2L, "   ", "OLD", "NEW", 3L, "emp@example.com"));

        assertEquals(DomainConstants.Traceability.ERROR_CLIENT_EMAIL_REQUIRED, ex.getMessage());
    }

    @Test
    void recordOrderStatusChange_nullNewStatus_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.recordOrderStatusChange(1L, 2L, "client@example.com", "OLD", null, 3L, "emp@example.com"));

        assertEquals(DomainConstants.Traceability.ERROR_NEW_STATUS_REQUIRED, ex.getMessage());
    }

    @Test
    void recordOrderStatusChange_emptyNewStatus_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.recordOrderStatusChange(1L, 2L, "client@example.com", "OLD", "  ", 3L, "emp@example.com"));

        assertEquals(DomainConstants.Traceability.ERROR_NEW_STATUS_REQUIRED, ex.getMessage());
    }

    // --------- Tests para getOrderTraceability ---------

    @Test
    void getOrderTraceability_successful() {
        Page<OrderTraceability> expectedPage = new Page<>();
        when(persistencePort.existsOrderForClient(1L, 2L)).thenReturn(true);
        when(persistencePort.findByOrderIdAndClientId(1L, 2L, 0, 10)).thenReturn(expectedPage);

        Page<OrderTraceability> result = traceabilityUseCase.getOrderTraceability(1L, 2L, 0, 10);

        assertEquals(expectedPage, result);
        verify(persistencePort).existsOrderForClient(1L, 2L);
        verify(persistencePort).findByOrderIdAndClientId(1L, 2L, 0, 10);
    }

    @Test
    void getOrderTraceability_orderNotBelongsToClient_throwsException() {
        when(persistencePort.existsOrderForClient(1L, 2L)).thenReturn(false);

        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.getOrderTraceability(1L, 2L, 0, 10));

        assertEquals(DomainConstants.Traceability.ERROR_ORDER_NOT_BELONGS_TO_CLIENT, ex.getMessage());
    }

    @Test
    void getOrderTraceability_nullOrderId_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.getOrderTraceability(null, 2L, 0, 10));

        assertEquals(DomainConstants.Traceability.ERROR_ORDER_ID_REQUIRED, ex.getMessage());
    }

    @Test
    void getOrderTraceability_nullClientId_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.getOrderTraceability(1L, null, 0, 10));

        assertEquals(DomainConstants.Traceability.ERROR_CLIENT_ID_REQUIRED, ex.getMessage());
    }

    @Test
    void getOrderTraceability_invalidPageNumber_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.getOrderTraceability(1L, 2L, -1, 10));

        assertEquals(DomainConstants.Traceability.ERROR_PAGE_NUMBER_INVALID, ex.getMessage());
    }

    @Test
    void getOrderTraceability_invalidPageSizeTooSmall_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.getOrderTraceability(1L, 2L, 0, 0));

        assertEquals(DomainConstants.Traceability.ERROR_PAGE_SIZE_INVALID, ex.getMessage());
    }

    @Test
    void getOrderTraceability_invalidPageSizeTooLarge_throwsException() {
        InvalidTraceabilityException ex = assertThrows(InvalidTraceabilityException.class,
                                                       () -> traceabilityUseCase.getOrderTraceability(1L, 2L, 0, 101));

        assertEquals(DomainConstants.Traceability.ERROR_PAGE_SIZE_TOO_LARGE, ex.getMessage());
    }
}

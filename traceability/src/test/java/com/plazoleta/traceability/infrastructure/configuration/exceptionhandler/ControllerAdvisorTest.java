package com.plazoleta.traceability.infrastructure.configuration.exceptionhandler;

import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityDatabaseException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityRetrieveException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilitySaveException;
import com.plazoleta.traceability.domain.util.exceptions.InvalidTraceabilityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerAdvisorTest {

    private ControllerAdvisor advisor;

    @BeforeEach
    void setUp() {
        advisor = new ControllerAdvisor();
    }

    @Test
    void handleValidationExceptions_returnsBadRequest() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(
                Collections.singletonList(new FieldError("object", "field", "must not be null"))
        );

        ResponseEntity<ExceptionResponse> response = advisor.handleValidationExceptions(exception);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("must not be null"));
    }

    @Test
    void handleMethodArgumentTypeMismatchException_returnsBadRequest() {
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn("orderId");

        ResponseEntity<ExceptionResponse> response = advisor.handleMethodArgumentTypeMismatchException(exception);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("orderId"));
    }

    @Test
    void handleInvalidTraceabilityException_returnsNotFound() {
        InvalidTraceabilityException exception = new InvalidTraceabilityException("Not found");

        ResponseEntity<ExceptionResponse> response = advisor.handleInvalidTraceabilityException(exception);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void handleTraceabilitySaveException_returnsServerError() {
        TraceabilitySaveException exception = new TraceabilitySaveException("DB error");

        ResponseEntity<ExceptionResponse> response = advisor.handleTraceabilitySaveException(exception);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error guardando la trazabilidad del pedido", response.getBody().getMessage());
    }

    @Test
    void handleTraceabilityRetrieveException_returnsNotFound() {
        TraceabilityRetrieveException exception = new TraceabilityRetrieveException("Not found");

        ResponseEntity<ExceptionResponse> response = advisor.handleTraceabilityRetrieveException(exception);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No se pudo consultar la trazabilidad del pedido", response.getBody().getMessage());
    }

    @Test
    void handleTraceabilityDatabaseException_returnsServerError() {
        TraceabilityDatabaseException exception = new TraceabilityDatabaseException("DB down");

        ResponseEntity<ExceptionResponse> response = advisor.handleTraceabilityDatabaseException(exception);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error interno en el sistema de trazabilidad", response.getBody().getMessage());
    }

    @Test
    void handleAccessDeniedException_returnsForbidden() {
        AccessDeniedException exception = new AccessDeniedException("Forbidden");

        ResponseEntity<ExceptionResponse> response = advisor.handleAccessDeniedException(exception);

        assertEquals(403, response.getStatusCodeValue());
        assertEquals("Acceso denegado - No tiene permisos para realizar esta acción", response.getBody().getMessage());
    }

    @Test
    void handleIllegalArgumentException_returnsBadRequest() {
        IllegalArgumentException exception = new IllegalArgumentException("Invalid arg");

        ResponseEntity<ExceptionResponse> response = advisor.handleIllegalArgumentException(exception);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid arg", response.getBody().getMessage());
    }

    @Test
    void handleGeneralException_returnsServerError() {
        Exception exception = new Exception("Internal error");

        ResponseEntity<ExceptionResponse> response = advisor.handleGeneralException(exception);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error interno del servidor", response.getBody().getMessage());
    }
}

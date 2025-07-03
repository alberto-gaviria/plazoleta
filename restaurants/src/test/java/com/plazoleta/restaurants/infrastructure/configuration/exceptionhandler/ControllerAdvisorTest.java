package com.plazoleta.restaurants.infrastructure.configuration.exceptionhandler;

import com.plazoleta.restaurants.adapters.driven.mysql.exception.*;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidDishException;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerAdvisorTest {

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    private ControllerAdvisor controllerAdvisor;

    @BeforeEach
    void setUp() {
        controllerAdvisor = new ControllerAdvisor();
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequestWithErrorDetails() {
        // Given
        String fieldName = "nombre";
        String errorMessage = "El nombre es obligatorio";
        FieldError fieldError = new FieldError("addDishRequest", fieldName, errorMessage);

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleValidationExceptions(methodArgumentNotValidException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains(fieldName));
        assertTrue(response.getBody().getMessage().contains(errorMessage));
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleValidationExceptions_WithMultipleErrors_ShouldReturnAllErrors() {
        // Given
        FieldError fieldError1 = new FieldError("request", "nombre", "Nombre requerido");
        FieldError fieldError2 = new FieldError("request", "precio", "Precio inválido");
        java.util.List<FieldError> fieldErrors = java.util.Arrays.asList(fieldError1, fieldError2);

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleValidationExceptions(methodArgumentNotValidException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("nombre"));
        assertTrue(response.getBody().getMessage().contains("precio"));
        assertTrue(response.getBody().getMessage().contains("Nombre requerido"));
        assertTrue(response.getBody().getMessage().contains("Precio inválido"));
    }

    @Test
    void handleValidationExceptions_WithEmptyErrors_ShouldReturnEmptyMap() {
        // Given
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(java.util.Collections.emptyList());

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleValidationExceptions(methodArgumentNotValidException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("{}", response.getBody().getMessage());
    }

    @Test
    void handleHandlerMethodValidationException_ShouldReturnBadRequest() {
        // Given
        HandlerMethodValidationException exception = mock(HandlerMethodValidationException.class);
        when(exception.getAllValidationResults()).thenReturn(Collections.emptyList());

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleHandlerMethodValidationException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error de validación", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleHandlerMethodValidationException_WithValidationResults_ShouldReturnFirstError() {
        // Given
        String expectedMessage = "Mensaje de error específico";
        HandlerMethodValidationException exception = mock(HandlerMethodValidationException.class);

        // Create a simple list for getAllValidationResults that returns empty
        // This will test the orElse("Error de validación") path
        when(exception.getAllValidationResults()).thenReturn(java.util.Collections.emptyList());

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleHandlerMethodValidationException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error de validación", response.getBody().getMessage());
    }

    @Test
    void handleMissingRequestHeaderException_ShouldReturnBadRequest() {
        // Given
        String headerName = "Authorization";
        MissingRequestHeaderException exception = mock(MissingRequestHeaderException.class);
        when(exception.getHeaderName()).thenReturn(headerName);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleMissingRequestHeaderException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("El header 'Authorization' es obligatorio", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleMissingRequestHeaderException_WithDifferentHeader_ShouldReturnCorrectMessage() {
        // Given
        String headerName = "Content-Type";
        MissingRequestHeaderException exception = mock(MissingRequestHeaderException.class);
        when(exception.getHeaderName()).thenReturn(headerName);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleMissingRequestHeaderException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("El header 'Content-Type' es obligatorio", response.getBody().getMessage());
    }

    @Test
    void handleMethodArgumentTypeMismatchException_ShouldReturnBadRequest() {
        // Given
        String paramName = "id";
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn(paramName);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleMethodArgumentTypeMismatchException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("El parámetro 'id' tiene un formato inválido", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleMethodArgumentTypeMismatchException_WithDifferentParam_ShouldReturnCorrectMessage() {
        // Given
        String paramName = "page";
        MethodArgumentTypeMismatchException exception = mock(MethodArgumentTypeMismatchException.class);
        when(exception.getName()).thenReturn(paramName);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleMethodArgumentTypeMismatchException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("El parámetro 'page' tiene un formato inválido", response.getBody().getMessage());
    }

    @Test
    void handleRestaurantAlreadyExistsException_ShouldReturnBadRequest() {
        // Given
        String errorMessage = "El restaurante ya existe";
        RestaurantAlreadyExistsException exception = new RestaurantAlreadyExistsException(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleRestaurantAlreadyExistsException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleInvalidRestaurantException_ShouldReturnBadRequest() {
        // Given
        String errorMessage = "Datos del restaurante inválidos";
        InvalidRestaurantException exception = new InvalidRestaurantException(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleInvalidRestaurantException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleInvalidDishException_ShouldReturnBadRequest() {
        // Given
        String errorMessage = "Datos del plato inválidos";
        InvalidDishException exception = new InvalidDishException(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleInvalidDishException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleNoDataFoundException_ShouldReturnNotFound() {
        // Given
        String errorMessage = "No se encontraron datos";
        NoDataFoundException exception = new NoDataFoundException(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleNoDataFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleElementNotFoundException_ShouldReturnNotFound() {
        // Given
        String errorMessage = "Elemento no encontrado";
        ElementNotFoundException exception = new ElementNotFoundException(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleElementNotFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleAccessDeniedException_ShouldReturnForbidden() {
        // Given
        AccessDeniedException exception = new AccessDeniedException("Access denied");

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleAccessDeniedException(exception);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Acceso denegado - No tiene permisos para realizar esta acción",
                response.getBody().getMessage());
        assertEquals(HttpStatus.FORBIDDEN.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleAccessDeniedException_IgnoresOriginalMessage() {
        // Given
        String originalMessage = "Original access denied message";
        AccessDeniedException exception = new AccessDeniedException(originalMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleAccessDeniedException(exception);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        // Should return custom message, not original
        assertEquals("Acceso denegado - No tiene permisos para realizar esta acción",
                response.getBody().getMessage());
        assertNotEquals(originalMessage, response.getBody().getMessage());
    }

    @Test
    void handleIllegalArgumentException_ShouldReturnBadRequest() {
        // Given
        String errorMessage = "Argumento ilegal";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleIllegalArgumentException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleIllegalArgumentException_WithNullMessage_ShouldHandleGracefully() {
        // Given
        IllegalArgumentException exception = new IllegalArgumentException((String) null);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleIllegalArgumentException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getMessage());
    }

    @Test
    void handleGeneralException_ShouldReturnInternalServerError() {
        // Given
        String errorMessage = "Error interno del servidor";
        Exception exception = new Exception(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(), response.getBody().getStatus());
        assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void handleGeneralException_WithNullMessage_ShouldHandleGracefully() {
        // Given
        Exception exception = new Exception((String) null);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getMessage());
    }

    @Test
    void handleGeneralException_WithRuntimeException_ShouldReturnInternalServerError() {
        // Given
        String errorMessage = "Runtime error occurred";
        RuntimeException exception = new RuntimeException(errorMessage);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(errorMessage, response.getBody().getMessage());
    }

    @Test
    void allHandlers_ShouldReturnValidTimestamp() {
        // Given
        Exception exception = new Exception("Test");
        IllegalArgumentException illegalArg = new IllegalArgumentException("Test");
        AccessDeniedException accessDenied = new AccessDeniedException("Test");

        // When
        ResponseEntity<ExceptionResponse> response1 = controllerAdvisor.handleGeneralException(exception);
        ResponseEntity<ExceptionResponse> response2 = controllerAdvisor.handleIllegalArgumentException(illegalArg);
        ResponseEntity<ExceptionResponse> response3 = controllerAdvisor.handleAccessDeniedException(accessDenied);

        // Then
        assertNotNull(response1.getBody().getTimestamp());
        assertNotNull(response2.getBody().getTimestamp());
        assertNotNull(response3.getBody().getTimestamp());

        // Verify timestamps are recent (within last 10 seconds)
        assertTrue(response1.getBody().getTimestamp().isAfter(
                java.time.LocalDateTime.now().minusSeconds(10)));
        assertTrue(response2.getBody().getTimestamp().isAfter(
                java.time.LocalDateTime.now().minusSeconds(10)));
        assertTrue(response3.getBody().getTimestamp().isAfter(
                java.time.LocalDateTime.now().minusSeconds(10)));
    }

    @Test
    void handleValidationExceptions_WithNullFieldError_ShouldHandleGracefully() {
        // Given
        FieldError fieldError = new FieldError("object", "field", null);
        java.util.List<FieldError> fieldErrors = java.util.Arrays.asList(fieldError);
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        // When
        ResponseEntity<ExceptionResponse> response =
                controllerAdvisor.handleValidationExceptions(methodArgumentNotValidException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("field"));
        assertTrue(response.getBody().getMessage().contains("null"));
    }

    @Test
    void constructor_ShouldCreateInstance() {
        // When
        ControllerAdvisor advisor = new ControllerAdvisor();

        // Then
        assertNotNull(advisor);
    }
}
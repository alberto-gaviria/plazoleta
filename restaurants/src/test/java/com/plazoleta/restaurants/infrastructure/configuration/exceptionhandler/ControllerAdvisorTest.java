package com.plazoleta.restaurants.infrastructure.configuration.exceptionhandler;

import com.plazoleta.restaurants.adapters.driven.mysql.exception.ElementNotFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.NoDataFoundException;
import com.plazoleta.restaurants.adapters.driven.mysql.exception.RestaurantAlreadyExistsException;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidDishException;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}
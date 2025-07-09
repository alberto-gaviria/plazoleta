package com.plazoleta.messaging.infrastructure.configuration.exceptionhandler;

import com.plazoleta.messaging.domain.util.exceptions.NotificationException;
import com.plazoleta.messaging.domain.util.exceptions.TwilioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ControllerAdvisorTest {

    @InjectMocks
    private ControllerAdvisor controllerAdvisor;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Test
    void handleNotificationException_ShouldReturnBadRequest() {
        // Given
        NotificationException exception = new NotificationException("Test error");

        // When
        ResponseEntity<ControllerAdvisor.ErrorResponse> response =
                controllerAdvisor.handleNotificationException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Test error", response.getBody().getMessage());
        assertEquals("NOTIFICATION_ERROR", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void handleTwilioException_ShouldReturnInternalServerError() {
        // Given
        TwilioException exception = new TwilioException("Twilio error");

        // When
        ResponseEntity<ControllerAdvisor.ErrorResponse> response =
                controllerAdvisor.handleTwilioException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error en el servicio de mensajería", response.getBody().getMessage());
        assertEquals("TWILIO_ERROR", response.getBody().getError());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    void handleValidationExceptions_ShouldReturnBadRequest() {
        // Given
        FieldError fieldError = new FieldError("object", "field", "error message");
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(fieldError));

        // When
        ResponseEntity<ControllerAdvisor.ErrorResponse> response =
                controllerAdvisor.handleValidationExceptions(methodArgumentNotValidException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Errores de validación"));
        assertEquals("VALIDATION_ERROR", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void handleGenericException_ShouldReturnInternalServerError() {
        // Given
        Exception exception = new RuntimeException("Generic error");

        // When
        ResponseEntity<ControllerAdvisor.ErrorResponse> response =
                controllerAdvisor.handleGenericException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Ha ocurrido un error interno en el servidor", response.getBody().getMessage());
        assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getError());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    void errorResponse_ShouldCreateCorrectly() {
        // When
        ControllerAdvisor.ErrorResponse errorResponse =
                new ControllerAdvisor.ErrorResponse("Test message", "TEST_ERROR", 400);

        // Then
        assertEquals("Test message", errorResponse.getMessage());
        assertEquals("TEST_ERROR", errorResponse.getError());
        assertEquals(400, errorResponse.getStatus());
        assertNotNull(errorResponse.getTimestamp());
    }

    @Test
    void errorResponse_SettersAndGetters_ShouldWorkCorrectly() {
        // Given
        ControllerAdvisor.ErrorResponse errorResponse =
                new ControllerAdvisor.ErrorResponse("Test", "ERROR", 500);

        // When
        errorResponse.setMessage("New message");
        errorResponse.setError("NEW_ERROR");
        errorResponse.setStatus(404);

        // Then
        assertEquals("New message", errorResponse.getMessage());
        assertEquals("NEW_ERROR", errorResponse.getError());
        assertEquals(404, errorResponse.getStatus());
    }
}
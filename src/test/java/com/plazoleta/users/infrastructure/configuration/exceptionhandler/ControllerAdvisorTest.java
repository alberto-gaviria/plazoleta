package com.plazoleta.users.infrastructure.configuration.exceptionhandler;

import com.plazoleta.users.adapters.driven.mysql.exception.*;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
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

import java.util.List;

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
    void handleValidationExceptions_ShouldReturnBadRequest() {
        // Given
        FieldError fieldError = new FieldError("addUsuarioRequest", "nombre", "El nombre es obligatorio");
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleValidationExceptions(methodArgumentNotValidException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getMessage().contains("nombre"));
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void handleUsuarioAlreadyExistsException_ShouldReturnBadRequest() {
        // Given
        String message = "Ya existe un usuario con ese correo";
        UserAlreadyExistsException exception = new UserAlreadyExistsException(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleUsuarioAlreadyExistsException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void handleInvalidUsuarioException_ShouldReturnBadRequest() {
        // Given
        String message = "El usuario no es válido";
        InvalidUsuarioException exception = new InvalidUsuarioException(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleInvalidUsuarioException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void handleInvalidAgeException_ShouldReturnBadRequest() {
        // Given
        String message = "El usuario debe ser mayor de edad";
        InvalidAgeException exception = new InvalidAgeException(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleInvalidAgeException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void handleNoDataFoundException_ShouldReturnNotFound() {
        // Given
        String message = "No se encontraron datos";
        NoDataFoundException exception = new NoDataFoundException(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleNoDataFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().getStatus());
    }

    @Test
    void handleElementNotFoundException_ShouldReturnNotFound() {
        // Given
        String message = "Elemento no encontrado";
        ElementNotFoundException exception = new ElementNotFoundException(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleElementNotFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().getStatus());
    }

    @Test
    void handleIllegalArgumentException_ShouldReturnBadRequest() {
        // Given
        String message = "Argumento ilegal";
        IllegalArgumentException exception = new IllegalArgumentException(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleIllegalArgumentException(exception);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void handleGeneralException_ShouldReturnInternalServerError() {
        // Given
        String message = "Error interno";
        Exception exception = new Exception(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleGeneralException(exception);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(), response.getBody().getStatus());
    }

    @Test
    void handleRoleNotFoundException_ShouldReturnNotFound() {
        // Given
        String message = "Rol no encontrado";
        RoleNotFoundException exception = new RoleNotFoundException(message);

        // When
        ResponseEntity<ExceptionResponse> response = controllerAdvisor.handleRoleNotFoundException(exception);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(message, response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().getStatus());
    }
}
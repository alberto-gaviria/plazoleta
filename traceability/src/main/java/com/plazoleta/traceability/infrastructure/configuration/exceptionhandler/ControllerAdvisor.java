package com.plazoleta.traceability.infrastructure.configuration.exceptionhandler;

import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityDatabaseException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilityRetrieveException;
import com.plazoleta.traceability.adapters.driven.mongodb.exception.TraceabilitySaveException;
import com.plazoleta.traceability.domain.util.exceptions.InvalidTraceabilityException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                                                                      errors.put(error.getField(), error.getDefaultMessage())
        );

        logger.warn("Errores de validación: {}", errors);
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errors.toString(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionResponse> handleHandlerMethodValidationException(HandlerMethodValidationException exception) {

        String errorMessage = exception.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        if (errorMessage.isEmpty()) {
            errorMessage = "Error de validación";
        }

        logger.warn("Error de validación de método: {}", errorMessage);
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errorMessage,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String paramName = exception.getName();
        String errorMessage = "El parámetro '" + paramName + "' tiene un formato inválido";

        logger.warn("Error de tipo de argumento: {}", errorMessage);
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errorMessage,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidTraceabilityException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidTraceabilityException(InvalidTraceabilityException ex) {
        logger.warn("Excepción de trazabilidad inválida: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(TraceabilitySaveException.class)
    public ResponseEntity<ExceptionResponse> handleTraceabilitySaveException(TraceabilitySaveException ex) {
        logger.error("Error guardando trazabilidad: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(
                "Error guardando la trazabilidad del pedido",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(TraceabilityRetrieveException.class)
    public ResponseEntity<ExceptionResponse> handleTraceabilityRetrieveException(TraceabilityRetrieveException ex) {
        logger.warn("Error consultando trazabilidad: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                "No se pudo consultar la trazabilidad del pedido",
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(TraceabilityDatabaseException.class)
    public ResponseEntity<ExceptionResponse> handleTraceabilityDatabaseException(TraceabilityDatabaseException ex) {
        logger.error("Error de base de datos en trazabilidad: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(
                "Error interno en el sistema de trazabilidad",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        logger.warn("Acceso denegado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                "Acceso denegado - No tiene permisos para realizar esta acción",
                HttpStatus.FORBIDDEN.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Argumento ilegal: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex) {
        logger.error("Error inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(
                "Error interno del servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now()));
    }
}
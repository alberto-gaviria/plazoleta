package com.plazoleta.restaurants.infrastructure.configuration.exceptionhandler;

import com.plazoleta.restaurants.adapters.driven.mysql.exception.*;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidRestaurantException;
import com.plazoleta.restaurants.domain.util.exceptions.InvalidDishException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    // Para validaciones de @RequestBody (Restaurant - Spring Boot < 3.2)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errors.toString(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    // Para validaciones de @RequestBody + @RequestHeader (Dish - Spring Boot >= 3.2)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionResponse> handleHandlerMethodValidationException(HandlerMethodValidationException exception) {
        String errorMessage = exception.getAllValidationResults()
                .stream()
                .flatMap(result -> result.getResolvableErrors().stream())
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Error de validación");

        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errorMessage,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    // Para headers faltantes (@RequestHeader sin valor)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ExceptionResponse> handleMissingRequestHeaderException(MissingRequestHeaderException exception) {
        String headerName = exception.getHeaderName();
        String errorMessage = "El header '" + headerName + "' es obligatorio";

        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errorMessage,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    // Para headers con formato inválido (ej: "invalid" cuando se espera Long)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String paramName = exception.getName();
        String errorMessage = "El parámetro '" + paramName + "' tiene un formato inválido";

        return ResponseEntity.badRequest().body(new ExceptionResponse(
                errorMessage,
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantAlreadyExistsException(RestaurantAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidRestaurantException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRestaurantException(InvalidRestaurantException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidDishException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidDishException(InvalidDishException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoDataFoundException(NoDataFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleElementNotFoundException(ElementNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now()));
    }
}
package com.plazoleta.users.infrastructure.configuration.exceptionhandler;

import com.plazoleta.users.adapters.driven.mysql.exception.*;
import com.plazoleta.users.domain.util.exceptions.InvalidUsuarioException;
import com.plazoleta.users.domain.util.exceptions.InvalidCredentialsException;
import com.plazoleta.users.domain.util.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

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

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUsuarioAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidUsuarioException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidUsuarioException(InvalidUsuarioException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidAgeException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidAgeException(InvalidAgeException ex) {
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.toString(),
                LocalDateTime.now()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                "Acceso denegado - No tiene permisos para realizar esta acción",
                HttpStatus.FORBIDDEN.toString(),
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
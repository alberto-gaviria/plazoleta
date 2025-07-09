package com.plazoleta.messaging.infrastructure.configuration.exceptionhandler;

import com.plazoleta.messaging.domain.util.exceptions.NotificationException;
import com.plazoleta.messaging.domain.util.exceptions.TwilioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorResponse> handleNotificationException(NotificationException ex) {
        logger.error("Error de notificación: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "NOTIFICATION_ERROR",
                                                        HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(TwilioException.class)
    public ResponseEntity<ErrorResponse> handleTwilioException(TwilioException ex) {
        logger.error("Error de Twilio: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Error en el servicio de mensajería",
                                                        "TWILIO_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = new ErrorResponse("Errores de validación: " + errors.toString(),
                                                        "VALIDATION_ERROR", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Error interno del servidor: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Ha ocurrido un error interno en el servidor",
                                                        "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    public static class ErrorResponse {
        private String message;
        private String error;
        private int status;
        private LocalDateTime timestamp;

        public ErrorResponse(String message, String error, int status) {
            this.message = message;
            this.error = error;
            this.status = status;
            this.timestamp = LocalDateTime.now();
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }

        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}
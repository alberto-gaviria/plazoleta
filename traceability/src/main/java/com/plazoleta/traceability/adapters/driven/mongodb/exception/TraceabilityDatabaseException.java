package com.plazoleta.traceability.adapters.driven.mongodb.exception;

public class TraceabilityDatabaseException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Error inesperado en la base de datos de trazabilidad";

    public TraceabilityDatabaseException(String message) {
        super(message);
    }

    public TraceabilityDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TraceabilityDatabaseException() {
        super(DEFAULT_MESSAGE);
    }

    public TraceabilityDatabaseException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
package com.plazoleta.traceability.adapters.driven.mongodb.exception;

public class TraceabilityRetrieveException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Error al recuperar la trazabilidad";

    public TraceabilityRetrieveException(String message) {
        super(message);
    }

    public TraceabilityRetrieveException(String message, Throwable cause) {
        super(message, cause);
    }

    public TraceabilityRetrieveException() {
        super(DEFAULT_MESSAGE);
    }

    public TraceabilityRetrieveException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
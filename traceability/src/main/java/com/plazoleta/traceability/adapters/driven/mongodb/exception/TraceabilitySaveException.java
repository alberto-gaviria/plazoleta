package com.plazoleta.traceability.adapters.driven.mongodb.exception;

public class TraceabilitySaveException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Error al guardar la trazabilidad";

    public TraceabilitySaveException(String message) {
        super(message);
    }

    public TraceabilitySaveException(String message, Throwable cause) {
        super(message, cause);
    }

    public TraceabilitySaveException() {
        super(DEFAULT_MESSAGE);
    }
}
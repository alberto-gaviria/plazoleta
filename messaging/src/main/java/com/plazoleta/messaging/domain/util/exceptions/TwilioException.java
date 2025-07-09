package com.plazoleta.messaging.domain.util.exceptions;

public class TwilioException extends RuntimeException {

    public TwilioException(String message) {
        super(message);
    }

    public TwilioException(String message, Throwable cause) {
        super(message, cause);
    }
}
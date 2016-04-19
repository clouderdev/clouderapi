package com.clouder.clouderapi.exception;

public class ClouderException extends RuntimeException {

    private static final long serialVersionUID = -516836284101479258L;

    public ClouderException(String message, Throwable exception) {
        super(message, exception);
    }

}

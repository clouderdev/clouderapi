package com.clouder.clouderapi.exception;

public class CloudException extends RuntimeException {

    private static final long serialVersionUID = 3771240179199687595L;

    public CloudException(String message, Throwable e) {
        super(message, e);
    }

}

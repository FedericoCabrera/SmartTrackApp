package com.isp.smarttrackapp.exceptions;

public class UnhandledRepositoryException extends Exception {

    public UnhandledRepositoryException(String message) {
        super(message);
    }

    public UnhandledRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}

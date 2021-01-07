package com.epam.esm.exception;

public class ResourceIsAlreadyExistException extends Exception {
    public ResourceIsAlreadyExistException() {
    }

    public ResourceIsAlreadyExistException(String message) {
        super(message);
    }

    public ResourceIsAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceIsAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
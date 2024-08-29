package ru.isaev.service.Utilities.Exceptions;

public class InvalidProductOperationException extends RuntimeException{
    public InvalidProductOperationException(String message) {
        super(message);
    }
}

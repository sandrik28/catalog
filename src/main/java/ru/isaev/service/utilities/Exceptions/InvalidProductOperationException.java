package ru.isaev.service.utilities.Exceptions;

public class InvalidProductOperationException extends RuntimeException{
    public InvalidProductOperationException(String message) {
        super(message);
    }
}

package ru.isaev.Service.Utilities.Exceptions;

public class InvalidProductOperationException extends RuntimeException{
    public InvalidProductOperationException(String message) {
        super(message);
    }
}

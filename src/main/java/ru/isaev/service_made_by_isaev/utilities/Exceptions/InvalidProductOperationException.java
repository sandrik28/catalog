package ru.isaev.service_made_by_isaev.utilities.Exceptions;

public class InvalidProductOperationException extends RuntimeException{
    public InvalidProductOperationException(String message) {
        super(message);
    }
}

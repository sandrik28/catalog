package ru.isaev.service.Utilities.Exceptions;

public class NotYourProductException extends RuntimeException {
    public NotYourProductException(String message) {
        super(message);
    }
}
package ru.isaev.service.utilities.Exceptions;

public class NotYourProductException extends RuntimeException {
    public NotYourProductException(String message) {
        super(message);
    }
}
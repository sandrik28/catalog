package ru.isaev.Service.Utilities.Exceptions;

public class NotYourProductException extends RuntimeException {
    public NotYourProductException(String message) {
        super(message);
    }
}
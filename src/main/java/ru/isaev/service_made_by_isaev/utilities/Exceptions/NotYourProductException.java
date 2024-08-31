package ru.isaev.service_made_by_isaev.utilities.Exceptions;

public class NotYourProductException extends RuntimeException {
    public NotYourProductException(String message) {
        super(message);
    }
}
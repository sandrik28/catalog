package ru.isaev.service.Utilities.Exceptions;

public class NotYourProfileException extends RuntimeException {
    public NotYourProfileException(String message) {
        super(message);
    }
}

package ru.isaev.service.utilities.Exceptions;

public class NotYourProfileException extends RuntimeException {
    public NotYourProfileException(String message) {
        super(message);
    }
}

package ru.isaev.service_made_by_isaev.utilities.Exceptions;

public class InvalidLoginAndPasswordException extends RuntimeException {
    public InvalidLoginAndPasswordException(String message) {
        super(message);
    }
}

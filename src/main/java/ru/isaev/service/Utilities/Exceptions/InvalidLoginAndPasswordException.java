package ru.isaev.service.Utilities.Exceptions;

public class InvalidLoginAndPasswordException extends RuntimeException {
    public InvalidLoginAndPasswordException(String message) {
        super(message);
    }
}

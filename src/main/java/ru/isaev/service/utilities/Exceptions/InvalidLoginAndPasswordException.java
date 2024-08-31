package ru.isaev.service.utilities.Exceptions;

public class InvalidLoginAndPasswordException extends RuntimeException {
    public InvalidLoginAndPasswordException(String message) {
        super(message);
    }
}

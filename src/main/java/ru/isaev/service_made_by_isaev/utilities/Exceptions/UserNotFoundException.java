package ru.isaev.service_made_by_isaev.utilities.Exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
package ru.isaev.service.utilities.Exceptions;

public class ProductNotFoundExceptions extends RuntimeException {
    public ProductNotFoundExceptions(String msg) {
        super(msg);
    }
}
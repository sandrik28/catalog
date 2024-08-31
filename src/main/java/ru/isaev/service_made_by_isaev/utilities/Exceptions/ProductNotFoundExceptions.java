package ru.isaev.service_made_by_isaev.utilities.Exceptions;

public class ProductNotFoundExceptions extends RuntimeException {
    public ProductNotFoundExceptions(String msg) {
        super(msg);
    }
}
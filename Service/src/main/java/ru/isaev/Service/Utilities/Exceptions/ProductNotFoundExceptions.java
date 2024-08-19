package ru.isaev.Service.Utilities.Exceptions;

public class ProductNotFoundExceptions extends RuntimeException {
    public ProductNotFoundExceptions(String msg) {
        super(msg);
    }
}
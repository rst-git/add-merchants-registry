package br.com.myproject.exception;

public class InvalidCnpjException extends RuntimeException {

    public InvalidCnpjException(String message) {
        super(message);
    }
}
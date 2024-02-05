package com.example;

public class ClientApplicationException extends RuntimeException{
    public ClientApplicationException(String message) {
        super(message);
    }
}

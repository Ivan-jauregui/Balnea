package com.balneamdp.exceptions;

public class ResortNotFoundException extends RuntimeException {
    public ResortNotFoundException(String message) {
        super(message);
    }
}

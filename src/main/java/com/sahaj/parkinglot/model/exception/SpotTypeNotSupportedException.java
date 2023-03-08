package com.sahaj.parkinglot.model.exception;

public class SpotTypeNotSupportedException extends RuntimeException{
    public SpotTypeNotSupportedException(String message) {
        super(message);
    }
}

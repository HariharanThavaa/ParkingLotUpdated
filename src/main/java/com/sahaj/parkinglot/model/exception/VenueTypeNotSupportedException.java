package com.sahaj.parkinglot.model.exception;

public class VenueTypeNotSupportedException extends RuntimeException{
    public VenueTypeNotSupportedException(String message) {
        super(message);
    }
}

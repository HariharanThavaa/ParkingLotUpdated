package com.sahaj.parkinglot.model;

import lombok.Value;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Value
public class ParkingReceipt {

    private static int receiptCounter = 0;
    String receiptNumber;
    LocalDateTime entryTimestamp;
    LocalDateTime exitTimestamp;
    long fees;

    public ParkingReceipt(LocalDateTime entryTimestamp, LocalDateTime exitTimestamp, long fees) {
        this.receiptNumber = format("R-%03d", ++receiptCounter);
        this.entryTimestamp = entryTimestamp;
        this.exitTimestamp = exitTimestamp;
        this.fees = fees;
    }
}

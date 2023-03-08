package com.sahaj.parkinglot.model;

import lombok.Value;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Value
public class ParkingTicket {

    private static int ticketCounter = 0;
    String ticketNumber;
    int spotNumber;
    LocalDateTime entryTimestamp;

    public ParkingTicket(int spotNumber, LocalDateTime entryTimestamp) {
        ticketNumber = format("%03d", ++ticketCounter);
        this.spotNumber = spotNumber;
        this.entryTimestamp = entryTimestamp;
    }
}

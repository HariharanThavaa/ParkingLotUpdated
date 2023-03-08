package com.sahaj.parkinglot.model;

import com.sahaj.parkinglot.model.exception.NoSpaceAvailableException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.sahaj.parkinglot.model.SpotType.*;
import static com.sahaj.parkinglot.model.VenueType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;



class VenueTest {

    @Test
    void testInitialiseVenue() {
        Venue venue = Venue.initialiseVenue(MALL, Map.of(MOTORCYCLE_OR_SCOOTER, 2));
        assertThat(venue.getType()).isEqualTo(MALL);
        Set<SpotType> expected = new HashSet<>(Arrays.asList(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV,
                BUS_OR_TRUCK));
        assertThat(venue.getSupportedSpotType()).isEqualTo(expected);
    }

    @Test
    void testExample1() throws NoSpaceAvailableException {
        Venue venue = Venue.initialiseVenue(MALL, Map.of(MOTORCYCLE_OR_SCOOTER, 2));
        // Park motorcycle
        ParkingTicket ticket1 = venue.park(MOTORCYCLE_OR_SCOOTER, LocalDateTime.of( 2022, 5, 29, 14, 4, 7));
        assertThat(ticket1.getTicketNumber()).isEqualTo("001");
        assertThat(ticket1.getSpotNumber()).isEqualTo(1);
        assertThat(ticket1.getEntryTimestamp()).isEqualTo(LocalDateTime.of( 2022, 5, 29, 14, 4, 7));

        // Park Scooter
        ParkingTicket ticket2 = venue.park(MOTORCYCLE_OR_SCOOTER, LocalDateTime.of( 2022, 5, 29, 14, 44, 7));
        assertThat(ticket2.getTicketNumber()).isEqualTo("002");
        assertThat(ticket2.getSpotNumber()).isEqualTo(2);
        assertThat(ticket2.getEntryTimestamp()).isEqualTo(LocalDateTime.of( 2022, 5, 29, 14, 44, 7));

        // Park Scooter
        assertThatExceptionOfType(NoSpaceAvailableException.class)
                .isThrownBy(() -> venue.park(MOTORCYCLE_OR_SCOOTER, LocalDateTime.now()))
                .withMessage("No space available");

        // Un park scooter, ticket number 002
        ParkingReceipt receipt1 = venue.unPark(MOTORCYCLE_OR_SCOOTER, ticket2,
                LocalDateTime.of(2022, 5, 29, 15, 40, 7));
        assertThat(receipt1.getReceiptNumber()).isEqualTo("R-001");
        assertThat(receipt1.getEntryTimestamp()).isEqualTo(LocalDateTime.of( 2022, 5, 29, 14, 44, 7));
        assertThat(receipt1.getExitTimestamp()).isEqualTo(LocalDateTime.of(2022, 5, 29, 15, 40, 7));
        assertThat(receipt1.getFees()).isEqualTo(10);

        // Park motorcycle
        ParkingTicket ticket3 = venue.park(MOTORCYCLE_OR_SCOOTER, LocalDateTime.of( 2022, 5, 29, 15, 59, 7));
        assertThat(ticket3.getTicketNumber()).isEqualTo("003");
        assertThat(ticket3.getSpotNumber()).isEqualTo(2);
        assertThat(ticket3.getEntryTimestamp()).isEqualTo(LocalDateTime.of( 2022, 5, 29, 15, 59, 7));

        // Un park motorcycle, ticket number 001
        ParkingReceipt receipt2 = venue.unPark(MOTORCYCLE_OR_SCOOTER, ticket1,
                LocalDateTime.of(2022, 5, 29, 17, 44, 7));
        assertThat(receipt2.getReceiptNumber()).isEqualTo("R-002");
        assertThat(receipt2.getEntryTimestamp()).isEqualTo(LocalDateTime.of( 2022, 5, 29, 14, 4, 7));
        assertThat(receipt2.getExitTimestamp()).isEqualTo(LocalDateTime.of(2022, 5, 29, 17, 44, 7));
        assertThat(receipt2.getFees()).isEqualTo(40);
    }

    @Test
    void testExample2() {
        // Mall parking lot
        Venue venue = Venue.initialiseVenue(MALL,
                Map.of(MOTORCYCLE_OR_SCOOTER, 100, CAR_OR_SUV, 80, BUS_OR_TRUCK, 10));

        // Motorcycle parked for 3 hours and 30 mins. Fees: 40
        assertThat(venue.calculateFee(MOTORCYCLE_OR_SCOOTER, 0, 3, 30)).isEqualTo(40);

        // Car parked for 6 hours and 1 min. Fees: 140
        assertThat(venue.calculateFee(CAR_OR_SUV, 0, 6, 1)).isEqualTo(140);

        // Truck parked for 1 hour and 59 mins. Fees: 100
        assertThat(venue.calculateFee(BUS_OR_TRUCK, 0, 1, 59)).isEqualTo(100);
    }

    @Test
    void testExample3() {
        // Stadium parking lot
        Venue venue = Venue.initialiseVenue(STADIUM,
                Map.of(MOTORCYCLE_OR_SCOOTER, 1000, CAR_OR_SUV, 1500));

        // Motorcycle parked for 3 hours and 40 mins. Fees: 30
        assertThat(venue.calculateFee(MOTORCYCLE_OR_SCOOTER, 0, 3, 40)).isEqualTo(30);

        // Motorcycle parked for 14 hours and 59 mins. Fees: 390
        assertThat(venue.calculateFee(MOTORCYCLE_OR_SCOOTER, 0, 14, 59)).isEqualTo(390);

        // Electric SUV parked for 11 hours and 30 mins. Fees: 180.
        assertThat(venue.calculateFee(CAR_OR_SUV, 0, 11, 30)).isEqualTo(180);

        // SUV parked for 13 hours and 5 mins. Fees: 580.
        assertThat(venue.calculateFee(CAR_OR_SUV, 0, 13, 5)).isEqualTo(580);
    }

    @Test
    void testExample4() {
        // Airport parking lot
        Venue venue = Venue.initialiseVenue(AIRPORT,
                Map.of(MOTORCYCLE_OR_SCOOTER, 200, CAR_OR_SUV, 500));

        // Motorcycle parked for 55 mins. Fees: 0
        assertThat(venue.calculateFee(MOTORCYCLE_OR_SCOOTER, 0, 0, 55)).isEqualTo(0);

        // Motorcycle parked for 14 hours and 59 mins. Fees: 60
        assertThat(venue.calculateFee(MOTORCYCLE_OR_SCOOTER, 0, 14, 59)).isEqualTo(60);

        // Motorcycle parked for 1 day and 12 hours. Fees: 160
        assertThat(venue.calculateFee(MOTORCYCLE_OR_SCOOTER, 1, 12, 0)).isEqualTo(160);

        // Car parked for 50 mins. Fees: 60
        assertThat(venue.calculateFee(CAR_OR_SUV, 0, 0, 50)).isEqualTo(60);

        // SUV parked for 23 hours and 59 mins. Fees: 80
        assertThat(venue.calculateFee(CAR_OR_SUV, 0, 23, 0)).isEqualTo(80);

        // Car parked for 3 days and 1 hour. Fees: 400
        assertThat(venue.calculateFee(CAR_OR_SUV, 3, 1, 0)).isEqualTo(400);
    }

}
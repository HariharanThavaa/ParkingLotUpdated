package com.sahaj.parkinglot.model.venues;

import com.sahaj.parkinglot.model.SpotType;
import com.sahaj.parkinglot.model.Venue;
import com.sahaj.parkinglot.model.VenueType;
import com.sahaj.parkinglot.model.exception.SpotTypeNotSupportedException;

import java.util.Set;

import static com.sahaj.parkinglot.model.SpotType.BUS_OR_TRUCK;
import static com.sahaj.parkinglot.model.SpotType.CAR_OR_SUV;
import static com.sahaj.parkinglot.model.SpotType.MOTORCYCLE_OR_SCOOTER;
import static com.sahaj.parkinglot.model.VenueType.MALL;
import static com.sahaj.parkinglot.utils.Utils.getHoursToCalculate;
import static java.lang.String.format;

public class Mall extends Venue {

    private static final long HOURLY_RATE_FOR_MOTORCYCLE_OR_SCOOTER = 10;

    private static final long HOURLY_RATE_FOR_CAR_OR_SUV = 20;

    private static final long HOURLY_RATE_FOR_BUS_OR_TRUCK = 50;

    @Override
    public VenueType getType() {
        return MALL;
    }

    @Override
    protected long calculateFee(SpotType spotType, long days, long hours, long mins) {
        long hoursToCalculate = getHoursToCalculate(days, hours, mins);
        switch (spotType) {
            case MOTORCYCLE_OR_SCOOTER:
                return calculateFeeForMotorcycleOrScooter(hoursToCalculate);
            case CAR_OR_SUV:
                return calculateFeeForCarOrSUV(hoursToCalculate);
            case BUS_OR_TRUCK:
                return calculateFeeForBusOrTruck(hoursToCalculate);
        }
        throw new SpotTypeNotSupportedException(format("Spot type [%s] is not supported", spotType));
    }

    @Override
    protected Set<SpotType> getSupportedSpotType() {
        return Set.of(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV, BUS_OR_TRUCK);
    }

    private long calculateFeeForMotorcycleOrScooter(long hours){
        return hours * HOURLY_RATE_FOR_MOTORCYCLE_OR_SCOOTER;
    }

    private long calculateFeeForCarOrSUV(long hours){
        return hours * HOURLY_RATE_FOR_CAR_OR_SUV;
    }

    private long calculateFeeForBusOrTruck(long hours){
        return hours * HOURLY_RATE_FOR_BUS_OR_TRUCK;
    }
}

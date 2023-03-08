package com.sahaj.parkinglot.model.venues;

import com.sahaj.parkinglot.model.SpotType;
import com.sahaj.parkinglot.model.Venue;
import com.sahaj.parkinglot.model.VenueType;
import com.sahaj.parkinglot.model.exception.SpotTypeNotSupportedException;

import java.util.Set;

import static com.sahaj.parkinglot.model.SpotType.*;
import static com.sahaj.parkinglot.model.VenueType.STADIUM;
import static com.sahaj.parkinglot.utils.Utils.getHoursToCalculate;
import static java.lang.String.format;

public class Stadium extends Venue {

    private static final long FIRST_FIXED_RATE_INTERVAL_UPPER_BOUND = 4;

    private static final long FIRST_FIXED_RATE_FOR_MOTORCYCLE_OR_SCOOTER = 30;

    private static final long FIRST_FIXED_RATE_FOR_CAR_OR_SUV = 60;

    private static final long SECOND_FIXED_RATE_INTERVAL_UPPER_BOUND = 12;

    private static final long SECOND_FIXED_RATE_FOR_MOTORCYCLE_OR_SCOOTER = 60;

    private static final long SECOND_FIXED_RATE_FOR_CAR_OR_SUV = 120;

    private static final long HOURLY_RATE_FOR_MOTORCYCLE_OR_SCOOTER = 100;

    private static final long HOURLY_RATE_FOR_CAR_OR_SUV = 200;

    @Override
    public VenueType getType() {
        return STADIUM;
    }

    @Override
    protected long calculateFee(SpotType spotType, long days, long hours, long mins) {
        long hoursToCalculate = getHoursToCalculate(days, hours, mins);
        switch (spotType) {
            case MOTORCYCLE_OR_SCOOTER:
                return calculateFeeForMotorcycleOrScooter(hoursToCalculate);
            case CAR_OR_SUV:
                return calculateFeeForCarOrSUV(hoursToCalculate);
        }

        throw new SpotTypeNotSupportedException(format("Spot type [%s] is not supported", spotType));
    }

    private long calculateFeeForCarOrSUV(long hoursToCalculate) {
        if (hoursToCalculate <= FIRST_FIXED_RATE_INTERVAL_UPPER_BOUND) {
            return FIRST_FIXED_RATE_FOR_CAR_OR_SUV;
        } else if (hoursToCalculate <= SECOND_FIXED_RATE_INTERVAL_UPPER_BOUND) {
            return FIRST_FIXED_RATE_FOR_CAR_OR_SUV
                    + SECOND_FIXED_RATE_FOR_CAR_OR_SUV;
        } else {
            return FIRST_FIXED_RATE_FOR_CAR_OR_SUV
                    + SECOND_FIXED_RATE_FOR_CAR_OR_SUV
                    + HOURLY_RATE_FOR_CAR_OR_SUV * (hoursToCalculate - 12);
        }
    }

    private long calculateFeeForMotorcycleOrScooter(long hoursToCalculate) {
        if (hoursToCalculate <= FIRST_FIXED_RATE_INTERVAL_UPPER_BOUND) {
            return FIRST_FIXED_RATE_FOR_MOTORCYCLE_OR_SCOOTER;
        } else if (hoursToCalculate <= SECOND_FIXED_RATE_INTERVAL_UPPER_BOUND) {
            return FIRST_FIXED_RATE_FOR_MOTORCYCLE_OR_SCOOTER + SECOND_FIXED_RATE_FOR_MOTORCYCLE_OR_SCOOTER;
        } else {
            return FIRST_FIXED_RATE_FOR_MOTORCYCLE_OR_SCOOTER + SECOND_FIXED_RATE_FOR_MOTORCYCLE_OR_SCOOTER + HOURLY_RATE_FOR_MOTORCYCLE_OR_SCOOTER * (hoursToCalculate - 12);
        }
    }

    @Override
    protected Set<SpotType> getSupportedSpotType() {
        return Set.of(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV);
    }
}

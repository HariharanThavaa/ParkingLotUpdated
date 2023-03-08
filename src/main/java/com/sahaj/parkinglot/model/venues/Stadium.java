package com.sahaj.parkinglot.model.venues;

import com.sahaj.parkinglot.model.Rate;
import com.sahaj.parkinglot.model.SpotType;
import com.sahaj.parkinglot.model.Venue;
import com.sahaj.parkinglot.model.VenueType;

import java.util.Map;
import java.util.Set;

import static com.sahaj.parkinglot.model.SpotType.*;
import static com.sahaj.parkinglot.model.VenueType.STADIUM;

public class Stadium extends Venue {

    private static final Map<SpotType, Rate> feeModel =
            Map.of(MOTORCYCLE_OR_SCOOTER, new Rate(100, 12, 0 , 0,  Map.of( 0L, 30L, 4L, 60L)),
                    CAR_OR_SUV, new Rate(200, 12, 0, 0, Map.of( 0L, 60L, 4L, 120L)));

    @Override
    public VenueType getType() {
        return STADIUM;
    }

    @Override
    protected long calculateFee(SpotType spotType, long days, long hours, long mins) {
        long hoursToCalculate = getHoursToCalculate(days, hours, mins);
        long fees = 0;

        Rate rate = feeModel.get(spotType);

        for (long k: rate.getFromRate().keySet()) {
            if ( k < hoursToCalculate) {
                fees += rate.getFromRate().get(k);
            }
        }

        if (hoursToCalculate > rate.getHourlyRateFrom()) {
            fees += (hoursToCalculate - rate.getHourlyRateFrom()) * rate.getHourlyRate();
        }

        return fees;
    }

    @Override
    protected Set<SpotType> getSupportedSpotType() {
        return Set.of(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV);
    }
}

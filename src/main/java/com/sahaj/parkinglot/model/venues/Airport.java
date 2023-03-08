package com.sahaj.parkinglot.model.venues;

import com.sahaj.parkinglot.model.Rate;
import com.sahaj.parkinglot.model.SpotType;
import com.sahaj.parkinglot.model.Venue;
import com.sahaj.parkinglot.model.VenueType;

import java.util.Map;
import java.util.Set;

import static com.sahaj.parkinglot.model.SpotType.CAR_OR_SUV;
import static com.sahaj.parkinglot.model.SpotType.MOTORCYCLE_OR_SCOOTER;
import static com.sahaj.parkinglot.model.VenueType.AIRPORT;

public class Airport extends Venue {

    private static final Map<SpotType, Rate> feeModel =
            Map.of(MOTORCYCLE_OR_SCOOTER, new Rate(0, 0, 80, 24,  Map.of( 0L, 0L, 1L, 40L, 8L, 60L)),
                    CAR_OR_SUV, new Rate(0, 0, 100, 24, Map.of( 0L, 60L, 12L, 80L)));

    @Override
    public VenueType getType() {
        return AIRPORT;
    }

    @Override
    protected long calculateFee(SpotType spotType, long days, long hours, long mins) {
        long hoursToCalculate = getHoursToCalculate(days, hours, mins);
        long fees = 0;

        Rate rate = feeModel.get(spotType);

        for (long k: rate.getFromRate().keySet()) {
            if ( k < hoursToCalculate && fees < rate.getFromRate().get(k)) {
                fees = rate.getFromRate().get(k);
            }
        }

        if (hoursToCalculate > rate.getDailyRateFrom()) {
            fees = (days + 1) * rate.getDailyRate();
        }

        return fees;
    }

    @Override
    protected Set<SpotType> getSupportedSpotType() {
        return Set.of(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV);
    }
}

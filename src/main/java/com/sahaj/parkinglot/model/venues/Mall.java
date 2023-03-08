package com.sahaj.parkinglot.model.venues;

import com.sahaj.parkinglot.model.SpotType;
import com.sahaj.parkinglot.model.Venue;
import com.sahaj.parkinglot.model.VenueType;

import java.util.Map;
import java.util.Set;

import static com.sahaj.parkinglot.model.SpotType.BUS_OR_TRUCK;
import static com.sahaj.parkinglot.model.SpotType.CAR_OR_SUV;
import static com.sahaj.parkinglot.model.SpotType.MOTORCYCLE_OR_SCOOTER;
import static com.sahaj.parkinglot.model.VenueType.MALL;

public class Mall extends Venue {

    private static final Map<SpotType, Integer> feeModel =
            Map.of(MOTORCYCLE_OR_SCOOTER, 10,
                    CAR_OR_SUV, 20,
                    BUS_OR_TRUCK, 50);

    @Override
    public VenueType getType() {
        return MALL;
    }

    @Override
    protected long calculateFee(SpotType spotType, long days, long hours, long mins) {
        return getHoursToCalculate(days, hours, mins) * feeModel.get(spotType);
    }

    @Override
    protected Set<SpotType> getSupportedSpotType() {
        return Set.of(MOTORCYCLE_OR_SCOOTER, CAR_OR_SUV, BUS_OR_TRUCK);
    }
}

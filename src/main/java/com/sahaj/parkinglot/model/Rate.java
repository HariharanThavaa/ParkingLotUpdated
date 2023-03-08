package com.sahaj.parkinglot.model;

import lombok.Value;

import java.util.Map;

@Value
public class Rate {

    long hourlyRate;
    long hourlyRateFrom;
    long dailyRate;
    long dailyRateFrom;
    Map<Long, Long> fromRate;

}

package com.sahaj.parkinglot.model;

import lombok.Value;

@Value
public class Spot {
    SpotType type;
    int spotNumber;
}

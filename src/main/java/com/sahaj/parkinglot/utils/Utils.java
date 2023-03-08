package com.sahaj.parkinglot.utils;

public class Utils {

    public static long getHoursToCalculate(long days, long hours, long mins){
        hours = mins > 0 ? hours + 1 : hours;
        hours = days > 0 ? hours + days * 24 : hours;
        return hours;
    }
}

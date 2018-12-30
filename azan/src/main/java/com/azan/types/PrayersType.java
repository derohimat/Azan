package com.azan.types;


/**
 * Created by AhmedEltaher on 11/11/16.
 */

public enum PrayersType {
    FAJR(0),
    SUNRISE(1),
    ZUHR(2),
    ASR(3),
    MAGHRIB(4),
    ISHA(5);
    private int index;

    PrayersType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}

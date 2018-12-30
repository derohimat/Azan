package com.azan;

import com.azan.types.PrayersType;

import java.util.Date;

import static com.azan.Constants.MILLIS_IN_SECOND;
import static com.azan.Constants.MINUTE_IN_HOUR;
import static com.azan.Constants.SECOND_IN_MINUTE;
import static com.azan.types.PrayersType.ASR;
import static com.azan.types.PrayersType.FAJR;
import static com.azan.types.PrayersType.ISHA;
import static com.azan.types.PrayersType.MAGHRIB;
import static com.azan.types.PrayersType.SUNRISE;
import static com.azan.types.PrayersType.ZUHR;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Math.ceil;


public class PrayerTimes {
    private boolean useSecond = false;
    private double[] prayerTimes;
    private long millis;

    PrayerTimes(long millis, double... prayerTimes) {
        this.prayerTimes = prayerTimes;
        this.millis = millis;
        if (prayerTimes[SUNRISE.getIndex()] == NEGATIVE_INFINITY || prayerTimes[MAGHRIB.getIndex()] == POSITIVE_INFINITY) {
            prayerTimes[ZUHR.getIndex()] = POSITIVE_INFINITY;
            prayerTimes[ASR.getIndex()] = POSITIVE_INFINITY;
        }
    }

    public Date getPrayTime(PrayersType prayersType) {
        double time = prayerTimes[prayersType.getIndex()];
        if (time == POSITIVE_INFINITY || time == NEGATIVE_INFINITY) {
            return null;
        }
        // negative hours will raise exception
        if (isUseSecond())
            return new Date(this.millis + (long) ceil(time * MINUTE_IN_HOUR * SECOND_IN_MINUTE) * MILLIS_IN_SECOND);
        else
            return new Date(this.millis + (long) ceil(time * MINUTE_IN_HOUR) * SECOND_IN_MINUTE * MILLIS_IN_SECOND);
    }

    public Date getPrayTime(int prayersType) {
        switch (prayersType) {
            case 0:
                return getPrayTime(FAJR);
            case 1:
                return getPrayTime(SUNRISE);
            case 2:
                return getPrayTime(ZUHR);
            case 3:
                return getPrayTime(ASR);
            case 4:
                return getPrayTime(MAGHRIB);
            case 5:
                return getPrayTime(ISHA);
            default:
                throw new IllegalArgumentException("prayersType (" + prayersType + ") is not an integer between 0 and 5");
        }
    }

    public Time getTimeinHoursAndMinutesAndSecounds(int timeName) {
        double time = prayerTimes[timeName];
        if (time == POSITIVE_INFINITY || time == NEGATIVE_INFINITY)
            return null;
        if (isUseSecond())
            return new Time(time);
        else
            return new Time(ceil(time * MINUTE_IN_HOUR) / SECOND_IN_MINUTE);
    }

    /**
     * Whether second is used, if not it will be zero and minute is rounded to the top (ceil).
     */
    public boolean isUseSecond() {
        return useSecond;
    }

    public void setUseSecond(boolean useSecond) {
        this.useSecond = useSecond;
    }

}

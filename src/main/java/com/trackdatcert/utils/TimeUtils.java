package com.trackdatcert.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtils {

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    public LocalDate getDate(long time) {
        return Instant.ofEpochMilli(time)
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }
}

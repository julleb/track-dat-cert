package com.trackdatcert.utils;

import java.sql.Date;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SQLUtils {

    public Date getSqlDate(long epoch) {
        return new Date(epoch);
    }

    public long getEpoch(Date date) {
        return date.getTime();
    }

}

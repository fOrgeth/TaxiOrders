package com.th.forge.taxiorders.utils;

import java.text.SimpleDateFormat;

public class DateTimeParser {
    public static String getReadableString(String pattern, long timeInMillis){
        return new SimpleDateFormat(pattern).format(timeInMillis);
    }

}

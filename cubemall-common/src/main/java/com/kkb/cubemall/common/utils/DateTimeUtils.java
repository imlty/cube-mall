package com.kkb.cubemall.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final String pattern = "yyyy-MM-dd HH:mm:ss";

    public static String getDateTime(int plusDays){
        LocalDate now = LocalDate.now();
        LocalDate dp = now.plusDays(plusDays);
        LocalDateTime dpTime = LocalDateTime.of(dp, LocalTime.MIN);
        String timestr = dpTime.format(DateTimeFormatter.ofPattern(pattern));
        System.out.println(timestr);
        return timestr;
    }
}

package com.kkb.cubemall.seckill;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeTest {

    @Test
    public void getTime(){
        LocalDate now = LocalDate.now();
        LocalDate d1 = now.plusDays(1);
        LocalDate d2 = now.plusDays(2);
        LocalDate d3 = now.plusDays(3);
        System.out.println(now);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);


        LocalDateTime startDateTime = LocalDateTime.of(now,LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(d3,LocalTime.MIN);

        System.out.println(startDateTime);
        System.out.println(endDateTime);


        String start = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String end = endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println(start);
        System.out.println(end);

    }
}

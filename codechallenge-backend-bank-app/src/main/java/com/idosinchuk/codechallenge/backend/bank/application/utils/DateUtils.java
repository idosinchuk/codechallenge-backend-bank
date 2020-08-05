package com.idosinchuk.codechallenge.backend.bank.application.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static Date startDayDate;
    private static Date endDayDate;

    private DateUtils() {
        Calendar startDay = Calendar.getInstance();
        Calendar endDay = Calendar.getInstance();

        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.MILLISECOND, 0);

        endDay.set(Calendar.HOUR_OF_DAY, 23);
        endDay.set(Calendar.MINUTE, 59);
        endDay.set(Calendar.SECOND, 59);
        endDay.set(Calendar.MILLISECOND, 999);

        startDayDate = startDay.getTime();
        endDayDate = endDay.getTime();
    }

    public static boolean isAfterToday(Date transactionDate) {
        new DateUtils();

        return transactionDate.compareTo(endDayDate) > 0;
    }

    public static boolean isBeforeToday(Date transactionDate) {
        new DateUtils();

        return transactionDate.compareTo(startDayDate) < 0;
    }

    public static boolean isToday(Date transactionDate) {
        new DateUtils();

        return transactionDate.compareTo(startDayDate) > 0 && transactionDate.compareTo(endDayDate) < 0;
    }

}

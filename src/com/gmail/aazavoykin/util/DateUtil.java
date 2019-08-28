package com.gmail.aazavoykin.util;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private final static YearMonth NOW = YearMonth.now();
    public static final DateTimeFormatter HTML_FORMATTER = DateTimeFormatter.ofPattern("MM-uuuu");

    public static YearMonth toDate(String date) {
        try {
            return YearMonth.parse(date, HTML_FORMATTER);
        } catch (DateTimeParseException e) {
            return NOW;
        }
    }

}

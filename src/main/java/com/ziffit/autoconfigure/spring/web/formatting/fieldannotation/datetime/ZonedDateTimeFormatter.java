package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.datetime;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ZonedDateTimeFormatter implements Formatter<ZonedDateTime> {

    @Override
    public ZonedDateTime parse(String text, Locale locale) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").withZone(ZoneId.of("Europe/London"));
        return formatter.parse(text, ZonedDateTime::from);
    }

    @Override
    public String print(ZonedDateTime dateTime, Locale locale) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(dateTime.toLocalDateTime());
    }
}

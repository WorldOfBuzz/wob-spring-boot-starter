package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.datetime;

import com.google.common.collect.ImmutableSet;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.time.ZonedDateTime;
import java.util.Set;

public class ZonedDateTimeFormatterAnnotationFormatterFactory implements AnnotationFormatterFactory<ZonedDateTimeFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return ImmutableSet.of(ZonedDateTime.class);
    }

    @Override
    public Printer<?> getPrinter(ZonedDateTimeFormat annotation, Class<?> fieldType) {
        return new ZonedDateTimeFormatter();
    }

    @Override
    public Parser<?> getParser(ZonedDateTimeFormat annotation, Class<?> fieldType) {
        return new ZonedDateTimeFormatter();
    }
}

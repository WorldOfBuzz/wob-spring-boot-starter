package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.percentage;

import com.google.common.collect.ImmutableSet;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.math.BigDecimal;
import java.util.Set;

public class PercentageFormatterAnnotationFormatterFactory implements AnnotationFormatterFactory<PercentageFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return ImmutableSet.of(BigDecimal.class);
    }

    @Override
    public Printer<?> getPrinter(PercentageFormat annotation, Class<?> fieldType) {
        return new PercentageFormatter();
    }

    @Override
    public Parser<?> getParser(PercentageFormat annotation, Class<?> fieldType) {
        return new PercentageFormatter();
    }
}

package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.percentage;

import com.google.common.base.Strings;
import org.springframework.format.Formatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;

public class PercentageFormatter implements Formatter<BigDecimal> {

    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        if (Strings.isNullOrEmpty(text)) {
            return null;
        } else {
            BigDecimal displayedValue = new BigDecimal(text);
            return displayedValue.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
        }
    }

    @Override
    public String print(BigDecimal number, Locale locale) {
        if (number == null) {
            return "";
        } else {
            return new BigDecimal("100").multiply(number).setScale(2, RoundingMode.HALF_UP).toString();
        }
    }
}

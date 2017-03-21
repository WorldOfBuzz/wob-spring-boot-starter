package com.ziffit.autoconfigure.spring.web.formatting.serializer;

import com.google.common.collect.Range;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

// TODO: Add generic type inference. For now it only converts Range<Integer>.
public class RangeFormatter implements Formatter<Range<?>> {

    @Override
    public Range<?> parse(String text, Locale locale) throws ParseException {
        String[] splitSource = text.replace("[", "").replace("]", "").split("\u2025");
        Integer lowerBound = Integer.valueOf(splitSource[0]);
        Integer upperBound = "&#x221e;".equals(splitSource[1]) ? Integer.MAX_VALUE : Integer.valueOf(splitSource[1]);
        return Range.closed(lowerBound, upperBound);
    }

    @Override
    public String print(Range<?> object, Locale locale) {
        String lowerBound = object.lowerEndpoint().toString();
        String upperBound = Integer.MAX_VALUE == (Integer) object.upperEndpoint() ? "&#x221e;" : object.upperEndpoint().toString();
        return lowerBound + " - " + upperBound;
    }
}

package com.ziffit.autoconfigure.database.hibernate.type;

import com.google.common.collect.Range;
import com.ziffit.autoconfigure.database.hibernate.type.internal.PGRange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.TimeZone;

public class TstzRange extends PGRange {

    public static final Instant CUSTOM_INFINITY = OffsetDateTime.of(2200, 8, 20, 14, 25, 0, 0,
        TimeZone.getDefault().toZoneId().getRules().getOffset(LocalDateTime.of(2200, 8, 20, 14, 25))
    ).toInstant();

    private static final Logger logger = LogManager.getLogger();
    private static final String PG_TYPE = "TSTZRANGE";

    @Override
    public Class returnedClass() {
        return Range.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String rawRange = rs.getString(names[0]);
        String rangeValuesWithoutParentheses = rawRange.replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "");
        String[] splitRangeValues = rangeValuesWithoutParentheses.split(",");
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 9, true)
            .optionalEnd()
            .appendOffset("+HH", "+00")
            .toFormatter();
        Instant fromDateTime = dateTimeFormatter.parse(splitRangeValues[0], Instant::from);
        Instant toDateTime = splitRangeValues.length == 1 || "infinity".equals(splitRangeValues[1]) ?
            CUSTOM_INFINITY :
            dateTimeFormatter.parse(splitRangeValues[1], Instant::from);
        return Range.closed(fromDateTime, toDateTime);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        String pgRepresentation = createPgRepresentation(value);
        super.nullSafeSet(st, index, PG_TYPE, pgRepresentation);
    }

    private String createPgRepresentation(Object value) {
        @SuppressWarnings("unchecked")
        Range<Instant> dateRange = (Range<Instant>) value;
        if (CUSTOM_INFINITY.equals(dateRange.upperEndpoint())) {
            return "[" + dateRange.lowerEndpoint().toString() + ",]";
        } else {
            return dateRange.toString().replace("..", ",");
        }
    }
}

package com.ziffit.autoconfigure.database.hibernate.type;

import com.google.common.collect.Range;
import com.ziffit.autoconfigure.database.hibernate.type.internal.PGRange;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NumRange extends PGRange {

    private static final Logger logger = LogManager.getLogger();
    private static final String PG_TYPE = "NUMRANGE";
    private static final BigDecimal MAX_NUMERIC_VALUE = new BigDecimal("999.00");

    @Override
    public Class returnedClass() {
        return Range.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String rawRange = rs.getString(names[0]);
        String rangeValuesWithoutParentheses = rawRange.replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "");
        String[] splitRangeValues = rangeValuesWithoutParentheses.split(",");
        BigDecimal lowerBound = new BigDecimal(splitRangeValues[0], new MathContext(7)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal upperBound = splitRangeValues.length == 1 ?
            MAX_NUMERIC_VALUE :
            new BigDecimal(splitRangeValues[1], new MathContext(7)).setScale(2, RoundingMode.HALF_UP);
        return Range.closed(lowerBound, upperBound);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        String pgRepresentation = createPgRepresentation(value);
        super.nullSafeSet(st, index, PG_TYPE, pgRepresentation);
    }

    private String createPgRepresentation(Object value) {
        @SuppressWarnings("unchecked")
        Range<BigDecimal> numericRange = (Range<BigDecimal>) value;
        if (MAX_NUMERIC_VALUE.equals(numericRange.upperEndpoint())) {
            return "[" + numericRange.lowerEndpoint().toString() + ",]";
        } else {
            return numericRange.toString().replace("..", ",");
        }
    }
}

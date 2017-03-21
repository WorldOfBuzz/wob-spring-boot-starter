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

public class Int4Range extends PGRange {

    private static final Logger logger = LogManager.getLogger();
    private static final String PG_TYPE = "INT4RANGE";

    @Override
    public Class returnedClass() {
        return Range.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String rawRange = rs.getString(names[0]);
        String rangeValuesWithoutParentheses = rawRange.replace("[", "").replace("]", "").replace("(", "").replace(")", "").replace("\"", "");
        String[] splitRangeValues = rangeValuesWithoutParentheses.split(",");
        Integer lowerBound = Integer.valueOf(splitRangeValues[0]);
        Integer upperBound = splitRangeValues.length == 1 ?
            Integer.MAX_VALUE :
            Integer.valueOf(splitRangeValues[1]) - 1;
        return Range.closed(lowerBound, upperBound);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        String pgRepresentation = createPgRepresentation(value);
        super.nullSafeSet(st, index, PG_TYPE, pgRepresentation);
    }

    private String createPgRepresentation(Object value) {
        @SuppressWarnings("unchecked")
        Range<Integer> integerRange = (Range<Integer>) value;
        if (Integer.MAX_VALUE == integerRange.upperEndpoint()) {
            return "[" + integerRange.lowerEndpoint() + ",)";
        } else {
            return integerRange.toString().replace("..", ",");
        }
    }
}

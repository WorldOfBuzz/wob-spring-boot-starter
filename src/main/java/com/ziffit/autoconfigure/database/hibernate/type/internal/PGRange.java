package com.ziffit.autoconfigure.database.hibernate.type.internal;

import com.google.common.collect.Range;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.postgresql.util.PGobject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * This class handles the conversion between {@link Range} on the Java side, and the various PostgreSQL range types.
 */
public abstract class PGRange implements MutableUserType {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.OTHER};
    }

    @Override
    public Object deepCopy(Object value) throws HibernateException {
        Range<?> oldValue = (Range<?>) value;
        return Range.closed(oldValue.lowerEndpoint(), oldValue.upperEndpoint());
    }

    public void nullSafeSet(PreparedStatement st, int index, String pgType, String pgRepresentation) throws SQLException {
        PGobject pgObject = new PGobject();
        pgObject.setType(pgType);
        pgObject.setValue(pgRepresentation);
        st.setObject(index, pgObject);
        logger.debug("binding parameter [{}] as [{}] - [{}]", index, pgType, pgRepresentation);
    }
}

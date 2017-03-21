package com.ziffit.autoconfigure.database.hibernate.type.internal;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.util.Objects;

public interface MutableUserType extends UserType {

    @Override
    default boolean equals(Object x, Object y) throws HibernateException {
        return Objects.equals(x, y);
    }

    @Override
    default int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    default boolean isMutable() {
        return true;
    }

    @Override
    default Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    default Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    default Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }
}

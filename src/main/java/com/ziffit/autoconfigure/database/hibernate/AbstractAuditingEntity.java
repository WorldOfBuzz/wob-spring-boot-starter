package com.ziffit.autoconfigure.database.hibernate;

import com.google.common.collect.Range;
import com.ziffit.autoconfigure.database.hibernate.type.Int4Range;
import com.ziffit.autoconfigure.database.hibernate.type.NumRange;
import com.ziffit.autoconfigure.database.hibernate.type.TstzRange;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@TypeDefs({
    @TypeDef(
        name = "int4range",
        typeClass = Int4Range.class,
        defaultForType = Range.class
    ),
    @TypeDef(
        name = "numrange",
        typeClass = NumRange.class,
        defaultForType = Range.class
    ),
    @TypeDef(
        name = "tstzrange",
        typeClass = TstzRange.class,
        defaultForType = Range.class
    )
})
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
/*
 * Contains auditing information, equals / hashCode implementation, and type definitions.
 */
public abstract class AbstractAuditingEntity {

    protected String uuid = UUID.randomUUID().toString();
    @CreatedDate
    protected Instant createdDate;
    @LastModifiedDate
    protected Instant lastModifiedDate;

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractAuditingEntity)) {
            return false;
        }
        AbstractAuditingEntity otherEntity = (AbstractAuditingEntity) other;
        return Objects.equals(getUuid(), otherEntity.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}

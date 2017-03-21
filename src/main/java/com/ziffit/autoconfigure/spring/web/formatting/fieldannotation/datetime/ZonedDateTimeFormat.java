package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.datetime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ZonedDateTimeFormat {

}

package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.percentage;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This formatter is useful when percentages arrive in an easily readable
 * format, such as 25%, but we want to store them in their decimal format, such as 0.25.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PercentageFormat {

}

package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.barcode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import org.hibernate.validator.constraints.NotEmpty;

import com.ziffit.autoconfigure.spring.web.validator.BarcodeValidator;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BarcodeValidator.class)
@NotEmpty
public @interface ValidBarcode {
	String message() default "Invalid EAN.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

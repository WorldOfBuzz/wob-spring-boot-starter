package com.worldofbooks.autoconfigure.spring.web.formatting.fieldannotation.barcode;

import com.worldofbooks.autoconfigure.spring.web.validator.BarcodeValidator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BarcodeValidator.class)
@NotEmpty
public @interface Barcode {

    String message() default "Sorry, the barcode you've entered hasn't been recognised. Please try again.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

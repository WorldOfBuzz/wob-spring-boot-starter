package com.worldofbuzz.autoconfigure.spring.web.validator;

import com.worldofbuzz.autoconfigure.spring.web.formatting.fieldannotation.barcode.Barcode;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class BarcodeValidator implements ConstraintValidator<Barcode, String> {

    @Override
    public void initialize(Barcode barcode) {

    }

    @Override
    public boolean isValid(String barcode, ConstraintValidatorContext constraintValidatorContext) {
        if (barcode == null) {
            return false;
        }

        if (barcode.matches("[0123456789Xx]+")) {
            if (barcode.startsWith("0000")) {
                return false;
            }

            if (barcode.length() == 9) {
                barcode = "0" + barcode;
            }

            if (barcode.length() == 10) {
                return validateISBN10(barcode);
            } else if (barcode.length() == 12) {
                return validateUPC12(barcode);
            } else if (barcode.length() == 13) {
                return validateEan13(barcode);
            }

            return false;
        } else {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("The barcode contains invalid characters.")
                .addConstraintViolation();

            return false;
        }
    }

    private boolean validateUPC12(String barcode) {
        int sum = 0;

        for (int i = 1; i < 12; i++) {
            if (i % 2 == 0) {
                sum += Integer.parseInt(Character.toString(barcode.charAt(i - 1)));
            } else {
                sum += Integer.parseInt(Character.toString(barcode.charAt(i - 1))) * 3;
            }
        }

        int checkDigit = 10 - sum % 10;
        if (checkDigit == 10) {
            checkDigit = 0;
        }

        return checkDigit == Integer.parseInt(Character.toString(barcode.charAt(11)));
    }

    private boolean validateISBN10(String barcode) {
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += Integer.parseInt(Character.toString(barcode.charAt(i))) * (i + 1);
        }
        int checkDigit = sum % 11;

        return checkDigit == Integer.parseInt(Character.toString(barcode.charAt(barcode.length() - 1)));
    }

    private boolean validateEan13(String barcode) {
        int sum = 0;

        for (int i = 0; i < 12; i++) {
            if (i % 2 == 0) {
                sum += Integer.parseInt(Character.toString(barcode.charAt(i)));
            } else {
                sum += Integer.parseInt(Character.toString(barcode.charAt(i))) * 3;
            }
        }

        int checkDigit = 10 - sum % 10;
        if (checkDigit == 10) {
            checkDigit = 0;
        }

        return checkDigit == Integer.parseInt(Character.toString(barcode.charAt(barcode.length() - 1)));
    }
}

package com.worldofbooks.autoconfigure.spring.web.validator;

import com.worldofbooks.autoconfigure.spring.web.formatting.fieldannotation.barcode.Barcode;
import com.worldofbooks.autoconfigure.spring.web.formatting.fieldannotation.barcode.Barcode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

public class BarcodeValidatorTest {

    private class BarcodeValidatorTester {

        @Barcode
        private String barcode;

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }
    }

    private Validator validator;

    private final String validEan = "9781338099133";
    private final String validUpc = "025545409088";
    private final String validIsbn = "1338099132";

    @Before
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void shouldValidateWithoutViolationWhenBarcodeIsValidEanOrEanFromUpc12() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();
        tester.setBarcode(validEan);

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(0, violations.size());

        tester.setBarcode("0" + validUpc);
        violations = validator.validate(tester, Default.class);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void shouldValidateWithoutViolationWhenBarcodeIsValidUpc12() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();
        tester.setBarcode(validUpc);

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void shouldValidateWithoutViolationWhenBarcodeIsValidIsbn10() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();

        tester.setBarcode(validIsbn);
        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void shouldReturnOneValidationViolationWhenBarcodeIsInvalidEan() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();
        tester.setBarcode("9781338099132");

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void shouldReturnOneValidationViolationWhenBarcodeIsInvalidUpc12() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();
        tester.setBarcode("025545409087");

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void shouldReturnOneValidationViolationWhenBarcodeIsInvalidIsbn10() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();
        tester.setBarcode("1338099131");

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void shouldReturnValidationAndEmptyErrorWhenBarcodeIsEmpty() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();
        tester.setBarcode("");

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(2, violations.size());
    }

    @Test
    public void shouldReturnValidationAndNullErrorWhenBarcodeIsNull() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(2, violations.size());
    }

    @Test
    public void shouldReturnValidationErrorWhenBarcodeIsNotValid() throws NoSuchFieldException {
        BarcodeValidatorTester tester = new BarcodeValidatorTester();
        tester.setBarcode("test");

        Set<ConstraintViolation<BarcodeValidatorTester>> violations = validator.validate(tester, Default.class);
        Assert.assertEquals(1, violations.size());
    }
}

package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.barcode;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

public class BarcodeFormatter implements Formatter<String> {
    
    @Override
    public String parse(String barcode, Locale locale) throws ParseException {
        if(barcode.matches("[0123456789Xx]+")) {
            if (barcode.length() == 9) {
                barcode = "0" + barcode;
            }
    
            if (barcode.length() == 10) {
                barcode = formatISBN10(barcode);
            } else if (barcode.length() == 12) {
                barcode = formatUPC12(barcode);
            }
        }
        
        return barcode;
    }
    
    
    @Override
    public String print(String barcode, Locale locale) {
        return barcode;
    }
    
    private String formatUPC12(String barcode) {
        return "0" + barcode;
    }
    
    private String formatISBN10(String barcode) {
        String substring = barcode.substring(0, 9);
    
        String ean13 = "978" + substring;
    
        int result = 0;
        for (int i = 0; i < ean13.length(); i++) {
            result += Integer.parseInt(Character.toString(ean13.charAt(i))) * ((i % 2 == 0) ? 1 : 3);
        }
    
        int checkDigit = 10 - (result % 10);
        if (checkDigit == 10) {
            checkDigit = 0;
        }
        ean13 += checkDigit;
    
        return ean13;
    }
}

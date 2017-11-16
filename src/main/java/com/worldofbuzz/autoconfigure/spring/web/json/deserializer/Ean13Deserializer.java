package com.worldofbuzz.autoconfigure.spring.web.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class Ean13Deserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String barcode = parser.getValueAsString();
        if (barcode.matches("[0123456789Xx]+")) {
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

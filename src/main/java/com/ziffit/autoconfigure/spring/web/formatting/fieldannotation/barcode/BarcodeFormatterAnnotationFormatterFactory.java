package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.barcode;

import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import com.google.common.collect.ImmutableSet;

public class BarcodeFormatterAnnotationFormatterFactory implements AnnotationFormatterFactory<BarcodeFormat> {
	@Override
	public Set<Class<?>> getFieldTypes() {
		return ImmutableSet.of(String.class);
	}
	
	@Override
	public Printer<?> getPrinter(BarcodeFormat barcodeFormat, Class<?> aClass) {
		return new BarcodeFormatter();
	}
	
	@Override
	public Parser<?> getParser(BarcodeFormat barcodeFormat, Class<?> aClass) {
		return new BarcodeFormatter();
	}
}

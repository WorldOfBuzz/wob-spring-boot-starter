package com.ziffit.autoconfigure.spring.web.formatting.fieldannotation.barcode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class BarcodeFormatterTest {
	
	@Controller
	private class BarcodeFormatterTestController {
		@RequestMapping("/format")
		public @ResponseBody String formatBarcode(@BarcodeFormat String barcode) {
			return barcode;
		}
	}
	
	private final String validEan = "9781338099133";
	private final String validUpc = "025545409088";
	private final String validIsbn = "1338099132";
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		FormattingConversionService cs = new DefaultFormattingConversionService();
		cs.addFormatterForFieldAnnotation(new BarcodeFormatterAnnotationFormatterFactory());
		
		this.mockMvc = standaloneSetup(new BarcodeFormatterTestController())
				.setConversionService(cs)
				.alwaysExpect(status().isOk())
				.build();
	}
	
	@Test
	public void shouldReturnEanWhenBarcodeIsIsbn() throws Exception {
		mockMvc.perform(get("/format?barcode=" + validIsbn))
				.andExpect(content().string(validEan));
	}
	
	@Test
	public void shouldReturn0PlusUpcWhenBarcodeIsUpc() throws Exception {
		mockMvc.perform(get("/format?barcode=" + validUpc))
				.andExpect(content().string("0" + validUpc));
	}
	
	@Test
	public void shouldReturnValidEanWhenBarcodeIs9CharactersLong() throws Exception {
		mockMvc.perform(get("/format?barcode=338099132"))
				.andExpect(content().string("9780338099136"));
	}
	
	@Test
	public void shouldReturnInputWhenBarcodeIsValidEan() throws Exception {
		mockMvc.perform(get("/format?barcode=" + validEan))
				.andExpect(content().string(validEan));
	}
	
	@Test
	public void shouldReturnInputWhenBarcodeIsNonValidRandomString() throws Exception {
		List<String> testStrings = Arrays.asList("CGet3oD0G", "U9z3ZQR8gE", "w5MnA1l4ObYZ", "fbPZiawSvnKhF", "pLVnUzIQmcFUvKCs");
		
		for (String testString : testStrings) {
			mockMvc.perform(get("/format?barcode=" + testString))
					.andExpect(content().string(testString));
		}
	}
}
package com.infinity.ProductiveIO.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GeneralUtils {
	
	public static String dateGetTodayDBFormatted() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));		
	}

}

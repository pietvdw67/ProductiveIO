package com.infinity.ProductiveIO.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GeneralUtils {
	
	public static String dateGetTodayDBFormatted() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
	public static boolean dateIsToday(String dateDBFormatted) {
		return dateDBFormatted.equals(dateGetTodayDBFormatted());		
	}
	
	public static String dateDBFormatedFromSQLDate(java.sql.Date sqlDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(sqlDate);		
	}
	
	public static String getDayOfWeek(java.sql.Date sqlDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		return sdf.format(sqlDate);		
	}

}

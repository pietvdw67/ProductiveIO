package com.infinity.ProductiveIO.dailyHistory.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DailyHistoryJDBC {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void deleteByDate(String dateDBFormatted){
						
		String sql = "Delete from dailyhistory where countdate = '" + dateDBFormatted + "'";
		jdbcTemplate.execute(sql);
	}

}

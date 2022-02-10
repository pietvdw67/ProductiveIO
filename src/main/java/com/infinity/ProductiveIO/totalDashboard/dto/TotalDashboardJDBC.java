package com.infinity.ProductiveIO.totalDashboard.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.infinity.ProductiveIO.util.GeneralUtils;

@Component
public class TotalDashboardJDBC {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public int lastUploadAmount(long machineId){
		List<Integer> returnValuelist = new ArrayList<>();
				
		String sql = "select countamount from dailydetail where machineid = " + machineId + " and countdate = '" + GeneralUtils.dateGetTodayDBFormatted() + "' order by counttime desc limit 1";
		jdbcTemplate.query(sql, (rs) -> {
			returnValuelist.add(rs.getInt(1));
		});
		
		
		if (returnValuelist.size()==1) {
			return returnValuelist.get(0);
		} else {
			return 0;
		}
	}
	
	
	

}

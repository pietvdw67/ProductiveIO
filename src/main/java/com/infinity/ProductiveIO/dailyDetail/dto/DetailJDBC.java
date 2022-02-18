package com.infinity.ProductiveIO.dailyDetail.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.infinity.ProductiveIO.util.GeneralUtils;

@Component
public class DetailJDBC {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public long getTotalDetailForMachineForDay(long machineId,String detailDateDBFormatted){
		List<Integer> returnValuelist = new ArrayList<>();
		
		if (detailDateDBFormatted.equalsIgnoreCase("today")) {
			detailDateDBFormatted = GeneralUtils.dateGetTodayDBFormatted(); 
		}
				
		String sql = "select sum(countamount) from dailydetail where machineid = " + machineId + " and countdate = '" + detailDateDBFormatted + "'";
		jdbcTemplate.query(sql, (rs) -> {
			returnValuelist.add(rs.getInt(1));
		});
		
		
		if (returnValuelist.size()==1) {
			return returnValuelist.get(0);
		} else {
			return 0;
		}
	}
	
	public Map<Long,Integer> getInactiveMinutesCountByDay(String dateDBFormatted){
		Map<Long,Integer> returnMap = new HashMap<>();
		
		String sql = "select machineid,count(1) from dailydetail where countdate = '" + dateDBFormatted +"' and countamount = 0 group by machineid";
		jdbcTemplate.query(sql, (rs) -> {
			returnMap.put(rs.getLong(1),rs.getInt(2));
		});
		
		return returnMap;		
	}
	
	public Map<Long,Integer> getActiveMinutesCountByDay(String dateDBFormatted){
		Map<Long,Integer> returnMap = new HashMap<>();
		
		String sql = "select machineid,count(1) from dailydetail where countdate = '" + dateDBFormatted +"' and countamount > 0 group by machineid";
		jdbcTemplate.query(sql, (rs) -> {
			returnMap.put(rs.getLong(1),rs.getInt(2));
		});
		
		return returnMap;		
	}
	
	
	
	

}

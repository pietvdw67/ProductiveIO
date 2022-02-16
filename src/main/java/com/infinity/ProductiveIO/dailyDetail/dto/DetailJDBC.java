package com.infinity.ProductiveIO.dailyDetail.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DetailJDBC {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public long getTotalDetailForMachineForDay(long machineId,String detailDateDBFormatted){
		List<Integer> returnValuelist = new ArrayList<>();
				
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

}

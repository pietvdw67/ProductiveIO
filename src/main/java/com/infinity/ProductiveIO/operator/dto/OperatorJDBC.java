package com.infinity.ProductiveIO.operator.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.infinity.ProductiveIO.operator.model.OperatorItem;
import com.infinity.ProductiveIO.operator.repository.OperatorRepository;
import com.infinity.ProductiveIO.util.GeneralUtils;

@Component
public class OperatorJDBC {
	
	@Autowired
	OperatorRepository operatorRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public OperatorItem findOperatorForDay(String dateDBFormatted,long machineId) {
		
		List<Long> operatorForDayList = new ArrayList<>();
		List<OperatorItem> operatorToReturn = new ArrayList<>();
		if (!GeneralUtils.dateIsToday(dateDBFormatted)) {
			String operatorIdForDaySQL = "select operatorid from dailyhistory where countdate ='" + dateDBFormatted + "' and machineid = " + machineId;
			jdbcTemplate.query(operatorIdForDaySQL, (rs) -> {
				operatorForDayList.add(rs.getLong(1));
			});
			
			if (operatorForDayList.size()>0) {
				Optional<OperatorItem> operatorItemOptional = operatorRepository.findById(operatorForDayList.get(0));
				return operatorItemOptional.orElse( new OperatorItem());			}
		} else {
			List<OperatorItem> operators = operatorRepository.findOperatorByMachineId(machineId);
			if (operators.size() > 0) {
				return operators.get(0);
			} else {
				return new OperatorItem();
			}
		}
		
		return new OperatorItem();
	}
	
	

}

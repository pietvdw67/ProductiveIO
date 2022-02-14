package com.infinity.ProductiveIO.operator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.operator.dto.OperatorJDBC;
import com.infinity.ProductiveIO.operator.model.OperatorItem;
import com.infinity.ProductiveIO.operator.repository.OperatorRepository;

@Service
public class OperatorService {
	
	@Autowired
	OperatorRepository operatorRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	OperatorJDBC operatorJDBC;
	
	public void removeMachineIdFromAllOperators(long machineid) {
		String sql = "update operator set machineid = null where machineid = " + machineid;
		jdbcTemplate.execute(sql);
	}
	
	public OperatorItem findOperatorForDay(String dateDBFormatted,long machineId) {
		return operatorJDBC.findOperatorForDay(dateDBFormatted,machineId);
	}

}

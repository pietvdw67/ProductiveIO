package com.infinity.ProductiveIO.machineDetail.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;

@Service
public class MachineDetailService {
	
	@Autowired
	MachineDetailRepository repository;
	
	public Map<Long,MachineDetail> getMachineDetailsMap(){
		List<MachineDetail> machineDetails =  repository.findAll();
		Map<Long,MachineDetail> machineDetailsMap = new HashMap<>();
		
		machineDetails.forEach(machineDetail -> {
			machineDetailsMap.put(machineDetail.getId(), machineDetail);
		});
		
		return machineDetailsMap;
		
	}

}

package com.infinity.ProductiveIO.dailyDetail.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;
import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.model.ItemDetailView;
import com.infinity.ProductiveIO.operator.model.OperatorItem;
import com.infinity.ProductiveIO.operator.service.OperatorService;
import com.infinity.ProductiveIO.util.GeneralUtils;

@Service
public class DetailService {
	
	@Autowired
	MachineDetailRepository machineDetailRepository;
	
	@Autowired
	OperatorService operatorService;
	
	public List<ItemDetailView> ItemDetailToItemDetailView(List<ItemDetail> itemDetails){
		List<ItemDetailView> returnList = new ArrayList<>();
		
		Map<Long,String> machineNames = getMachineNameMap(machineDetailRepository.findAll());
		
		for (int i = 0; i< itemDetails.size();i++) {
			ItemDetailView itemDetailView = new ItemDetailView();
			itemDetailView.setId(itemDetails.get(i).getId());
			itemDetailView.setMachineid(itemDetails.get(i).getMachineid());
			itemDetailView.setCountdate(itemDetails.get(i).getCountdate());
			itemDetailView.setCounttime(itemDetails.get(i).getCounttime());
			itemDetailView.setCountamount(itemDetails.get(i).getCountamount());
			
			OperatorItem operator = operatorService.findOperatorForDay(GeneralUtils.dateDBFormatedFromSQLDate(itemDetails.get(i).getCountdate()),itemDetails.get(i).getMachineid());
			if (Objects.nonNull(operator.getOperatorname()) && operator.getOperatorname().length()>0) {
				itemDetailView.setOperatorName(operator.getOperatorname());
			} else {
				itemDetailView.setOperatorName("Not set");
			}
			
			if (machineNames.containsKey(Long.parseLong(String.valueOf(itemDetails.get(i).getMachineid())))) {
				itemDetailView.setMachinename(machineNames.get(Long.parseLong(String.valueOf(itemDetails.get(i).getMachineid()))));
			}
			
			returnList.add(itemDetailView);
		}		
		
		return returnList;
		
	}
	
	
	
	private Map<Long,String> getMachineNameMap(List<MachineDetail> machineDetails){
		Map<Long,String> machineNames = new HashMap<>();
		
		machineDetails.forEach( machineDetailItem -> {
			if (!machineNames.containsKey(machineDetailItem.getId())) {
				machineNames.put(machineDetailItem.getId(), machineDetailItem.getName());
			}
		});
		
		return machineNames;
	}

}

package com.infinity.ProductiveIO.dailyHistory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;
import com.infinity.ProductiveIO.dailyHistory.model.HistoryItemView;
import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;

@Service
public class HistoryService {
	
	@Autowired
	MachineDetailRepository machineDetailRepository;
	
	public List<HistoryItemView> historyItemToHistoryItemView(List<HistoryItem> historyItems){
		List<HistoryItemView> returnList = new ArrayList<>();

		Map<Long,String> machineNames = getMachineNameMap(machineDetailRepository.findAll());
		
		for (int i = 0; i< historyItems.size();i++) {
			HistoryItemView historyItemView = new HistoryItemView();
			historyItemView.setId(historyItems.get(i).getId());
			historyItemView.setMachineid(historyItems.get(i).getMachineid());		
			historyItemView.setCountdate(historyItems.get(i).getCountdate());
			historyItemView.setCountamount(historyItems.get(i).getCountamount());
			
			if (machineNames.containsKey(Long.parseLong(String.valueOf(historyItems.get(i).getMachineid())))) {
				historyItemView.setMachinename(machineNames.get(Long.parseLong(String.valueOf(historyItems.get(i).getMachineid()))));
			}
			
			returnList.add(historyItemView);
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

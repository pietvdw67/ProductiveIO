package com.infinity.ProductiveIO.dailyHistory.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;
import com.infinity.ProductiveIO.dailyHistory.model.HistoryItemView;
import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.machineDetail.repository.MachineDetailRepository;
import com.infinity.ProductiveIO.operator.model.OperatorItem;
import com.infinity.ProductiveIO.operator.repository.OperatorRepository;



@Service
public class HistoryService {
	
	Logger logger = Logger.getLogger(HistoryService.class.toString());
	
	@Autowired
	MachineDetailRepository machineDetailRepository;
	
	@Autowired
	OperatorRepository operatorRepostitory;
	
	public List<HistoryItemView> historyItemToHistoryItemView(List<HistoryItem> historyItems){
		List<HistoryItemView> returnList = new ArrayList<>();

		Map<Long,String> machineNames = getMachineNameMap(machineDetailRepository.findAll());
		List<OperatorItem> operators = operatorRepostitory.findAll(); 
		
		for (int i = 0; i< historyItems.size();i++) {
			HistoryItemView historyItemView = new HistoryItemView();
			historyItemView.setId(historyItems.get(i).getId());
			historyItemView.setMachineid(historyItems.get(i).getMachineid());		
			historyItemView.setCountdate(historyItems.get(i).getCountdate());
			historyItemView.setCountamount(historyItems.get(i).getCountamount());
			
			logger.info("************ -> " + historyItems.get(i).toString());
			
			if (machineNames.containsKey(Long.parseLong(String.valueOf(historyItems.get(i).getMachineid())))) {
				historyItemView.setMachinename(machineNames.get(Long.parseLong(String.valueOf(historyItems.get(i).getMachineid()))));
			}
			
			if (Objects.nonNull(historyItems.get(i).getOperatorid())) {
				Optional<OperatorItem> operatorForMachine = operatorRepostitory.findById(historyItems.get(i).getOperatorid());
				
				if (operatorForMachine.isPresent()) {
					historyItemView.setOperatorname(operatorForMachine.get().getOperatorname());
				} else {
					historyItemView.setOperatorname("Not Set");
				}
			} else {
				historyItemView.setOperatorname("Not Set");
			}
			
			 
			
//			for (int operatorIndex =0; operatorIndex < operators.size();operatorIndex++ ) {
//				
//				if (operators.get(operatorIndex).getId() == historyItems.get(i).getOperatorid()) {
//					historyItemView.setOperatorname(operators.get(operatorIndex).getOperatorname());
//				} else {
//					historyItemView.setOperatorname("Not Set");
//				}
//			}
			
			
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

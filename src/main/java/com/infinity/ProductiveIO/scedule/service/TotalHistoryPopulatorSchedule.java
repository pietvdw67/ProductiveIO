package com.infinity.ProductiveIO.scedule.service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;
import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.operator.model.OperatorItem;
import com.infinity.ProductiveIO.scedule.model.RepositoryInstance;

public class TotalHistoryPopulatorSchedule implements Runnable {
	
	Logger logger = Logger.getLogger(TotalHistoryPopulatorSchedule.class.toString());

	@Override
	public void run() {
		logger.info(" *** Running TotalHistoryPopulatorSchedule ***");

		LocalDate yesterday = LocalDate.now().minusDays(1);;
		java.sql.Date yesterdayFormatted = new java.sql.Date(yesterday.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		List<ItemDetail> itemDetails = RepositoryInstance.getInstance().getDailyDetailRepository().findTotalPerDay(yesterdayFormatted);
		
		// Delete previous items
		List<HistoryItem> yesterdayItems = RepositoryInstance.getInstance().getDailyHistoryRepository().findByDate(yesterdayFormatted);
		yesterdayItems.forEach(historyItem -> RepositoryInstance.getInstance().getDailyHistoryRepository().delete(historyItem));
		
		// Operators
		List<OperatorItem> operatorsList = RepositoryInstance.getInstance().getOperatorRepository().findAll();
		
		// Insert new items
		itemDetails.forEach(itemDetail -> {
			HistoryItem historyItem = new HistoryItem();
			historyItem.setMachineid(itemDetail.getMachineid());
			historyItem.setCountdate(itemDetail.getCountdate());
			historyItem.setCountamount(itemDetail.getCountamount());
			
			Optional<OperatorItem> operatorOptional = operatorsList.stream()
					.filter(operatorItem -> Objects.nonNull(operatorItem.getMachineid()))
					.filter(operatorItem -> operatorItem.getMachineid() == itemDetail.getMachineid())
					.findFirst();
			if (operatorOptional.isPresent()) {
				historyItem.setOperatorid(operatorOptional.get().getId());
			}
			
			// update note
			Optional<MachineDetail> machineDetailOptional = RepositoryInstance.getInstance().getMachineDetailRepository().findById(Long.parseLong(String.valueOf(itemDetail.getMachineid())));
			if (machineDetailOptional.isPresent()) {
				if (Objects.nonNull(machineDetailOptional.get().getNote()) && machineDetailOptional.get().getNote().length() > 0) {
					historyItem.setNote(machineDetailOptional.get().getNote());
					
					machineDetailOptional.get().setNote("");
					RepositoryInstance.getInstance().getMachineDetailRepository().save(machineDetailOptional.get());
				}
			}
			
			RepositoryInstance.getInstance().getDailyHistoryRepository().save(historyItem);			
		});
		
		// Get averages
		Map<Long,Integer> averages = new HashMap<>();
		RepositoryInstance.getInstance().getJdbcTemplate().query("select machineid,avg(countamount) as average from dailydetail where countdate = '" + yesterdayFormatted +"' and countamount > 0 group by machineid", (rs) -> {			
			averages.put(rs.getLong("machineid"),(int)rs.getDouble("average"));
		});
		
		
		// populate averages into machine details
		List<MachineDetail> machineDetails = RepositoryInstance.getInstance().getMachineDetailRepository().findAll();
		machineDetails.forEach(machineDetail -> {
			if (averages.containsKey(machineDetail.getId())){
				machineDetail.setAverageval(averages.get(machineDetail.getId()));
				
				RepositoryInstance.getInstance().getMachineDetailRepository().save(machineDetail);
			} 				
				
		});

		
		
		
		


		
	}

}

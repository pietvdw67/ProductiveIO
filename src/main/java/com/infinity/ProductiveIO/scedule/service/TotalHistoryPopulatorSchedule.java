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
import com.infinity.ProductiveIO.util.GeneralUtils;

public class TotalHistoryPopulatorSchedule implements Runnable {
	
	Logger logger = Logger.getLogger(TotalHistoryPopulatorSchedule.class.toString());

	@Override
	public void run() {
		logger.info(" *** Running TotalHistoryPopulatorSchedule ***");

		LocalDate yesterday = LocalDate.now().minusDays(1);;
		java.sql.Date yesterdayFormatted = new java.sql.Date(yesterday.atStartOfDay().toInstant(ZoneOffset.ofHours(2)).toEpochMilli());
		String yesterdayDBFormatted = GeneralUtils.dateDBFormatedFromSQLDate(yesterdayFormatted);
		int workminutes = getWorkMinutes(yesterdayFormatted);
		
		List<ItemDetail> itemDetails = RepositoryInstance.getInstance().getDailyDetailRepository().findTotalPerDay(yesterdayFormatted);
		
		// Delete previous items
		RepositoryInstance.getInstance().getDailyHistoryJdbc().deleteByDate(yesterdayDBFormatted);
		
		// Operators
		List<OperatorItem> operatorsList = RepositoryInstance.getInstance().getOperatorRepository().findAll();
		
		// Get averages
		Map<Long,Integer> averages = new HashMap<>();
		RepositoryInstance.getInstance().getJdbcTemplate().query("select machineid,avg(countamount) as average from dailydetail where countdate = '" + yesterdayFormatted +"' and countamount > 0 group by machineid", (rs) -> {			
			averages.put(rs.getLong("machineid"),(int)rs.getDouble("average"));
		});
		
		// MachineDetails
		Map<Long,MachineDetail> machineDetailsMap = new HashMap<>();
		List<MachineDetail> machineDetailsList = RepositoryInstance.getInstance().getMachineDetailRepository().findAll();
		machineDetailsList.forEach(machineDetail -> machineDetailsMap.put(machineDetail.getId(), machineDetail));
		
		// Get inactiveMinutes
		Map<Long,Integer> inactiveMinutesCountMap = RepositoryInstance.getInstance().getDetailJDBC().getInactiveMinutesCountByDay(yesterdayDBFormatted);
		Map<Long,Integer> inactiveMinutestMap = new HashMap<>();
		for (long key: inactiveMinutesCountMap.keySet() ) {
			if (machineDetailsMap.containsKey(key)){
				MachineDetail machineDetail = machineDetailsMap.get(key);
				if (Objects.nonNull(machineDetail.getUploadmin())) {
					inactiveMinutestMap.put(key, machineDetail.getUploadmin() * inactiveMinutesCountMap.get(key));
				}
			}
		}
		
		// Get activeMinutes
		Map<Long,Integer> activeMinutesCountMap = RepositoryInstance.getInstance().getDetailJDBC().getActiveMinutesCountByDay(yesterdayDBFormatted);
		Map<Long,Integer> activeMinutesMap = new HashMap<>();
		for (long key: activeMinutesCountMap.keySet() ) {
			if (machineDetailsMap.containsKey(key)){
				MachineDetail machineDetail = machineDetailsMap.get(key);
				if (Objects.nonNull(machineDetail.getUploadmin())) {
					activeMinutesMap.put(key, machineDetail.getUploadmin() * activeMinutesCountMap.get(key));
				}
			}
		}
		
		// get OfflineMinutes
		Map<Long,Integer> offlineMinutesMap = new HashMap<>();
		int inactiveMinutes = 0;
		int activeMinutes = 0;
		for (long key: machineDetailsMap.keySet()) {
			if (inactiveMinutestMap.containsKey(key) && Objects.nonNull(inactiveMinutestMap.get(key))) {
				inactiveMinutes = inactiveMinutestMap.get(key);
			}
			if (activeMinutesMap.containsKey(key) && Objects.nonNull(activeMinutesMap.get(key))) {
				activeMinutes = activeMinutesMap.get(key);
			}
			
			if (workminutes > 0) {
				offlineMinutesMap.put(key, workminutes - inactiveMinutes - activeMinutes);
			} else {
				offlineMinutesMap.put(key,0);
			}
		}
		
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
			
			// average
			if (averages.containsKey(Long.parseLong(String.valueOf(itemDetail.getMachineid())))) {
				historyItem.setAverage(averages.get(Long.parseLong(String.valueOf(itemDetail.getMachineid()))));				
			} else {
				historyItem.setAverage(0);
			}
			
			// inactive minutes
			if (inactiveMinutestMap.containsKey(Long.parseLong(String.valueOf(itemDetail.getMachineid())))) {
				historyItem.setInactive(inactiveMinutestMap.get(Long.parseLong(String.valueOf(itemDetail.getMachineid()))));				
			} else {
				historyItem.setInactive(0);
			}
			
			// Offline minutes
			if (offlineMinutesMap.containsKey(Long.parseLong(String.valueOf(itemDetail.getMachineid())))) {
				historyItem.setOffline(offlineMinutesMap.get(Long.parseLong(String.valueOf(itemDetail.getMachineid()))));				
			} else {
				historyItem.setInactive(0);
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

		
		// populate averages into machine details
		List<MachineDetail> machineDetails = RepositoryInstance.getInstance().getMachineDetailRepository().findAll();
		machineDetails.forEach(machineDetail -> {
			if (averages.containsKey(machineDetail.getId())){
				machineDetail.setAverageval(averages.get(machineDetail.getId()));
				
				RepositoryInstance.getInstance().getMachineDetailRepository().save(machineDetail);
			} 				
				
		});		
	}
	
	private int getWorkMinutes(java.sql.Date dateRequired) {
		
		String dayOfWeek = GeneralUtils.getDayOfWeek(dateRequired);
		String hoursProperty = "";
		
		switch (dayOfWeek.toLowerCase()) {
		case "mon":
			hoursProperty = "hoursMonday";
			break;
		case "tue":
			hoursProperty = "hoursTuesday";
			break;
		case "wed":
			hoursProperty = "hoursWednesday";
			break;
		case "thu":
			hoursProperty = "hoursThursday";
			break;
		case "fri":
			hoursProperty = "hoursFriday";
			break;
		case "sat":
			hoursProperty = "hoursSaturday";
			break;
		case "sun":
			hoursProperty = "hoursSunday";
			break;
		}		
		String workHours = RepositoryInstance.getInstance().getSettingsService().getSetting(hoursProperty);
		Double workMinutes = Double.parseDouble(workHours) * 60;
		
		return (int) Math.round(workMinutes);
	}

}

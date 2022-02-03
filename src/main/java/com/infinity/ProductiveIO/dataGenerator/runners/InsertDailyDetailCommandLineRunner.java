package com.infinity.ProductiveIO.dataGenerator.runners;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;
import com.infinity.ProductiveIO.dailyHistory.repository.DailyHistoryRepository;
import com.infinity.ProductiveIO.model.ItemDetail;

@Component
public class InsertDailyDetailCommandLineRunner implements CommandLineRunner {
	
	Logger logger = Logger.getLogger(InsertDailyDetailCommandLineRunner.class.toString());
		
	@Autowired
	DailyDetailRepository repository;
	
	@Autowired
	DailyHistoryRepository dailyHistoryRepository;
	
	private static final boolean DO_RUN = false;
	
	private static final String DATA_START_TIME = "17/01/2022 06:00";
	private static final String DATA_END_TIME = "17/01/2022 18:00";
	private static final int MACHINE_ID_COUNT = 5;
	private static final int INCREMENT_MINUTE = 5;
	
	private Map<Integer,Integer> dailyTotalMap;
	
	@Override
	public void run(String... args) throws Exception {
		if (!DO_RUN) {
			return;
		}
		
		logger.info("*** Running InsertDailyDetailCommandLineRunner ***");
		
		Random random = new Random();
		dailyTotalMap = new HashMap<>();
		LocalDateTime time = LocalDateTime.parse(DATA_START_TIME, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		LocalDateTime endTime = LocalDateTime.parse(DATA_END_TIME, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		
		// Building and saving incremental data
		do {
			time = time.plusMinutes(INCREMENT_MINUTE);
			
			for ( int rep = 0; rep < MACHINE_ID_COUNT; rep++) {
				if (!dailyTotalMap.containsKey(rep+1)) {
					dailyTotalMap.put(rep+1,0);
				}
				ItemDetail itemDetail = new ItemDetail();
				itemDetail.setMachineid(rep+1);
				itemDetail.setCountamount(random.nextInt(100));
				itemDetail.setCountdate(new java.sql.Date(time.toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
				itemDetail.setCounttime(new java.sql.Time(time.toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
				
				dailyTotalMap.put(rep+1, dailyTotalMap.get(rep+1) + itemDetail.getCountamount());
				
				repository.save(itemDetail);
			}

			
		} while (time.isBefore(endTime));
		
		// Saving daily data
		if (time.isBefore(LocalDate.now().atStartOfDay())) {
			for (Integer machineId : dailyTotalMap.keySet()) {
				HistoryItem historyItem = new HistoryItem();
				historyItem.setCountamount(dailyTotalMap.get(machineId));
				historyItem.setCountdate(new java.sql.Date(time.toInstant(ZoneOffset.ofHours(2)).toEpochMilli()));
				historyItem.setMachineid(machineId);
				
				dailyHistoryRepository.save(historyItem);
			}
		}
		
		
		
		logger.info("*** Done with InterimDataInsertCommandLineRunner ***");
	}
}
